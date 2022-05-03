package org.jiangys.tool.idea.services

import org.jiangys.tool.idea.ToolBoxWindow
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import javax.swing.JComboBox


class TimeService(private val mainWindow: ToolBoxWindow) : TabService {

    private val scheduleExecutor = Executors.newScheduledThreadPool(10)
    private var curFuture: ScheduledFuture<*>? = null
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val defaultSelectZone = "UTC${ZonedDateTime.now().offset.id}"

    private val showTimeAreaFormatters = listOf<DateTimeFormatter>(
        dateTimeFormatter,
        DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss", Locale.ENGLISH),
        DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss", Locale.ENGLISH),
        DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        DateTimeFormatter.ISO_OFFSET_DATE_TIME
    )

    init {
        val now = LocalDateTime.now()
        val zones = ZoneId.getAvailableZoneIds().stream().map { ZoneId.of(it) }
            .sorted(ZoneComparator())
            .map { "UTC${now.atZone(it).offset.id}" }
            .collect(Collectors.toSet())
        zones.forEach {
            mainWindow.timeToTimestampZoneCcomboBox.addItem(it)
            mainWindow.timeShowZoneComboBox.addItem(it)
        }
        mainWindow.timeToTimestampZoneCcomboBox.selectedItem = defaultSelectZone
        mainWindow.timeShowZoneComboBox.selectedItem = defaultSelectZone

        //actions
        mainWindow.copyButton.addActionListener {
            val stringSelection = StringSelection(mainWindow.currentTimeTextField.text)
            val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
            clipboard.setContents(stringSelection, null)
        }

        mainWindow.toTimeTransformButton1.addActionListener{
            transformToTime(getTimeInMills(mainWindow.currentTimeUnitCombox,mainWindow.currentTimeTextField.text))
        }
    }

    override fun initActive() {
        val timestamp = System.currentTimeMillis()
        mainWindow.currentTimeTextField.text = getTimestampString(mainWindow.currentTimeUnitCombox, timestamp)
        mainWindow.inputTimestampTextField.text = getTimestampString(mainWindow.timestampUnitComboBox, timestamp)
        mainWindow.timeToTimestampTimeTextField.text = LocalDateTime.now().format(dateTimeFormatter)
        startUpdate()
    }

    override fun deActive() {
        stopUpdate()
    }

    override fun tabName(): String {
        return "Time"
    }

    private fun startUpdate() {
        stopUpdate()
        curFuture = scheduleExecutor.scheduleAtFixedRate(TimeTabTimeUpdateTask(mainWindow), 0, 1, TimeUnit.SECONDS)
    }

    private fun stopUpdate() {
        curFuture?.cancel(false)
    }

    private fun getTimestampString(comboBox: JComboBox<*>, time: Long): String {
        return if ("s" == comboBox.selectedItem?.toString()) (time / 1000).toString() else time.toString()
    }

    private fun getTimeInMills(comboBox: JComboBox<*>, time: String):Long{
        return if ("s" == comboBox.selectedItem?.toString()) (time.toLong() * 1000) else time.toLong()
    }

    private class ZoneComparator : Comparator<ZoneId?> {
        override fun compare(zoneId1: ZoneId?, zoneId2: ZoneId?): Int {
            val now = LocalDateTime.now()
            val offset1 = now.atZone(zoneId1).offset
            val offset2 = now.atZone(zoneId2).offset
            return offset1.compareTo(offset2)
        }
    }

    private fun transformToTime(timestamp: Long) {
        val selectedZone = ZoneId.of(mainWindow.timeShowZoneComboBox.selectedItem?.toString())
        val instant = Instant.ofEpochMilli(timestamp)
        val localDateTime = LocalDateTime.ofInstant(instant, selectedZone)
        val zonedDateTime = ZonedDateTime.of(localDateTime, selectedZone)
        mainWindow.showTimeTextField1.text = showTimeAreaFormatters[0].format(localDateTime)
        mainWindow.showTimeTextField2.text = showTimeAreaFormatters[1].format(zonedDateTime)
        mainWindow.showTimeTextField3.text = showTimeAreaFormatters[2].format(zonedDateTime)
        mainWindow.showTimeTextField4.text = showTimeAreaFormatters[3].format(localDateTime)
        mainWindow.showTimeTextField5.text = showTimeAreaFormatters[4].format(zonedDateTime)
    }

}
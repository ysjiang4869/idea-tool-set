package org.jiangys.tool.idea.services

import org.jiangys.tool.idea.ToolBoxWindow
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.time.*
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
    private var showTimestamp: Long = System.currentTimeMillis()

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
            .map { val zone = now.atZone(it).offset.id; if (zone == "Z") "UTC" else "UTC$zone" }
            .collect(Collectors.toSet()).sortedBy { now.atZone(ZoneId.of(it)).offset }
        zones.forEach {
            mainWindow.timeToTimestampZoneCcomboBox.addItem(it)
            mainWindow.timeShowZoneComboBox.addItem(it)
        }
        mainWindow.timeToTimestampZoneCcomboBox.selectedItem = defaultSelectZone
        mainWindow.timeShowZoneComboBox.selectedItem = defaultSelectZone

        //actions
        mainWindow.copyButton.addActionListener {
            val stringSelection = StringSelection(mainWindow.currentTimeTextField.text)
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            clipboard.setContents(stringSelection, null)
        }

        mainWindow.toTimeTransformButton1.addActionListener {
            showTimestamp = getTimeInMills(mainWindow.currentTimeUnitCombox, mainWindow.currentTimeTextField.text)
            transformToTime(showTimestamp)
        }
        mainWindow.toTimeTransformButton2.addActionListener {
            showTimestamp = getTimeInMills(mainWindow.timestampUnitComboBox, mainWindow.inputTimestampTextField.text)
            transformToTime(showTimestamp)
        }

        mainWindow.timeShowZoneComboBox.addActionListener { transformToTime(showTimestamp) }

        mainWindow.transformButton2.addActionListener { transformTimeToTimestamp() }
    }

    override fun initActive() {
        val timestamp = System.currentTimeMillis()
        mainWindow.currentTimeTextField.text = getTimestampString(mainWindow.currentTimeUnitCombox, timestamp)
        mainWindow.inputTimestampTextField.text = getTimestampString(mainWindow.timestampUnitComboBox, timestamp)
        mainWindow.timeToTimestampTimeTextField.text = LocalDateTime.now().format(dateTimeFormatter)
        showTimestamp = System.currentTimeMillis()
        transformToTime(showTimestamp)
        transformTimeToTimestamp()
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

    private fun getTimeInMills(comboBox: JComboBox<*>, time: String): Long {
        return if ("s" == comboBox.selectedItem?.toString()) (time.toLong() * 1000) else time.toLong()
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

    private fun transformTimeToTimestamp() {
        try {
            val instant =
                LocalDateTime.parse(mainWindow.timeToTimestampTimeTextField.text, dateTimeFormatter).toInstant(
                        ZoneId.of(mainWindow.timeToTimestampZoneCcomboBox.selectedItem?.toString()).rules.getOffset(
                            LocalDateTime.now()
                        )
                    ).toEpochMilli()
            mainWindow.timeToTimestampResultTextField.text =
                if ("s" == mainWindow.timeToTimestampUnitComboBox.selectedItem?.toString()) (instant / 1000).toString() else instant.toString()
        } catch (e: Exception) {
            mainWindow.timeToTimestampResultTextField.text = "time format error"
        }
    }

}
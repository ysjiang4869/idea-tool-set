package org.jiangys.tool.idea.services

import org.jiangys.tool.idea.ToolBoxWindow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.swing.JComboBox

class TimeService(private val mainWindow: ToolBoxWindow):TabService {

    private val scheduleExecutor = Executors.newScheduledThreadPool(10)
    private var curFuture: ScheduledFuture<*>? = null
    private val dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    override fun initActive(){
        val timestamp=System.currentTimeMillis()
        mainWindow.currentTimeTextField.text=getTimestampString(mainWindow.currentTimeUnitCombox,timestamp)
        mainWindow.inputTimestampTextField.text=getTimestampString(mainWindow.timestampUnitComboBox,timestamp)
        mainWindow.timeToTimestampTimeTextField.text=LocalDateTime.now().format(dateTimeFormatter)
        startUpdate()
    }

    override fun deActive(){
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

    private fun getTimestampString(comboBox:JComboBox<*>, time:Long):String{
        return if ("s"==comboBox.selectedItem?.toString()) (time/1000).toString() else time.toString()
    }

}
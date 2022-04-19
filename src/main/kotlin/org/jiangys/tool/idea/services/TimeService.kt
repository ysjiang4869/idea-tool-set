package org.jiangys.tool.idea.services

import org.jiangys.tool.idea.ToolBoxWindow
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class TimeService(private val mainWindow: ToolBoxWindow) {

    private val scheduleExecutor = Executors.newScheduledThreadPool(10)
    private var curFuture: ScheduledFuture<*>? = null

    fun startUpdate() {
        stopUpdate()
        curFuture = scheduleExecutor.scheduleAtFixedRate(TimeTabTimeUpdateTask(mainWindow), 0, 1, TimeUnit.SECONDS)
    }

    fun stopUpdate() {
        curFuture?.cancel(false)
    }


}
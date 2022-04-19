package org.jiangys.tool.idea.services

import org.jiangys.tool.idea.ToolBoxWindow
import javax.swing.JTabbedPane

class TabSelectedService(private val mainWindow: ToolBoxWindow) {

    private val pane: JTabbedPane = mainWindow.jsonTabbedPane;
    private val timerService = TimeService(mainWindow)
    fun init() {
        pane.addChangeListener { e ->
            val sourceTabbedPane = e.source as JTabbedPane
            val index = sourceTabbedPane.selectedIndex
            handleTabChanged(index)
        }
        val index = pane.selectedIndex
        handleTabChanged(index)

    }

    private fun handleTabChanged(index: Int) {
        when (index) {
            pane.indexOfTab("Json") -> timerService.stopUpdate()
            pane.indexOfTab("Cron") -> timerService.stopUpdate()
            pane.indexOfTab("Time") -> timerService.startUpdate()
        }
    }
}
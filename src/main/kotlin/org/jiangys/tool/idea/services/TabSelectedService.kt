package org.jiangys.tool.idea.services

import org.jiangys.tool.idea.ToolBoxWindow
import javax.swing.JTabbedPane

class TabSelectedService(mainWindow: ToolBoxWindow) {

    private val pane: JTabbedPane = mainWindow.jsonTabbedPane;
    private val tabs:List<TabService> = listOf(mainWindow.timeService)
    init{
        pane.addChangeListener { e ->
            val sourceTabbedPane = e.source as JTabbedPane
            val index = sourceTabbedPane.selectedIndex
            handleTabChanged(index)
        }
        val index = pane.selectedIndex
        handleTabChanged(index)
    }

    private fun handleTabChanged(index: Int) {
        tabs.forEach { if(pane.indexOfTab(it.tabName())==index) it.initActive() else it.deActive() }
    }
}
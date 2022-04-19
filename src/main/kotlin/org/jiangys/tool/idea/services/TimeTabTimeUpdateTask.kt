package org.jiangys.tool.idea.services

import org.jiangys.tool.idea.ToolBoxWindow
import javax.swing.SwingUtilities

class TimeTabTimeUpdateTask(private val mainWindow: ToolBoxWindow) : Runnable {

    override fun run() {
        SwingUtilities.invokeLater {
            val divide = if (mainWindow.currentTimeUnitCombox.selectedItem?.toString() == "s") 1000 else 1
            mainWindow.currentTimeTextField.text = (System.currentTimeMillis() / divide).toString()
        }
    }
}
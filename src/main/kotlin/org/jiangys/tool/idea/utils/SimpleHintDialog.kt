package org.jiangys.tool.idea.utils

import com.intellij.openapi.ui.DialogWrapper
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel


class SimpleHintDialog(private val errorMsg:String) : DialogWrapper(true) {

    init {
        title="ToolBoxJ Error"
        init()
    }

    override fun createCenterPanel(): JComponent {
        val dialogPanel = JPanel(BorderLayout())

        val label = JLabel(errorMsg)
        label.preferredSize = Dimension(100, 100)
        dialogPanel.add(label, BorderLayout.CENTER)

        return dialogPanel
    }
}
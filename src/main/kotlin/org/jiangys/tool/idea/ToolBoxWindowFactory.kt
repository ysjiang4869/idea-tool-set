package org.jiangys.tool.idea

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class ToolBoxWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myWindow = ToolBoxWindow(toolWindow)
        val content = contentFactory.createContent(myWindow.rootPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }

    private val contentFactory = ContentFactory.SERVICE.getInstance()

//    override fun shouldBeAvailable(project: Project) = true

}

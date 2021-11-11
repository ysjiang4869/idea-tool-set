package org.jiangys.tool.idea.services

import org.jiangys.tool.idea.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}

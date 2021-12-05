package org.jiangys.tool.idea.services

import com.intellij.openapi.project.Project
import org.jiangys.tool.idea.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}

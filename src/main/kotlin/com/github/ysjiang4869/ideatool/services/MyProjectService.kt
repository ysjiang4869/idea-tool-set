package com.github.ysjiang4869.ideatool.services

import com.github.ysjiang4869.ideatool.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}

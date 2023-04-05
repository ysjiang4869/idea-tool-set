package org.jiangys.tool.idea.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiManager
import org.jiangys.tool.idea.ToolBoxWindow
import org.jiangys.tool.idea.utils.SimpleHintDialog
import java.awt.Button
import java.awt.event.ActionEvent
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream
import javax.swing.JButton


class JdkDeserializeService (private val mainWindow: ToolBoxWindow) : TabService{


    fun tmp(event:ActionEvent,c: JButton){
        val dataManager = DataManager.getInstance();
        val dataContext = dataManager.getDataContext(c)
        val project = LangDataKeys.PROJECT.getData(dataContext)
        if(project==null){
            SimpleHintDialog("Input [${mainWindow.addSubDayNumberText.text}] is not a number").showAndGet()
            return
        }
        val vfile = FileEditorManager.getInstance(project)
            .selectedEditor?.file
        if(vfile==null){
            SimpleHintDialog("Input [${mainWindow.addSubDayNumberText.text}] is not a number").showAndGet()
            return
        }
        val psiFile=PsiManager.getInstance(project).findFile(vfile)
        if (psiFile is PsiJavaFile) {
            val javaFile: PsiJavaFile =
                psiFile as PsiJavaFile
            val classes: Array<PsiClass> = javaFile.getClasses()
            if (classes.isNotEmpty()) {
                val className = classes[0].getName()
                println("Current class: $className")
            }
        }
    }


    fun <T : Any> deserialize(serializedString: String, clazz: Class<T>): T? {
        val byteInputStream = ByteArrayInputStream(serializedString.toByteArray())
        val objectInputStream = ObjectInputStream(byteInputStream)
        return clazz.cast(objectInputStream.readObject())
    }

    fun <T : Any> serialize(obj: T): String {
        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(obj)
    }

    fun <T : Any> deserializeToJson(serializedString: String, clazz: Class<T>): String? {
        val byteInputStream = ByteArrayInputStream(serializedString.toByteArray())
        val objectInputStream = ObjectInputStream(byteInputStream)
        return serialize(clazz.cast(objectInputStream.readObject()))
    }

    override fun initActive() {
        TODO("Not yet implemented")
    }

    override fun deActive() {
        TODO("Not yet implemented")
    }

    override fun tabName(): String {
        TODO("Not yet implemented")
    }
}
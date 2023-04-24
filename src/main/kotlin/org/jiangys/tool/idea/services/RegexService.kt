package org.jiangys.tool.idea.services

import com.intellij.openapi.observable.util.whenTextChanged
import kotlinx.coroutines.*
import org.jiangys.tool.idea.ToolBoxWindow
import java.awt.Cursor
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.regex.PatternSyntaxException
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.text.Document
import javax.swing.text.PlainDocument


/**
 * todo 1 regex grammars
 */

class RegexService(private val mainWindow: ToolBoxWindow) : TabService {


    private val scope= CoroutineScope(Dispatchers.Default)

    init {
        val labels= mapOf(Pair(mainWindow.regexDateLabel,"\\d{4}-\\d{2}-\\d{2}"),
            Pair(mainWindow.regexEmailLabel,"\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}"),
            Pair(mainWindow.regexIpv4Label,"\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}"),
            Pair(mainWindow.regexPhoneNumberLabel,"(13\\d|14[579]|15[^4\\D]|17[^49\\D]|18\\d)\\d{8}"),
            Pair(mainWindow.regexIdCardLabel,"\\d{17}[0-9Xx]|\\d{15}"))
        labels.forEach { item->
            run {
                item.key.text = "<HTML><U>${item.key.text}</U></HTML>"
                item.key.cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                item.key.addMouseListener(object : MouseAdapter() {
                    override fun mouseClicked(e: MouseEvent) {
                        // 在这里编写鼠标点击事件的处理逻辑
                        mainWindow.regexInputTextField.text= item.value
                    }
                })
            }
        }
        mainWindow.matchAllCheckBox.isSelected=true
        mainWindow.multiLineCheckBox.isSelected=true

        mainWindow.regexInputTextField.whenTextChanged {testRegex() }
        mainWindow.testCasesTextArea.whenTextChanged {testRegex()}
        mainWindow.matchAllCheckBox.addActionListener { testRegex() }
        mainWindow.ignoreCaseCheckBox.addActionListener { testRegex() }
        mainWindow.multiLineCheckBox.addActionListener { testRegex() }
        mainWindow.dotMatchAllCheckBox.addActionListener { testRegex() }

    }

    private  fun testRegex(){
        scope.launch {
            val regexString=mainWindow.regexInputTextField.document.getText(0, mainWindow.regexInputTextField.document.length)
            if (regexString.isBlank()){
                return@launch
            }
            val optionsSet= mutableSetOf<RegexOption>()

            if (mainWindow.ignoreCaseCheckBox.isSelected){
                optionsSet.add(RegexOption.IGNORE_CASE)
            }
            if (mainWindow.multiLineCheckBox.isSelected){
                optionsSet.add(RegexOption.MULTILINE)
            }
            if (mainWindow.dotMatchAllCheckBox.isSelected){
                optionsSet.add(RegexOption.DOT_MATCHES_ALL)
            }
            val regex:Regex
            try {
                regex=Regex(regexString,optionsSet)
            }catch (patternException: PatternSyntaxException){
                mainWindow.matchResultTextArea.text="Regex pattern syntax error"
                return@launch
            }catch (exception: Exception){
                mainWindow.matchResultTextArea.text="Unknown error"
                return@launch
            }

            val case=mainWindow.testCasesTextArea.text
            val matched= if (mainWindow.matchAllCheckBox.isSelected) regex.findAll(case) else sequenceOf(regex.find(case))
            mainWindow.matchResultTextArea.text=matched.map { it?.value ?: "" }.joinToString("\n")
        }
    }


    override fun initActive() {
        return
    }

    override fun deActive() {
        return
    }

    override fun tabName(): String {
        return "Regex"
    }
}
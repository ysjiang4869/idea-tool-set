package org.jiangys.tool.idea.services

import com.intellij.openapi.observable.util.whenTextChanged
import org.jiangys.tool.idea.ToolBoxWindow

class RegexService(private val mainWindow: ToolBoxWindow) : TabService {


    init {
        mainWindow.regexInputTextField.whenTextChanged {testRegex() }
        mainWindow.testCasesTextArea.whenTextChanged {testRegex()}
        mainWindow.matchAllCheckBox.addActionListener { testRegex() }
        mainWindow.ignoreCaseCheckBox.addActionListener { testRegex() }
        mainWindow.multiLineCheckBox.addActionListener { testRegex() }
        mainWindow.dotMatchAllCheckBox.addActionListener { testRegex() }

    }

    private fun testRegex(){
        val regexString=mainWindow.regexInputTextField.document.getText(0, mainWindow.regexInputTextField.document.length)
        if (regexString.isBlank()){
            return
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
        val regex=Regex(regexString,optionsSet)

        val case=mainWindow.testCasesTextArea.text
        val matched= if (mainWindow.matchAllCheckBox.isSelected) regex.findAll(case) else sequenceOf(regex.find(case))
        mainWindow.matchResultTextArea.text=matched.map { it?.value ?: "" }.joinToString("\n")
    }


    override fun initActive() {
        mainWindow.matchAllCheckBox.isSelected=true
        mainWindow.multiLineCheckBox.isSelected=true
    }

    override fun deActive() {
        TODO("Not yet implemented")
    }

    override fun tabName(): String {
        return "Regex"
    }
}
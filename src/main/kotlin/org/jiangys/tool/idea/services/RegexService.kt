package org.jiangys.tool.idea.services

import com.intellij.openapi.observable.util.whenTextChanged
import kotlinx.coroutines.*
import org.jiangys.tool.idea.ToolBoxWindow
import java.util.regex.PatternSyntaxException

/**
 * todo 1 regex grammars 2 ui improve 3 common examples
 */

class RegexService(private val mainWindow: ToolBoxWindow) : TabService {


    private val scope= CoroutineScope(Dispatchers.Default)

    init {
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
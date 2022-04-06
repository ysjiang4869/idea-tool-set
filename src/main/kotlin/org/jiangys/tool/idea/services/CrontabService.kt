package org.jiangys.tool.idea.services

import com.cronutils.descriptor.CronDescriptor
import com.cronutils.model.Cron
import com.cronutils.model.CronType
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.model.time.ExecutionTime
import com.cronutils.parser.CronParser
import org.jiangys.tool.idea.ToolBoxWindow
import java.awt.event.ActionListener
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.swing.ButtonGroup

class CrontabService(private val mainWindow: ToolBoxWindow) {

    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    fun init() {
        mainWindow.cronNextTimes.lineWrap = true
        mainWindow.cronNextTimes.isEditable = false
        mainWindow.cronExplain.isEditable = false
        val cronGroup = ButtonGroup()
        cronGroup.add(mainWindow.linuxRadioButton)
        cronGroup.add(mainWindow.javaSpringRadioButton)
        cronGroup.add(mainWindow.javaQuartzRadioButton)
        mainWindow.linuxRadioButton.actionCommand = "linux"
        mainWindow.linuxRadioButton.addActionListener {
            mainWindow.cronTextField.text = "0 */12 * * *"
            mainWindow.cronNextTimes.text = ""
            mainWindow.cronExplain.text = this::class.java.getResource("/cron_linux.txt").readText()
            mainWindow.cronExplain.caretPosition = 0
        }
        mainWindow.javaSpringRadioButton.actionCommand = "javaSpring"
        mainWindow.javaSpringRadioButton.addActionListener {
            mainWindow.cronTextField.text = "0 0 18 28-31 * ?"
            mainWindow.cronNextTimes.text = ""
            mainWindow.cronExplain.text = this::class.java.getResource("/cron_spring.txt").readText()
            mainWindow.cronExplain.caretPosition = 0
        }
        mainWindow.javaQuartzRadioButton.actionCommand = "javaQuartz"
        mainWindow.javaQuartzRadioButton.addActionListener {
            mainWindow.cronTextField.text = "0 0 18 L * ?"
            mainWindow.cronNextTimes.text = ""
            mainWindow.cronExplain.text = this::class.java.getResource("/cron_quartz.txt").readText()
            mainWindow.cronExplain.caretPosition = 0
        }

        mainWindow.showTimesButton.addActionListener(ActionListener {

            val cronType: CronType = when (cronGroup.selection?.actionCommand) {
                "linux" -> CronType.UNIX
                "javaSpring" -> CronType.SPRING
                "javaQuartz" -> CronType.QUARTZ
                else -> {
                    mainWindow.cronNextTimes.text = ""
                    return@ActionListener
                }
            }
            val cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(cronType)
            val cronParser = CronParser(cronDefinition)
            val cron: Cron
            try {
                cron = cronParser.parse(mainWindow.cronTextField.text)
                cron.validate()
            } catch (exception: IllegalArgumentException) {
                mainWindow.cronNextTimes.text = "cron express not correct"
                return@ActionListener
            }
            val descriptor = CronDescriptor.instance(Locale.getDefault())
            val description = descriptor.describe(cron)
            mainWindow.cronNextTimes.append(description)
            mainWindow.cronNextTimes.append("\n")
            var zonedDateTime = ZonedDateTime.now()
            val executionTime = ExecutionTime.forCron(cron)
            for (i in 0..6) {
                val nextTime = executionTime.nextExecution(zonedDateTime)
                if (nextTime.isEmpty) {
                    break
                }
                mainWindow.cronNextTimes.append(nextTime.get().format(dateFormatter))
                mainWindow.cronNextTimes.append("\n")
                zonedDateTime = nextTime.get().plusNanos(1)
            }
        })
    }
}
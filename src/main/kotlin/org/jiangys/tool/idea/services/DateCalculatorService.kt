package org.jiangys.tool.idea.services

import org.jiangys.tool.idea.ToolBoxWindow
import org.jiangys.tool.idea.utils.SimpleHintDialog
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.swing.JTextField

class DateCalculatorService(private val mainWindow: ToolBoxWindow) : TabService {

    private val formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd")

    init {
        mainWindow.setAsTodayButton1.addActionListener {
            setAsToday(mainWindow.diffDayYearText1,mainWindow.diffDayMonthText1,mainWindow.diffDayDayText1)
        }
        mainWindow.setAsTodayButton2.addActionListener {
            setAsToday(mainWindow.diffDayYearText2,mainWindow.diffDayMonthText2,mainWindow.diffDayDayText2)
        }
        mainWindow.setAsTodayButton3.addActionListener {
            setAsToday(mainWindow.addSubDayYearText,mainWindow.addSubDayMonthText,mainWindow.addSubDayDayText)
        }

        mainWindow.diifCalculateButton.addActionListener {
            val date1=getDate(mainWindow.diffDayYearText1,mainWindow.diffDayMonthText1,mainWindow.diffDayDayText1)?:return@addActionListener
            val date2=getDate(mainWindow.diffDayYearText2,mainWindow.diffDayMonthText2,mainWindow.diffDayDayText2)?:return@addActionListener
            val diff=Duration.between(date1.atStartOfDay(),date2.atStartOfDay()).toDays()
            mainWindow.diffDayResultText.text=diff.toString()
        }

        mainWindow.addSubDayCalculateButton.addActionListener {
            val beginDate=getDate(mainWindow.addSubDayYearText,mainWindow.addSubDayMonthText,mainWindow.addSubDayDayText)?:return@addActionListener
            val subAdd=if (mainWindow.addSubDayTypeComboBox.selectedIndex==0) -1L else 1L
            val units= arrayOf(ChronoUnit.DAYS,ChronoUnit.WEEKS,ChronoUnit.MONTHS)
            val unit=units[mainWindow.addSubDayUnitComboBox.selectedIndex]
            val number= mainWindow.addSubDayNumberText.text.toIntOrNull()?.let {
                val result=beginDate.plus(subAdd*it,unit)
                mainWindow.addSubDayResultText.text=formatter.format(result)
            }
            if(number==null){
                SimpleHintDialog("Input [${mainWindow.addSubDayNumberText.text}] is not a number").showAndGet();
            }

        }
    }

    private fun setAsToday(yearField:JTextField, monthField: JTextField, dayField: JTextField){
        val today=LocalDate.now()
        yearField.text=today.year.toString()
        monthField.text=today.month.value.toString()
        dayField.text=today.dayOfMonth.toString()
    }

    private fun getDate(yearField:JTextField, monthField: JTextField, dayField: JTextField):LocalDate?{
        try {
            val year=yearField.text.toInt()
            val month=monthField.text.toInt()
            val day=dayField.text.toInt()
            return LocalDate.of(year,month,day)
        }catch (e:NumberFormatException){
            SimpleHintDialog("Input date not correct").showAndGet();
            return null
        }
    }

    override fun initActive() {
        return
    }

    override fun deActive() {
        return
    }

    override fun tabName(): String {
        return "Date Calc"
    }
}
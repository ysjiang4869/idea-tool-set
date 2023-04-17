package org.jiangys.tool.idea;

import com.intellij.openapi.wm.ToolWindow;
import org.jiangys.tool.idea.services.*;
import org.jiangys.tool.idea.utils.JsonUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author jiangyuesong
 * @date 2022/2/18
 */
public class ToolBoxWindow {

    private final TimeService timeService;
    private final CrontabService crontabService;
    private final TabSelectedService tabSelectedService;
    private final TreeViewService treeViewService;

    private final DateCalculatorService dateCalculatorService;
    private final RegexService regexService;

    private JPanel rootPanel;
    private JTabbedPane mainTabbedPane;
    private JTabbedPane jsonToolTabbedPane;
    private JButton compressButton;
    private JButton deCompressButton;
    private JTextArea formattedJsonTextarea;
    private JTextArea simpleJsonTextArea;
    private JTextArea treeTextArea;
    private JTree tree1;
    private JRadioButton linuxRadioButton;
    private JRadioButton javaSpringRadioButton;
    private JRadioButton javaQuartzRadioButton;
    private JTextField cronTextField;
    private JButton showTimesButton;
    private JTextArea cronNextTimes;
    private JTextPane cronExplain;
    private JTextField currentTimeTextField;
    private JComboBox currentTimeUnitCombox;
    private JButton copyButton;
    private JButton toTimeTransformButton1;
    private JTextField inputTimestampTextField;
    private JComboBox timestampUnitComboBox;
    private JButton toTimeTransformButton2;
    private JTextField timeToTimestampTimeTextField;
    private JButton transformTimeToTimestampButton;
    private JComboBox timeToTimestampZoneCcomboBox;
    private JTextField timeToTimestampResultTextField;
    private JComboBox timeToTimestampUnitComboBox;
    private JComboBox timeShowZoneComboBox;
    private JTextField showTimeTextField5;
    private JTextField showTimeTextField4;
    private JTextField showTimeTextField3;
    private JTextField showTimeTextField2;
    private JTextField showTimeTextField1;
    private JTextField diffDayYearText1;
    private JTextField diffDayMonthText1;
    private JTextField diffDayDayText1;
    private JTextField diffDayYearText2;
    private JTextField diffDayMonthText2;
    private JTextField diffDayDayText2;
    private JTextArea diffDayResultText;
    private JButton diifCalculateButton;
    private JButton setAsTodayButton1;
    private JButton setAsTodayButton2;
    private JTextField addSubDayYearText;
    private JTextField addSubDayMonthText;
    private JTextField addSubDayDayText;
    private JButton setAsTodayButton3;
    private JComboBox addSubDayTypeComboBox;
    private JTextField addSubDayNumberText;
    private JComboBox addSubDayUnitComboBox;
    private JButton addSubDayCalculateButton;
    private JTextArea addSubDayResultText;
    private JTextField regexInputTextField;
    private JCheckBox matchAllCheckBox;
    private JCheckBox dotMatchAllCheckBox;
    private JCheckBox ignoreCaseCheckBox;
    private JCheckBox multiLineCheckBox;
    private JTextArea matchResultTextArea;
    private JTextArea testCasesTextArea;


    public ToolBoxWindow(ToolWindow toolWindow) {
        compressButton.addActionListener(e -> {
            try {
                simpleJsonTextArea.setText(JsonUtil.INSTANCE.toSimpleString(formattedJsonTextarea.getText()));
                formattedJsonTextarea.setText(JsonUtil.INSTANCE.toPrettyString(formattedJsonTextarea.getText()));
            } catch (PluginException ex) {
                simpleJsonTextArea.setText(ex.getMessage());
            }
        });
        deCompressButton.addActionListener(e -> {
            try {
                formattedJsonTextarea.setText(JsonUtil.INSTANCE.toPrettyString(simpleJsonTextArea.getText()));
            } catch (PluginException ex) {
                formattedJsonTextarea.setText(ex.getMessage());
            }
        });

        tree1.clearSelection();
        treeViewService = new TreeViewService(tree1);

        treeTextArea.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                treeViewService.updateModel(treeTextArea.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                treeViewService.updateModel(treeTextArea.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                treeViewService.updateModel(treeTextArea.getText());
            }
        });

        crontabService = new CrontabService(this);
        timeService = new TimeService(this);
        tabSelectedService = new TabSelectedService(this);
        dateCalculatorService = new DateCalculatorService(this);
        regexService=new RegexService(this);
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }


    public JRadioButton getLinuxRadioButton() {
        return linuxRadioButton;
    }

    public JRadioButton getJavaSpringRadioButton() {
        return javaSpringRadioButton;
    }

    public JRadioButton getJavaQuartzRadioButton() {
        return javaQuartzRadioButton;
    }

    public JTextField getCronTextField() {
        return cronTextField;
    }

    public JButton getShowTimesButton() {
        return showTimesButton;
    }

    public JTextArea getCronNextTimes() {
        return cronNextTimes;
    }

    public JTextPane getCronExplain() {
        return cronExplain;
    }

    public JTabbedPane getJsonTabbedPane() {
        return mainTabbedPane;
    }


    public JTextField getCurrentTimeTextField() {
        return currentTimeTextField;
    }

    public JComboBox getCurrentTimeUnitCombox() {
        return currentTimeUnitCombox;
    }

    public JButton getCopyButton() {
        return copyButton;
    }

    public JButton getToTimeTransformButton1() {
        return toTimeTransformButton1;
    }

    public JTextField getInputTimestampTextField() {
        return inputTimestampTextField;
    }

    public JComboBox getTimestampUnitComboBox() {
        return timestampUnitComboBox;
    }

    public JButton getToTimeTransformButton2() {
        return toTimeTransformButton2;
    }

    public JTextField getTimeToTimestampTimeTextField() {
        return timeToTimestampTimeTextField;
    }

    public JButton getTransformButton2() {
        return transformTimeToTimestampButton;
    }

    public JComboBox getTimeToTimestampZoneCcomboBox() {
        return timeToTimestampZoneCcomboBox;
    }

    public JTextField getTimeToTimestampResultTextField() {
        return timeToTimestampResultTextField;
    }

    public JComboBox getTimeToTimestampUnitComboBox() {
        return timeToTimestampUnitComboBox;
    }

    public TimeService getTimeService() {
        return timeService;
    }

    public JComboBox getTimeShowZoneComboBox() {
        return timeShowZoneComboBox;
    }

    public JTextField getShowTimeTextField5() {
        return showTimeTextField5;
    }

    public JTextField getShowTimeTextField4() {
        return showTimeTextField4;
    }

    public JTextField getShowTimeTextField3() {
        return showTimeTextField3;
    }

    public JTextField getShowTimeTextField2() {
        return showTimeTextField2;
    }

    public JTextField getShowTimeTextField1() {
        return showTimeTextField1;
    }

    public JButton getTransformTimeToTimestampButton() {
        return transformTimeToTimestampButton;
    }

    public JTextField getDiffDayYearText1() {
        return diffDayYearText1;
    }

    public JTextField getDiffDayMonthText1() {
        return diffDayMonthText1;
    }

    public JTextField getDiffDayDayText1() {
        return diffDayDayText1;
    }

    public JTextField getDiffDayYearText2() {
        return diffDayYearText2;
    }

    public JTextField getDiffDayMonthText2() {
        return diffDayMonthText2;
    }

    public JTextField getDiffDayDayText2() {
        return diffDayDayText2;
    }

    public JTextArea getDiffDayResultText() {
        return diffDayResultText;
    }

    public JButton getDiifCalculateButton() {
        return diifCalculateButton;
    }

    public JButton getSetAsTodayButton1() {
        return setAsTodayButton1;
    }

    public JButton getSetAsTodayButton2() {
        return setAsTodayButton2;
    }

    public JTextField getAddSubDayYearText() {
        return addSubDayYearText;
    }

    public JTextField getAddSubDayMonthText() {
        return addSubDayMonthText;
    }

    public JTextField getAddSubDayDayText() {
        return addSubDayDayText;
    }

    public JButton getSetAsTodayButton3() {
        return setAsTodayButton3;
    }

    public JComboBox getAddSubDayTypeComboBox() {
        return addSubDayTypeComboBox;
    }

    public JTextField getAddSubDayNumberText() {
        return addSubDayNumberText;
    }

    public JComboBox getAddSubDayUnitComboBox() {
        return addSubDayUnitComboBox;
    }

    public JButton getAddSubDayCalculateButton() {
        return addSubDayCalculateButton;
    }

    public JTextArea getAddSubDayResultText() {
        return addSubDayResultText;
    }

    public JTextField getRegexInputTextField() {
        return regexInputTextField;
    }

    public JCheckBox getMatchAllCheckBox() {
        return matchAllCheckBox;
    }

    public JCheckBox getDotMatchAllCheckBox() {
        return dotMatchAllCheckBox;
    }

    public JCheckBox getIgnoreCaseCheckBox() {
        return ignoreCaseCheckBox;
    }

    public JCheckBox getMultiLineCheckBox() {
        return multiLineCheckBox;
    }

    public JTextArea getMatchResultTextArea() {
        return matchResultTextArea;
    }

    public JTextArea getTestCasesTextArea() {
        return testCasesTextArea;
    }
}

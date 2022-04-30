package org.jiangys.tool.idea;

import com.intellij.openapi.wm.ToolWindow;
import org.jiangys.tool.idea.services.CrontabService;
import org.jiangys.tool.idea.services.TabSelectedService;
import org.jiangys.tool.idea.services.TimeService;
import org.jiangys.tool.idea.services.TreeViewService;
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

    private JPanel rootPanel;
    private JTabbedPane jsonTabbedPane;
    private JTabbedPane cronTabbedPane;
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
    private JButton transformButton2;
    private JComboBox timeToTimestampZoneCcomboBox;
    private JTextField timeToTimestampResultTextField;
    private JComboBox timeToTimestampUnitComboBox;
    private JComboBox comboBox5;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;


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
        return jsonTabbedPane;
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
        return transformButton2;
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

    public JComboBox getComboBox5() {
        return comboBox5;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public JTextField getTextField4() {
        return textField4;
    }

    public JTextField getTextField5() {
        return textField5;
    }

    public TimeService getTimeService() {
        return timeService;
    }
}

package org.jiangys.tool.idea;

import com.intellij.openapi.wm.ToolWindow;
import org.jiangys.tool.idea.services.CrontabService;
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
        TreeViewService treeViewService = new TreeViewService(tree1);

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

        CrontabService crontabService = new CrontabService(this);
        crontabService.init();
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
}

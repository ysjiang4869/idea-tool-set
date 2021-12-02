package org.jiangys.tool.idea;

import com.intellij.openapi.wm.ToolWindow;
import org.jiangys.tool.idea.utils.JsonUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author jiangyuesong
 * @date 2022/2/18
 */
public class ToolBoxWindow {

    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JButton compressButton;
    private JButton deCompressButton;
    private JTextArea formattedJsonTextarea;
    private JTextArea simpleJsonTextArea;

    public ToolBoxWindow(ToolWindow toolWindow) {
        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpleJsonTextArea.setText(JsonUtil.INSTANCE.toSimpleString(formattedJsonTextarea.getText()));
            }
        });
        deCompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formattedJsonTextarea.setText(JsonUtil.INSTANCE.toPrettyString(simpleJsonTextArea.getText()));
            }
        });
    }


    public JPanel getRootPanel() {
        return rootPanel;
    }
}

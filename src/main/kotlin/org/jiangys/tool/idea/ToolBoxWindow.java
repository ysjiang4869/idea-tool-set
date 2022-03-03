package org.jiangys.tool.idea;

import com.intellij.openapi.wm.ToolWindow;
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
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JButton compressButton;
    private JButton deCompressButton;
    private JTextArea formattedJsonTextarea;
    private JTextArea simpleJsonTextArea;
    private JTextArea treeTextArea;
    private JTree tree1;

    public ToolBoxWindow(ToolWindow toolWindow) {
        compressButton.addActionListener(e ->
                simpleJsonTextArea.setText(JsonUtil.INSTANCE.toSimpleString(formattedJsonTextarea.getText())));
        deCompressButton.addActionListener(e ->
                formattedJsonTextarea.setText(JsonUtil.INSTANCE.toPrettyString(simpleJsonTextArea.getText())));

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
    }


    public JPanel getRootPanel() {
        return rootPanel;
    }
}

package org.jiangys.tool.idea.services

import com.fasterxml.jackson.databind.JsonNode
import org.jiangys.tool.idea.utils.JsonUtil
import java.util.Locale
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

/**
 * reference to https://github.com/tgvaughan/JsonBrowse
 */
class TreeViewService(private val tree: JTree) {
    fun updateModel(text: String) {
        val rootJsonNode = JsonUtil.mapper.readTree(text)
        // Construct tree model
        val rootNode = buildTree("root", rootJsonNode)
        tree.model = DefaultTreeModel(rootNode)
    }

    /**
     * Builds a tree of TreeNode objects using the tree under the
     * given JsonNode.
     *
     * @param name Text to be associated with node
     * @param node
     * @return root TreeNode
     */
    private fun buildTree(name: String, node: JsonNode): DefaultMutableTreeNode {
        val treeNode = DefaultMutableTreeNode(name)
        val it = node.fields()
        while (it.hasNext()) {
            val entry = it.next()
            treeNode.add(buildTree(entry.key, entry.value))
        }
        if (node.isArray) {
            for (i in 0 until node.size()) {
                val child = node[i]
                if (child.isValueNode) {
                    treeNode.add(DefaultMutableTreeNode(child.asText()))
                } else {
                    treeNode.add(buildTree(String.format(Locale.ENGLISH, "[%d]", i), child))
                }
            }
        } else if (node.isValueNode) {
            treeNode.add(DefaultMutableTreeNode(node.asText()))
        }
        return treeNode
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.util;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Collections;

public class JTreeUtil {

    public static void setTreeExpandedState(JTree tree, boolean expanded) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getModel().getRoot();
        setNodeExpandedState(tree, node, expanded);
    }

    public static void setNodeExpandedState(JTree tree, DefaultMutableTreeNode node, boolean expanded) {
        ArrayList<DefaultMutableTreeNode> list = Collections.list(node.children());
        for (DefaultMutableTreeNode treeNode : list) {
            setNodeExpandedState(tree, treeNode, expanded);
        }
        if (!expanded && node.isRoot()) {
            return;
        }
        TreePath path = new TreePath(node.getPath());
        if (expanded) {
            tree.expandPath(path);
        } else {
            tree.collapsePath(path);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.gui.renders;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import net.vnleng.generator.data.Project;
import net.vnleng.generator.data.ints.ResourceElement;
import net.vnleng.generator.resources.ProjectResourceHandler;

/**
 *
 * @author gabri
 */
public class ProjectTreeRender extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        //super.getTreeCellRendererComponent(tree, value, leaf, expanded, leaf, row, hasFocus);
        if (value instanceof DefaultMutableTreeNode node) {
            Object uo = node.getUserObject();
            switch (uo) {
                case ResourceElement re -> {
                    switch (re.getType()) {
                        case FunctionBlock -> {
                            setIcon(ProjectResourceHandler.FBIcon_24);  // transform it back
                            setText(re.getName());
                        }
                        case Function -> {
                            setIcon(ProjectResourceHandler.FCIcon_24);  // transform it back
                            setText(re.getName());
                        }
                        case DataBlock -> {
                            setIcon(ProjectResourceHandler.DBIcon_24);  // transform it back
                            setText(re.getName());
                        }
                        case FunctionInstance -> {
                            setIcon(ProjectResourceHandler.DBInstanceIcon_24);  // transform it back
                            setText(re.getName());
                        }
                    }
                }
                case Project p -> {
                    setIcon(ProjectResourceHandler.ProjectFileIcon_32);  // transform it back
                    setText(p.getProjectName() + " [PRJ]");
                }
                default -> {
                    setText("Errore Risorsa");
                }
            }
        }
        return this;
    }

}

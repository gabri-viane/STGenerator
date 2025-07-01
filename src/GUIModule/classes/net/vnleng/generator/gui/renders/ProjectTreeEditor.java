/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.gui.renders;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import net.vnleng.generator.commons.Pair;
import net.vnleng.generator.data.ints.ResourceElement;

/**
 *
 * @author gabri
 */
public class ProjectTreeEditor extends JTextField implements TreeCellEditor {

    private List<CellEditorListener> listeners;
    private final JTree tree;
    private ResourceElement re;

    public ProjectTreeEditor(JTree tree) {
        this.tree = tree;
        this.listeners = new ArrayList<>();
        ProjectTreeEditor me = this;
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    ChangeEvent evt = new ChangeEvent(me);
                    List.copyOf(listeners).forEach(l -> l.editingCanceled(evt));
                    return;
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (re == null) {
                        ChangeEvent evt = new ChangeEvent(me);
                        List.copyOf(listeners).forEach(l -> l.editingCanceled(evt));
                        return;
                    }
                    ChangeEvent evt = new ChangeEvent(new Pair<>(re, getText()));
                    List.copyOf(listeners).forEach(l -> l.editingStopped(evt));
                    tree.setEditable(false);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        if (value != null && value instanceof DefaultMutableTreeNode node) {
            Object userObject = node.getUserObject();
            if (userObject == null) {
                return null;
            }
            if (userObject instanceof ResourceElement re_element) {
                this.re = re_element;
                this.setText(re_element.getName());
                return this;
            }
            this.setText(userObject.toString());
            return this;

        }
        return null;
    }

    @Override
    public Object getCellEditorValue() {
        return re;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
//        Object b = anEvent.getSource();
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        return !this.getText().isBlank();
    }

    @Override
    public void cancelCellEditing() {
        ChangeEvent evt = new ChangeEvent(re);
        List.copyOf(listeners).forEach(l -> l.editingCanceled(evt));
        tree.setEditable(false);
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
        listeners.add(l);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        listeners.remove(l);
    }

}

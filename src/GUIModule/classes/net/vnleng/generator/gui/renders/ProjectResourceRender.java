/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.gui.renders;

import net.vnleng.generator.data.ProjectResource;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author gabri
 */
public class ProjectResourceRender extends JLabel
        implements ListCellRenderer<ProjectResource> {

    private Color defBg;
    private Color defFg;

    public ProjectResourceRender() {
        setOpaque(true);
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);
        defBg = this.getBackground();
        defFg = this.getForeground();
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ProjectResource> list, ProjectResource value, int index, boolean isSelected, boolean cellHasFocus) {
        this.setText(value.getResourceName());
        if (isSelected) {
            this.setBackground(Color.decode("#02F0FA"));
            this.setForeground(Color.decode("#000000"));
        } else {
            this.setBackground(defBg);
            this.setForeground(defFg);
        }
        return this;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.gui.renders.table;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import net.vnleng.generator.data.ints.var.Variable;

/**
 *
 * @author gabri
 */
public class VariableTableRowRender implements TableCellRenderer {
    
    JLabel name = new JLabel();
    JLabel type = new JLabel();
    JLabel startValue = new JLabel();
    JLabel comment = new JLabel();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        if(value instanceof Variable v){
            switch(column){
                case 0 -> {
                    name.setText(v.getName());
                    return name;
                }
                case 1 -> {
                    type.setText(v.getType().toString());
                    return type;
                }
                case 2->{
                    startValue.setText(v.getDefaultValue().toString());
                    return startValue;
                }
                case 3->{
                    comment.setText(v.getComment());
                    return comment;
                }
            }
        }
        return null;
    }

}

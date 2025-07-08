/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package net.vnleng.generator.gui.renders.table;

/**
 *
 * @author gabri
 */
public interface TableRow<T> {

    Object getValueAt(CustomTableColumn col, T rowValue);

    void setValueAt(CustomTableColumn col, Object value, T rowReference);

    boolean isEditable(CustomTableColumn col,T rowReference);

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.gui.renders.table;

import javax.swing.table.TableCellEditor;

/**
 *
 * @author gabri
 */
public interface CustomTableColumn{
    
    String getColumnName();
    
    TableCellEditor getCellEditor();
    
    Class<?> getColumnClass();
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.gui.renders.table;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import net.vnleng.generator.commons.events.ChangeRequest;
import net.vnleng.generator.data.ints.Variable;
import net.vnleng.generator.data.ints.VariableType;
import net.vnleng.generator.resources.TextResources;

/**
 *
 * @author gabri
 */
public class VariableTableRender extends AbstractTableModel {

    public static enum Direction {
        UP, DOWN, NEW;
    }

    private final ResourceBundle TXTBundle;

    private final List<CustomTableColumn> columns;
    private final List<Variable> rows;
    private final TableRow<Variable> editor;

    public VariableTableRender(TableRow<Variable> cellEditorHandler) {
        TXTBundle = TextResources.GUITextBundle;
        columns = new ArrayList<>();
        rows = new ArrayList<>();
        editor = cellEditorHandler;
        init();
    }

    private void init() {
        JTextField jtf = new JTextField();
        TableCellEditor col0Editor = new DefaultCellEditor(jtf);
        jtf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    col0Editor.stopCellEditing();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        JComboBox<VariableType> varCB = new JComboBox<>();
        varCB.addItem(null);
        for (VariableType vt : VariableType.values()) {
            varCB.addItem(vt);
        }
        TableCellEditor col1Editor = new DefaultCellEditor(varCB);

        columns.add(0, new CustomTableColumn() {
            @Override
            public String getColumnName() {
                return TXTBundle.getString("Name");
            }

            @Override
            public TableCellEditor getCellEditor() {
                return col0Editor;
            }

            @Override
            public Class<?> getColumnClass() {
                return String.class;
            }
        });
        columns.add(1, new CustomTableColumn() {
            @Override
            public String getColumnName() {
                return TXTBundle.getString("Type");
            }

            @Override
            public TableCellEditor getCellEditor() {
                return col1Editor;
            }

            @Override
            public Class<?> getColumnClass() {
                return VariableType.class;
            }
        });
        columns.add(2, new CustomTableColumn() {
            @Override
            public String getColumnName() {
                return TXTBundle.getString("StartValue");
            }

            @Override
            public TableCellEditor getCellEditor() {
                return col0Editor;
            }

            @Override
            public Class<?> getColumnClass() {
                return String.class;
            }
        });
        columns.add(3, new CustomTableColumn() {
            @Override
            public String getColumnName() {
                return TXTBundle.getString("Comment");
            }

            @Override
            public TableCellEditor getCellEditor() {
                return col0Editor;
            }

            @Override
            public Class<?> getColumnClass() {
                return String.class;
            }
        });
    }

    public CustomTableColumn getColumn(int index) {
        return this.columns.get(index);
    }

    public TableCellEditor getColumnEditor(int index) {
        return this.columns.get(index).getCellEditor();
    }

    public void addColumn(CustomTableColumn col) {
        this.columns.add(col);
    }

    public void addRow(Variable row) {
        rows.add(row);
        this.fireTableRowsInserted(rows.size() - 1, rows.size() - 1);
    }

    public void addRow(int index, Variable row) {
        if (index < 0 || index > rows.size() || row == null) {
            return;
        }
        rows.add(row);
        this.fireTableRowsInserted(index, rows.size() - 1);
    }

    public void moveRow(Variable row, Direction direction) {
        int indexOf = rows.indexOf(row);
        if (indexOf == -1) {
            switch (direction) {
                case DOWN -> {
                    rows.addLast(row);
                    this.fireTableRowsInserted(rows.size() - 1, rows.size() - 1);
                }
                case UP -> {
                    rows.addFirst(row);
                    this.fireTableRowsInserted(0, rows.size() - 1);
                }
                case NEW -> {
                    rows.add(row);
                    this.fireTableRowsInserted(rows.size() - 1, rows.size() - 1);
                }
            }
            return;
        }

        int newIndex = switch (direction) {
            case DOWN ->
                indexOf + 1;
            case UP ->
                indexOf - 1;
            case NEW ->
                indexOf;
        };

        rows.remove(indexOf);
        rows.add(newIndex, row);
        this.fireTableRowsInserted(newIndex, rows.size() - 1);
    }

    public void removeRow(int index) {
        rows.remove(index);
        this.fireTableRowsDeleted(index, index);
    }

    public Variable getRow(int index) {
        return rows.get(index);
    }

    public Variable getLastRow() {
        if (rows.isEmpty()) {
            return null;
        }
        return rows.getLast();
    }

    public List<Variable> getVariables() {
        return Collections.unmodifiableList(rows);
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getColumnName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns.get(columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editor.isEditable(columns.get(columnIndex), rows.get(rowIndex));
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Variable get = rows.get(rowIndex);
        return switch (columnIndex) {
            case 0 ->
                get.getName();
            case 1 ->
                Variable.getFullType(get);
            case 2 ->
                get.getDefaultValue();
            case 3 ->
                get.getComment();
            default ->
                editor.getValueAt(columns.get(columnIndex), get);
        };
    }

    ChangeRequest<Variable, VariableType> changeTypeListener = (t, v) -> {
    };

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Variable variable = rows.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                if (aValue instanceof String name) {
                    variable.setName(name);
                }
            }
            case 1 -> {
                if (aValue instanceof VariableType type) {
                    changeTypeListener.onChangeRequest(variable, type);
                }
            }
            case 2 -> {
                if (aValue instanceof String value) {
                    variable.setDefaultValue(value);
                }
            }
            case 3 -> {
                if (aValue instanceof String comment) {
                    variable.setComment(comment);
                }
            }
            default ->
                editor.setValueAt(columns.get(columnIndex), aValue, variable);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void setTypeListener(ChangeRequest<Variable, VariableType> reqList) {
        if (reqList != null) {
            this.changeTypeListener = reqList;
        } else {
            this.changeTypeListener = (t, v) -> {
            };
        }
    }
}

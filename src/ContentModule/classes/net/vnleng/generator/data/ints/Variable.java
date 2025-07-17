/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.ints;

import java.io.Serializable;

/**
 *
 * @author gabri
 */
public class Variable implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private VariableType type;
    protected final VariableTypeModifier typeModifier;
    private Object defaultValue;
    private String comment;

    public Variable(String name, VariableType varType) {
        this.name = name;
        this.type = varType;
        this.comment = "";
        this.defaultValue = null;
        this.typeModifier = new VariableTypeModifier();
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            return;
        }
        this.name = name;
    }

    public void setType(VariableType type) {
        if (type == null) {
            return;
        }
        this.type = type;
    }

    public VariableTypeModifier getModifier() {
        return this.typeModifier;
    }

    public VariableType getType() {
        return type;
    }

    public String getDeclaration() {
        StringBuilder sb = new StringBuilder(this.name);
        sb.append(" : ").append(getFullType(this)).append(";");
        if (this.comment != null && !this.comment.isBlank()) {
            sb.append(" //").append(this.comment);
        }
        return sb.toString();
    }

    public static String getFullType(Variable v) {
        if (v.typeModifier.isList()) {
            StringBuilder sb = new StringBuilder(v.type.toString());
            sb.append("[").append(v.typeModifier.getLength()).append("]");
            return sb.toString();
        }
        if (v.typeModifier.isArray()) {
            StringBuilder sb = new StringBuilder("Array[");
            sb.append(v.typeModifier.getStart()).append("..")
                    .append(v.typeModifier.getStart() + v.typeModifier.getLength())
                    .append("] of ").append(v.type.toString());
            return sb.toString();
        }
        return v.type.toString();
    }

}

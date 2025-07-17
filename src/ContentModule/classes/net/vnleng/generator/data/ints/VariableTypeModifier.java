/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.ints;

/**
 *
 * @author gabri
 */
public class VariableTypeModifier {

    private boolean isList = false;
    private boolean isArray = false;
    private int length = 0;
    private int start = 0;

    protected VariableTypeModifier() {

    }

    public void reset() {
        isArray = false;
        isList = false;
        length = 0;
        start = 0;
    }

    public void setIsArray(boolean isArray, int start, int length) {
        this.isArray = isArray;
        this.isList = false;
        if (length < 1) {
            length = 1;
        }
        if (start < 0) {
            start = 0;
        }
        this.start = start;
        this.length = length;
    }

    public void setIsList(boolean isList, int length) {
        this.isList = isList;
        this.isArray = false;
        this.start = 0;
        if (length < 1) {
            length = 1;
        }
        this.length = length;
    }

    public boolean isArray() {
        return isArray;
    }

    public boolean isList() {
        return isList;
    }

    public int getLength() {
        return length;
    }

    public int getStart() {
        return start;
    }

    public void of(Variable v) {
        this.isArray = v.typeModifier.isArray;
        this.isList = v.typeModifier.isList;
        this.length = v.typeModifier.length;
        this.start = v.typeModifier.start;
    }

}

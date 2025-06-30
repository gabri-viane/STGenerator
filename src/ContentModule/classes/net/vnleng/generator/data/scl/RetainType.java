/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package net.vnleng.generator.data.scl;

import java.io.Serializable;

/**
 *
 * @author gabri
 */
public enum RetainType implements Serializable {
    RETAIN(""), NON_RETAIN("NON_RETAIN"), SET_IN_DB("");

    private static final long serialVersionUID = 1L;
    private String content;

    private RetainType(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }

}

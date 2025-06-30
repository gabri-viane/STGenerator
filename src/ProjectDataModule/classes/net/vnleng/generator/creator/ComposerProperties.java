/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.creator;

import java.io.Serializable;

/**
 *
 * @author gabri
 */
public class ComposerProperties implements Serializable {

    private static final long serialVersionUID = 1L;
    private int number = 0;

    public ComposerProperties() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}

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
public enum VariableType implements Serializable{
    //Se è "Variable" allora è il nome di una variabile e non un tipo, va preceduto da #
    //Se è "Tag" allora è il nome di una tag e non un tipo, va scritto dentro ""
    Bool, Byte, Word, DWord, Char, Sint, Int, DInt, USInt, UInt, UDInt, Real, LReal, String, Variable, Tag;
    
    private static final long serialVersionUID = 1L;
}

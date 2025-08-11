package net.vnleng.generator.data.ints.var;

import java.io.Serializable;

/**
 * Indica i possibili tipi di variabili con l'agginta di "Array", "Variabile" e
 * "Tag" per permettere comodamente di scegliere da un'unica enum il tipo di
 * variabile a cui si riferisce.
 * <p>
 * Questa classe è serializzabile.
 * </p>
 *
 * @author gabri
 */
public enum VariableType implements Serializable {
    //Se è "Variable" allora è il nome di una variabile e non un tipo, va preceduto da #
    //Se è "Tag" allora è il nome di una tag e non un tipo, va scritto dentro ""
    Bool, Byte, Word, DWord, Char, Sint, Int, DInt, USInt, UInt, UDInt, Real, LReal, String, WString, Array, Variable, Tag;

    private static final long serialVersionUID = 2L;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.ints.var;

import java.io.Serializable;

/**
 * Estende il concetto introdotto dall'enum {@link VariableType} permettendo di
 * definire una dimensione nel caso di stringhe ed array.
 * <p>
 * La classe è serializzabile.
 * </p>
 *
 * @author gabri
 */
public class VariableTypeModifier implements Serializable {

    private final static long serialVersionUID = 1L;
    /**
     * Indica se questa classe è un'insieme di elementi: viene usato per i tipi
     * di variabile "String" oppure "WString"
     */
    private boolean isList = false;
    /**
     * Viene usato per definire un'array di variabili.
     */
    private boolean isArray = false;
    /**
     * Viene usato sia per le liste che per gli array per definire la loro
     * dimensione.
     */
    private int length = 0;
    /**
     * Nel caso degli array è possibile definire un indice differente per la
     * partenza.
     */
    private int start = 0;

    /**
     * Questo costruttore è protetto in quanto una {@link Variable} istanzia la
     * propria istanza di questa classe alla creazione.
     */
    protected VariableTypeModifier() {

    }

    /**
     * Ripristina i valori a quelli di default: viene disattivata sia la
     * modifica di tipo lista, che di tipo array.
     */
    public void reset() {
        isArray = false;
        isList = false;
        length = 0;
        start = 0;
    }

    /**
     * Imposta il modificatore come array se la variabile {@code isArray} è
     * impostata a true. Chiamando questo metodo di conseguenza viene
     * disattivato in automatico la caratteristica {@link #isList}.
     *
     * @param isArray Se impostata a {@code true} allora questa istanza
     * rappresenterà una modifica di tipo array.
     * @param start Indice di inizio dell'array. Deve essere > 0.
     * @param length Lunghezza dell'array. Deve essere > 1.
     */
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

    /**
     * Imposta il modificatore come lista se la variabile {@code isList} è
     * impostata a {@code true}. Chiamando questo metodo di conseguenza viene
     * disattivato in automatico la caratteristica {@link #isArray}.
     *
     * @param isListSe impostata a {@code true} allora questa istanza
     * rappresenterà una modifica di tipo lista.
     * @param length Lunghezza della lista. Deve esser > 1.
     */
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

    /**
     * Restituisce la lunghezza di questo elemento, sia che sia una lista sia
     * che sia un array.
     *
     * @return La lunghezza, > 1.
     */
    public int getLength() {
        return length;
    }

    /**
     * Restituisce l'indice di partenza dell'array, per le liste è sempre 0.
     *
     * @return Indice di partenza.
     */
    public int getStart() {
        return start;
    }

    /**
     * Imposta i dati di questo modificatore prendendoli dal modificatore della
     * variabile passata come argomento.
     *
     * @param v Variabile da cui copiare i modificatori.
     */
    public void of(Variable v) {
        this.isArray = v.typeModifier.isArray;
        this.isList = v.typeModifier.isList;
        this.length = v.typeModifier.length;
        this.start = v.typeModifier.start;
    }

}

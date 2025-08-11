/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.ints.res;

import net.vnleng.generator.commons.block.KeyLock;
import net.vnleng.generator.data.ints.Clonable;

/**
 * Implementa la base dell'interfaccia {@link Resource} aggiungendo il supporto
 * per la richiesta del {@link VariablePack} e obbligando ad usarlo per cambiare
 * nome alla risorsa.
 *
 * @author gabri
 */
public abstract class ResourceElement implements Resource, Clonable<ResourceElement> {

    private static final long serialVersionUID = 1L;
    protected String name;
    protected ResourceType rt;
    protected ResourceElement bounded = null;

    public ResourceElement(ResourceType rt) {
        this.rt = rt;
    }

    /**
     * Restituisce un VariablePack associato ad un {@link PackType}. Se il
     * PackType non è presente nella risorsa viene restituito {@code null},
     * altrimenti se la risorsa contiene un unico {@link PackType} viene
     * restituito indipendentemente da quello che viene richiesto.
     *
     * @param pt Il {@link PackType} da selezionare.
     * @return Il {@link VariablePack} associato (o l'unico presente) oppure
     * {@code null}.
     */
    public abstract VariablePack getVariables(PackType pt);

    @Override
    public final String getName() {
        return this.name;
    }

    /**
     * Imposta il nome della risorsa. Per prevenire cambiamenti in parti del
     * codice non previste questo metodo è bloccato tramite incapsulamento di
     * moduli (non viene esposta la classe {@link KeyLock}).
     *
     * @param k Istanza di KeyLock non nulla per chiamare il metodo
     * @param name Nuovo nome da impostare.
     */
    public final void setName(KeyLock k, String name) {
        if (k != null) {
            this.name = name;
        }
    }

    @Override
    public final ResourceType getType() {
        return this.rt;
    }

    @Override
    public final String toString() {
        return getDeclaration() + "\n" + getDefinition();
    }

    /**
     * Restituisce la risorsa associata, se presente.
     *
     * @return {@link ResourceElement} oppure {@code null} se non è presente
     * nessuna risorsa associata.
     */
    public final ResourceElement getBounded() {
        return bounded;
    }

    @Override
    public abstract ResourceElement clone();

    @Override
    public abstract void restore(ResourceElement reourceFrom);

}

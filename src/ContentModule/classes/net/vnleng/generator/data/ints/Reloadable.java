/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package net.vnleng.generator.data.ints;

/**
 * Le risorse che necessitano di essere ricaricate dopo una modifica di una
 * risorsa associata devono implementare questa interfaccia. Ad esempio le
 * istanze delle FB o FC devono implementarla in caso di modifiche delle
 * interfacce delle variabili.
 *
 * @author gabri
 */
public interface Reloadable {

    /**
     * Ricarica i contenuti a seguito di una modifica di questa risorsa o di una
     * risorsa da cui dipende.
     */
    public void reload();
}

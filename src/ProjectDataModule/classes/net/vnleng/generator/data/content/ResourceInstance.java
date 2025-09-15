/*
 * Copyright (C) 2025 gabri
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.vnleng.generator.data.content;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Rappresenta un elemento che clonerà una risorsa. In questo elemento è
 * possibile definire le variabili di cui sovrascrivere i valori di default.
 *
 * @author gabri
 */
public class ResourceInstance implements Serializable {

    private static final long serialVersionUID = 2L;

    private String name;
    /**
     * Variabili da sovrascrivere: nome - nuovo valore di default
     */
    private final Map<String, String> overrideVariableDefaults;

    protected ResourceInstance(String name) {
        this.name = name;
        overrideVariableDefaults = new HashMap<>();
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Permette di aggiungere o sovrascrivere i valori delle variabili di
     * default
     *
     * @param name Nome della variabile da modificare/aggiungere
     * @param newdefault Valore della variabili
     */
    public void overrideVariable(String name, String newdefault) {
        this.overrideVariableDefaults.put(name, newdefault);
    }

    /**
     * Restituisce le variabili che devono essere sovrascritte rispetto alla
     * risorsa che deve cloneare. Il formato della mappa è:
     * <p>
     * Nome Variabile - Valore di default</p>
     *
     * @return
     */
    public Map<String, String> getOverrideVariableDefaults() {
        return overrideVariableDefaults;
    }

    protected void clearDefaults() {
        this.overrideVariableDefaults.clear();
    }

}

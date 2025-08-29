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

import java.util.Map;

/**
 * Gestisce i metodi protetti delle classi {@link CloneData} e
 * {@link ResourceCloneList}
 *
 * @author gabri
 */
public class CloneHandler {

    private CloneHandler() {

    }

    /**
     * Crea un clone di un CloneData copiando le coppie Nome Variabile e Valore
     * di default ed assegnando un nuovo nome
     *
     * @param name Nuovo nome della risorsa
     * @param newDefaults I valori da copiare
     * @return La CloneData appena creata
     */
    public static CloneData createCloneData(String name, Map<String, String> newDefaults) {
        CloneData cd = new CloneData(name);
        newDefaults.forEach((n, v) -> {
            cd.overrideVariable(n, v);
        });
        return cd;
    }

    /**
     * Pulisce gli elementi di una CloneData.
     * @param cd 
     */
    public static void clearCloneData(CloneData cd) {
        if (cd != null) {
            cd.clearDefaults();
        }
    }

    public static boolean addCloneData(ResourceCloneList rcl, CloneData cd) {
        if (rcl == null || cd == null) {
            return false;
        }
        if (cd.getName().isBlank()) {
            return false;
        }
        rcl.addClone(cd);
        return true;
    }

    public static boolean transferToCloneData(CloneData to, CloneData from) {
        if (to == null) {
            return false;
        }
        to.clearDefaults();
        if (from == null) {
            return true;
        }
        from.getOverrideVariableDefaults().forEach((n, v) -> {
            to.overrideVariable(n, v);
        });
        return true;
    }
}

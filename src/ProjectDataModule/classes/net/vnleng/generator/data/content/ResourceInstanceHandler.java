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
public class ResourceInstanceHandler {

    private ResourceInstanceHandler() {

    }

    /**
     * Crea un clone di un ResourceInstance copiando le coppie Nome Variabile e
     * Valore di default ed assegnando un nuovo nome
     *
     * @param name Nuovo nome della risorsa
     * @param newDefaults I valori da copiare
     * @return La ResourceInstance appena creata
     */
    public static ResourceInstance createCloneData(String name, Map<String, String> newDefaults) {
        ResourceInstance ri = new ResourceInstance(name);
        if (newDefaults != null) {
            newDefaults.forEach((n, v) -> {
                ri.overrideVariable(n, v);
            });
        }
        return ri;
    }

    /**
     * Pulisce gli elementi di una ResourceInstance.
     *
     * @param cd
     */
    public static void clearResourceInstance(ResourceInstance cd) {
        if (cd != null) {
            cd.clearDefaults();
        }
    }

    public static boolean addResourceInstance(ResourceInstanceList rcl, ResourceInstance cd) {
        if (rcl == null || cd == null) {
            return false;
        }
        if (cd.getName().isBlank()) {
            return false;
        }
        rcl.addInstance(cd);
        return true;
    }

    public static boolean transferToResourceInstance(ResourceInstance to, ResourceInstance from) {
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

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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.vnleng.generator.data.ints.res.ResourceElement;

/**
 * Lista di risorse che devono essere clonate partendo da una risorsa di base
 * collegata.
 *
 * @author gabri
 */
public class ResourceCloneList implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title = "Instances of ";
    private final Map<String, CloneData> clones;
    private ResourceElement linkedResource;

    public ResourceCloneList(ResourceElement re) {
        if (re == null) {
            throw new IllegalArgumentException("Can't clone null resource element.");
        }
        title = title + re.getName();
        clones = new HashMap<>();
    }

    public ResourceElement getLinkedResource() {
        return linkedResource;
    }

    public CloneData addClone(String name) {
        CloneData cd = new CloneData(name);
        clones.put(name, cd);
        return cd;
    }

    protected void addClone(CloneData cd) {
        clones.put(cd.getName(), cd);
    }

    public void updateCloneName(String name, String newname) {
        CloneData get = clones.remove(name);
        if (get == null) {
            return;
        }

        get.setName(newname);
        clones.put(newname, get);
    }

    public void removeClone(String name) {
        if (name == null || name.isBlank()) {
            return;
        }
        clones.remove(name);
    }

    public Map<String, CloneData> getClones() {
        return Collections.unmodifiableMap(clones);
    }

    public void clear() {
        clones.clear();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
    }

}

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
public class ResourceInstanceList implements Serializable {

    private static final long serialVersionUID = 2L;

    private String title = "Instances of ";
    protected final Map<String, ResourceInstance> instances;
    private ResourceElement linkedResource;

    public ResourceInstanceList(ResourceElement re) {
        if (re == null) {
            throw new IllegalArgumentException("Can't clone null resource element.");
        }
        title = title + re.getName();
        instances = new HashMap<>();
    }

    public ResourceElement getLinkedResource() {
        return linkedResource;
    }

    public ResourceInstance addInstance(String name) {
        ResourceInstance cd = new ResourceInstance(name);
        instances.put(name, cd);
        return cd;
    }

    protected void addInstance(ResourceInstance cd) {
        instances.put(cd.getName(), cd);
    }

    public void updateInstanceName(String name, String newname) {
        ResourceInstance get = instances.remove(name);
        if (get == null) {
            return;
        }

        get.setName(newname);
        instances.put(newname, get);
    }

    public void removeClone(String name) {
        if (name == null || name.isBlank()) {
            return;
        }
        instances.remove(name);
    }

    public Map<String, ResourceInstance> getInstances() {
        return Collections.unmodifiableMap(instances);
    }

    public void clear() {
        instances.clear();
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

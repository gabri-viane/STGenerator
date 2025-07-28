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
package net.vnleng.generator.data.ints.res;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import net.vnleng.generator.data.ints.var.Variable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

/**
 *
 * @author gabri
 */
public final class VariablePack implements Serializable, Iterable<Variable>, List<Variable> {

    private static final long serialVersionUID = 2L;

    private final PackType type;
    private final List<Variable> orderedVars;
    private final HashMap<String, Variable> variables;

    public VariablePack(PackType type) {
        this.type = type;
        this.orderedVars = new ArrayList<>();
        this.variables = new HashMap<>();
    }

    @Override
    public boolean add(Variable v) {
        if (v == null) {
            return false;
        }
        Variable old = variables.get(v.getName());
        if (old != null) {
            int index = orderedVars.indexOf(old);
            orderedVars.remove(index);
            orderedVars.add(index, v);
            variables.put(v.getName(), v);
        } else {
            orderedVars.add(v);
            variables.put(v.getName(), v);
        }
        return true;
    }

    public Variable add(Variable v, int index) {
        if (v == null || index < -1 || index > orderedVars.size()) {
            return null;
        }
        Variable old = variables.get(v.getName());
        if (old != null) {
            int indexold = orderedVars.indexOf(old);
            orderedVars.remove(indexold);
            orderedVars.add(index, v);
            variables.put(v.getName(), v);
        } else {
            orderedVars.add(index, v);
            variables.put(v.getName(), v);
        }
        return old;
    }

    public void rename(Variable v, String newName) {
        Variable remove = variables.remove(v.getName());
        v.setName(newName);
        if (remove == null) {
            add(v);
            return;
        }
        variables.put(newName, v);
    }

    public void copyOf(VariablePack vp, boolean clear, boolean keepDefaults) {
        HashMap<String, String> oldDefaults = new HashMap<>();
        if (keepDefaults) {
            variables.forEach((s, v) -> oldDefaults.put(s, v.getDefaultValue()));
        }
        if (clear) {
            variables.clear();
            orderedVars.clear();
        }
        if (vp == null || vp.orderedVars.isEmpty()) {
            return;
        }
        if (keepDefaults) {
            vp.orderedVars.forEach((t) -> {
                Variable v = t.copy();
                v.setDefaultValue(oldDefaults.getOrDefault(v.getName(), v.getDefaultValue()));

                orderedVars.add(v);
                variables.put(v.getName(), v);
            });
        } else {
            vp.orderedVars.forEach((t) -> {
                Variable v = t.copy();
                orderedVars.add(v);
                variables.put(v.getName(), v);
            });
        }
    }

    public boolean removeVariable(Variable v) {
        if (v == null) {
            return false;
        }
        Variable old = variables.remove(v.getName());
        if (old != null) {
            int index = orderedVars.indexOf(old);
            orderedVars.remove(index);
            return true;
        }
        return false;
    }

    public Variable removeVariable(int index) {
        if (index < 0 || index >= variables.size()) {
            return null;
        }
        Variable remove = orderedVars.remove(index);
        if (remove != null) {
            variables.remove(remove.getName());
            return remove;
        }
        return remove;
    }

    public Variable get(String name) {
        return variables.get(name);
    }

    @Override
    public Variable get(int index) {
        return orderedVars.get(index);
    }

    public PackType getType() {
        return type;
    }

    @Override
    public boolean isEmpty() {
        return orderedVars.isEmpty();
    }

    @Override
    public int size() {
        return orderedVars.size();
    }

    @Override
    public Iterator<Variable> iterator() {
        return new Iter();
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof Variable v) {
            return this.variables.containsKey(v.getName());
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        return orderedVars.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return orderedVars.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof Variable v) {
            return this.removeVariable(v);
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.orderedVars.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Variable> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        c.iterator().forEachRemaining((t) -> {
            add(t.copy());
        });
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Variable> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        for (Variable t : c) {
            add(index++, t.copy());
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        c.iterator().forEachRemaining((t) -> {
            this.remove(t);
        });
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        copyOf(null, true, false);
    }

    @Override
    public Variable set(int index, Variable element) {
        return add(element, index);
    }

    @Override
    public void add(int index, Variable element) {
        add(element, index);
    }

    @Override
    public Variable remove(int index) {
        return removeVariable(index);
    }

    @Override
    public int indexOf(Object o) {
        return orderedVars.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return orderedVars.lastIndexOf(o);
    }

    @Override
    public ListIterator<Variable> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Variable> listIterator(int index) {
        return null;
    }

    @Override
    public List<Variable> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public void addFirst(Variable e) {
        add(e, 0);
    }

    @Override
    public void addLast(Variable e) {
        add(e);
    }

    @Override
    public Variable getFirst() {
        return orderedVars.getFirst();
    }

    @Override
    public Variable getLast() {
        return orderedVars.getLast();
    }

    private class Iter implements Iterator<Variable> {

        private final Iterator<Variable> mainiter;
        private Variable lastReturned;

        public Iter() {
            mainiter = VariablePack.this.orderedVars.iterator();
        }

        @Override
        public boolean hasNext() {
            return mainiter.hasNext();
        }

        @Override
        public Variable next() {
            lastReturned = mainiter.next();
            return lastReturned;
        }

        @Override
        public void remove() {
            mainiter.remove();
            if (lastReturned != null) {
                VariablePack.this.variables.remove(lastReturned.getName());
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super Variable> action) {
            mainiter.forEachRemaining(action);
        }
    }

}

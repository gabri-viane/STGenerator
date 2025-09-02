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
import net.vnleng.generator.commons.block.KeyLock;

/**
 * Questa classse gestisce gli insiemi di {@link Variable} garantendono
 * l'unicità per nome. La classe è sia una {@link List} che una {@link HashMap}
 * in modo tale da poter effettuare una ricerca per nome oppure per ordinamento.
 * <p>
 * Questa classe assicura che le variabili inserite siano univoche di nome e
 * fornisce metodi di aggiunta e rimozione per garantirlo. Inoltre, per evitare
 * discrepanze viene messo a disposizione un metodo per rinominare una variabile
 * aggiornando così anche la lista interna e verificando che non sia presente
 * una stessa variabile.<br/>
 * Variabili con lo stesso nome vengono sovrascritte.
 * </p>
 * <p>
 * Tramite il metodo {@link #copyOf(net.vnleng.generator.data.ints.res.VariablePack, boolean, boolean)
 * } è possibile copiare le variabili di questa classe duplicando tutte le
 * istanze.
 * </p>
 *
 * <p>
 * Questo inoltre permette di iterare la lista di variabili o di inserirle in
 * una certa posizione, se desiderato.
 * </p>
 * <p>
 * Per comodità viene utilizzata la enum {@link PackType} per identificare di
 * che tipologia di pacchetto è questa lista di variabili.
 * </p>
 *
 *
 * @author gabri
 */
public final class VariablePack implements Serializable, Iterable<Variable>, List<Variable> {

    private static final long serialVersionUID = 2L;

    /**
     * Tipologia di lista d'appartenenza delle variabili.
     */
    private final PackType type;
    /**
     * Lista delle variabili in modo da poter sfruttare un ordinamento
     * sequenziale.
     */
    private final List<Variable> orderedVars;
    /**
     * Mappa delel variabili per poter inserire e gestirele per nome.
     */
    private final HashMap<String, Variable> variables;

    /**
     * Crea una nuova istanza per gestire delle variabili.
     *
     * @param type Il tipo di lista che rappresenta.
     */
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

    /**
     * Aggiunge una variabile alla posizione desiderata.
     *
     * @param v Variabile da aggiungere
     * @param index Posizione a cui aggiungere la variabile
     * @return Restituisce la precedente variabile oppure {@code null} se la
     * posizione non è valida o non è presente una variabile.
     */
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

    /**
     * Rinomina una variabile assicurandosi di aggiornare i valori e di non
     * avere variabili con lo stesso nome.
     * <br/>
     * Nel caso di duplicati la variabile già presente verrà sovrascritta con
     * quella nuova.
     *
     * @param v Variabile da rinominare.
     * @param newName Nuovo nome della variabile.
     */
    public void rename(Variable v, String newName) {
        Variable remove = variables.remove(v.getName());
        v.setName(KeyLock.KEY, newName);
        if (remove == null) {
            add(v);
            return;
        }
        variables.put(newName, v);
    }

    /**
     * Crea una copia di un {@link VariablePack} duplicando tutti i valori e
     * inserendoli in questa istanza. Se la variabile {@code keepDefualts} è
     * impostata a {@code true} allora se è presente già in questa lista una
     * variabile con lo stesso nome viene mantenuto il valore precedente di
     * default.
     *
     * @param vp Lista da cui copiare i contenuti
     * @param clear Se vale {@code true} allora esegue un reset della lista
     * prima di copiare le variabili.
     * @param keepDefaults Mantiene i default delle variabili presenti.
     */
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

    /**
     * Associa le variabili di un {@link VariablePack} inserendo le istanze. 
     * Se la variabile {@code keepDefualts} è impostata a {@code true} allora se
     * è il valore precedente di default.
     *
     * @param vp Lista da cui referenziare i contenuti
     * @param clear Se vale {@code true} allora esegue un reset della lista
     * prima di copiare le variabili.
     * @param keepDefaults Mantiene i default delle variabili presenti.
     */
    public void bind(VariablePack vp, boolean clear, boolean keepDefaults) {
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
                t.setDefaultValue(oldDefaults.getOrDefault(t.getName(), t.getDefaultValue()));
                orderedVars.add(t);
                variables.put(t.getName(), t);
            });
        } else {
            vp.orderedVars.forEach((t) -> {
                orderedVars.add(t);
                variables.put(t.getName(), t);
            });
        }
    }

    /**
     * Rimuove una variabile aggiornando la lista.
     *
     * @param v La variabile da rimuovere.
     * @return {@code true} se la variabile è stata rimossa.
     */
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

    /**
     * Rimuove una variabile usando la posizione nella lista e aggiornandola.
     *
     * @param index L'indice della variabile da rimuovere.
     * @return {@code true} se la variabile è stata rimossa.
     */
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

    /**
     * Prende una variabile tramite il suo nome.
     *
     * @param name Il nome della variabile da prendere.
     * @return La variabile, se trovata, altrimenti {code null}.
     */
    public Variable get(String name) {
        return variables.get(name);
    }

    @Override
    public Variable get(int index) {
        return orderedVars.get(index);
    }

    /**
     * Restituisce il tipo di lista.
     *
     * @return Tipo di pacchetto di variabili.
     */
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

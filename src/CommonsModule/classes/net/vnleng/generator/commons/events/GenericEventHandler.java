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
package net.vnleng.generator.commons.events;

/**
 * Classe che permette di creare un handler di eventi completo con l'handler
 * dell'evento e l'handler di fine evento. Questa classe è di tipo builder dove
 * i metodi ritornano l'istanza stessa.
 *
 * @author gabri
 */
public final class GenericEventHandler<T, K extends CallbackEventHandler<T>> {

    private CallbackEventHandler<Void> onEndHandler = (element) -> {
    };
    private K eventHandler = null;

    public GenericEventHandler() {
    }

    /**
     * Imposta l'handler che gestisce l'evento e restituisce l'istanza per
     * questo costruttore.
     *
     * @param eventHandler L'handler che gestirà la chiamata
     * @return
     */
    public GenericEventHandler<T, K> setHandler(K eventHandler) {
        this.eventHandler = eventHandler;
        return this;
    }

    /**
     * Imposta l'handler che viene chiamato dopo che l'evento viene gestito. In
     * questo evento è utile sottoscrivere
     * <p>
     * Lo schema di funzionamento è il seguente:<br/>
     * Evento scatenato <br/>
     * -> chiamata dell'handler {@link #eventHandler}<br/>
     * -> chiamata dell'handler di fine {@link #onEndHandler}<br/>
     * -> fine gestione evento
     * </p>
     *
     * @param onEndEventHandler Handler di fine evento
     * @return L'istanza stessa come costruttore.
     */
    public GenericEventHandler<T, K> setOnEndHandler(CallbackEventHandler<Void> onEndEventHandler) {
        if (onEndEventHandler == null) {
            this.onEndHandler = (element) -> {
            };
            return this;
        }
        this.onEndHandler = onEndEventHandler;
        return this;
    }

    /**
     * Chiamata all'handler dell'evento
     *
     * @param data Il dato da passare all'handler
     */
    public void handle(T data) {
        if (eventHandler == null) {
            return;
        }
        eventHandler.callback(data);
    }

    /**
     * Chiamata all'handler di fine evento
     */
    public void onEnd() {
        onEndHandler.callback(null);
    }

}

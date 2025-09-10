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

import java.util.ArrayList;
import java.util.List;

/**
 * Gestisce una lista di Handler.
 *
 * @author gabri
 */
public final class EventHandlerList<T, K extends CallbackEventHandler<T>> {

    private final ArrayList<GenericEventHandler<T, K>> handlers;

    public EventHandlerList() {
        this.handlers = new ArrayList<>();
    }

    public void addHandler(GenericEventHandler<T, K> handler) {
        handlers.add(handler);
    }

    public void removeHandler(GenericEventHandler<T, K> handler) {
        handlers.remove(handler);
    }

    public void handle(T data) {
        List<GenericEventHandler<T, K>> bkp = List.copyOf(handlers);
        bkp.forEach((t) -> {
            t.handle(data);
        });
        bkp.forEach(GenericEventHandler::onEnd);
    }

}

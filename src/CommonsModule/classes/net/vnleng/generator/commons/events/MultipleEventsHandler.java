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

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gabri
 */
public final class MultipleEventsHandler<T, K, V extends CallbackEventHandler<K>> {

    private static final CallbackEventHandler<? extends Object> EMPTY_HANDLER = (Object element) -> {
    };

    private final Map<T, V> handlers;
    private CallbackEventHandler<? extends Object> backupHandler = EMPTY_HANDLER;

    public MultipleEventsHandler() {
        handlers = new HashMap<>();
    }

    public void setSecureHandler(V secureHandler) {
        if (secureHandler != null) {
            this.backupHandler = secureHandler;
        } else {
            this.backupHandler = EMPTY_HANDLER;
        }
    }

    public void addHandler(T key, V handler) {
        handlers.put(key, handler);
    }

    public V getHandler(T key) {
        return handlers.get(key);
    }

    public CallbackEventHandler<? extends Object> getSecureHandler(T key) {
        V get = handlers.get(key);
        if (get != null) {
            return get;
        }
        return backupHandler;
    }

}

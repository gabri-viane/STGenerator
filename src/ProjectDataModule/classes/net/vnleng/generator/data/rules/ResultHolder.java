/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.rules;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gabri
 * @param <T> Oggetto che è stato elaborato
 */
public class ResultHolder<T> {

    private final T computedElement;
    private final Map<String, Object> properties;

    public ResultHolder(T computedElement) {
        this.computedElement = computedElement;
        this.properties = new HashMap<>();
    }

    public Map<String, Object> getProperties() {
        return Map.copyOf(this.properties);
    }
    
    public Object get(String propName){
        return properties.get(propName);
    }

    public void addProperty(String propertyName, Object result) {
        this.properties.put(propertyName, result);
    }

    public T getComputedElement() {
        return computedElement;
    }

}

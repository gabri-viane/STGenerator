/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.rules;

import net.vnleng.generator.data.rules.ints.FunctionCallback;
import net.vnleng.generator.data.rules.ints.RuleType;
import net.vnleng.generator.data.rules.ints.Rule;
import net.vnleng.generator.data.ints.ResourceElement;
import java.util.HashMap;
import java.util.Map;

/**
 * Permette di eseguire un'azione per ogni elemento presente nel
 * {@link RuleApplier}.
 *
 * @author gabri
 * @param <T> Tipo di dato restituito dalla funzione di callback.
 * @param <K> Funzione di callback chiamata per ogni {@link ResourceElement} nel
 * {@link RuleApplier}.
 */
public class ForEachRule<T, K extends FunctionCallback<ResourceElement, T>> implements Rule<Map<String, T>, RuleApplier, Void> {

    private static final long serialVersionUID = 1L;
    private final K callback;

    public ForEachRule(K callback) {
        this.callback = callback;
    }

    @Override
    public RuleType getRuleType() {
        return RuleType.LOOP_RULE;
    }

    @Override
    public Map<String, T> compute(RuleApplier generator, Void v) {
        Map<String, ResourceElement> resources = Map.copyOf(generator.resources);
        Map<String, T> results = new HashMap<>();
        resources.forEach((name, element) -> {
            results.put(name, this.callback.onCall(element));
        });
        return results;
    }

}

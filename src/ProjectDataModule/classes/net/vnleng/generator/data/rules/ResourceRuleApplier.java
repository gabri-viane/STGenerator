/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.rules;

import net.vnleng.generator.data.rules.ints.ResourceRule;
import net.vnleng.generator.data.rules.ints.RuleType;
import net.vnleng.generator.data.ints.ResourceElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author gabri
 */
public class ResourceRuleApplier implements Serializable {

    private static final long serialVersionUID = 1L;

    protected HashMap<RuleType, List<ResourceRule>> rules;

    public ResourceRuleApplier() {
        this.rules = new HashMap<>();
    }

    public void addRule(int position, ResourceRule rl) {
        if (rl != null) {
            List<ResourceRule> get = rules.get(rl.getRuleType());
            if (get != null) {
                get.add(position, rl);
            } else {
                ArrayList<ResourceRule> list = new ArrayList<>();
                list.add(rl);
                rules.put(rl.getRuleType(), list);
            }
        }
    }

    public void addRule(ResourceRule rl) {
        if (rl != null) {
            List<ResourceRule> get = rules.get(rl.getRuleType());
            if (get != null) {
                get.add(rl);
            } else {
                ArrayList<ResourceRule> list = new ArrayList<>();
                list.add(rl);
                rules.put(rl.getRuleType(), list);
            }
        }
    }

    public ResultHolder<ResourceElement> applyRules(ResourceElement resource) {
        ResultHolder<ResourceElement> result = new ResultHolder(resource);
        for (RuleType rt : RuleType.values()) {
            List<ResourceRule> rls = this.rules.get(rt);
            if (rls == null) {
                continue;
            }
            rls.forEach((rl) -> {
                rl.compute(resource, result);
            });
        }
        return result;
    }
}

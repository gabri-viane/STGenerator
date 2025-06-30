/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.rules;

import net.vnleng.generator.data.rules.ints.RuleType;
import net.vnleng.generator.data.rules.ints.Rule;
import net.vnleng.generator.data.ints.ResourceElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gabri
 */
public class RuleApplier implements Serializable {

    private static final long serialVersionUID = 1L;

    protected HashMap<RuleType, List<Rule>> rules;
    protected Map<String, ResourceElement> resources;

    public RuleApplier() {
        this.rules = new HashMap<>();
        this.resources = new HashMap<>();
    }

    public void addRule(int position, Rule rl) {
        if (rl != null) {
            List<Rule> get = rules.get(rl.getRuleType());
            if (get != null) {
                get.add(position, rl);
            } else {
                ArrayList<Rule> list = new ArrayList<>();
                list.add(rl);
                rules.put(rl.getRuleType(), list);
            }
        }
    }

    public void addRule(Rule rl) {
        if (rl != null) {
            List<Rule> get = rules.get(rl.getRuleType());
            if (get != null) {
                get.add(rl);
            } else {
                ArrayList<Rule> list = new ArrayList<>();
                list.add(rl);
                rules.put(rl.getRuleType(), list);
            }
        }
    }

    public void addResource(ResourceElement re) {
        if (re == null) {
            return;
        }
        this.resources.put(re.getName(), re);
    }

    public void removeResource(ResourceElement re) {
        if (re == null) {
            return;
        }
        this.resources.remove(re.getName());
    }

    public void compute() {
        Map<RuleType, List<Rule>> _rules = Map.copyOf(this.rules);
        BoolAtom bool = new BoolAtom(false);

        for (RuleType rt : RuleType.values()) {
            List<Rule> rls = _rules.get(rt);
            if (rls == null) {
                continue;
            }
            rls.forEach((rl) -> {
                Object compute = rl.compute(this, null);
                if (compute instanceof Rule<?, ?, ?>) {
                    bool.set(true);
                }
            });
        }

        if (bool.get()) {
            this.compute();
        }
    }

    private static class BoolAtom {

        private boolean val;

        public BoolAtom(boolean val) {
            this.val = val;
        }

        public void set(boolean val) {
            this.val = val;
        }

        public boolean get() {
            return this.val;
        }

    }
}

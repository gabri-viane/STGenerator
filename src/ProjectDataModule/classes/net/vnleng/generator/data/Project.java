/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data;

import net.vnleng.generator.data.rules.RuleApplier;
import net.vnleng.generator.data.ints.ResourceElement;
import net.vnleng.generator.data.ints.ResourceType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.vnleng.generator.data.scl.impls.DataBlockInstanceElement;

/**
 *
 * @author gabri
 */
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String projectName;
    private final Map<ResourceType, Map<String, ResourceElement>> resources;
    private final Map<ResourceElement, List<ResourceElement>> binded_resources;
    private final List<RuleApplier> ruleAppliers;

    public Project(String name) {
        this.projectName = name;
        this.resources = new HashMap<>();
        this.binded_resources = new HashMap<>();
        this.ruleAppliers = new ArrayList<>();

        resources.put(ResourceType.Function, new HashMap<>());
        resources.put(ResourceType.DataBlock, new HashMap<>());
        resources.put(ResourceType.FunctionBlock, new HashMap<>());
        resources.put(ResourceType.FunctionInstance, new HashMap<>());
    }

    public void addResource(ResourceElement re) {
        ResourceType rt = re.getType();
        resources.get(rt).put(re.getName(), re);
        /*
        Nel caso di una DB instance per comodità salvo in un una mappa la funzione a cui è associato
         */
        if (re.getType().equals(ResourceType.FunctionInstance)) {
            ResourceElement fun = ((DataBlockInstanceElement) re).getBindedFunction();
            List<ResourceElement> elements = binded_resources.get(fun);
            if (elements != null) {
                elements.add(re);
            } else {
                elements = new ArrayList<>();
                elements.add(re);
                binded_resources.put(re, elements);
            }
        }
    }

    public ResourceElement getResource(ResourceType rt, String name) {
        ResourceElement toret = null;
        Map<String, ResourceElement> typeResources = resources.get(rt);
        if (typeResources != null) {
            toret = typeResources.get(name);
        }
        return toret;
    }

    public Map<ResourceType, Map<String, ResourceElement>> getResources() {
        return Map.copyOf(resources);
    }

    public Map<ResourceElement, List<ResourceElement>> getBindedResources() {
        return Map.copyOf(binded_resources);
    }

    public void removeResource(ResourceElement re) {
        if (re == null) {
            return;
        }
        Map<String, ResourceElement> get = resources.get(re.getType());
        if (get == null) {
            return;
        }
        ResourceElement remove = get.remove(re.getName());
        /*
        Rimuovo l'istanza associata anche alla FB o FC se era una DB d'istanza
        */
        if(remove == null){
            return;
        }
        if (remove.getType().equals(ResourceType.FunctionInstance)) {
            ResourceElement fun = ((DataBlockInstanceElement) re).getBindedFunction();
            List<ResourceElement> elements = binded_resources.get(fun);
            if (elements != null) {
                elements.remove(re);
            }
        }
    }

    public void addRuleApplier(RuleApplier ra) {
        this.ruleAppliers.add(ra);
    }

    public String getProjectName() {
        return projectName;
    }

}

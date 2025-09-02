/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data;

import net.vnleng.generator.data.rules.RuleApplier;
import net.vnleng.generator.data.ints.res.ResourceElement;
import net.vnleng.generator.data.ints.res.ResourceType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.vnleng.generator.commons.block.KeyLock;
import net.vnleng.generator.data.content.CloneData;
import net.vnleng.generator.data.content.CloneHandler;
import net.vnleng.generator.data.content.ResourceCloneList;
import net.vnleng.generator.data.scl.impls.DataBlockInstanceElement;

/**
 *
 * @author gabri
 */
public class Project implements Serializable {

    private static final long serialVersionUID = 2L;

    private final String projectName;
    private final Map<ResourceType, Map<String, ResourceElement>> resources;
    private final Map<ResourceElement, List<ResourceElement>> binded_resources;
    /**
     * Contiene i link delle instanze di CloneData, contenute in
     * {@link #projectData}, collegati alle risorse del progetto.
     */
    private final Map<ResourceElement, ResourceCloneList> instances;
    /**
     * Contiene tutte le istanze dai dati di base che vengono usati per clonare
     * una risorsa: tipo "PT912" con tutte le
     */
    private final Map<String, CloneData> projectData;
    private final List<RuleApplier> ruleAppliers;

    private ResourceElement lastEditedElement = null;

    public Project(String name) {
        this.projectName = name;
        this.resources = new HashMap<>();
        this.binded_resources = new HashMap<>();
        this.ruleAppliers = new ArrayList<>();
        this.instances = new HashMap<>();
        this.projectData = new HashMap<>();

        resources.put(ResourceType.Function, new HashMap<>());
        resources.put(ResourceType.DataBlock, new HashMap<>());
        resources.put(ResourceType.FunctionBlock, new HashMap<>());
        resources.put(ResourceType.FunctionInstance, new HashMap<>());
    }

    public void renameResource(ResourceElement re, String newName) {
        String oldName = re.getName();
        resources.get(re.getType()).remove(oldName);
        re.setName(KeyLock.KEY, newName);
        resources.get(re.getType()).put(newName, re);
        lastEditedElement = re;
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
                if (!elements.contains(re)) {
                    elements.add(re);
                }
            } else {
                elements = new ArrayList<>();
                elements.add(re);
                binded_resources.put(fun, elements);
            }
        }
        instances.put(re, new ResourceCloneList(re));
        lastEditedElement = re;
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

    public ResourceElement getLastEdited() {
        return this.lastEditedElement;
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
        if (remove == null) {
            return;
        }
        if (remove.getType().equals(ResourceType.FunctionInstance)) {
            ResourceElement fun = ((DataBlockInstanceElement) re).getBindedFunction();
            List<ResourceElement> elements = binded_resources.get(fun);
            if (elements != null) {
                elements.remove(re);
            }
        }

        ResourceCloneList rem = instances.remove(re);
        if (rem != null) {
            rem.clear();
        }
        if (remove == lastEditedElement) {
            lastEditedElement = null;
        }
    }

    public void addRuleApplier(RuleApplier ra) {
        this.ruleAppliers.add(ra);
    }

    /**
     * Crea un'istanza di CloneData con i valori di default delle variabili da
     * sovrascrivere.
     *
     * @param name Il nome della risorsa:e.g. "PT201"
     * @param variableDefaults Una mappa di: "Nome Var -
     */
    public void createCloneData(String name, Map<String, String> variableDefaults) {
        CloneData cd = CloneHandler.createCloneData(name, variableDefaults);
        if (projectData.containsKey(name)) {
            CloneData get = projectData.get(name);
            if (variableDefaults == null || variableDefaults.isEmpty()) {
                CloneHandler.clearCloneData(get);
            } else {
                CloneHandler.transferToCloneData(get, cd);
            }
        } else {
            projectData.put(name, cd);
        }
    }

    /**
     * Collega una CloneData ad una risorsa del progetto.
     *
     * @param cd La CloneData da collegare
     * @param re La risorsa a cui collegare il dato
     * @return
     */
    public boolean bindCloneData(CloneData cd, ResourceElement re) {
        if (cd == null || re == null) {
            return false;
        }
        ResourceCloneList get = instances.get(re);
        if (get == null) {
            return false;
        }
        return CloneHandler.addCloneData(get, cd);
    }

    public String getProjectName() {
        return projectName;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.shared;

import net.vnleng.generator.data.shared.listeners.SharedDataChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import net.vnleng.generator.data.Project;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.vnleng.generator.commons.events.EventHandlerList;
import net.vnleng.generator.commons.events.GenericEventHandler;
import net.vnleng.generator.data.content.ResourceInstance;
import net.vnleng.generator.data.ints.res.ResourceElement;
import net.vnleng.generator.data.serialization.ProjectSerializer;
import net.vnleng.generator.data.shared.listeners.DataListener;
import net.vnleng.generator.data.shared.listeners.ProjectListener;

/**
 *
 * @author gabri
 */
public class SharedData {

    private final EventHandlerList<Project, ProjectListener> projectOpenedEventListeners;
    private final EventHandlerList<Project, ProjectListener> projectClosedEventListeners;
    private final EventHandlerList<Project, ProjectListener> projectEditedEventListeners;
    private final EventHandlerList<ResourceInstance, DataListener> instanceBindedEventListeners;
    private final EventHandlerList<ResourceInstance, DataListener> instanceCreatedEventListeners;
    private final EventHandlerList<ResourceInstance, DataListener> instanceRemovedEventListeners;
    private final List<SharedDataChangeListener<SharedData>> saveEventListeners;

    private boolean hasBeenEdited = false;
    private boolean hasFileAssocieted = false;
    private String filePath = null;

    private Project p;

    public SharedData() {
        this.projectOpenedEventListeners = new EventHandlerList<>();
        this.projectClosedEventListeners = new EventHandlerList<>();
        this.projectEditedEventListeners = new EventHandlerList<>();
        this.instanceBindedEventListeners = new EventHandlerList<>();
        this.instanceCreatedEventListeners = new EventHandlerList<>();
        this.instanceRemovedEventListeners = new EventHandlerList<>();
        this.saveEventListeners = new ArrayList<>();
    }

    public void setOpenedProject(Project p, boolean fromFile, String path) {
        this.p = p;
        if (fromFile && path != null && !path.isBlank()) {
            hasFileAssocieted = true;
            filePath = path;
            hasBeenEdited = false;
        } else {
            hasFileAssocieted = false;
            filePath = null;
            hasBeenEdited = true;
        }
        projectOpenedEventListeners.handle(p);
    }

    public GenericEventHandler<Project, ProjectListener> addProjectListener(ProjectListener.ProjectEventType type) {
        GenericEventHandler<Project, ProjectListener> handler = new GenericEventHandler<>();
        switch (type) {
            case CLOSED ->
                this.projectClosedEventListeners.addHandler(handler);
            case EDITED ->
                this.projectEditedEventListeners.addHandler(handler);
            case OPENED ->
                this.projectOpenedEventListeners.addHandler(handler);
        }
        return handler;
    }

    public GenericEventHandler<ResourceInstance, DataListener> addResourceInstanceListener(DataListener.DataEventType type) {
        GenericEventHandler<ResourceInstance, DataListener> handler = new GenericEventHandler<>();
        switch (type) {
            case INSTANTIATED ->
                this.instanceBindedEventListeners.addHandler(handler);
            case CREATED ->
                this.instanceCreatedEventListeners.addHandler(handler);
            case REMOVED ->
                this.instanceRemovedEventListeners.addHandler(handler);
        }
        return handler;
    }

    public void addSaveEventListener(SharedDataChangeListener<SharedData> listener) {
        this.saveEventListeners.add(listener);
    }

    public void addResource(ResourceElement re) {
        if (re != null) {
            p.addResource(re);
            boolean previous = hasBeenEdited;
            this.hasBeenEdited = true;
            projectEditedEventListeners.handle(p);
            if (this.hasBeenEdited != previous) {
                List.copyOf(saveEventListeners).forEach(t -> {
                    t.callback(this);
                });
            }
        }
    }

    public void renameResource(ResourceElement re, String newName) {
        if (re != null && newName != null && !newName.isBlank()) {
            p.renameResource(re, newName);
            boolean previous = hasBeenEdited;
            this.hasBeenEdited = true;
            projectEditedEventListeners.handle(p);
            if (this.hasBeenEdited != previous) {
                List.copyOf(saveEventListeners).forEach(t -> {
                    t.callback(this);
                });
            }
        }
    }

    public void removeResource(ResourceElement re) {
        if (re != null) {
            p.removeResource(re);
            boolean previous = hasBeenEdited;
            this.hasBeenEdited = true;
            projectEditedEventListeners.handle(p);
            if (this.hasBeenEdited != previous) {
                List.copyOf(saveEventListeners).forEach(t -> {
                    t.callback(this);
                });
            }
        }
    }

    /**
     * Permette di creare, ed in caso associare ad una risorsa, una
     * ResourceInstance.
     *
     * @param re Se {@code re != null} allora viene registrata la clone data
     * alla risorsa.
     * @param name Il nome della risorsa da creare, non può essere nullo o
     * vuoto.
     * @param values La mappa dei valori di default che la risorsa sovrascriverà
     * alla generazione.
     * @return Restituisce {@code true} nel caso in cui la risorsa venga creata
     * ed associata correttamente.
     */
    public boolean createCloneData(ResourceElement re, String name, Map<String, String> values) {
        if (name == null || name.isBlank()) {
            return false;
        }
        boolean result = p.createResourceInstance(name, values);
        ResourceInstance ri = p.getInstance(re, name);
        if (re == null || !result) {
            if (result) {
                hasBeenEdited = true;
                projectEditedEventListeners.handle(p);
            }
            return result;
        }
        result = p.bindResourceInstance(name, re);
        if (result) {
            hasBeenEdited = true;
            instanceBindedEventListeners.handle(ri);
            projectEditedEventListeners.handle(p);
        }
        return result;
    }

    public void save(String filepath) throws FileNotFoundException, IOException {
        ProjectSerializer.serializeProject(p, filepath);
        filePath = filepath;
        boolean previous = hasBeenEdited;
        hasBeenEdited = false;
        this.hasFileAssocieted = true;
        if (this.hasBeenEdited != previous) {
            List.copyOf(saveEventListeners).forEach(t -> {
                t.callback(this);
            });
        }
    }

    public Project getProject() {
        return p;
    }

    public void close() {
        p = null;
        hasBeenEdited = false;
        hasFileAssocieted = false;
        filePath = null;
        projectClosedEventListeners.handle(p);
    }

    public boolean hasBeenEdited() {
        return hasBeenEdited;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean hasFileAssocieted() {
        return hasFileAssocieted;
    }

}

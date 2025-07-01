/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.shared;

import java.io.FileNotFoundException;
import java.io.IOException;
import net.vnleng.generator.data.Project;
import java.util.ArrayList;
import java.util.List;
import net.vnleng.generator.data.ints.ResourceElement;
import net.vnleng.generator.data.serialization.ProjectSerializer;

/**
 *
 * @author gabri
 */
public class SharedData {

    private final List<SharedDataChangeListener<Project>> projectOpenedEventListeners;
    private final List<SharedDataChangeListener<Project>> projectClosedEventListeners;
    private final List<SharedDataChangeListener<Project>> projectEditedEventListeners;
    private final List<SharedDataChangeListener<SharedData>> saveEventListeners;
    private boolean hasBeenEdited = false;

    private boolean hasFileAssocieted = false;
    private String filePath = null;

    private Project p;

    public SharedData() {
        this.projectOpenedEventListeners = new ArrayList<>();
        this.projectClosedEventListeners = new ArrayList<>();
        this.projectEditedEventListeners = new ArrayList<>();
        this.saveEventListeners = new ArrayList<>();
    }

    public void setOpenedProject(Project p, boolean fromFile, String path) {
        this.p = p;
        if (fromFile && path != null && !path.isBlank()) {
            hasFileAssocieted = true;
            filePath = path;
        } else {
            hasFileAssocieted = false;
            filePath = null;
        }
        List.copyOf(projectOpenedEventListeners).forEach((t) -> {
            t.onChange(p);
        });
    }

    public void addProjectOpenedEventListener(SharedDataChangeListener<Project> listener) {
        this.projectOpenedEventListeners.add(listener);
    }

    public void addProjectClosedEventListener(SharedDataChangeListener<Project> listener) {
        this.projectClosedEventListeners.add(listener);
    }

    public void addProjectEditedEventListener(SharedDataChangeListener<Project> listener) {
        this.projectEditedEventListeners.add(listener);
    }

    public void addSaveEventListener(SharedDataChangeListener<SharedData> listener) {
        this.saveEventListeners.add(listener);
    }

    public void addResource(ResourceElement re) {
        if (re != null) {
            p.addResource(re);
            boolean previous = hasBeenEdited;
            this.hasBeenEdited = true;
            List.copyOf(projectEditedEventListeners).forEach((t) -> {
                t.onChange(p);
            });
            if (this.hasBeenEdited != previous) {
                List.copyOf(saveEventListeners).forEach(t -> {
                    t.onChange(this);
                });
            }
        }
    }

    public void renameResource(ResourceElement re, String newName) {
        if (re != null && newName != null && !newName.isBlank()) {
            p.renameResource(re, newName);
            boolean previous = hasBeenEdited;
            this.hasBeenEdited = true;
            List.copyOf(projectEditedEventListeners).forEach((t) -> {
                t.onChange(p);
            });
            if (this.hasBeenEdited != previous) {
                List.copyOf(saveEventListeners).forEach(t -> {
                    t.onChange(this);
                });
            }
        }
    }

    public void removeResource(ResourceElement re) {
        if (re != null) {
            p.removeResource(re);
            boolean previous = hasBeenEdited;
            this.hasBeenEdited = true;
            List.copyOf(projectEditedEventListeners).forEach((t) -> {
                t.onChange(p);
            });
            if (this.hasBeenEdited != previous) {
                List.copyOf(saveEventListeners).forEach(t -> {
                    t.onChange(this);
                });
            }
        }
    }

    public void save(String filepath) throws FileNotFoundException, IOException {
        ProjectSerializer.serializeProject(p, filePath);
        filePath = filepath;
        boolean previous = hasBeenEdited;
        hasBeenEdited = false;
        if (this.hasBeenEdited != previous) {
            List.copyOf(saveEventListeners).forEach(t -> {
                t.onChange(this);
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
        this.projectClosedEventListeners.forEach(l -> l.onChange(p));
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

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
    private final List<SharedDataChangeListener<Project>> projectEditedEventListeners;
    private boolean hasBeenEdited = false;

    private boolean hasFileAssocieted = false;
    private String filePath = null;

    private Project p;

    public SharedData() {
        this.projectOpenedEventListeners = new ArrayList<>();
        this.projectEditedEventListeners = new ArrayList<>();
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

    public void addProjectEditedEventListener(SharedDataChangeListener<Project> listener) {
        this.projectEditedEventListeners.add(listener);
    }

    public void addResource(ResourceElement re) {
        if (re != null) {
            p.addResource(re);
            this.hasBeenEdited = true;
            List.copyOf(projectEditedEventListeners).forEach((t) -> {
                t.onChange(p);
            });
        }
    }

    public void removeResource(ResourceElement re) {
        if (re != null) {
            p.removeResource(re);
            this.hasBeenEdited = true;
            List.copyOf(projectEditedEventListeners).forEach((t) -> {
                t.onChange(p);
            });
        }
    }

    public void save(String filepath) throws FileNotFoundException, IOException {
        ProjectSerializer.serializeProject(p, filePath);
        filePath = filepath;
        hasBeenEdited = false;
    }

    public Project getProject() {
        return p;
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

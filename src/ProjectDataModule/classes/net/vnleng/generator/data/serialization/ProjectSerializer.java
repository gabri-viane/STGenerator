/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.serialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.vnleng.generator.data.Project;

/**
 *
 * @author gabri
 */
public class ProjectSerializer {

    private static final int PROJECT_VERSION = 1;

    public static void serializeProject(Project p, String filePath) throws FileNotFoundException, IOException {
        if(filePath == null || filePath.isBlank()){
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeByte(PROJECT_VERSION);
            oos.writeObject(p);
            oos.flush();
        }
    }

    public static Project deserializeProject(String filePath) throws FileNotFoundException, IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            byte readByte = ois.readByte();
            if (readByte != PROJECT_VERSION) {
                throw new ProjectVersionError();
            }
            Object obj = ois.readObject();
            try {
                Project p = (Project) obj;
                return p;
            } catch (ClassCastException ex) {
                throw new ProjectUnrecognizedError();
            }
        } catch (ClassNotFoundException ex) {
            throw new ProjectUnrecognizedError();
        }
    }

}

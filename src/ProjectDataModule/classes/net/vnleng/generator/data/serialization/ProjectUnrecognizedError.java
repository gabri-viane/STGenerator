/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.serialization;

/**
 *
 * @author gabri
 */
public class ProjectUnrecognizedError extends RuntimeException {

    public ProjectUnrecognizedError() {
        super("Non è possibile aprire il progetto: classe non trovata");
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package net.vnleng.generator.data.ints;

import java.io.Serializable;

/**
 *
 * @author gabri
 */
public interface Resource extends Serializable {

    public String getName();

    public String getDefinition();

    public String getDeclaration();

    public ResourceType getType();
}

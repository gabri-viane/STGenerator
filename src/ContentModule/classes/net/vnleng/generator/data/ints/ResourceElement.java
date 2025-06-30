/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.ints;

import java.util.Map;

/**
 *
 * @author gabri
 */
public abstract class ResourceElement implements Resource {
    
    private static final long serialVersionUID = 1L;
    protected String name;
    protected ResourceType rt;

    public ResourceElement(ResourceType rt) {
        this.rt = rt;
    }

    public abstract Map<String, Variable> getVariables();

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final ResourceType getType() {
        return this.rt;
    }

    @Override
    public final String toString() {
        return getDeclaration() + "\n" + getDefinition();
    }

}

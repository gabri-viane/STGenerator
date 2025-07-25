/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.ints.res;

import net.vnleng.generator.commons.block.KeyLock;

/**
 *
 * @author gabri
 */
public abstract class ResourceElement implements Resource {

    private static final long serialVersionUID = 1L;
    protected String name;
    protected ResourceType rt;
    protected ResourceElement bounded = null;

    public ResourceElement(ResourceType rt) {
        this.rt = rt;
    }

    public abstract VariablePack getVariables(PackType pt);

    @Override
    public final String getName() {
        return this.name;
    }

    public final void setName(KeyLock k, String name) {
        if (k != null) {
            this.name = name;
        }
    }

    @Override
    public final ResourceType getType() {
        return this.rt;
    }

    @Override
    public final String toString() {
        return getDeclaration() + "\n" + getDefinition();
    }

    public final ResourceElement getBounded() {
        return bounded;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.scl.ints;

import net.vnleng.generator.data.scl.DataHandler;
import net.vnleng.generator.data.ints.res.ResourceElement;
import net.vnleng.generator.data.ints.res.ResourceType;
import net.vnleng.generator.data.scl.RetainType;
import net.vnleng.generator.data.ints.res.PackType;
import net.vnleng.generator.data.ints.res.VariablePack;

/**
 *
 * @author gabri
 */
public abstract class DataBlockResource extends ResourceElement {

    private static final long serialVersionUID = 2L;
    protected boolean optimizedAccess = false;
    protected String version = "0.1";
    protected RetainType retain = RetainType.RETAIN;

    protected VariablePack variables;

    public DataBlockResource(String name) {
        super(ResourceType.DataBlock);
        super.name = name;
        this.variables = new VariablePack(PackType.STATIC);
    }

    protected DataBlockResource(String name, ResourceType rt) {
        super(rt);
        super.name = name;
        this.variables = new VariablePack(PackType.STATIC);
    }

    protected abstract String getDeclarationEnd();

    public void setOptimizedAccess(boolean optimizedAccess) {
        this.optimizedAccess = optimizedAccess;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setRetain(RetainType retain) {
        this.retain = retain;
    }

    public boolean isOptimizedAccess() {
        return optimizedAccess;
    }

    public String getVersion() {
        return version;
    }

    public RetainType getRetain() {
        return retain;
    }

    @Override
    public VariablePack getVariables(PackType pt) {
        return variables;
    }

    @Override
    public String getDefinition() {
        StringBuilder sb = new StringBuilder("BEGIN\n");
        this.variables.iterator().forEachRemaining(v -> {
            sb.append("\t").append(v.getName()).append(" := ").append(DataHandler.computeDefault(v)).append(";\n");
        });
        sb.append("END_DATA_BLOCK");
        return sb.toString();
    }

    @Override
    public String getDeclaration() {
        StringBuilder sb = new StringBuilder("DATA_BLOCK \"");
        sb.append(super.name).append("\"\n").append("{ S7_Optimized_Access := '")
                .append(this.optimizedAccess ? "TRUE' }\n" : "FALSE' }\n")
                .append("VERSION : ").append(this.version).append("\n")
                .append(this.retain.toString()).append("\n");
        sb.append(getDeclarationEnd());
        return sb.toString();
    }

}

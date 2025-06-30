/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.scl.impls;

import net.vnleng.generator.data.ints.Resource;
import net.vnleng.generator.data.ints.ResourceType;
import net.vnleng.generator.data.scl.DataHandler;
import net.vnleng.generator.data.scl.ints.SCLInstruction;
import net.vnleng.generator.data.ints.Variable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabri
 */
public class FunctionCallInstruction implements SCLInstruction {

    private static final long serialVersionUID = 1L;

    private Resource callingFunction;
    private boolean hasDBInstance;

    private Resource bindedDB;
    private List<Variable> callParameters;

    public FunctionCallInstruction(Resource FunctionInstance) {
        if (!FunctionInstance.getType().equals(ResourceType.Function)
                && !FunctionInstance.getType().equals(ResourceType.FunctionBlock)) {
            throw new RuntimeException("Expected Function or FunctionBlock call. Found : " + FunctionInstance.getType().name());
        }
        this.callingFunction = FunctionInstance;
        this.callParameters = new ArrayList<>();
    }

    public void bindDBInstance(Resource DB) {
        if (DB == null || !DB.getType().equals(ResourceType.FunctionInstance)) {
            this.bindedDB = null;
            this.hasDBInstance = false;
            return;
        }
        this.bindedDB = DB;
        this.hasDBInstance = true;
    }

    public void removeBinding() {
        this.bindedDB = null;
        this.hasDBInstance = false;
    }

    public void addCallParameter(Variable v) {
        this.callParameters.add(v);
    }

    public void removeCallParameter(Variable v) {
        this.callParameters.remove(v);
    }

    @Override
    public String getName() {
        return "CALL";
    }

    @Override
    public String getInstruction() {
        StringBuilder sb = new StringBuilder();

        sb.append("CALL \"").append(this.callingFunction.getName()).append("\"");
        if (this.hasDBInstance) {
            sb.append(", \"").append(this.bindedDB.getName()).append("\"");
        }
        if (!this.callParameters.isEmpty()) {
            sb.append("\t( ");
            this.callParameters.forEach(v -> {
                Object value = DataHandler.getValue(v);
                if (value != null) {
                    String name = v.getName();
                    if (name.contains(" ") || name.contains("\t")) {
                        sb.append("\"").append(name).append("\"");
                    } else {
                        sb.append(name);
                    }
                    sb.append(" := ").append(value.toString()).append(",\n");
                }
            });
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
            sb.append(");");
        } else {
            sb.append(";");
        }
        return sb.toString();
    }

}

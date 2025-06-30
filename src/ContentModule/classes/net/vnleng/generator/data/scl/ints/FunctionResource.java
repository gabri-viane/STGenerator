/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package net.vnleng.generator.data.scl.ints;

import net.vnleng.generator.data.ints.Reloadable;
import net.vnleng.generator.data.ints.ResourceElement;
import net.vnleng.generator.data.ints.ResourceType;
import net.vnleng.generator.data.scl.Network;
import net.vnleng.generator.data.ints.Variable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gabri
 */
public abstract class FunctionResource extends ResourceElement implements Reloadable {

    private static final long serialVersionUID = 1L;

    protected final Map<String, Variable> inputs;
    protected final Map<String, Variable> outputs;
    protected final Map<String, Variable> inout;
    protected final Map<String, Variable> statics;
    private final Map<String, Variable> temp;

    protected String declaration;
    protected String definition;
    protected final List<Network> networks;

    public FunctionResource(String name, ResourceType rt) {
        super(rt);
        super.name = name;
        inputs = new HashMap<>();
        outputs = new HashMap<>();
        inout = new HashMap<>();
        statics = new HashMap<>();
        temp = new HashMap<>();
        networks = new ArrayList<>();
    }

    public void addNetwork(Network n) {
        if (n == null) {
            return;
        }
        this.networks.add(n);
    }

    public void removeNetwork(Network n) {
        if (n == null) {
            return;
        }
        this.networks.remove(n);
    }

    public void addInput(Variable v) {
        this.inputs.put(v.getName(), v);
    }

    public void removeInput(Variable v) {
        this.inputs.remove(v.getName());
    }

    public void addOutput(Variable v) {
        this.outputs.put(v.getName(), v);
    }

    public void removeOutput(Variable v) {
        this.outputs.remove(v.getName());
    }

    public void addInOut(Variable v) {
        this.inout.put(v.getName(), v);
    }

    public void removeInOut(Variable v) {
        this.inout.remove(v.getName());
    }

    public void addStatic(Variable v) {
        this.statics.put(v.getName(), v);
    }

    public void removeStatic(Variable v) {
        this.statics.remove(v.getName());
    }

    public void addTemp(Variable v) {
        this.temp.put(v.getName(), v);
    }

    public void removeTemp(Variable v) {
        this.temp.remove(v.getName());
    }

    public Map<String, Variable> getInputs() {
        return Map.copyOf(inputs);
    }

    public Map<String, Variable> getOutputs() {
        return Map.copyOf(outputs);
    }

    public Map<String, Variable> getInout() {
        return Map.copyOf(inout);
    }

    public Map<String, Variable> getStatics() {
        return Map.copyOf(statics);
    }

    @Override
    public void reload() {
        StringBuilder sb = new StringBuilder("TITLE = " + super.name + "\n"
                + "{ S7_Optimized_Access := 'TRUE'}\n"
                + "VERSION : 0.1\n"
        );
        sb.append("VAR_INPUT\n");
        inputs.values().forEach(v -> {
            sb.append("\t").append(v.getName()).append(" : ").append(v.getType().toString());
            if (v.getDefaultValue() != null) {
                sb.append(" : ").append(v.getDefaultValue().toString());
            }
            sb.append(";");
            String comment = v.getComment();
            if (comment != null && !comment.isBlank()) {
                sb.append("//").append(comment);
            }
            sb.append("\n");
        });
        sb.append("END_VAR\n");

        sb.append("VAR_OUTPUT\n");
        outputs.values().forEach(v -> {
            sb.append("\t").append(v.getName()).append(" : ").append(v.getType().toString()).append(";");
            String comment = v.getComment();
            if (comment != null && !comment.isBlank()) {
                sb.append("//").append(comment);
            }
            sb.append("\n");
        });
        sb.append("END_VAR\n");

        sb.append("VAR_IN_OUT\n");
        inout.values().forEach(v -> {
            sb.append("\t").append(v.getName()).append(" : ").append(v.getType().toString());
            if (v.getDefaultValue() != null) {
                sb.append(" : ").append(v.getDefaultValue().toString());
            }
            sb.append(";");
            String comment = v.getComment();
            if (comment != null && !comment.isBlank()) {
                sb.append("//").append(comment);
            }
            sb.append("\n");
        });
        sb.append("END_VAR\n");

        sb.append("VAR_TEMP\n");
        temp.values().forEach(v -> {
            sb.append("\t").append(v.getName()).append(" : ").append(v.getType().toString());
            if (v.getDefaultValue() != null) {
                sb.append(" : ").append(v.getDefaultValue().toString());
            }
            sb.append(";");
            String comment = v.getComment();
            if (comment != null && !comment.isBlank()) {
                sb.append("//").append(comment);
            }
            sb.append("\n");
        });
        sb.append("END_VAR\n");

        sb.append("VAR CONSTANT\n");
        statics.values().forEach(v -> {
            sb.append("\t").append(v.getName()).append(" : ").append(v.getType().toString());
            if (v.getDefaultValue() != null) {
                sb.append(" : ").append(v.getDefaultValue().toString());
            }
            sb.append(";");
            String comment = v.getComment();
            if (comment != null && !comment.isBlank()) {
                sb.append("//").append(comment);
            }
            sb.append("\n");
        });
        sb.append("END_VAR\n");
        declaration = sb.toString();
    }

}

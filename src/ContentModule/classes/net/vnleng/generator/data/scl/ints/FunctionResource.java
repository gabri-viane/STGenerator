/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package net.vnleng.generator.data.scl.ints;

import net.vnleng.generator.data.ints.Reloadable;
import net.vnleng.generator.data.ints.res.ResourceElement;
import net.vnleng.generator.data.ints.res.ResourceType;
import net.vnleng.generator.data.scl.Network;
import net.vnleng.generator.data.ints.var.Variable;
import java.util.ArrayList;
import java.util.List;
import net.vnleng.generator.data.ints.res.PackType;
import net.vnleng.generator.data.ints.res.VariablePack;

/**
 *
 * @author gabri
 */
public abstract class FunctionResource extends ResourceElement implements Reloadable {

    private static final long serialVersionUID = 3L;

    protected final VariablePack inputs;
    protected final VariablePack outputs;
    protected final VariablePack inout;
    protected final VariablePack statics;
    protected final VariablePack consts;
    protected final VariablePack temp;

    protected String declaration;
    protected String definition;
    protected final List<Network> networks;

    public FunctionResource(String name, ResourceType rt) {
        super(rt);
        super.name = name;
        inputs = new VariablePack(PackType.INPUT);
        outputs = new VariablePack(PackType.OUTPUT);
        inout = new VariablePack(PackType.INOUT);
        statics = new VariablePack(PackType.STATIC);
        consts = new VariablePack(PackType.CONST);
        temp = new VariablePack(PackType.TEMP);
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
        this.inputs.add(v);
    }

    public void removeInput(Variable v) {
        this.inputs.removeVariable(v);
    }

    public void addOutput(Variable v) {
        this.outputs.add(v);
    }

    public void removeOutput(Variable v) {
        this.outputs.removeVariable(v);
    }

    public void addInOut(Variable v) {
        this.inout.add(v);
    }

    public void removeInOut(Variable v) {
        this.inout.removeVariable(v);
    }

    public void addStatic(Variable v) {
        this.statics.add(v);
    }

    public void removeStatic(Variable v) {
        this.statics.removeVariable(v);
    }

    public void addConst(Variable v) {
        this.consts.add(v);
    }

    public void removeConst(Variable v) {
        this.consts.removeVariable(v);
    }

    public void addTemp(Variable v) {
        this.temp.add(v);
    }

    public void removeTemp(Variable v) {
        this.temp.removeVariable(v);
    }

    /**
     * Restituisce una mappa non modificabile delle variabili di input
     *
     * @return
     */
    public VariablePack getInputs() {
        return inputs;
    }

    /**
     * Restituisce una mappa non modificabile delle variabili di output
     *
     * @return
     */
    public VariablePack getOutputs() {
        return outputs;
    }

    /**
     * Restituisce una mappa non modificabile delle variabili di inout
     *
     * @return
     */
    public VariablePack getInout() {
        return inout;
    }

    /**
     * Restituisce una mappa non modificabile delle variabili statiche
     *
     * @return
     */
    public VariablePack getStatics() {
        return statics;
    }

    /**
     * Restituisce una mappa non modificabile delle variabili costanti
     *
     * @return
     */
    public VariablePack getConsts() {
        return consts;
    }

    /**
     * Restituisce una mappa non modificabile delle variabili temporanee
     *
     * @return
     */
    public VariablePack getTemps() {
        return temp;
    }

    @Override
    public void reload() {
        StringBuilder sb = new StringBuilder("TITLE = " + super.name + "\n"
                + "{ S7_Optimized_Access := 'TRUE'}\n"
                + "VERSION : 0.1\n"
        );
        sb.append("VAR_INPUT\n");
        inputs.iterator().forEachRemaining(v -> {
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
        outputs.iterator().forEachRemaining(v -> {
            sb.append("\t").append(v.getName()).append(" : ").append(v.getType().toString()).append(";");
            String comment = v.getComment();
            if (comment != null && !comment.isBlank()) {
                sb.append("//").append(comment);
            }
            sb.append("\n");
        });
        sb.append("END_VAR\n");

        sb.append("VAR_IN_OUT\n");
        inout.iterator().forEachRemaining(v -> {
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
        temp.iterator().forEachRemaining(v -> {
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
        consts.iterator().forEachRemaining(v -> {
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

        sb.append("VAR STATIC\n");
        statics.iterator().forEachRemaining(v -> {
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

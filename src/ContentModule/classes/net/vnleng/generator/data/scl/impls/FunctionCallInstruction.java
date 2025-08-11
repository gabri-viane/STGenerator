package net.vnleng.generator.data.scl.impls;

import net.vnleng.generator.data.ints.res.Resource;
import net.vnleng.generator.data.ints.res.ResourceType;
import net.vnleng.generator.data.scl.DataHandler;
import net.vnleng.generator.data.scl.ints.SCLInstruction;
import net.vnleng.generator.data.ints.var.Variable;
import java.util.ArrayList;
import java.util.List;
import net.vnleng.generator.data.errs.ResourceNotFound;
import net.vnleng.generator.data.ints.res.ResourceElement;
import net.vnleng.generator.exchange.ResourceFinder;

/**
 * Rappresenta una chiamata a funzione FC o FB. Viene usata per generare dei
 * segmenti di chiamata.
 *
 * @author gabri
 */
public class FunctionCallInstruction implements SCLInstruction {

    private static final long serialVersionUID = 2L;

    private String callingFunction;
    private boolean hasDBInstance;

    private String bindedDB;
    private List<Variable> callParameters;

    public FunctionCallInstruction(Resource FunctionInstance) {
        if (!FunctionInstance.getType().equals(ResourceType.Function)
                && !FunctionInstance.getType().equals(ResourceType.FunctionBlock)) {
            throw new RuntimeException("Expected Function or FunctionBlock call. Found : " + FunctionInstance.getType().name());
        }
        this.callingFunction = FunctionInstance.getName();
        this.callParameters = new ArrayList<>();
    }

    public void bindDBInstance(Resource DB) {
        if (DB == null || !DB.getType().equals(ResourceType.FunctionInstance)) {
            this.bindedDB = null;
            this.hasDBInstance = false;
            return;
        }
        this.bindedDB = DB.getName();
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
    public String getInstruction(ResourceFinder rf) {
        StringBuilder sb = new StringBuilder();

        ResourceElement cllFnc = rf.find(this.callingFunction);
        if (cllFnc == null) {
            throw new ResourceNotFound("The Function (FB or FC) resource of this Function Call Instruction couldn't be found with the provided ResourceFinder.");
        }

        sb.append("CALL \"").append(cllFnc.getName()).append("\"");
        if (this.hasDBInstance) {
            ResourceElement bndDB = rf.find(this.bindedDB);
            if (bndDB == null) {
                throw new ResourceNotFound("The binded DB resource to this Function Call Instruction couldn't be found with the provided ResourceFinder.");
            }
            sb.append(", \"").append(bndDB.getName()).append("\"");
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

    @Override
    public SCLInstruction clone() {
        FunctionElement tempFunction = new FunctionElement("Temp");
        FunctionCallInstruction fci = new FunctionCallInstruction(tempFunction);
        fci.callingFunction = this.callingFunction;
        fci.bindedDB = this.bindedDB;
        this.callParameters.forEach(v -> fci.addCallParameter(v.copy()));
        return fci;
    }

    @Override
    public void restore(SCLInstruction resourceFrom) {
        if (resourceFrom instanceof FunctionCallInstruction fci) {
            fci.callingFunction = this.callingFunction;
            fci.bindedDB = this.bindedDB;
            this.callParameters = fci.callParameters;
        }
    }

}

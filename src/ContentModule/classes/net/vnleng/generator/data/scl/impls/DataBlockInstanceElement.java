package net.vnleng.generator.data.scl.impls;

import net.vnleng.generator.data.ints.Reloadable;
import net.vnleng.generator.data.ints.res.ResourceType;
import net.vnleng.generator.data.ints.var.Variable;
import net.vnleng.generator.data.scl.ints.DataBlockResource;
import net.vnleng.generator.data.scl.ints.FunctionResource;
import net.vnleng.generator.data.ints.res.PackType;
import net.vnleng.generator.data.ints.res.ResourceElement;
import net.vnleng.generator.data.ints.res.VariablePack;

/**
 * Rapparesenta un DataBlock d'istanza ed è associato a una Function [FC] o
 * FunctionBlock [FB].
 * <br>
 * Per modificare i valori delle variabili usare {@link #getVariables() } in
 * congiunta con {@link #setVariableDefaultValue(generatoretags.data.scl.Variable, java.lang.Object)
 * }.
 *
 * @author gabri
 */
public class DataBlockInstanceElement extends DataBlockResource implements Reloadable {

    private static final long serialVersionUID = 3L;
    private final FunctionResource bindedFunction;

    private final VariablePack in;
    private final VariablePack out;
    private final VariablePack inout;
    private final VariablePack consts;
    private final VariablePack statics;

    public DataBlockInstanceElement(String name, FunctionResource function) {
        super(name, ResourceType.FunctionInstance);
        this.in = new VariablePack(PackType.INPUT);
        this.out = new VariablePack(PackType.OUTPUT);
        this.inout = new VariablePack(PackType.INOUT);
        this.consts = new VariablePack(PackType.CONST);
        this.statics = new VariablePack(PackType.STATIC);
        this.bindedFunction = function;
        reload();
    }

    @Override
    protected String getDeclarationEnd() {
        StringBuilder sb = new StringBuilder("\"");
        sb.append(this.bindedFunction.getName()).append("\"\n");
        return sb.toString();
    }

    @Override
    public VariablePack getVariables(PackType pt) {
        switch (pt) {
            case CONST -> {
                return consts;
            }
            case INOUT -> {
                return inout;
            }
            case INPUT -> {
                return in;
            }
            case OUTPUT -> {
                return out;
            }
            case STATIC -> {
                return statics;
            }
            case TEMP -> {
                return null;
            }
            case GENERIC -> {
                return variables;
            }
            default -> {
                return variables;
            }
        }
    }

    /**
     * Imposta il valore di default di una variabile già definita in questo DB
     * associato all'interfaccia di una Function o FunctionBlock
     *
     * @param v La variabile (deve essere presente nella lista) a cui impostare
     * il valore di default.
     * @param value Il valore da impostare.
     */
    public void setVariableDefaultValue(Variable v, String value) {
        if (v == null) {
            return;
        }
        Variable variable = this.variables.get(v.getName());
        if (variable != null) {
            variable.setDefaultValue(value);
        }
    }

    @Override
    public final void reload() {
        in.copyOf(bindedFunction.getInputs(), true, true);
        out.copyOf(bindedFunction.getOutputs(), true, true);
        inout.copyOf(bindedFunction.getInout(), true, true);
        consts.copyOf(bindedFunction.getConsts(), true, true);
        statics.copyOf(bindedFunction.getStatics(), true, true);

        variables.bind(in, true, true);
        variables.bind(out, false, true);
        variables.bind(inout, false, true);
        variables.bind(consts, false, true);
        variables.bind(statics, false, true);

        bounded = bindedFunction;
    }

    public FunctionResource getBindedFunction() {
        return bindedFunction;
    }

    @Override
    public ResourceElement clone() {
        DataBlockInstanceElement dbie = new DataBlockInstanceElement(name, bindedFunction);
        dbie.bounded = this.bounded;
        dbie.optimizedAccess = this.optimizedAccess;
        dbie.retain = this.retain;
        dbie.rt = this.rt;
        dbie.version = this.version;
        dbie.reload();
        return dbie;
    }

    @Override
    public void restore(ResourceElement resourceFrom) {
        if (resourceFrom.getType() != ResourceType.FunctionInstance) {
            return;
        }
        DataBlockInstanceElement dbie = (DataBlockInstanceElement) resourceFrom;
        this.bounded = dbie.bounded;
        this.name = dbie.name;
        this.optimizedAccess = dbie.optimizedAccess;
        this.retain = dbie.retain;
        this.rt = dbie.rt;
        this.version = dbie.version;
        this.variables = dbie.variables;
    }

}

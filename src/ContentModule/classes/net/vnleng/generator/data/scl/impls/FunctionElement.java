package net.vnleng.generator.data.scl.impls;

import net.vnleng.generator.data.ints.res.ResourceType;
import net.vnleng.generator.data.scl.ints.FunctionResource;
import net.vnleng.generator.data.ints.res.PackType;
import static net.vnleng.generator.data.ints.res.PackType.INOUT;
import static net.vnleng.generator.data.ints.res.PackType.INPUT;
import static net.vnleng.generator.data.ints.res.PackType.OUTPUT;
import static net.vnleng.generator.data.ints.res.PackType.STATIC;
import static net.vnleng.generator.data.ints.res.PackType.TEMP;
import net.vnleng.generator.data.ints.res.ResourceElement;
import net.vnleng.generator.data.ints.res.VariablePack;

/**
 * Rappresenta una FC.
 *
 * @author gabri
 */
public class FunctionElement extends FunctionResource {

    private static final long serialVersionUID = 1L;

    public FunctionElement(String name) {
        super(name, ResourceType.Function);
    }

    @Override
    public String getDefinition() {
        StringBuilder sb = new StringBuilder("BEGIN\n");
        this.networks.forEach(netw -> {
            sb.append(netw.toString()).append("\n");
        });
        sb.append("END_FUNCTION");
        this.definition = sb.toString();
        return this.definition;
    }

    @Override
    public String getDeclaration() {
        if (declaration == null) {
            this.reload();
        }
        String declInit = "FUNCTION \"" + super.name + "\" : void\n";
        return declInit + this.declaration;
    }

    @Override
    public VariablePack getVariables(PackType pt) {
        return switch (pt) {
            case INOUT -> {
                yield inout;
            }
            case INPUT -> {
                yield inputs;
            }
            case OUTPUT -> {
                yield outputs;
            }
            case STATIC -> {
                yield statics;
            }
            case TEMP -> {
                yield temp;
            }
            default -> {
                yield null;
            }
        };
    }

    @Override
    public ResourceElement clone() {
        FunctionElement fbe = new FunctionElement(name);
        fbe.bounded = this.bounded;
        this.networks.forEach(n -> fbe.networks.add(n.clone()));
        fbe.rt = this.rt;
        fbe.inout.copyOf(inout, true, false);
        fbe.inputs.copyOf(inputs, true, false);
        fbe.outputs.copyOf(outputs, true, false);
        fbe.statics.copyOf(statics, true, false);
        fbe.temp.copyOf(temp, true, false);
        return fbe;
    }
    
        @Override
    public void restore(ResourceElement resourceFrom) {
        if (resourceFrom.getType() != ResourceType.Function) {
            return;
        }
        FunctionElement fbe = (FunctionElement) resourceFrom;
        this.bounded = fbe.bounded;
        this.name = fbe.name;
        this.rt = fbe.rt;
        fbe.inout.copyOf(inout, true, false);
        fbe.inputs.copyOf(inputs, true, false);
        fbe.outputs.copyOf(outputs, true, false);
        fbe.statics.copyOf(statics, true, false);
        fbe.temp.copyOf(temp, true, false);

    }

}

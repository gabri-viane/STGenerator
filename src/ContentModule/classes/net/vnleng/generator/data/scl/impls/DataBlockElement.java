package net.vnleng.generator.data.scl.impls;

import net.vnleng.generator.data.ints.res.ResourceElement;
import net.vnleng.generator.data.ints.res.ResourceType;
import net.vnleng.generator.data.scl.ints.DataBlockResource;

/**
 * Rapparesenta un DataBlock Globale. Viene definito con l'istruzione STRUCT e
 * non è associato a una Function [FC] o FunctionBlock [FB].
 * <br>
 * Per aggiungere elementi usare {@link #getVariables() } e aggiungere le
 * variabili direttamente all'array.
 *
 *
 * @author gabri
 */
public class DataBlockElement extends DataBlockResource {

    private static final long serialVersionUID = 1L;

    public DataBlockElement(String name) {
        super(name);
    }

    @Override
    protected String getDeclarationEnd() {
        StringBuilder sb = new StringBuilder("STRUCT\n");
        this.variables.iterator().forEachRemaining(v -> {
            sb.append("\t").append(v.getDeclaration()).append("\n");
        });
        sb.append("END_STRUCT;\n");
        return sb.toString();
    }

    @Override
    public ResourceElement clone() {
        DataBlockElement dbe = new DataBlockElement(name);
        dbe.bounded = this.bounded;
        dbe.optimizedAccess = this.optimizedAccess;
        dbe.retain = this.retain;
        dbe.rt = this.rt;
        dbe.variables.copyOf(this.variables, true, false);
        dbe.version = this.version;
        return dbe;
    }

    @Override
    public void restore(ResourceElement resourceFrom) {
        if(resourceFrom.getType() != ResourceType.DataBlock){
            return;
        }
        DataBlockElement dbe = (DataBlockElement) resourceFrom;
        this.name = dbe.name;
        this.optimizedAccess = dbe.optimizedAccess;
        this.retain = dbe.retain;
        this.rt = dbe.rt;
        this.version = dbe.version;
        this.variables = dbe.variables;
    }

    
    
}

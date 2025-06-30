/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.scl.impls;

import net.vnleng.generator.data.ints.ResourceType;
import net.vnleng.generator.data.ints.Variable;
import net.vnleng.generator.data.scl.ints.FunctionResource;
import java.util.HashMap;
import java.util.Map;

/**
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
    public Map<String, Variable> getVariables() {
        Map<String, Variable> variables = new HashMap<>();
        variables.putAll(this.inputs);
        variables.putAll(this.outputs);
        variables.putAll(this.outputs);
        variables.putAll(this.statics);
        return variables;
    }
    
}

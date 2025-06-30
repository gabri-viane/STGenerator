/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.scl.impls;

import net.vnleng.generator.data.ints.Reloadable;
import net.vnleng.generator.data.ints.ResourceType;
import net.vnleng.generator.data.ints.Variable;
import net.vnleng.generator.data.scl.ints.DataBlockResource;
import net.vnleng.generator.data.scl.ints.FunctionResource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Rapparesenta un DataBlock d'istanza ed è associato a una Function [FC] o
 * FunctionBlock [FB].
 * <br>
 * Per modificare i valori delle variabili usare {@link #getVariables() } in
 * congiunta con {@link #setVariableDefaultValue(generatoretags.data.scl.Variable, java.lang.Object)
 * }.
 *
 *
 * @author gabri
 */
public class DataBlockInstanceElement extends DataBlockResource implements Reloadable {

    private final FunctionResource bindedFunction;

    public DataBlockInstanceElement(String name, FunctionResource function) {
        super(name, ResourceType.FunctionInstance);
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
    public Map<String, Variable> getVariables() {
        return Collections.unmodifiableMap(variables);
    }

    /**
     * Imposta il valore di default di una variabile già definita in questo DB
     * associato all'interfaccia di una Function o FunctionBlock
     *
     * @param v La variabile (deve essere presente nella lista) a cui impostare
     * il valore di default.
     * @param value Il valore da impostare.
     */
    public void setVariableDefaultValue(Variable v, Object value) {
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
        super.variables = new HashMap<>();
        variables.putAll(bindedFunction.getInputs());
        variables.putAll(bindedFunction.getOutputs());
        variables.putAll(bindedFunction.getInout());
        variables.putAll(bindedFunction.getStatics());
    }

    public FunctionResource getBindedFunction() {
        return bindedFunction;
    }

}

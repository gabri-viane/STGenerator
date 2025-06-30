/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.scl.impls;

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
        this.variables.values().forEach(v -> {
            sb.append("\t").append(v.getDeclaration()).append("\n");
        });
        sb.append("END_STRUCT;\n");
        return sb.toString();
    }

}

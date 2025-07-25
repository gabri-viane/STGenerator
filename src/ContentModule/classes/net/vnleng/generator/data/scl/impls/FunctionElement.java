/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.scl.impls;

import net.vnleng.generator.data.ints.res.ResourceType;
import net.vnleng.generator.data.scl.ints.FunctionResource;
import net.vnleng.generator.data.ints.res.PackType;
import static net.vnleng.generator.data.ints.res.PackType.INOUT;
import static net.vnleng.generator.data.ints.res.PackType.INPUT;
import static net.vnleng.generator.data.ints.res.PackType.OUTPUT;
import static net.vnleng.generator.data.ints.res.PackType.STATIC;
import static net.vnleng.generator.data.ints.res.PackType.TEMP;
import net.vnleng.generator.data.ints.res.VariablePack;

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

}

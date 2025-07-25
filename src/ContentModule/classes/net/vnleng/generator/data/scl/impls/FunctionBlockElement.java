/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.scl.impls;

import net.vnleng.generator.data.scl.ints.FunctionResource;
import net.vnleng.generator.data.ints.res.ResourceType;
import net.vnleng.generator.data.ints.res.PackType;
import net.vnleng.generator.data.ints.res.VariablePack;

/**
 *
 * @author gabri
 */
public class FunctionBlockElement extends FunctionResource {

    private static final long serialVersionUID = 2L;

    public FunctionBlockElement(String name) {
        super(name, ResourceType.FunctionBlock);
    }

    @Override
    public String getDefinition() {
        StringBuilder sb = new StringBuilder("BEGIN\n");
        this.networks.forEach(netw -> {
            sb.append(netw.toString()).append("\n");
        });
        sb.append("END_FUNCTION_BLOCK");
        this.definition = sb.toString();
        return this.definition;
    }

    @Override
    public String getDeclaration() {
        if (declaration == null) {
            this.reload();
        }
        String declInit = "FUNCTION_BLOCK \"" + super.name + "\"\n";
        return declInit + this.declaration;
    }

    @Override
    public VariablePack getVariables(PackType pt) {
        return switch (pt) {
            case CONST -> {
                yield consts;
            }
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

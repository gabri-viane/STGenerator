/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.scl;

import net.vnleng.generator.data.scl.ints.SCLInstruction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.vnleng.generator.data.ints.Clonable;
import net.vnleng.generator.exchange.ResourceFinder;

/**
 *
 * @author gabri
 */
public class Network implements Serializable, Clonable<Network> {

    private static final long serialVersionUID = 1L;

    private final List<SCLInstruction> instructions;
    private String title;
    private ResourceFinder rf;

    public Network(String title, ResourceFinder rf) {
        instructions = new ArrayList<>();

        if (this.title == null) {
            this.title = "";
        } else {
            this.title = title;
        }
    }

    public void addInstruction(SCLInstruction inst) {
        if (inst == null) {
            return;
        }
        this.instructions.add(inst);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("NETWORK\nTITLE = ");
        sb.append(title).append("\n");
        this.instructions.forEach(instr -> {
            sb.append(instr.getInstruction(rf)).append("\n");
        });
        return sb.toString();
    }

    @Override
    public Network clone() {
        Network n = new Network(title, rf);
        this.instructions.forEach(inst -> {
            n.instructions.add(inst.clone());
        });
        return n;
    }

    @Override
    public void restore(Network resourceFrom) {
        this.rf = resourceFrom.rf;
        this.title = resourceFrom.title;
        this.instructions.clear();
        this.instructions.addAll(resourceFrom.instructions);
    }

}

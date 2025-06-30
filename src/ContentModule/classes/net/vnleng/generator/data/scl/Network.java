/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.scl;

import net.vnleng.generator.data.scl.ints.SCLInstruction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabri
 */
public class Network implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<SCLInstruction> instructions;
    private String title;

    public Network(String title) {
        instructions = new ArrayList<>();

        if (this.title == null) {
            this.title = "";
        } else {
            this.title = title;
        }
    }
    
    public void addInstruction(SCLInstruction inst){
        if(inst == null){
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
        this.instructions.forEach(instr->{
            sb.append(instr.getInstruction()).append("\n");
        });
        return sb.toString();
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package net.vnleng.generator.data.scl.ints;

import java.io.Serializable;
import net.vnleng.generator.data.ints.Clonable;
import net.vnleng.generator.exchange.ResourceFinder;

/**
 *
 * @author gabri
 */
public interface SCLInstruction extends Serializable, Clonable<SCLInstruction>{
    
    public String getName();
    
    public String getInstruction(ResourceFinder rf);
    
}

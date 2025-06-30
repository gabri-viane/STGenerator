/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package net.vnleng.generator.data.scl.ints;

import java.io.Serializable;

/**
 *
 * @author gabri
 */
public interface SCLInstruction extends Serializable{
    
    public String getName();
    
    public String getInstruction();
    
}

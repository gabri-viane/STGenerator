/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package net.vnleng.generator.data.ints;

import java.io.Serializable;

/**
 *
 * @author gabri
 */
public enum ResourceType implements Serializable {
    
    FunctionBlock("FB"), Function("FC"), DataBlock("DB"), FunctionInstance("DBI");
    
    
    private static final long serialVersionUID = 1L;
    private final String shortName;
    
    private ResourceType(String shrtName){
        this.shortName = shrtName;
    }

    @Override
    public String toString() {
        return this.shortName; 
    }
    
    
}

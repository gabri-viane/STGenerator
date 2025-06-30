/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.rules;

import net.vnleng.generator.data.ints.ResourceElement;

/**
 *
 * @author gabri
 */
public class ResourceGeneratorRule extends RuleApplier {

    private static final long serialVersionUID = 1L;
    protected ResourceElement re;
    protected String elementName;

    public void setResurce(ResourceElement re) {
        this.re = re;
    }

    public void setElementName(String name) {
        this.elementName = name;
    }

}

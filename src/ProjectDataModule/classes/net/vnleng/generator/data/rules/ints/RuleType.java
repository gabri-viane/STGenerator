/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.data.rules.ints;

import java.io.Serializable;

/**
 *
 * @author gabri
 */
public enum RuleType implements Serializable {
    NAME_RULE, INITIALIZER_RULE, RULE_GENERATOR_RULE, CONDITION_RULE, LOOP_RULE, VALUE_RULE, GENERIC_CONTENT_RULE, FILE_RULE;

    private static final long serialVersionUID = 1L;
}

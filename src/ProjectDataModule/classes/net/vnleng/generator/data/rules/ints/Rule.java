/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package net.vnleng.generator.data.rules.ints;

import java.io.Serializable;

/**
 *
 * @author gabri
 * @param <R> Valore restituito dalla regola
 * @param <P1>
 * @param <P2>
 */
public interface Rule<R, P1, P2> extends Serializable {

    public RuleType getRuleType();

    public R compute(P1 param1, P2 param2);
}

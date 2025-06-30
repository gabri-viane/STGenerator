/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package net.vnleng.generator.data.rules.ints;

import java.io.Serializable;

/**
 *
 * @author gabri
 * @param <P> Tipo del parametro
 * @param <R> Tipo di ritorno
 */
public interface FunctionCallback<P,R> extends Serializable{
    public R onCall(P param);
}

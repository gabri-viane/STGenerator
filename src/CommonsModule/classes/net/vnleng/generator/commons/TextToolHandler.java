/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gabri
 */
public class TextToolHandler {

    static Pattern p = Pattern.compile("(.*)_([0-9]+)");

    public static String getNextName(String name) {
        Matcher m = p.matcher(name);
        if (m.matches()) {
            return m.group(1) + "_" + (Integer.parseInt( m.group(2)) + 1);
        }
        return name + "_1";
    }

}

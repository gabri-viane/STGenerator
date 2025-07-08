/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.resources;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import net.vnleng.generator.settings.DataInitializer;

/**
 *
 * @author gabri
 */
public class TextResources {

    public static final ResourceBundle GUITextBundle;
    public static final ResourceBundle DialogsTextBundle;

    static {
        Locale locale = DataInitializer.getInstance().getLocale();
        GUITextBundle = PropertyResourceBundle.getBundle("net.vnleng.generator.resources.i18n.GUITexts", locale);
        DialogsTextBundle = PropertyResourceBundle.getBundle("net.vnleng.generator.resources.i18n.DialogsTexts", locale);
    }

    private TextResources() {
    }

}

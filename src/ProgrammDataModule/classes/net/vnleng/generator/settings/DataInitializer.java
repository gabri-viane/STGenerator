/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.settings;

import java.util.Locale;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 *
 * @author gabri
 */
public class DataInitializer {

    private static DataInitializer instance;

    private Preferences pfs;

    private DataInitializer() {
        pfs = Preferences.userRoot().node("net").node("vnleng").node("stgen");
        try {
            if (!pfs.nodeExists("settings")) {
                pfs = pfs.node("settings");
                setDefaults();
            }
        } catch (BackingStoreException ex) {
            System.getLogger(DataInitializer.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private void setDefaults() {
        pfs.put("locale", Locale.getDefault().toLanguageTag());
    }

    public void setLocale(Locale l) {
        if (l == null) {
            l = Locale.getDefault();
        }
        pfs.put("locale", l.toLanguageTag());

    }

    public Locale getLocale() {
        String getloc = pfs.get("locale", null);
        if (getloc == null) {
            Locale l = Locale.getDefault();
            setLocale(l);
            return l;
        }
        return Locale.forLanguageTag(getloc);
    }

    public static DataInitializer getInstance() {
        if (instance == null) {
            instance = new DataInitializer();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    instance.flush();
                }
            });
        }
        return instance;
    }

    public void flush() {
        try {
            pfs.flush();
        } catch (BackingStoreException ex) {
            System.getLogger(DataInitializer.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

}

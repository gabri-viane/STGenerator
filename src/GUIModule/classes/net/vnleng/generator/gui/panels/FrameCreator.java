/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.gui.panels;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import net.vnleng.generator.gui.ints.ClosableFrame;
import net.vnleng.generator.resources.ProjectResourceHandler;

/**
 *
 * @author gabri
 */
public class FrameCreator {

    public static JInternalFrame createFrame(String title, boolean resizable, JComponent content) {
        JInternalFrame jif = new JInternalFrame(title, resizable, true, true, true);
        //Impostazione layout e aggiunta componente
        jif.setLayout(new BorderLayout());
        jif.add(content, BorderLayout.CENTER);
        //Aggiornamento icona
        jif.setFrameIcon(ProjectResourceHandler.AppIcon_32);
        //Impostazione dimensioni
        jif.pack();
        //Imposto visibile
        jif.setVisible(true);
        return jif;
    }

    public static JInternalFrame createFrame(String title, boolean resizable, JComponent content, JDesktopPane desktop) {
        JInternalFrame jif = createFrame(title, resizable, content);
        if (desktop != null) {
            if (content instanceof ClosableFrame closableFrame) {
                closableFrame.addCloseRequestListener(() -> {
                    jif.setVisible(false);
                    desktop.remove(jif);
                });
            }
            desktop.add(jif);
            try {
                jif.setSelected(true);
            } catch (PropertyVetoException ex) {
                System.getLogger(FrameCreator.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        return jif;
    }
}

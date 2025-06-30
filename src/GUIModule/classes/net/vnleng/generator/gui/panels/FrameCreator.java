/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.gui.panels;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import net.vnleng.generator.resources.ProjectResourceHandler;

/**
 *
 * @author gabri
 */
public class FrameCreator {

    public static JInternalFrame createFrame(String title, boolean resizable, JComponent content) {
        JInternalFrame jif = new JInternalFrame("Nuovo progetto", resizable, true, true, true);
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
}

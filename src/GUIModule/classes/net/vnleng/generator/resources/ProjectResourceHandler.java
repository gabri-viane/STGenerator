package net.vnleng.generator.resources;

import java.awt.Image;
import javax.swing.ImageIcon;
import net.vnleng.generator.gui.panels.FrameCreator;

/**
 *
 * @author gabri
 */
public class ProjectResourceHandler {

    public static final ImageIcon AppIcon_32;
    public static final ImageIcon AppIcon_512;
    public static final ImageIcon ProjectFileIcon_32;
    public static final ImageIcon FBIcon_32;
    public static final ImageIcon FBIcon_24;
    public static final ImageIcon FCIcon_32;
    public static final ImageIcon FCIcon_24;
    public static final ImageIcon DBIcon_32;
    public static final ImageIcon DBIcon_24;
    public static final ImageIcon DBInstanceIcon_32;
    public static final ImageIcon DBInstanceIcon_24;

    static {

        AppIcon_512 = new ImageIcon(FrameCreator.class.getResource("/net/vnleng/generator/resources/icon.png"));
        AppIcon_32 = new ImageIcon(AppIcon_512.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH));

        Image image = new ImageIcon(ProjectResourceHandler.class.getResource("/net/vnleng/generator/resources/ProjectFileIcon.png")).getImage(); // transform it 
        Image newimg = image.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        ProjectFileIcon_32 = new ImageIcon(newimg);  // transform it back

        FBIcon_32 = new ImageIcon(ProjectResourceHandler.class.getResource("/net/vnleng/generator/resources/FBIcon_32.png")); // transform it 
        FBIcon_24 = new ImageIcon(ProjectResourceHandler.class.getResource("/net/vnleng/generator/resources/FBIcon_24.png")); // transform it 

        FCIcon_32 = new ImageIcon(ProjectResourceHandler.class.getResource("/net/vnleng/generator/resources/FCIcon_32.png")); // transform it 
        FCIcon_24 = new ImageIcon(ProjectResourceHandler.class.getResource("/net/vnleng/generator/resources/FCIcon_24.png")); // transform it 

        DBIcon_32 = new ImageIcon(ProjectResourceHandler.class.getResource("/net/vnleng/generator/resources/DBIcon_32.png")); // transform it 
        DBIcon_24 = new ImageIcon(ProjectResourceHandler.class.getResource("/net/vnleng/generator/resources/DBIcon_24.png")); // transform it 

        DBInstanceIcon_32 = new ImageIcon(ProjectResourceHandler.class.getResource("/net/vnleng/generator/resources/DBInstanceIcon_32.png")); // transform it 
        DBInstanceIcon_24 = new ImageIcon(ProjectResourceHandler.class.getResource("/net/vnleng/generator/resources/DBInstanceIcon_24.png")); // transform it 

    }
}

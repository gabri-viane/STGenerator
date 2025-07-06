/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module GUIModule {
    requires ProgrammDataModule;
    requires CommonsModule;
    requires ContentModule;
    requires ProjectDataModule;
    
    requires com.formdev.flatlaf;
    
    requires java.desktop;
    
    exports net.vnleng.generator.gui;
}

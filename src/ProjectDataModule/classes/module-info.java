/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2SEModule/module-info.java to edit this template
 */

module ProjectDataModule {
    
    requires ContentModule;
    requires CommonsModule;
    
    
    exports net.vnleng.generator.creator;
    exports net.vnleng.generator.data;
    exports net.vnleng.generator.data.rules;
    exports net.vnleng.generator.data.rules.ints;
    exports net.vnleng.generator.data.shared;
    exports net.vnleng.generator.data.serialization;
}

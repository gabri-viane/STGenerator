/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.creator;

import net.vnleng.generator.commons.Pair;
import net.vnleng.generator.data.ints.ResourceElement;
import net.vnleng.generator.data.ints.ResourceType;
import net.vnleng.generator.data.rules.ints.Rule;
import net.vnleng.generator.data.rules.ints.RuleType;
import net.vnleng.generator.data.scl.impls.DataBlockElement;
import net.vnleng.generator.data.scl.impls.DataBlockInstanceElement;
import net.vnleng.generator.data.scl.impls.FunctionBlockElement;
import net.vnleng.generator.data.scl.impls.FunctionElement;
import net.vnleng.generator.data.scl.ints.FunctionResource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author gabri
 */
public class Composer implements Serializable {

    private static final long serialVersionUID = 1L;

    private Rule<String, ResourceElement, ComposerProperties> fileName;
    private Rule<String, String, ComposerProperties> resourceName;

    public static List<Pair<String, String>> getComposerHelp() {
        ArrayList<Pair<String, String>> replacements = new ArrayList<>();
        replacements.add(new Pair<>("$NAME$", "Nome dell'elemento"));
        replacements.add(new Pair<>("$NUMBER$", "Numero dell'elemento generato"));
        replacements.add(new Pair<>("$TIME$", "Orario corrente"));
        replacements.add(new Pair<>("$DATE$", "Data corrente"));
        return replacements;
    }

    public Composer() {
    }

    private String formatter(String rule, ResourceElement element, ComposerProperties properties) {
        String s = rule.replaceAll("$NAME$", element.getName());
        if (properties != null) {
            s = s.replaceAll("$NUMBER$", "" + properties.getNumber());
        }
        Date d = new Date();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy_dd_MM");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH_mm_ss");
        s = s.replaceAll("$TIME$", time.format(d.toInstant()));
        s = s.replaceAll("$DATE$", date.format(d.toInstant()));
        return s;
    }

    public void setFileNameRule(String stringRule) {
        fileName = new Rule<String, ResourceElement, ComposerProperties>() {
            @Override
            public RuleType getRuleType() {
                return RuleType.FILE_RULE;
            }

            @Override
            public String compute(ResourceElement param1, ComposerProperties param2) {
                return formatter(stringRule, param1, param2);
            }
        };
    }

    public void setResourceNameRule(String stringRule) {
        resourceName = new Rule<String, String, ComposerProperties>() {
            @Override
            public RuleType getRuleType() {
                return RuleType.NAME_RULE;
            }

            @Override
            public String compute(String param1, ComposerProperties param2) {
                String s = stringRule.replaceAll("$NAME$", param1);
                if (param2 != null) {
                    s = s.replaceAll("$NUMBER$", "" + param2.getNumber());
                }
                Date d = new Date();
                DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy_dd_MM");
                DateTimeFormatter time = DateTimeFormatter.ofPattern("HH_mm_ss");
                s = s.replaceAll("$TIME$", time.format(d.toInstant()));
                s = s.replaceAll("$DATE$", date.format(d.toInstant()));
                return s;
            }
        };
    }

    public ResourceElement generateElement(String resourceName, ResourceType type, FunctionResource toInstantiate) {
        String name = this.resourceName.compute(resourceName, null);
        ResourceElement re = null;
        switch (type) {
            case DataBlock ->
                re = new DataBlockElement(name);
            case Function ->
                re = new FunctionElement(name);
            case FunctionInstance -> {
                if (toInstantiate != null) {
                    re = new DataBlockInstanceElement(name, toInstantiate);
                } else {
                    throw new IllegalArgumentException("Function to instantiate must be not-null.");
                }
            }
            case FunctionBlock ->
                re = new FunctionBlockElement(name);
            default ->
                throw new IllegalArgumentException("ResourceType invalid.");
        }
        return re;
    }

    public File generateFile(String path, ResourceElement element, ComposerProperties properties) throws IOException {
        String fname = this.fileName.compute(element, properties);
        File f = new File(path + File.separatorChar + fname);
        try (FileWriter fw = new FileWriter(f)) {
            fw.write(element.toString());
            fw.flush();
        } catch (IOException ex) {
            throw ex;
        }
        return f;
    }

}

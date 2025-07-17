/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.vnleng.generator.transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.vnleng.generator.data.errs.TypeConversionException;
import net.vnleng.generator.data.ints.Variable;
import net.vnleng.generator.data.ints.VariableType;

/**
 * Allows to transform data from plain CSV, with tabbed columns, to Variables.
 * <p>
 * It converts the data in the formats allowed in TIA Portal:
 * <ul>
 * <li>10 columns: e.g. the interface of an FB not optimized</li>
 * <li>9 columns: e.g. the interface of an FB optimized</li>
 * <li>4 columns: e.g. the interface of an FC</li>
 * </ul>
 * </p>
 * This class allows straight copy&paste from TIA Portal interfaces or Excel
 * without needing to define the variable from this SW.
 *
 * @author gabri
 */
public class CopyTransfer {

    private static final Pattern LIST_MATCHER = Pattern.compile("([a-zA-Z]+)\\[([0-9]+)\\]");
    private static final Pattern ARRAY_MATCHER = Pattern.compile("Array\\[([0-9]+)..([0-9]+)\\] of ([a-zA-Z]+)");

    private final String data;
    private final List<Variable> generic;
    private final List<Variable> inputs;
    private final List<Variable> outputs;
    private final List<Variable> inouts;
    private final List<Variable> statics;
    private final List<Variable> temps;
    private final List<Variable> consts;

    /**
     * Converts the data to variables dividing them in their list: inputs,
     * outputs, inouts,...
     *
     * If no type is specified then "generic" is used: {@link #getGeneric() }
     *
     * @param data The data containing the Tabbed Table to be converted.
     */
    public CopyTransfer(String data) {
        this.data = data;
        generic = new ArrayList<>();
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        inouts = new ArrayList<>();
        statics = new ArrayList<>();
        temps = new ArrayList<>();
        consts = new ArrayList<>();
        compute();
    }

    private void compute() {
        String dt = this.data.replaceAll("\r", "");
        String lines[] = dt.split("\n");
        List<Variable> ref = generic;
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replaceFirst("\t", "");
            String columns[] = lines[i].split("\t");
            if (columns.length == 0 || columns[0].isBlank()) {
                continue;
            }
            if (columns.length == 1) { //Il tipo di dato è vuoto
                ref = switch (columns[0].toLowerCase()) {
                    case "input" ->
                        inputs;
                    case "output" ->
                        outputs;
                    case "inout" ->
                        inouts;
                    case "static" ->
                        statics;
                    case "temp" ->
                        temps;
                    case "constant" ->
                        consts;
                    case "return" -> {
                        yield null;
                    }
                    default ->
                        generic;
                };
                if (ref == null) {
                    return;
                }
                continue;
            }
            final String varTypeDecl = columns[1].toLowerCase();
            int length = 0;
            int start = 0;
            String type = varTypeDecl;
            boolean isArr = false;
            boolean isList = false;

            Matcher listMatcher = LIST_MATCHER.matcher(varTypeDecl);
            if (listMatcher.find()) {
                isList = true;
                type = listMatcher.group(1);
                length = Integer.parseInt(listMatcher.group(2));
            }
            Matcher arrayMatcher = ARRAY_MATCHER.matcher(varTypeDecl);
            if (arrayMatcher.find()) {
                isArr = true;
                type = arrayMatcher.group(3);
                start = Integer.parseInt(arrayMatcher.group(1));
                length = Integer.parseInt(arrayMatcher.group(2)) - start;
            }
            VariableType vt = convertString(type);
            Variable v = new Variable(columns[0], vt);
            if (isArr) {
                v.getModifier().setIsArray(isArr, start, length);
            }
            if (isList) {
                v.getModifier().setIsList(isList, length);
            }
            String startValue = null;
            String comment = null;
            switch (columns.length) {
                case 10 -> {
                    //Nome-Tipo-Offset-StartValue-Accessible-...-Comment
                    startValue = columns[3];
                    comment = columns[9];
                }
                case 9 -> {
                    //nel caso di DB ottimizzati senza offset
                    startValue = columns[2];
                    comment = columns[8];
                }
                case 4 -> {
                    //Nel caso di solamente le 4 colonne
                    startValue = columns[2];
                    comment = columns[3];
                }
            }
            v.setComment(comment);
            v.setDefaultValue(startValue);
            ref.add(v);
        }
    }

    private static VariableType convertString(String varType) {
        return switch (varType) {
            case "bool" ->
                VariableType.Bool;
            case "byte" ->
                VariableType.Byte;
            case "word" ->
                VariableType.Word;
            case "dword" ->
                VariableType.DWord;
            case "char" ->
                VariableType.Char;
            case "sint" ->
                VariableType.Sint;
            case "int" ->
                VariableType.Int;
            case "dint" ->
                VariableType.DInt;
            case "usint" ->
                VariableType.USInt;
            case "uint" ->
                VariableType.UInt;
            case "udint" ->
                VariableType.UDInt;
            case "real" ->
                VariableType.Real;
            case "lreal" ->
                VariableType.LReal;
            case "string" ->
                VariableType.String;
            case "wstring" ->
                VariableType.WString;
            default -> {
                throw new TypeConversionException(varType);
            }
        };
    }

    public List<Variable> getConsts() {
        return consts;
    }

    public String getData() {
        return data;
    }

    /**
     * All variables not defined to be in a specific context will end in this
     * list.
     *
     * @return A list of variables that do not appartain to oher categories.
     */
    public List<Variable> getGeneric() {
        return generic;
    }

    public List<Variable> getInOuts() {
        return inouts;
    }

    public List<Variable> getInputs() {
        return inputs;
    }

    public List<Variable> getOutputs() {
        return outputs;
    }

    public List<Variable> getStatics() {
        return statics;
    }

    public List<Variable> getTemps() {
        return temps;
    }

}

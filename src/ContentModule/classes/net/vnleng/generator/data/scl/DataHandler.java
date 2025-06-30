package net.vnleng.generator.data.scl;

import net.vnleng.generator.data.ints.Variable;

/**
 *
 * @author gabri
 */
public class DataHandler {

    public static boolean hasDefault(Variable v) {
        return v.getDefaultValue() != null;
    }

    public static Object getValue(Variable v) {
        if(v == null){
            return null;
        }
        return switch (v.getType()) {
            case Variable -> "#" + v.getName();
            case Tag -> "\"" + v.getName() + "\"";
            default -> computeDefault(v);
        };
    }

    public static Object computeDefault(Variable v) {
        if (v == null) {
            return null;
        }
        if (v.getDefaultValue() != null) {
            return v.getDefaultValue();
        }
        switch (v.getType()) {
            case Bool -> {
                return "FALSE";
            }
            case DInt, DWord, Int, Sint, UDInt, UInt, USInt, Word, Byte -> {
                return 0;
            }
            case Char, String -> {
                return "''";
            }
            case LReal, Real -> {
                return 0.0d;
            }
            default -> {
                return null;
            }
        }
    }
}

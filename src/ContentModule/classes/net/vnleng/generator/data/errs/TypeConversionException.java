
package net.vnleng.generator.data.errs;

/**
 *
 * @author gabri
 */
public class TypeConversionException extends RuntimeException {

    public TypeConversionException(String type) {
        super("Variable Type can't be converted: unknown '" + type + "' type.");
    }

}

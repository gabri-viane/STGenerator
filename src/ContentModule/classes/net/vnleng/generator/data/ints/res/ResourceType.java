package net.vnleng.generator.data.ints.res;

import java.io.Serializable;

/**
 * Rappresenta il tipo di risorsa che è possibile generare tramite questo
 * programma.
 *
 * @author gabri
 */
public enum ResourceType implements Serializable {

    FunctionBlock("FB"), Function("FC"), DataBlock("DB"), FunctionInstance("DBI");

    private static final long serialVersionUID = 1L;
    private final String shortName;

    private ResourceType(String shrtName) {
        this.shortName = shrtName;
    }

    @Override
    public String toString() {
        return this.shortName;
    }

}

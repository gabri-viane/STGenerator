package net.vnleng.generator.data.ints.var;

import java.io.Serializable;
import java.util.Objects;
import net.vnleng.generator.commons.block.KeyLock;
import net.vnleng.generator.data.ints.res.VariablePack;

/**
 * Rappresenta una variabile di una tabella.
 * <p>
 * La variabile viene definita tramite:
 * <ul>
 * <li>Nome</li>
 * <li>Tipo e modificatori</li>
 * </ul>
 * Inoltre è possibile assegnare un valore di default e un commento.
 * </p>
 * <p>
 * Questa classe è serializzabile ed è alla base del programma. Per comodità il
 * valore di default è definito come stringa in quanto questo programma non si
 * occupa di verificare la correttezza della dichiarazione o definizione ma solo
 * della generazione batch delle DB.
 * </p>
 * <p>
 * Le variabili devono essere utilizzate, per consentirne la gestione corretta,
 * tramite un {@link VariablePack} che gestiscono l'unicità della variabile in
 * una lista e la sua posizione nell'elenco.
 * </p>
 * <p>
 * Questa classe inoltre sovrascrive il metodo di {@link #equals(java.lang.Object)
 * } per verificare solamente che nome e tipo siano uguali. Da notare che il
 * TypeModifier non influenza la condizione di uguaglianza.
 * </p>
 *
 *
 * @author gabri
 */
public class Variable implements Serializable {

    private static final long serialVersionUID = 3L;

    /**
     * Nome della variabile: deve essere univoco in una lista per consentire la
     * gestione corretta
     */
    private String name;
    /**
     * Tipo della variabile
     */
    private VariableType type;
    /**
     * Il modificatore di tipo consente di definire questa variabile come
     * un'array oppure per definire la dimensione della variabile nel caso
     * questa si tratti di una stringa.
     */
    protected final VariableTypeModifier typeModifier;
    /**
     * Valore di default: è una stringa, se "null" allora il valore non viene
     * assegnato. La stringa permette una definizione libera del valore.
     */
    private String defaultValue;
    /**
     * Commento, opzionale, che viene aggiunto solo alla definizione della
     * variabile.
     */
    private String comment;

    /**
     * Crea una nuova instanza della variabile definendono il nome e il tipo. Di
     * base {@link #comment} è una stringa vuota e {@link #defaultValue} è
     * nulla.
     *
     * <p>
     * Se {@code  varType} è {@link VariableType#String} allora viene definita
     * con una lunghezza di base pari a 5.
     * </p>
     *
     * @param name Nome della variabile
     * @param varType Tipo della variabile
     */
    public Variable(String name, VariableType varType) {
        this.name = name;
        this.type = varType;
        this.comment = "";
        this.defaultValue = null;
        this.typeModifier = new VariableTypeModifier();
        if (varType == VariableType.String || varType == VariableType.WString) {
            typeModifier.setIsList(true, 5);
        }
    }

    /**
     * Imposta il valore di default da assegnare alla variabile
     *
     * @param defaultValue Valore di default. Può essere anche nullo.
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Imposta il commento della variabile. Viene inserito solo nella
     * dichiarazione.
     *
     * @param comment Commento: una qualsiasi stringa.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Restituisce il commento, se associato.
     *
     * @return Il commento.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Restituisce il valore di default, se inseritp.
     *
     * @return Il valore di default.
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Restituisce il nome della variabile.
     *
     * @return Nome della variabile.
     */
    public String getName() {
        return name;
    }

    /**
     * Questo metodo permette di modificare il nome della variabile. Deve essere
     * gestito quando però la variabile è già inserito in una lista, come ad
     * esempio fa la classe {@link VariablePack}, in quanto non viene scatenato
     * nessuna evento di modifica del nome.
     *
     * @param name Il nome della variabile: non può essere nullo o una stringa
     * vuota.
     */
    public void setName(KeyLock lock, String name) {
        if (lock == null || name == null || name.isBlank()) {
            return;
        }
        this.name = name;
    }

    /**
     * Imposta il tipo della variabile.
     *
     * @param type Tipo della variabile: non può essere nullo
     */
    public void setType(VariableType type) {
        if (type == null) {
            return;
        }
        this.type = type;
    }

    /**
     * Restituisce l'istanza associata a questo oggetto del modificatore del
     * tipo di variabile.
     *
     * @return L'istanza associata.
     */
    public VariableTypeModifier getModifier() {
        return this.typeModifier;
    }

    /**
     * Restituisce il tipo della variabile.
     *
     * @return Tipo della variabile.
     */
    public VariableType getType() {
        return type;
    }

    /**
     * Restituisce la dichiarazione di questa variabile come usato in SCL,
     * ovvero:
     * <p>
     * {@code [NOME] : [TIPO]; {//[COMMENTO]}}
     * </p>
     * Dove il commento è opzionale se è stato inserito.
     *
     * @return La stringa formattata
     */
    public String getDeclaration() {
        StringBuilder sb = new StringBuilder(this.name);
        sb.append(" : ").append(getFullType(this)).append(";");
        if (this.comment != null && !this.comment.isBlank()) {
            sb.append(" //").append(this.comment);
        }
        return sb.toString();
    }

    /**
     * Restituisce il tipo completo di una variabile: ovvero il tipo di
     * variabile più gli eventuali modificatori di dimensione o di Array.
     * <p>
     * Un esempio di dichiarazione con array è:<br/> {@code Array[n] of Bool}
     * </p>
     *
     * @param v Variabile da cui prendere il tipo e il modificatore
     * @return La stringa del tipo estesa.
     */
    public static String getFullType(Variable v) {
        if (v.typeModifier.isList()) {
            StringBuilder sb = new StringBuilder(v.type.toString());
            sb.append("[").append(v.typeModifier.getLength()).append("]");
            return sb.toString();
        }
        if (v.typeModifier.isArray()) {
            StringBuilder sb = new StringBuilder("Array[");
            sb.append(v.typeModifier.getStart()).append("..")
                    .append(v.typeModifier.getStart() + v.typeModifier.getLength())
                    .append("] of ").append(v.type.toString());
            return sb.toString();
        }
        return v.type.toString();
    }

    /**
     * Crea una copia di questa variabile: il nome copiato è lo stesso e deve
     * quindi essere gestita in una lista.
     *
     * @return La copia di questa variabile
     */
    public Variable copy() {
        Variable v = new Variable(name, type);
        v.comment = comment;
        v.defaultValue = defaultValue;
        v.typeModifier.of(this);
        return v;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable v) {
            return v.name.equals(name) && v.type.equals(type);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.type);
        return hash;
    }

}

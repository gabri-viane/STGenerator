package net.vnleng.generator.data.ints.res;

import java.io.Serializable;

/**
 * Interfaccia che le risorse devono estendere per essere gestite dal programma.
 * Una risorsa è un elemento che può essere gestito nella generazione, come un
 * Data Block, una Function o una Function Block. Le risorse sono quindi
 * serializzabili ed hanno un nome, una dichiarazione e una definizione.
 *
 * @author gabri
 */
public interface Resource extends Serializable {

    /**
     * Restituisce il nome della risorsa corrente.
     *
     * @return Nome della risorsa.
     */
    public String getName();

    /**
     * Restituisce la definizione, ovvero il corpo di codice che segue la
     * dichiarazione.
     *
     * @return La stringa di definizione SCL.
     */
    public String getDefinition();

    /**
     * Restituisce la dichiarazione, ovvero il corpo di codice che precede la
     * definizione e che contiene il nome e tipo di risorsa e le eventuali
     * risorse associate.
     *
     * @return La stringa di dichiarazione SCL.
     */
    public String getDeclaration();

    /**
     * Restituisce la tipologia di risorsa che rappresenta l'istanza corrente.
     *
     * @return Il tipo di risorsa.
     */
    public ResourceType getType();
}

package it.unicam.cs.pa2223.view;

/**
 * Rappresenta l'interfaccia con cui un utente interagisce
 * @param <T> tipo dell'interazione che avviene tra l'utente e il sistema.
 */
public interface IView<T> {

    /**
     * Permette di inviare un messaggio all'utente.
     * @param message messaggio da inviare all'utente.
     */
    void message(T message);

    /**
     * Permette di inviare un messaggio all'utente e di ottenere una risposta da esso.
     * @param message messaggio da mandare all'utente.
     * @return messaggio di risposta.
     */
    T ask(T message);


    /**
     * Permette di scegliere un intero che va da 1 al limite dato.
     * @param message messaggio da mostrare all'utente.
     * @param upperBound limite superiore del numero da scegliere.
     * @return numero scelto.
     */
    int fetchChoice(T message, int upperBound);

    /**
     * Permette di scegliere un intero.
     * @return numero intero scelto.
     */
    int fetchInt();


    /**
     * Permette di scegliere un booleano.
     * @return valore booleano scelto
     */
    boolean fetchBool();
}

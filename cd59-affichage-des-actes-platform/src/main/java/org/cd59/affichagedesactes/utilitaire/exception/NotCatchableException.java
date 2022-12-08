package org.cd59.affichagedesactes.utilitaire.exception;

public class NotCatchableException extends UtilitaireException {
    /**
     * Initialise une nouvelle instance de la classe {@link UtilitaireException}.
     * @param message Le message de l'exception.
     */
    public NotCatchableException(String message) {
        super(message);
    }
}

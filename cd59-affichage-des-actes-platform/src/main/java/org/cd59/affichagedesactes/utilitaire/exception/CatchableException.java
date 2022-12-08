package org.cd59.affichagedesactes.utilitaire.exception;

/**
 * Exception personnalisée utilisée si l'erreur est métier et qu'elle doit être attrapée.
 */
public class CatchableException extends UtilitaireException {
    /**
     * Initialise une nouvelle instance de la classe {@link UtilitaireException}.
     * @param message Le message de l'exception.
     */
    public CatchableException(String message) {
        super(message);
    }
}

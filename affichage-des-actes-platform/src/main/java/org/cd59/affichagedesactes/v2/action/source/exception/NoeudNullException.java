package org.cd59.affichagedesactes.v2.action.source.exception;

/**
 * Exception personnalisée si le nœud est null ou vide.
 */
public class NoeudNullException extends ActionMetierException {
    /**
     * Initialise une nouvelle instance de la classe {@link NoeudNullException}.
     *
     * @param message Le message d'exception accompagnant l'exception.
     */
    public NoeudNullException(String message) {
        super(message);
    }
}

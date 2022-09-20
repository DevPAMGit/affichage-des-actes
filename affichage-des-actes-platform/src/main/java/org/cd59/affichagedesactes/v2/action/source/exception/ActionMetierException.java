package org.cd59.affichagedesactes.v2.action.source.exception;

/**
 * Exception personnalisée pour toutes les exceptions lancée depuis une action.
 */
public class ActionMetierException extends Exception {
    /**
     * Initialise une nouvelle instance de la classe {@link ActionMetierException}.
     * @param message Le message d'exception accompagnant l'exception.
     */
    public ActionMetierException(String message) {
        super(message);
    }
}

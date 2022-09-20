package org.cd59.affichagedesactes.v2.action.source.exception;

/**
 * Exception personnalisée lancée si le nom du
 */
public class NomNoeudVideException extends ActionMetierException {
    /**
     * Initialise une nouvelle instance de la classe {@link NomNoeudVideException}.
     * @param message Le message d'exception accompagnant l'exception.
     */
    public NomNoeudVideException(String message) {
        super(message);
    }
}

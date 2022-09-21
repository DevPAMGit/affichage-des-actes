package org.cd59.affichagedesactes.v2.action.source.exception;

/**
 * Exception personnalisée si les préconditions ne sont pas respectées.
 */
public class PreconditionException extends ActionMetierException {
    /**
     * Initialise une nouvelle instance de la classe {@link PreconditionException}.
     * @param message Le message d'exception accompagnant l'exception.
     */
    public PreconditionException(String message) {
        super(message);
    }
}

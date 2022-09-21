package org.cd59.affichagedesactes.v2.action.envoi.exception;

import org.cd59.affichagedesactes.v2.action.source.exception.ActionMetierException;

/**
 * Exception personnalisée lancée si le retour attendu pour le web service n'est pas valide.
 */
public class ResultatNonConformeException extends ActionMetierException {
    /**
     * Initialise une nouvelle instance de la classe {@link ResultatNonConformeException}.
     * @param message Le message d'exception accompagnant l'exception.
     */
    public ResultatNonConformeException(String message) {
        super(message);
    }
}

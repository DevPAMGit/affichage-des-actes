package org.cd59.affichagedesactes.action.custom.stockage.exception;

import org.cd59.affichagedesactes.action.custom.source.exception.CD59Exception;

/**
 * Exception personnalisée pour toutes les erreurs métiers d'une action.
 */
public class ActionMetierException extends CD59Exception {
    /**
     * Initialise une nouvelle instance de la classe {@link ActionMetierException}.
     * @param message Le message de l'exception.
     */
    public ActionMetierException(String message) {
        super(message);
    }

}

package org.cd59.affichagedesactes.action.custom.source.exception.prerequis;

import org.cd59.affichagedesactes.utilitaire.exception.UtilitaireException;

/**
 * Exception personnalisée si d'utilisation de librairies ont lieu.
 */
public class PreRequisException extends UtilitaireException {
    /**
     * Initialise une nouvelle instance de classe  {@link PreRequisException}.
     * @param message Le message de l'exception.
     */
    public PreRequisException(String message) {
        super(message);
    }
}

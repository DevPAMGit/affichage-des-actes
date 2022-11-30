package org.cd59.affichagedesactes.modele.donnee.exception;

import org.cd59.affichagedesactes.utilitaire.UtilitaireException;

/**
 * Exception personnalisée pour les modèles de données.
 */
public class ModeleException extends UtilitaireException {
    /**
     * Initialise une nouvelle instance de la classe {@link ModeleException}.
     * @param message Le message de l'exception.
     */
    public ModeleException(String message) {
        super(message);
    }
}

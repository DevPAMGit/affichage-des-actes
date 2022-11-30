package org.cd59.affichagedesactes.action.custom.stockage.exception;

/**
 * Exception personnalisée si un aspect attendu n'est pas présent.
 */
public class AspectNonPresentException extends ActionMetierException {
    /**
     * Initialise une nouvelle instance de la classe {@link AspectNonPresentException}.
     * @param prefix Le préfixe de l'aspect attendu.
     * @param nom Le nom de l'aspect attendu.
     */
    public AspectNonPresentException(String prefix, String nom) {
        super(String.format("L'aspect %s:%s n'est pas présent sur le noeud.", prefix, nom));
    }
}

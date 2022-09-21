package org.cd59.affichagedesactes.v2.action.source.exception;

/**
 * Exception personnalisée indiquant qu'un nœud n'a pas l'aspect attendu.
 */
public class AspectException extends ActionMetierException {
    /**
     * Initialise une nouvelle instance de la classe {@link AspectException}.
     * @param nomAspect Le nom de l'aspect.
     */
    public AspectException(String nomAspect) {
        super(String.format("Le noeud n'a pas l'aspect ('%s') requis.", nomAspect));
    }
}

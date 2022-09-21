package org.cd59.affichagedesactes.v2.action.source.exception;

/**
 * Exception personnalisée si une requête de recherche est null ou vide.
 */
public class RequeteRechercheNullException extends ActionMetierException {
    /**
     * Initialise une nouvelle instance de la classe {@link RequeteRechercheNullException}.
     */
    public RequeteRechercheNullException() {
        super("La requete de votre recherche est null ou vide.");
    }
}

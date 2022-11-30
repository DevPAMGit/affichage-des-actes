package org.cd59.affichagedesactes.action.custom.source.exception.prerequis;

public class RequeteNullException extends PreRequisException {
    /**
     * Initialise une nouvelle instance de classe  {@link PreRequisException}.
     */
    public RequeteNullException() {
        super("La requête de recherche ne peut être null ou vide.");
    }
}

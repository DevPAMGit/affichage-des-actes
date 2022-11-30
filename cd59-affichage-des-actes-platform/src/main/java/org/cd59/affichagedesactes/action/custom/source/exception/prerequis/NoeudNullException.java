package org.cd59.affichagedesactes.action.custom.source.exception.prerequis;

/**
 * Classe d'exception personnalisée pour une erreur sur un nœud null.
 */
public class NoeudNullException extends PreRequisException {
    /**
     * Initialise une nouvelle instance de classe  {@link NoeudNullException}.
     */
    public NoeudNullException() {
        super("Le noeud en paramètre ne peut être null ou vide.");
    }
}

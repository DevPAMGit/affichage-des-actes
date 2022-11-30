package org.cd59.affichagedesactes.action.custom.source.exception.prerequis;

/**
 * Classe d'exception personnalisée pour une erreur sur une propriété nulle.
 */
public class QNameNullException extends PreRequisException {
    /**
     * Initialise une nouvelle instance de classe  {@link QNameNullException}.
     */
    public QNameNullException() {
        super("La propriété en paramètre ne peut être null ou vide.");
    }
}

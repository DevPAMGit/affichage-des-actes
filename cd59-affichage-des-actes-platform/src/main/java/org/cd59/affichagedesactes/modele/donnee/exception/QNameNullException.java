package org.cd59.affichagedesactes.modele.donnee.exception;

/**
 * Classe d'exception personnalisée pour une erreur sur une propriété nulle.
 */
public class QNameNullException extends ModeleException {
    /**
     * Initialise une nouvelle instance de classe  {@link QNameNullException}.
     */
    public QNameNullException() {
        super("La propriété en paramètre ne peut être null ou vide.");
    }
}

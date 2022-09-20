package org.cd59.affichagedesactes.v2.action.source.exception;

/**
 * Exception personnalisée si une propriété ({@link org.alfresco.service.namespace.QName} mise en paramètre est null ou
 * vide.
 */
public class QNameNullException extends ActionMetierException {

    /**
     * Initialise une nouvelle instance de la classe {@link QNameNullException}.
     * @param message Le message d'exception accompagnant l'exception.
     */
    public QNameNullException(String message) {
        super(message);
    }
}

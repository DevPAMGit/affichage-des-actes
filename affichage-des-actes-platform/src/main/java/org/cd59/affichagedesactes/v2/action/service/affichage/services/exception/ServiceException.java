package org.cd59.affichagedesactes.v2.action.service.affichage.services.exception;

/**
 * Classe d'exception personnalisée lancée si une erreur de service est lancée.
 */
public class ServiceException extends Exception {

    /**
     * Initialise une nouvelle instance de la classe ServiceException.
     * @param message Le message d'erreur de l'exception.
     */
    public ServiceException(String message) {
        super(message);
    }
}

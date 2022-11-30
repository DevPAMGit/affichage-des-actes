package org.cd59.affichagedesactes.modele.donnee.exception;

/**
 * Exception personnalisée si le registre de service pour un modèle est null.
 */
public class RegistreServiceNullException extends ModeleException {
    /**
     * Initialise une nouvelle instance de la classe {@link RegistreServiceNullException}.
     * @param nomModele Le nom du modèle.
     */
    public RegistreServiceNullException(String nomModele) {
        super(String.format("Le registre de service du modèle '%s' ne peut être null.", nomModele));
    }
}

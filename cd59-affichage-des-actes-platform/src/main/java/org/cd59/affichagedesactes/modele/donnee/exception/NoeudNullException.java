package org.cd59.affichagedesactes.modele.donnee.exception;

/**
 * Exception personnalisée si le registre de service pour un modèle est null.
 */
public class NoeudNullException extends ModeleException {
    /**
     * Initialise une nouvelle instance de la classe {@link NoeudNullException}.
     * @param nomModele Le nom du modèle.
     */
    public NoeudNullException(String nomModele) {
        super(String.format("Le noeud du modèle '%s' ne peut être null.", nomModele));
    }
}

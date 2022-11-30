package org.cd59.affichagedesactes.action.custom.envoi.service.affichage.services.modele;

/**
 * Modele pour les fichiers de l'envoie d'acte.
 */
public class EnvoiActeFichierModel {

    /**
     * Le nom du fichier.
     */
    public final String nom;

    /**
     * Le contenu du fichier.
     */
    public final byte[] contenu;

    /**
     * Initialise une nouvelle instance de la classe EnvoiActeFichierModel.
     * @param nom Le nom du fichier.
     * @param contenu Le contenu du fichier.
     */
    public EnvoiActeFichierModel(String nom, byte[] contenu) {
        this.nom = String.format("%s.pdf", nom);
        this.contenu = contenu;
    }
}

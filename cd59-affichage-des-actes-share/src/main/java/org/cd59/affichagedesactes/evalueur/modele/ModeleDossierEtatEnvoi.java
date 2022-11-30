package org.cd59.affichagedesactes.evalueur.modele;

/**
 * Enumération pour le stockage des dossiers.
 */
public enum ModeleDossierEtatEnvoi {
    /**
     * Le dossier est en attente du stockage.
     */
    EN_ATTENTE("En attente du stockage"),

    /**
     * Le dossier est prêt à être envoyé.
     */
    PRET_A_ETRE_ENVOYE("Prêt à être envoyé"),

    /**
     * Le dossier est envoyé.
     */
    ENVOYE("Envoyé"),

    /**
     * L'envoi du dossier est en erreur.
     */
    ERREUR("Erreur");

    /**
     * La valeur de l'énumération.
     */
    String valeur;

    /**
     * Initialise une nouvelle valeur de l'énumération ModeleDossierEtatEnvoi.
     * @param valeur La valeur de l'énumération.
     */
    ModeleDossierEtatEnvoi(String valeur) {
        this.valeur = valeur;
    }

    /**
     * Recherche l'énumération associée à la valeur.
     * @param valeur La valeur dont on souhaite trouver l'énumération.
     */
    public static ModeleDossierEtatEnvoi searchValeur(String valeur) {
        ModeleDossierEtatEnvoi resultat = null;
        ModeleDossierEtatEnvoi[] enums = values();
        int index = 0, max = valeur.length();

        while(index < max && !enums[index].valeur.equals(valeur))
            index++;

        if(index == max) return EN_ATTENTE;
        return enums[index];
    }
}

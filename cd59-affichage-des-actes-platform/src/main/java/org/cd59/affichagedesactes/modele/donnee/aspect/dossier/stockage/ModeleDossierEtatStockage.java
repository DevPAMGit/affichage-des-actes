package org.cd59.affichagedesactes.modele.donnee.aspect.dossier.stockage;

/**
 * Enumération pour le stockage des dossiers.
 */
public enum ModeleDossierEtatStockage {
    /**
     * Le dossier est prêt à être stocké.
     */
    PRET_A_ETRE_STOCKE("Prêt à être stocké"),

    /**
     * Le dossier est stocké.
     */
    STOCKE("Stocké"),

    /**
     * Le stockage du dossier est en erreur.
     */
    ERREUR("Erreur");

    /**
     * La valeur de l'énumération.
     */
    String valeur;

    /**
     * Initialise une nouvelle valeur de l'énumération ModeleDossierStockage.
     * @param valeur La valeur de l'énumération.
     */
    ModeleDossierEtatStockage(String valeur) {
        this.valeur = valeur;
    }

    /**
     * Recherche l'énumération associée à la valeur.
     * @param valeur La valeur dont on souhaite trouver l'énumération.
     */
    public static ModeleDossierEtatStockage searchValeur(String valeur) {
        ModeleDossierEtatStockage resultat = null;
        ModeleDossierEtatStockage[] enums = values();
        int index = 0, max = valeur.length();

        while(index < max && !enums[index].valeur.equals(valeur))
            index++;

        if(index == max) return PRET_A_ETRE_STOCKE;
        return enums[index];
    }
}

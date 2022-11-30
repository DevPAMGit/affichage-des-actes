package org.cd59.affichagedesactes.modele.donnee.aspect.document.source;

/**
 * Enumération sur le type de dossier.
 */
public enum ModeleDocumentType {

    /**
     * Un type acte original.
     */
    ACTE_ORIGINAL("ACTE_ORIGINAL"),

    /**
     * Un type acte.
     */
    ACTE("ACTE"),

    /**
     * Un type annexe.
     */
    ANNEXE("ANNEXE");

    /**
     * La valeur de l'énumération.
     */
    public final String valeur;

    /**
     * Initialise une nouvelle instance de la classe ETypeDossierInformation.
     * @param valeur La valeur de l'énumération.
     */
    ModeleDocumentType(String valeur) {
        this.valeur = valeur;
    }
}

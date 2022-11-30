package org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.type;

/**
 * Enumération sur le type de dossier.
 */
public enum ModeleDossierTypologieEnumeration {

    /**
     * Un type arrête.
     */
    ARRETE("ARRETE"),

    /**
     * Un type délibération.
     */
    DELIBERATION("DELIBERATION"),

    /**
     * Un type délibération.
     */
    Deliberation("Délibération"),

    /**
     * Un type Arrêté.
     */
    Arrete("Arrêté");

    /**
     * La valeur de l'énumération.
     */
    public final String valeur;

    /**
     * Initialise une nouvelle instance de la classe ETypeDossierInformation.
     * @param valeur La valeur de l'énumération.
     */
    ModeleDossierTypologieEnumeration(String valeur) {
        this.valeur = valeur;
    }
}

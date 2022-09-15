package org.cd59.affichagedesactes.v2.action.service.source.source;

/**
 * Lite des méthodes web disponibles.
 */
public enum MethodeRequeteWeb {
    /**
     * Requete POST.
     */
    POST("POST"),
    /**
     * Requete GET.
     */
    GET("GET");

    /**
     * Valeur de l'énumération.
     */
    public final String valeur;
    /** Initialize a new instance of {@link MethodeRequeteWeb} class.
     * @param value The method value. */

    /**
     * Initialise une nouvelle instance de la classe {@link MethodeRequeteWeb}.
     * @param valeur La valeur de l'énumération.
     */
    private MethodeRequeteWeb(String valeur) {
        this.valeur = valeur; }
}

package org.cd59.affichagedesactes.action.custom.envoi.service.source.web.core.url;

/**
 * Liste des méthodes disponibles.
 */
public enum UrlRequestMethod {

    /**
     * Méthode POST.
     */
    POST("POST"),

    /**
     * Méthode GET. */
    GET("GET");

    /**
     * La valeur de la méthode.
     */
    public final String value;

    /**
     * Initialise une nouvelle instance de la class UrlRequestMethod.
     * @param value La valeur de la méthode.
     */
    UrlRequestMethod(String value) { this.value = value; }
}

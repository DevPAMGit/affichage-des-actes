package org.cd59.affichagedesactes.action.custom.envoi.service.source.web.method.bearer;

import org.cd59.affichagedesactes.action.custom.envoi.service.source.web.method.classic.UrlPostMultipartRequestPost;

import java.io.IOException;

/**
 * Classe pour les requêtes web utilisant une authentification de type 'Bearer'.
 */
public abstract class BearerUrlPostPostMultipartRequest extends UrlPostMultipartRequestPost {

    /**
     * Le jeton d'authentification.
     */
    private final String token;

    /** Initialise une nouvelle instance de la classe {@link BearerUrlPostPostMultipartRequest} class.
     * @param host     Le nom de l'hôte ou null pour une adresse local.
     * @param resource La ressource à récupérer sur le serveur.
     * @param jeton    Le jeton d'authentification.
     * @throws IOException If an I/O error occurs when creating the socket. */
    public BearerUrlPostPostMultipartRequest(String host, String resource, String jeton) throws IOException {
        super(host, resource);
        this.token = jeton;
    }

    @Override
    protected void setHeaders() {
        super.setHeaders();
        this.addHeader("Authorization", String.format("Bearer %s", this.token));
    }
}

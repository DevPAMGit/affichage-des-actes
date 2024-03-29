package org.cd59.affichagedesactes.action.custom.envoi.service.affichage.services;



import org.cd59.affichagedesactes.action.custom.envoi.service.source.web.method.classic.RequeteURLPost;

import java.io.IOException;

/** Request to retrieve the authentication token. */
public class LoginCheckService extends RequeteURLPost {
    /** The service to call on the host. */
    private static final String RESOURCE = "/api/login_check";
    /**
     * Le login de connexion.
     */
    private final String username;

    /**
     * Le mot de passe du login de connexion.
     */
    private final String password;

    /** Initialise a new instance of {@link LoginCheckService} class.
     * @param host     The host name, or null for the loopback address.
     * @param username The user username.
     * @param password The user password.
     * @throws IOException              If an I/O error occurs when creating the socket.
     * @throws SecurityException        If a security manager exists and its checkConnect method doesn't allow the operation.
     * @throws IllegalArgumentException If the port parameter is outside the specified range of valid port values, which is between 0 and 65535, inclusive. */
    public LoginCheckService(String host, String username, String password) throws IOException {
        super(host, RESOURCE);
        this.username = username;
        this.password = password;
    }

    /**
     * Récupère le corps de la requête.
     * @return String Récupère la valeur du corps de la requête.
     */
    @Override
    protected String getBody() {
        return String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
    }

    /**
     * Modifie les en-têtes de la requête.
     */
    @Override
    protected void setHeaders() {
        this.addHeader("Content-Type", "application/json");
    }
}

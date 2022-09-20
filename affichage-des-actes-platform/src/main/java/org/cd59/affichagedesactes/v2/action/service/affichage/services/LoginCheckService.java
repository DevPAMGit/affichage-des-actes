package org.cd59.affichagedesactes.v2.action.service.affichage.services;



import org.cd59.affichagedesactes.v2.action.service.source.web.method.classic.RequeteURLPost;

import java.io.IOException;

/** Request to retrieve the authentication token. */
public class LoginCheckService extends RequeteURLPost {
    /** The service to call on the host. */
    private static final String RESOURCE = "/api/login_check";
    /** The  */
    private final String username;
    /** */
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

    @Override
    protected String getBody() {
        return String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
    }
    @Override
    protected void setHeaders() {
        this.addHeader("Content-Type", "application/json");
    }
}

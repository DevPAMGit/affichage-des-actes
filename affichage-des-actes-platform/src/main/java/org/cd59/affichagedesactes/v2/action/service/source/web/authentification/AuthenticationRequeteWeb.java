package org.cd59.affichagedesactes.v2.action.service.source.web.authentification;

import java.util.Base64;

/**
 * Génère l'élément pour l'entête "Authorization".
 */
public class AuthenticationRequeteWeb {
    /**
     *  Récupère l'authentification pour du 'Basic Auth'.
     */
    public static String basicAuth(String username, String password) {
        return Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes());
    }
}

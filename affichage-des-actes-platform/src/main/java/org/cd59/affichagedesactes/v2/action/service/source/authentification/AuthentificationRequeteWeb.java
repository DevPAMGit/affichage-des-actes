package org.cd59.affichagedesactes.v2.action.service.source.authentification;

import java.util.Base64;

/**
 * Contient les éléments de génération pour récupérer les éléments pour s'authentifier.
 */
public class AuthentificationRequeteWeb {

    /**
     * Récupère l'authentification pour une authentification basic.
     * @param login Le login.
     * @param motDePasse Le mot de passe lié au login.
     */
    public static String authentificationBasique(String login, String motDePasse) {
        return Base64.getEncoder().encodeToString(String.format("%s:%s", login, motDePasse).getBytes());
    }

}

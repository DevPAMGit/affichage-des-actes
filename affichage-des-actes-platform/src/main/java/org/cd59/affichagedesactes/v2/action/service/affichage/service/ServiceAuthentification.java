package org.cd59.affichagedesactes.v2.action.service.affichage.service;

import org.cd59.affichagedesactes.v2.action.service.source.methode.RequeteWebPost;

import java.io.IOException;

/**
 * Service d'authentification.
 */
public class ServiceAuthentification extends RequeteWebPost {

    /**
     * La ressource à attaquer sur le serveur.
     */
    private static final String RESSOURCE = "/api/login_check";

    /**
     * Le login d'authentification.
     */
    private final String identifiant;

    /**
     * Le mot de passe du login.
     */
    private final String motDePasse;

    /**
     * Initialise une nouvelle instance de la classe {@link ServiceAuthentification}.
     *
     * @param hote L'hôte sur lequel se connecter.
     * @param identifiant Le login de connexion.
     * @param motDePasse Le mot de passe lié au login.
     * @throws IOException Si une erreur d'entré/sortie est lancée à l'ouverture du socket.
     */
    public ServiceAuthentification(String hote, String identifiant, String motDePasse) throws IOException {
        super( String.format("%s%s", hote, RESSOURCE));
        this.motDePasse = motDePasse;
        this.identifiant = identifiant;
    }

    @Override
    protected void modifierEntetes() {
        this.ajouterEntete("Content-Type", "application/json");
    }

    @Override
    protected String obtenirCorps() {
        return String.format("{\"username\":\"%s\",\"password\":\"%s\"}", this.identifiant, this.motDePasse);
    }
}

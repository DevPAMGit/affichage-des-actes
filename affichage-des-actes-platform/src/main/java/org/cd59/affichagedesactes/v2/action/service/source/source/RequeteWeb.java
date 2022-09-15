package org.cd59.affichagedesactes.v2.action.service.source.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Classe permettant de gérer une communication web.
 */
public abstract class RequeteWeb {

    /**
     * Connection vers une URL.
     */
    protected final HttpURLConnection connexion;

    /**
     * Initialise une nouvelle instance de la classe {@link RequeteWeb}.
     * @param hote L'hôte sur lequel se connecter.
     * @param methode La méthode de la requête.
     * @throws IOException Si une erreur d'entré/sortie est lancée à l'ouverture du socket.
     */
    public RequeteWeb(String hote, MethodeRequeteWeb methode) throws IOException {
        this.connexion = (HttpURLConnection) new URL(hote).openConnection();
        this.connexion.setRequestMethod(methode.valeur);
        this.modifierEntetes();
    }

    /**
     * Execute la requête.
     * @throws IOException Si une erreur d'entré/sortie est lancée à l'ouverture du socket.
     */
    public String executer() throws IOException {
        this.envoyer();
        String resultat = this.obtenirReponse();
        if(this.connexion != null) this.connexion.disconnect();
        return resultat;
    }

    /**
     * Ajoute une valeur à l'entête.
     * @param cle La clé de l'entête.
     * @param valeur La valeur de l'entête.
     */
    protected void ajouterEntete(String cle, String valeur) {
        this.connexion.setRequestProperty(cle, valeur);
    }

    /**
     * Modifie/ajoute les valeurs contenues dans
     */
    protected abstract void modifierEntetes();

    /**
     * Récupère le corps du message à envoyer.
     * @return Le corps du message.
     */
    protected abstract String obtenirCorps();

    /**
     * Envoie la requête à l'hôte.
     * @throws IOException Si une erreur d'entré/sortie est lancée à l'écriture sur le socket.
     */
    protected void envoyer() throws IOException {
        String corps = this.obtenirCorps();
        OutputStream sortie = null;
        if(corps != null && !corps.isEmpty()){
            this.connexion.setDoOutput(true);
            sortie = this.connexion.getOutputStream();
            sortie.write(corps.getBytes());
        }
        sortie.flush();
        sortie.close();
    }

    /**
     * Récupère la réponse l'hôte.
     * @return La réponse de l'hôte.
     * @throws IOException Si une erreur d'entré/sortie est lancée à la récupération du lecteur de socket.
     */
    private String obtenirReponse() throws IOException {
        BufferedReader lecteur = new BufferedReader(new InputStreamReader(this.connexion.getInputStream()));
        StringBuilder resultat = new StringBuilder();
        String ligne = null;
        while((ligne = lecteur.readLine()) != null) resultat.append(ligne);
        lecteur.close();
        return resultat.toString();
    }

}

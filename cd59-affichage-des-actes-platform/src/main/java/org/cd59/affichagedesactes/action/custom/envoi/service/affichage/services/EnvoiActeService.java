package org.cd59.affichagedesactes.action.custom.envoi.service.affichage.services;

import org.cd59.affichagedesactes.action.custom.envoi.service.affichage.services.modele.EnvoiActeFichierModel;
import org.cd59.affichagedesactes.action.custom.envoi.service.source.web.method.bearer.BearerUrlPostPostMultipartRequest;
import org.json.JSONObject;

import java.io.IOException;

/** Classe de service permettant d'envoyer par méthode HTTP POST (multipart) un acte. */
public class EnvoiActeService extends BearerUrlPostPostMultipartRequest {

    /**
     * Le service à appeler sur l'hôte.
     */
    private static final String RESOURCE = "/api/document";

    /**
     * Le fichier d'acte à envoyer.
     */
    private final EnvoiActeFichierModel acte;

    /**
     * Les métadonnées de l'acte.
     */
    private final JSONObject metadonnees;

    /**
     * Le fichier d'annexe à envoyer.
     */
    private final EnvoiActeFichierModel annexe;

    /**
     * Initialise une nouvelle instance de la classe EnvoiActeService class.
     * @param host     L'adresse vers l'hôte, ou null si c'est une adresse locale (localhost).
     * @param token Le jeton de connexion au service web.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public EnvoiActeService(
            String host, String token, EnvoiActeFichierModel acte, EnvoiActeFichierModel annexe, JSONObject metadonnees)
            throws IOException {
        super(host, RESOURCE, token);

        this.metadonnees = metadonnees;
        this.annexe = annexe;
        this.acte = acte;
    }

    /**
     * Écrit le corps de la requête.
     * @throws IOException Si une erreur E/S est lancée.
     */
    @Override
    protected void ecrireLeCorps() throws IOException {

        this.envoyerFichier("file", this.acte.nom, this.acte.contenu);

        if(this.annexe != null)
            this.envoyerFichier("annexe", this.annexe.nom, this.annexe.contenu);

        this.envoyerChampJson("metadonnees", this.metadonnees);
        this.redacteur.append(String.format("--%s--", LIMITE));
        this.redacteur.flush();
    }
}

package org.cd59.affichagedesactes.v2.action.service.affichage;


import org.cd59.affichagedesactes.v2.action.service.affichage.services.EnvoiActeService;
import org.cd59.affichagedesactes.v2.action.service.affichage.services.LoginCheckService;
import org.cd59.affichagedesactes.v2.action.service.affichage.services.exception.ServiceException;
import org.cd59.affichagedesactes.v2.action.service.affichage.services.modele.EnvoiActeFichierModel;
import org.json.JSONObject;

import java.io.IOException;

public class AffichageDesActesWebservice {

    /**
     * L'hôte distant proposant les services webs.
     */
    private final String host;

    /**
     * Initialise une nouvelle instance de la classe AffichageDesActesWebservice.
     * @param hote L'hôte des services web.
     */
    public AffichageDesActesWebservice(String hote) {
        this.host = hote;
    }

    /**
     * Permet de récupérer le jeton d'authentification au serveur.
     * @param nomUtilisateur Le nom d'authentification pour le service web.
     * @param motDePasse Le mot de passe lié au nom d'authentification.
     * @throws IOException Si une erreur d'entrée sortie est lancée.
     */
    private String authentification(String nomUtilisateur, String motDePasse) throws IOException, ServiceException {
        String response = new LoginCheckService(this.host, nomUtilisateur, motDePasse).call();
        System.out.println(response);
        JSONObject json = new JSONObject(response);
        String message = null;

        if(json.has("token"))
            return json.getString("token");

        else if(json.has("message")) message = json.getString("message");

        if(message == null)
            throw new ServiceException(
                    String.format("Une erreur est apparut lors de l'authentification au service web : %s.",
                            json));

        throw new ServiceException(message);
    }

    /** Permet l'envoi d'acte pour affichage.
     * @param nomUtilisateur Le nom d'authentification pour le service web.
     * @param motDePasse Le mot de passe lié au nom d'authentification.
     */
    public String envoyerActe(String nomUtilisateur, String motDePasse, EnvoiActeFichierModel acte,
                              EnvoiActeFichierModel annexe, JSONObject metadonnees) throws IOException, ServiceException
    {
        String resultat = new EnvoiActeService(this.host, this.authentification(nomUtilisateur, motDePasse), acte, annexe,
                metadonnees).call();
        System.out.print(resultat);
        return resultat;
    }
}

package org.cd59.affichagedesactes.metiers.factory;

import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.erreurbase.ErreurBaseAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.erreurdossier.ErreurDossierAspectModele;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe factory pour générer "automatiquement" les modèles d'erreur.
 */
public class ErreurFactory {

    /**
     * Méthode permettant de récupérer les paramètres nécessaires pour l'aspect d'erreurs.
     * @param message Le message d'erreur.
     * @return La liste des paramètres pour l'aspect d'erreur.
     */
    public static HashMap<QName, Serializable> obtenirParametresErreur(String message) {
        HashMap<QName, Serializable> parametres = new HashMap<>();

        parametres.put(ErreurBaseAspectModele.DATE_ERREUR, new Date());
        parametres.put(ErreurBaseAspectModele.MESSAGE_ERREUR, message);

        return parametres;
    }

    /**
     * Méthode permettant de récupérer les paramètres nécessaires pour l'aspect d'erreurs de dossier.
     * @param nbContenuEnErreur Le nombre d'erreurs actuellement dans le dossier.
     * @return La liste des paramètres pour l'aspect de d'erreur de dossier.
     */
    public static Map<QName, Serializable> obtenirParametresErreurDossier(int nbContenuEnErreur) {
        HashMap<QName, Serializable> parametres = new HashMap<>();

        parametres.put(ErreurDossierAspectModele.NB_FICHIER_EN_ERREUR, nbContenuEnErreur);
        parametres.put(ErreurDossierAspectModele.DATE_ERREUR, new Date());
        parametres.put(ErreurBaseAspectModele.MESSAGE_ERREUR, obtenirDossierErreurMessage(nbContenuEnErreur));

        return parametres;
    }

    /**
     * Méthode permettant de récupérer le message d'erreur pour un aspect d'erreur de dossier.
     * @param nbContenuEnErreur Le nombre d'erreurs actuellement dans le dossier.
     * @return Le message d'erreur pour l'aspect d'erreur de dossier.
     */
    public static String obtenirDossierErreurMessage(int nbContenuEnErreur) {
        if(nbContenuEnErreur == 0)
            return "Le dossier ne contient aucun contenu en erreur.";

        String pluriel = nbContenuEnErreur > 1 ? "s" : "";
        return String.format("Le dossier contient %s élément%s en erreur%s.", nbContenuEnErreur, pluriel, pluriel);
    }
}

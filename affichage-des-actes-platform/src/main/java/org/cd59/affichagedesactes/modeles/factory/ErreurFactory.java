package org.cd59.affichagedesactes.modeles.factory;

import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.modeles.typescontenus.erreur59.aspect.erreurbase.ErreurBaseAspectModele;
import org.cd59.affichagedesactes.modeles.typescontenus.erreur59.aspect.erreurdossier.ErreurDossierAspectModele;

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
    public static HashMap<QName, Serializable> getParametresAspectErreur(String message) {
        HashMap<QName, Serializable> parametres = new HashMap<>();

        parametres.put(ErreurBaseAspectModele.DATE_ERREUR, new Date());
        parametres.put(ErreurBaseAspectModele.MESSAGE_ERREUR, message);

        return parametres;
    }

    /**
     * Méthode permettant de récupérer les paramètres nécessaires pour l'aspect d'erreurs de dossier.
     * @param nbErreurs Le nombre d'erreurs actuellement dans le dossier.
     * @return La liste des paramètres pour l'aspect de d'erreur de dossier.
     */
    public static Map<QName, Serializable> getParametresAspectDossierErreur(int nbErreurs) {
        HashMap<QName, Serializable> parametres = new HashMap<>();

        String pluriel = nbErreurs > 1 ? "s" : "";

        parametres.put(ErreurDossierAspectModele.NB_FICHIER_EN_ERREUR, nbErreurs);
        parametres.put(ErreurDossierAspectModele.DATE_ERREUR, new Date());
        parametres.put(ErreurBaseAspectModele.MESSAGE_ERREUR, String.format(
                "Le dossier contient %s élément%s en erreur%s.", nbErreurs, pluriel, pluriel)
        );

        return parametres;
    }
}

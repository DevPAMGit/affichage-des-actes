package org.cd59.evaluateur;

import org.alfresco.web.evaluator.BaseEvaluator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe de vérification du bon dérouler de l'action d'envoi à l'affichage.
 */
public class VerifierSiDossierEnvoyer extends BaseEvaluator {

    /**
     * L'aspect à récupérer.
     */
    private static final String ASPECT_ETAT = "affichage59:affichage";

    @Override
    public boolean evaluate(JSONObject jsonObject) {
        try {

            // Vérification que le nœud possède l'aspect
            JSONArray nodeAspects = getNodeAspects(jsonObject);
            if (nodeAspects == null) return false;

            if (!nodeAspects.contains(ASPECT_ETAT)) return false;

            // Vérification de l'état du dossier.
            String etat = (String) getProperty(jsonObject, "affichage59:etat");
            if (etat == null) return false;

            // Vérification de l'état.
            return etat.equals("Prêt à être envoyer");

        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

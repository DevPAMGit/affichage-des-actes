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
    private static final String ASPECT_ETAT = "actes59:dossierinfos";

    @Override
    public boolean evaluate(JSONObject jsonObject) {
        try {

            // Vérification que le nœud possède l'aspect
            JSONArray nodeAspects = getNodeAspects(jsonObject);
            if (nodeAspects == null) return false;

            if (!nodeAspects.contains(ASPECT_ETAT)) return false;

            String etat = (String) getProperty(jsonObject, "actes59:etatEnvoiListe");
            String etat2 = (String) getProperty(jsonObject, "actes59:etatStockageDossier");
            return (
                    (etat == null || etat.trim().isEmpty() || !etat.equals("Prêt à être envoyé")) ||
                    (etat2 == null || etat2.trim().isEmpty() || !etat2.equals("Stocké"))
            );

        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

package org.cd59.evalueur;

import org.alfresco.web.evaluator.BaseEvaluator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Evaluateur permettant de savoir si un dossier peut être stocké ou non.
 */
public class PeutEtreEnvoye extends BaseEvaluator {

    /** L'identifiant de l'aspect à trouver sur le nœud. */
    private static final String ASPECT_DOSSIER_ACTE = "actes59:dossierinfos";

    /** L'identifiant de la propriété */
    private static final String PROPRIETE_ETAT_STOCKAGE = "actes59:etatStockageDossier";

    @Override
    public boolean evaluate(JSONObject jsonObject) {
        // Récupération des aspects du nœud.
        try {
            JSONArray aspects = getNodeAspects(jsonObject);
            // S'il n'a pas l'aspect de dossier d'acte : pas d'affichage.
            if(aspects == null || !aspects.contains(ASPECT_DOSSIER_ACTE)) return false;

            // Récupération des propriétés d'aspects à vérifier.
            String etatStockage = (String)getProperty(jsonObject, PROPRIETE_ETAT_STOCKAGE);

            // Si l'état du stockage est null ou s'i i'l n'est pas stocké : Le dossier ne peut pas être stocké.
            return (etatStockage != null && etatStockage.equals("Stocké"));

        }catch (Exception e) {
            throw new RuntimeException(
                    String.format("Une erreur est survenue lors de l'évaluation d'affichage de l'action de " +
                            "stockage du dossier (%s).", e.getMessage()
                    )
            );
        }
    }
}

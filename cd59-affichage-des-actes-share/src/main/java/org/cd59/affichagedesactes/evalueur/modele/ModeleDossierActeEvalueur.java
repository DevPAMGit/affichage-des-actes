package org.cd59.affichagedesactes.evalueur.modele;

import org.alfresco.web.evaluator.BaseEvaluator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public abstract class ModeleDossierActeEvalueur extends BaseEvaluator {
    /**
     * L'aspect du dossier d'acte.
     */
    private static final String ASPECT_DOSSIER_ACTE = "actes59:dossierinfos";

    /**
     * L'identifiant de la propriété d'état d'envoi.
     */
    private static final String PROPRIETE_ETAT_ENVOI = "actes59:etatEnvoiDossier";

    /**
     * L'identifiant de la propriété d'état de stockage.
     */
    private static final String PROPRIETE_ETAT_STOCKAGE = "actes59:etatStockageDossier";

    /**
     * L'état de l'envoi.
     */
    protected ModeleDossierEtatEnvoi etatEnvoi;

    /**
     * L'état du stockage.
     */
    protected ModeleDossierEtatStockage etatStockage;

    @Override
    public boolean evaluate(JSONObject jsonObject) {
        try{
            // Récupération des aspects du nœud.
            JSONArray aspects = getNodeAspects(jsonObject);

            // Vérification que le nœud possède l'aspect attendu.
            if(aspects == null || !aspects.contains(ASPECT_DOSSIER_ACTE))
                return false;

            this.etatStockage = ModeleDossierEtatStockage.searchValeur((String)getProperty(jsonObject, PROPRIETE_ETAT_STOCKAGE));
            this.etatEnvoi = ModeleDossierEtatEnvoi.searchValeur((String)getProperty(jsonObject, PROPRIETE_ETAT_ENVOI));

            return this.evaluer();

        }catch (Exception e) {
            throw new RuntimeException("Une erreur à eu lieu lors d'une evaluation du dossier d'acte.");
        }
    }

    /**
     * Evalue la classe.
     * @return <c>true</c> si l'évaluation est correct; sinon <c>false</c>.
     */
    protected abstract boolean evaluer();
}

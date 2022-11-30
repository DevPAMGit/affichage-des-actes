package org.cd59.affichagedesactes.evalueur.envoi;

import org.cd59.affichagedesactes.evalueur.modele.ModeleDossierActeEvalueur;
import org.cd59.affichagedesactes.evalueur.modele.ModeleDossierEtatEnvoi;
import org.cd59.affichagedesactes.evalueur.modele.ModeleDossierEtatStockage;

/**
 * Classe d'évaluation permettant d'indiquer si un dossier d'acte est stockable.
 */
public class EstEnAttenteStockage extends ModeleDossierActeEvalueur {

    @Override
    protected boolean evaluer() {
        return (this.etatStockage != ModeleDossierEtatStockage.STOCKE);
    }
}

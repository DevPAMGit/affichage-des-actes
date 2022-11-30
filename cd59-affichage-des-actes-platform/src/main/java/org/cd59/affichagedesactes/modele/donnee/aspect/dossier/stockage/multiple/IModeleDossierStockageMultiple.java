package org.cd59.affichagedesactes.modele.donnee.aspect.dossier.stockage.multiple;

import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;

/**
 * Interface contractuelle pour les dossiers de stockage dont l'identifiant a été référencé plusieurs fois.
 */
public interface IModeleDossierStockageMultiple {
    /**
     * Switch l'indication en référence multiple du dossier.
     * @throws ModeleException Si la propriété à modifier est null ou vide.
     */
    void switchReferenceMultiple() throws ModeleException, PreRequisException, NoSuchMethodException;

    /**
     * Modifie le nom du nœud en y ajoutant un numéro d'arrivé.
     * @param numero Le numéro d'arrivé.
     * @throws ModeleException Si une propriété à modifier est null ou vide.
     */
    void setNom(int numero) throws ModeleException, PreRequisException, NoSuchMethodException;

    /**
     * Récupère la valeur de l'identifiant.
     * @return La valeur de l'identifiant du dossier.
     */
    String getIdentifiant();

    /**
     *
     * @return
     */
    boolean getReferenceMultiple();
}

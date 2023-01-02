package org.cd59.affichagedesactes.action.custom.stockage;

public class StockerDossierActeRequete {

    /**
     * Requête permettant de recherche les fichiers d'aspects 'actes59:docinfos' de type 'ACTE_ORIGINAL'.
     */
    public final static String RECHERCHE_ACTE_ORIGINAL = "select * from actes59:docinfos " +
                                                         "where actes59:typedocument = 'ACTE_ORIGINAL' " +
                                                         "and IN_TREE('%s')";

    /**
     * Requête permettant de recherche les fichiers d'aspects 'actes59:docinfos' et différents de 'ACTE_ORIGINAL'.
     */
    public final static String RECHERCHE_AUTRES_DOCUMENTS = "select * from actes59:docinfos " +
                                                            "where actes59:typedocument <> 'ACTE_ORIGINAL' " +
                                                            "and IN_TREE('%s')";

    /**
     * Requête permettant la recherche du dossier contenant tous les actes.
     */
    public final static String RECHERCHE_DOSSIER_ACTES = "select * from stockageactes59:dossierActes " +
                                                         "where IN_TREE('%s')";

    /**
     * Requête permettant la recherche d'un dossier annuel.
     */
    public final static String RECHERCHE_DOSSIER_ANNUEL = "select * from stockageactes59:dossierAnnee " +
                                                          "where cmis:name = '%d' and IN_TREE('%s')";

    /**
     * Requête permettant la recherche d'un dossier annuel.
     */
    public final static String RECHERCHE_DOSSIER_MENSUEL = "select * from stockageactes59:dossierMois " +
                                                           "where  cmis:name = '%s' and cmis:parentId = '%s'";

    /**
     * Requête permettant la recherche d'un dossier journalier.
     */
    public final static String RECHERCHE_DOSSIER_JOURNALIER = "select * from stockageactes59:dossierJour " +
                                                              "where cmis:name = '%s' and cmis:parentId = '%s'";

    /**
     * Requête permettant la recherche d'un dossier typologie.
     */
    public final static String RECHERCHE_DOSSIER_TYPOLOGIE = "select * from stockageactes59:dossierTypologie " +
                                                             "where cmis:name = '%s' and cmis:parentId = '%s'";

    /**
     * Requête permettant de rechercher les dossiers d'acte du même identifiant.
     */
    public final static String RECHERCHE_DOSSIER_ACTE = "select * from actes59:dossierinfos " +
                                                        "where actes59:iddossier = '%s' " +
                                                        "and IN_TREE('%s') " +
                                                        "order by cmis:creationDate asc";

    public final static String RECHERCHE_DOSSIER_ACTE_STOCKABLE = "select * from actes59:dossierinfos " +
                                                                  "where actes59:dossiercomplet = true " +
                                                                  "and IN_TREE('%s')";

}

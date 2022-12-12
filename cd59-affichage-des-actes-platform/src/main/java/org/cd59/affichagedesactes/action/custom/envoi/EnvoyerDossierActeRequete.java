package org.cd59.affichagedesactes.action.custom.envoi;

/**
 * Fichier de requêtes pour l'action d'envoi de d'acte.
 */
public class EnvoyerDossierActeRequete {
    /**
     * Requête permettant de récupérer l'acte original du dossier.
     */
    public final static String REQUETE_RECHERCHE_ACTE_ORIGINAL = "select * from actes59:docinfos " +
            "where actes59:typedocument = 'ACTE_ORIGINAL' " +
            "and IN_TREE('%s')";

    /**
     * Requête permettant de récupérer les annexes d'actes du dossier.
     */
    public final static String REQUETE_RECHERCHE_ANNEXES_ACTE = "select * from actes59:docinfos " +
            "where actes59:typedocument <> 'ACTE_ORIGINAL' " +
            "and IN_TREE('%s')";

    /**
     * La requête permettant de retrouver tous les dossiers en possibilité d'envoi contenus dans le dossier générale
     * des Actes.
     */
    public final static String REQUETE_RECHERCHE_DOSSIER_A_ENVOYER = "select * from actes59:dossierinfos " +
                                                                     "where actes59:etatStockageDossier = 'Stocké' " +
                                                                     "and actes59:etatEnvoiDossier = 'Prêt à être envoyé' " +
                                                                     "and IN_TREE('%s')";
}

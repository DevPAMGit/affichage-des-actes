package org.cd59.affichagedesactes.action.custom.stockage;

import org.alfresco.model.ContentModel;
import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.modele.alfresco.type.DossierAnneeTypeModele;
import org.cd59.affichagedesactes.modele.alfresco.type.DossierJourTypeModele;
import org.cd59.affichagedesactes.modele.alfresco.type.DossierMoisTypeModele;
import org.cd59.affichagedesactes.modele.alfresco.type.DossierTypologieTypeModele;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.stockage.ModeleDossierDateStockage;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.stockage.ModeleDossierStockage;
import org.cd59.affichagedesactes.utilitaire.UtilitaireChaineDeCaracteres;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Classe factory pour
 */
public class StockerDossierActeDonneesFactory {

    /**
     * Formate le nouveau nom du dossier.
     * @param identifiant L'identifiant du dossier.
     * @param numero Le numéro du dossier si celui-ci est en référence multiple.
     * @return Le nom du dossier d'acte formater.
     */
    public static String getNomDossier(String identifiant, int numero) {
        if(numero == 0) return identifiant;
        return String.format("%s_%s", identifiant, UtilitaireChaineDeCaracteres.entierSurNChiffres(numero, 2));
    }

    /**
     * Méthode permettant de récupérer les métadonnées pour la création d'un dossier de typologie pour les actes.
     * @param modele Le modele pour un dossier d'acte.
     * @return Une instance {@link HashMap} contenant les propriétés initialisées du nœud.
     */
    public static HashMap<QName, Serializable> obtenirMetadonneesTypologie(ModeleDossierStockage modele) {
        // Initialisation du résultat.
        HashMap<QName, Serializable> proprietes = new HashMap<>();

        // Mise à jour des données.
        proprietes.put(ContentModel.PROP_TITLE, modele.typologie.typeMajuscule.valeur);
        proprietes.put(ContentModel.PROP_NAME, modele.typologie.typeMajuscule.valeur);
        proprietes.put(ContentModel.PROP_DESCRIPTION,

                String.format("Dossier stockant les %ss du département du Nord pour la journée du %s %s %d.",
                        modele.typologie.typeMinuscule.valeur, modele.date.jourChaine, modele.date.nomMois,
                        modele.date.annee)
        );

        proprietes.put(DossierTypologieTypeModele.TYPOLOGIE_DOSSIER, modele.typologie.typeMinuscule.valeur);
        proprietes.put(DossierTypologieTypeModele.ANNEE, modele.date.annee);
        proprietes.put(DossierTypologieTypeModele.MOIS, modele.date.mois);
        proprietes.put(DossierTypologieTypeModele.JOUR, modele.date.jour);

        // Retour du résultat.
        return proprietes;
    }

    /**
     * Méthode permettant de récupérer les métadonnées pour la création d'un dossier journalier d'acte.
     * @param date Le modele de date pour un dossier d'acte.
     * @return Une instance {@link HashMap} contenant les propriétés initialisées du nœud.
     */
    public static HashMap<QName, Serializable> obtenirMetadonneesJour(ModeleDossierDateStockage date) {
        // Initialisation du résultat.
        HashMap<QName, Serializable> resultat = new HashMap<>();

        // Mise à jour des données.
        resultat.put(ContentModel.PROP_TITLE, date.jourChaine);
        resultat.put(ContentModel.PROP_NAME, date.jourChaine);

        resultat.put(DossierJourTypeModele.ANNEE, date.annee);
        resultat.put(DossierJourTypeModele.MOIS, date.mois);
        resultat.put(DossierJourTypeModele.JOUR, date.jour);

        resultat.put(ContentModel.PROP_DESCRIPTION, String.format(
                "Dossier stockant les actes du département du Nord pour la journée du %s %s %d.",
                date.jourChaine, date.nomMois, date.annee)
        );

        // Retour du résultat.
        return resultat;
    }

    /**
     * Méthode permettant de récupérer les métadonnées pour la création d'un dossier mensuel d'acte.
     * @param date Le modele de date pour un dossier d'acte.
     * @return Une instance {@link HashMap} contenant les propriétés initialisées du nœud.
     */
    public static HashMap<QName, Serializable> obtenirMetadonneesMois(ModeleDossierDateStockage date) {
        HashMap<QName, Serializable> resultat = new HashMap<>();

        resultat.put(ContentModel.PROP_TITLE, date.moisChaine);
        resultat.put(ContentModel.PROP_NAME, date.moisChaine);
        resultat.put(ContentModel.PROP_DESCRIPTION,
                String.format("Dossier stockant les actes du département du Nord pour le mois de %s de l'année %d.",
                        date.nomMois, date.annee)
        );
        resultat.put(DossierMoisTypeModele.MOIS, date.mois);
        resultat.put(DossierMoisTypeModele.ANNEE, date.annee);

        return resultat;
    }


    /**
     * Méthode permettant de récupérer les métadonnées pour la création d'un dossier annuel d'acte.
     * @param date Le modele de date pour un dossier d'acte.
     * @return Une instance {@link HashMap} contenant les propriétés initialisées du nœud.
     */
    public static HashMap<QName, Serializable> obtenirMetadonneesAnnee(ModeleDossierDateStockage date){
        HashMap<QName, Serializable> resultat = new HashMap<>();
        resultat.put(ContentModel.PROP_TITLE, date.anneeChaine);
        resultat.put(ContentModel.PROP_NAME, date.anneeChaine);
        resultat.put(ContentModel.PROP_DESCRIPTION, String.format("Dossier stockant les actes du département " +
                "du Nord pour l'année %s.", date.anneeChaine)
        );
        resultat.put(DossierAnneeTypeModele.ANNEE, date.annee);

        return resultat;
    }

    /**
     * Méthode permettant de récupérer le jeu de métadonnées pour le dossier d'acte.
     * @return Le jeu de données pour les métadonnées du dossier d'acte.
     */
    public static HashMap<QName, Serializable> obtenirMetadonneesDossierActe(){
        HashMap<QName, Serializable> resultat = new HashMap<>();

        resultat.put(ContentModel.PROP_NAME, "Actes");
        resultat.put(ContentModel.PROP_TITLE, "Actes");
        resultat.put(ContentModel.PROP_DESCRIPTION, "Dossier stockant les actes du département du Nord.");

        return resultat;
    }

}

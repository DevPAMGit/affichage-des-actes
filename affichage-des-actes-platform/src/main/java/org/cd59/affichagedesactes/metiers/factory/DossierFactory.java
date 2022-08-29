package org.cd59.affichagedesactes.metiers.factory;

import org.alfresco.model.ContentModel;
import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.aspect.informationdossier.InformationDossierAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossieracte.DossierActeTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierannee.DossierAnneeTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierjour.DossierJourTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossiermois.DossierMoisTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossiertypologie.DossierTypologieTypeModele;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class DossierFactory {

    private static final String[] MOIS = {"janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août",
            "septembre", "décembre"};

    /**
     * Méthode permettant de récupérer le jour de la date sur deux digits.
     *
     * @param jour Le jour du mois (normalement compris entre 1 et 31).
     * @return Une chaîne de caractères.
     */
    private static String obtenirDateDeuxDigit(int jour) {
        if (jour < 10) return String.format("0%d", jour);
        return Integer.toString(jour);
    }

    /**
     * Méthode permettant de récupérer les paramètre d'informations pour initialisation.
     *
     * @param nom         Le nom et titre du dossier.
     * @param description La description du dossier.
     * @return Une table avec les paramètres de base du dossier.
     */
    private static HashMap<QName, Serializable> obtenirParametresAspectDossierInfo(String nom, String description) {
        HashMap<QName, Serializable> resultat = new HashMap<>();
        resultat.put(ContentModel.PROP_NAME, nom);
        resultat.put(ContentModel.PROP_TITLE, nom);
        resultat.put(ContentModel.PROP_DESCRIPTION, description);
        resultat.put(InformationDossierAspectModele.NB_ACTES, 0);
        resultat.put(InformationDossierAspectModele.NB_ACTES_TRAITES, 0);
        resultat.put(InformationDossierAspectModele.NB_ACTES_EN_ERREURS, 0);
        return resultat;
    }

    /**
     * Méthode permettant de récupérer les propriétés pour un dossier contenant tous les actes.
     *
     * @return Les propriétés pour un dossier contenant tous les actes.
     */
    public static HashMap<QName, Serializable> obtenirParametresDossierActes() {

        return obtenirParametresAspectDossierInfo("Actes", "Dossier contenant tous les actes du département du Nord.");
    }

    /**
     * Méthode permettant de récupérer les propriétés pour un dossier contenant les actes pour une année.
     *
     * @param annee L'année utilisé pour ce nœud.
     * @return Les propriétés pour un dossier contenant les actes pour une année.
     */
    public static HashMap<QName, Serializable> obtenirParametresDossierAnnees(int annee) {
        // Récupération des propriétés des aspects "mandatory".
        HashMap<QName, Serializable> proprietes = obtenirParametresAspectDossierInfo(
                Integer.toString(annee),
                String.format("Dossier stockant les actes du département du Nord pour l'année %d.", annee)
        );

        // Ajout des propriétés spécifique.
        proprietes.put(DossierAnneeTypeModele.ANNEE, annee);
        // Retour du résultat.
        return proprietes;
    }

    /**
     * Méthode permettant de récupérer les propriétés pour un dossier contenant les actes pour un mois.
     *
     * @param mois  Le mois concerné.
     * @param annee L'année concerné.
     * @return Les propriétés pour un dossier contenant les actes pour un mois.
     */
    public static HashMap<QName, Serializable> obtenirParametresDossierMois(int mois, int annee) {
        // Récupération des propriétés des aspects "mandatory".
        HashMap<QName, Serializable> proprietes = obtenirParametresAspectDossierInfo(
                obtenirDateDeuxDigit(mois),
                String.format(
                        "Dossier stockant les actes du département du Nord pour le mois de %s pour l'année %d.",
                        MOIS[mois - 1],
                        annee
                )
        );

        // Ajout des propriétés spécifique.
        proprietes.put(DossierMoisTypeModele.ANNEE, annee);
        proprietes.put(DossierMoisTypeModele.MOIS, mois);

        // Retour du résultat.
        return proprietes;
    }

    /**
     * Méthode permettant de récupérer les propriétés pour un dossier contenant tous les actes pour un jour.
     *
     * @param jour  Le jour concerné.
     * @param mois  Le mois concerné.
     * @param annee L'année concerné.
     * @return Les propriétés pour un dossier contenant tous les actes.
     */
    public static Map<QName, Serializable> obtenirParametresDossierJour(int jour, int mois, int annee) {
        // Récupération des propriétés des aspects "mandatory".
        HashMap<QName, Serializable> proprietes = obtenirParametresAspectDossierInfo(
                obtenirDateDeuxDigit(jour),
                String.format(
                        "Dossier stockant les actes du département du Nord pour la journée du %s %s %d.",
                        obtenirDateDeuxDigit(jour), MOIS[mois - 1], annee
                )
        );
        // Ajout des propriétés spécifiques.
        proprietes.put(DossierJourTypeModele.ANNEE, annee);
        proprietes.put(DossierJourTypeModele.MOIS, mois);
        proprietes.put(DossierJourTypeModele.JOUR, jour);

        // Retour du résultat.
        return proprietes;
    }

    /**
     * Méthode permettant de récupérer les propriétés pour un dossier contenant tous les actes pour un type.
     *
     * @param jour  Le jour concerné.
     * @param mois  Le mois concerné.
     * @param annee L'année concerné.
     * @param type  Le type concerné.
     * @return Les propriétés pour un dossier contenant tous les actes.
     */
    public static Map<QName, Serializable> obtenirParametresDossierType(int jour, int mois, int annee, String type) {
        // Récupération des propriétés des aspects "mandatory".
        HashMap<QName, Serializable> proprietes = obtenirParametresAspectDossierInfo(
                type,
                String.format(
                        "Dossier stockant les %ss du département du Nord pour la journée du %s %s %d.",
                        type, obtenirDateDeuxDigit(jour), MOIS[mois - 1], annee
                )
        );

        // Ajout des propriétés spécifiques.
        proprietes.put(DossierTypologieTypeModele.ANNEE, annee);
        proprietes.put(DossierTypologieTypeModele.MOIS, mois);
        proprietes.put(DossierTypologieTypeModele.JOUR, jour);
        proprietes.put(DossierTypologieTypeModele.TYPOLOGIE_DOSSIER, type);

        // Retour du résultat.
        return proprietes;
    }

    /**
     * Méthode permettant de récupérer les propriétés pour un dossier d'acte.
     *
     * @param dossier    Le dossier d'acte.
     * @param numeroActe Le numéro de l'acte.
     * @return Les propriétés pour un dossier d'actes.
     */
    public static Map<QName, Serializable> obtenirParametresDossierActe(
            DossierinfosAspectHelperModele dossier, String numeroActe
    ) {
        // Récupération d'un calendrier pour parser la date.
        Calendar calendrier = new GregorianCalendar();
        calendrier.setTime(dossier.getDatedossier());

        int jour = calendrier.get(Calendar.DAY_OF_MONTH);
        int mois = calendrier.get(Calendar.MONTH);
        int annee = calendrier.get(Calendar.YEAR);

        Map<QName, Serializable> proprietes = obtenirParametresAspectDossierInfo(
                numeroActe, String.format(
                        "Dossier %s numéro %s du %s %s %d",
                        dossier.getTypedossier().equals("acte") ? "de l'arrêté" : "de la délibération",
                        numeroActe, obtenirDateDeuxDigit(jour), MOIS[mois - 1], annee
                )
        );

        proprietes.put(DossierActeTypeModele.MOIS, mois);
        proprietes.put(DossierActeTypeModele.JOUR, jour);
        proprietes.put(DossierActeTypeModele.ANNEE, annee);

        proprietes.put(DossierActeTypeModele.IDENTIFIANT, numeroActe);
        proprietes.put(DossierActeTypeModele.OBJET, dossier.getObjet());
        proprietes.put(DossierActeTypeModele.DATE, dossier.getDatedossier());
        proprietes.put(DossierActeTypeModele.SIGNATAIRE, dossier.getSignataire());
        proprietes.put(DossierActeTypeModele.SIGLE_DIRECTION, dossier.getOrgasigle());
        proprietes.put(DossierActeTypeModele.TYPOLOGIE_DOSSIER, dossier.getTypedossier().toUpperCase());

        return proprietes;
    }
}

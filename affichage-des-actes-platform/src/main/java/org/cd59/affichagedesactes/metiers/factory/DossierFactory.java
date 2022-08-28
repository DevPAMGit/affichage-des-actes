package org.cd59.affichagedesactes.metiers.factory;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.actesconteneur.ActesConteneurTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuracte.ConteneurActeTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuracte.ConteneurActeTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuracteannee.ConteneurActeAnneeTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuracteannee.ConteneurActeAnneeTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuractejour.ConteneurActeJourTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuractejour.ConteneurActeJourTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuractemois.ConteneurActeMoisTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuractemois.ConteneurActeMoisTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneurtype.ConteneurTypeTypeHelperModele;

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
     * @param jour Le jour du mois (normalement compris entre 1 et 31).
     * @return Une chaîne de caractères.
     */
    private static String obtenirDateDeuxDigit(int jour) {
        if(jour < 10) return String.format("0%d", jour);
        return Integer.toString(jour);
    }

    /**
     * Méthode permettant de récupérer les paramètre d'informations pour initialisation.
     * @param nom Le nom et titre du dossier.
     * @param description La description du dossier.
     * @return Une table avec les paramètres de base du dossier.
     */
    private static HashMap<QName, Serializable> obtenirParametresAspectDossierInfo(String nom, String description) {
        HashMap<QName, Serializable> resultat = new HashMap<>();
        resultat.put(ContentModel.PROP_NAME, nom);
        resultat.put(ContentModel.PROP_TITLE, nom);
        resultat.put(ContentModel.PROP_DESCRIPTION, description);
        return resultat;
    }

    /**
     * Méthode permettant de récupérer les propriétés pour un dossier contenant tous les actes.
     * @return Les propriétés pour un dossier contenant tous les actes.
     */
    private static HashMap<QName, Serializable> obtenirParametresDossierActes() {
        return obtenirParametresAspectDossierInfo("Actes", "Dossier contenant tous les actes du département du Nord.");
    }

    /**
     * Méthode permettant de récupérer les propriétés pour un dossier contenant les actes pour une année.
     * @param annee L'année utilisé pour ce nœud.
     * @return Les propriétés pour un dossier contenant les actes pour une année.
     */
    private static HashMap<QName, Serializable> obtenirParametresDossierAnnees(int annee) {
        // Récupération des propriétés des aspects "mandatory".
        HashMap<QName, Serializable> proprietes = obtenirParametresAspectDossierInfo(
                Integer.toString(annee),
                String.format("Dossier stockant les actes du département du Nord pour l'année %d.", annee)
        );

        // Ajout des propriétés spécifique.
        proprietes.put(ConteneurActeAnneeTypeModele.DATE_ANNEE, annee);
        // Retour du résultat.
        return proprietes;
    }

    /**
     * Méthode permettant de récupérer les propriétés pour un dossier contenant les actes pour un mois.
     * @param mois Le mois concerné.
     * @param annee L'année concerné.
     * @return Les propriétés pour un dossier contenant les actes pour un mois.
     */
    private static HashMap<QName, Serializable> obtenirParametresDossierMois(int mois, int annee) {
        // Récupération des propriétés des aspects "mandatory".
        HashMap<QName, Serializable> proprietes = obtenirParametresAspectDossierInfo(
                obtenirDateDeuxDigit(mois),
                String.format(
                        "Dossier stockant les actes du département du Nord pour le mois de %s pour l'année %d.",
                        MOIS[mois-1],
                        annee
                )
        );

        // Ajout des propriétés spécifique.
        proprietes.put(ConteneurActeMoisTypeModele.DATE_ANNEE, annee);
        proprietes.put(ConteneurActeMoisTypeModele.DATE_MOIS, mois);

        // Retour du résultat.
        return proprietes;
    }

    /**
     * Méthode permettant de récupérer les propriétés pour un dossier contenant tous les actes pour un jour.
     * @param jour Le jour concerné.
     * @param mois Le mois concerné.
     * @param annee L'année concerné.
     * @return Les propriétés pour un dossier contenant tous les actes.
     * */
    private static Map<QName, Serializable> obtenirParametresDossierJour(int jour, int mois, int annee) {
        // Récupération des propriétés des aspects "mandatory".
        HashMap<QName, Serializable> proprietes = obtenirParametresAspectDossierInfo(
                obtenirDateDeuxDigit(jour),
                String.format(
                        "Dossier stockant les actes du département du Nord pour la journée du %s %s %d.",
                        obtenirDateDeuxDigit(jour), MOIS[mois-1], annee
                )
        );
        // Ajout des propriétés spécifiques.
        proprietes.put(ConteneurActeJourTypeModele.DATE_ANNEE, annee);
        proprietes.put(ConteneurActeJourTypeModele.DATE_MOIS, mois);
        proprietes.put(ConteneurActeJourTypeModele.DATE_JOUR, jour);

        // Retour du résultat.
        return proprietes;
    }

    /**
     * Méthode permettant de récupérer les propriétés pour un dossier contenant tous les actes pour un type.
     * @param jour Le jour concerné.
     * @param mois Le mois concerné.
     * @param annee L'année concerné.
     * @param type Le type concerné.
     * @return Les propriétés pour un dossier contenant tous les actes.
     * */
    private static Map<QName, Serializable> obtenirParametresDossierType(int jour, int mois, int annee, String type) {
        // Récupération des propriétés des aspects "mandatory".
        HashMap<QName, Serializable> proprietes = obtenirParametresAspectDossierInfo(
                type,
                String.format(
                        "Dossier stockant les %ss du département du Nord pour la journée du %s %s %d.",
                        type, obtenirDateDeuxDigit(jour), MOIS[mois-1], annee
                )
        );

        // Ajout des propriétés spécifiques.
        proprietes.put(ConteneurActeTypeModele.DATE_ANNEE, annee);
        proprietes.put(ConteneurActeTypeModele.DATE_MOIS, mois);
        proprietes.put(ConteneurActeTypeModele.DATE_JOUR, jour);
        proprietes.put(ConteneurActeTypeModele.CONTENEUR_ACTE_TYPE, type.toUpperCase());

        // Retour du résultat.
        return proprietes;
    }

    /**
     * Méthode permettant de récupérer les propriétés pour un dossier d'acte.
     * @param dossier Le dossier d'acte.
     * @param numeroActe Le numéro de l'acte.
     * @return Les propriétés pour un dossier d'actes.
     * */
    private static  Map<QName, Serializable> obtenirParametersDossierActe(
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
                        dossier.getTypedossier().equals("acte") ? "de l'acte" :  "de la délibération",
                        numeroActe,
                        obtenirDateDeuxDigit(jour), obtenirDateDeuxDigit(mois), annee
                )
        );

        proprietes.put(ConteneurActeTypeModele.DATE_MOIS, mois);
        proprietes.put(ConteneurActeTypeModele.DATE_JOUR, jour);
        proprietes.put(ConteneurActeTypeModele.DATE_ANNEE, annee);

        proprietes.put(ConteneurActeTypeModele.STOCKAGE_IDENTIFIANT, numeroActe);
        proprietes.put(ConteneurActeTypeModele.STOCKAGE_OBJET, dossier.getObjet());
        proprietes.put(ConteneurActeTypeModele.STOCKAGE_DATE, dossier.getDatedossier());
        proprietes.put(ConteneurActeTypeModele.STOCKAGE_SIGNATAIRE, dossier.getSignataire());
        proprietes.put(ConteneurActeTypeModele.STOCKAGE_DIRECTION_SIGLE, dossier.getOrgasigle());
        proprietes.put(ConteneurActeTypeModele.CONTENEUR_ACTE_TYPE, dossier.getTypedossier().toUpperCase());

        return proprietes;
    }

    /**
     * Méthode permettant de créer ou récupérer le dossier contenant tous les actes "Actes".
     * @param nodeService Le service de nœud d'Alfresco.
     * @param dossierSource Le nœud dont dépend le nœud des "Actes".
     * @return Un modèle de données pour le dossier des actes.
     */
    public static synchronized ActesConteneurTypeHelperModele
    obtenirDossierActes(NodeService nodeService, NodeRef dossierSource) {

        // Récupération ou création d'un nœud.
        ActesConteneurTypeHelperModele resultat = new ActesConteneurTypeHelperModele(
                nodeService,
                new AlfrescoModeleHelper(nodeService, dossierSource).creerDossier("Actes")
        );

        // Vérification si le nœud a été créer ou récupérer (en cas de création le nœud n'est pas typé).
        if(!resultat.hasType()) {
            // Si a été créé : Ajout du type.
            resultat.addType();
            // Mise à jour des propriétés.
            resultat.majProprietes(obtenirParametresDossierActes());
        }

        // Retour du nœud vers le dossier.
        return resultat;
    }

    /**
     * Méthode permettant de créer ou récupérer le dossier pour une année donnée.
     * @param nodeService Le service de nœud d'Alfresco.
     * @param annee Le nœud dont dépend le nœud des "annee".
     * @return Un modèle de données pour le dossier des actes pour une année.
     */
    public static synchronized ConteneurActeAnneeTypeHelperModele
    obtenirDossierAnnee(NodeService nodeService, AlfrescoModeleHelper dossierActes, int annee) {
        // Récupération/Création du nœud.
        ConteneurActeAnneeTypeHelperModele resultat = new ConteneurActeAnneeTypeHelperModele(
                nodeService, dossierActes.creerDossier(Integer.toString(annee))
        );

        // En cas de création le nœud n'a pas le type requis.
        if(!resultat.hasType()) {
            // Ajout du type.
            resultat.addType();
            // Mise à jour des propriétés.
            resultat.majProprietes(obtenirParametresDossierAnnees(annee));
        }

        // Retour du résultat.
        return resultat;
    }

    /**
     * Méthode permettant de créer ou récupérer le dossier concernant un mois pour une année.
     * @param nodeService Le service de nœud d'Alfresco.
     * @param annee Le nœud dans lequel créer ou récupérer le dossier journalier.
     * @param mois Le mois que l'on souhaite récupérer.
     * @return Une référence vers le nœud journalier de l'acte.
     */
    public static synchronized ConteneurActeMoisTypeHelperModele
    obtenirDossierMois(NodeService nodeService, ConteneurActeAnneeTypeHelperModele annee, int mois) {

        // Récupération/Création du dossier.
        ConteneurActeMoisTypeHelperModele resultat = new ConteneurActeMoisTypeHelperModele(
                nodeService, annee.creerDossier(obtenirDateDeuxDigit(mois))
        );

        // Le nœud est trouvé. S'il n'a pas le type, c'est un nœud créé.
        if(!resultat.hasType()) {
            // Ajout du type.
            resultat.addType();
            // Mise à jour des propriétés.
            resultat.majProprietes(obtenirParametresDossierMois(mois, annee.getDateAnnee()));
        }

        // Retour du résultat.
        return resultat;
    }

    /**
     * Méthode permettant de créer ou récupérer le dossier concernant un jour dans un mois pour une année.
     * @param nodeService Le service de nœud d'Alfresco.
     * @param mois Le nœud dans lequel créer ou récupérer le dossier journalier.
     * @return Une référence vers le nœud journalier de l'acte.
     */
    public static synchronized ConteneurActeJourTypeHelperModele
    obtenirDossierJour(NodeService nodeService, ConteneurActeMoisTypeHelperModele mois, int jour) {

        // Récupération/Création du dossier.
        ConteneurActeJourTypeHelperModele resultat = new ConteneurActeJourTypeHelperModele(
                nodeService, mois.creerDossier(obtenirDateDeuxDigit(jour))
        );

        // Le nœud est trouvé. S'il n'a pas le type, c'est un nœud créé.
        if(!resultat.hasType()) {
            // Ajout du type.
            resultat.addType();
            // Mise à jour des propriétés.
            resultat.majProprietes(obtenirParametresDossierJour(jour, mois.getDateMois(), mois.getDateAnnee()));
        }

        // Retour du résultat.
        return resultat;
    }


    /**
     * Méthode permettant de récupérer le nœud dossier concernant un type à une date donnée.
     * @param nodeService Le service de noeud Alfresco.
     * @param jour Le jour concerné.
     * @param type Le type concerné
     * @return Un modèle qui référencie le nœud dossier concernant le type à la date donnée.
     */
    public static ConteneurTypeTypeHelperModele
    obtenirDossierType(NodeService nodeService, ConteneurActeJourTypeHelperModele jour, String type) {

        // Récupération/Création du dossier.
        ConteneurTypeTypeHelperModele resultat = new ConteneurTypeTypeHelperModele(
                nodeService, jour.creerDossier( type.toUpperCase())
        );

        // Le nœud est trouvé. S'il n'a pas le type : c'est un nœud créé.
        if(!resultat.hasType()) {
            // Ajout du type.
            resultat.addType();
            // Mise à jour des propriétés.
            resultat.majProprietes(obtenirParametresDossierType(
                    jour.getDateJour(), jour.getDateMois(), jour.getDateAnnee(), type
            ));
        }

        // Retour du résultat.
        return resultat;
    }

    /**
     * Méthode permettant de créer un nœud pour un acte.
     * @param nodeService Le service de nœud Alfresco.
     * @param type Le nœud qui devra contenir le nouveau nœud.
     * @param dossier Le modèle du dossier à créer.
     * @param numeroActe Le numéro de l'acte.
     * @return Une référence au nœud.
     */
    public synchronized static ConteneurActeTypeHelperModele creerDossierActe(
            NodeService nodeService, ConteneurTypeTypeHelperModele type,
            DossierinfosAspectHelperModele dossier, String numeroActe
    ) {
        ConteneurActeTypeHelperModele dossierActe = new ConteneurActeTypeHelperModele(
                nodeService, type.creerDossier(numeroActe)
        );
        dossierActe.majProprietes(obtenirParametersDossierActe(dossier, numeroActe));
        return dossierActe;
    }
}

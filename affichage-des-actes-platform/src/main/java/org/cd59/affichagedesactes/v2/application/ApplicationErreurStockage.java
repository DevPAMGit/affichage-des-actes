package org.cd59.affichagedesactes.v2.application;

import org.alfresco.model.ContentModel;
import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.aspect.informationdossier.InformationDossierAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossieracte.DossierActeTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierannee.DossierAnneeTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierjour.DossierJourTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossiermois.DossierMoisTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossiertypologie.DossierTypologieTypeModele;
import org.cd59.affichagedesactes.v2.action.source.ActionMetier;
import org.cd59.affichagedesactes.v2.application.source.ApplicationSource;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ApplicationErreurStockage extends ApplicationSource {

    private static final String[] MOIS = {"janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août",
            "septembre", "décembre"};

    /**
     * Initialise une nouvelle instance de la classe {@link ApplicationSource}.
     * @param action L'action à laquelle est reliée l'application.
     */
    public ApplicationErreurStockage(ActionMetier action) {
        super(action);
    }

    /**
     * Initie les données pour l'aspect 'stockageactes59:informationDossier'.
     * @return Le jeu de données pour l'aspect 'stockageactes59:informationDossier'.
     */
    private static HashMap<QName, Serializable> initierInformationDossier() {
        HashMap<QName, Serializable> resultat = new HashMap<>();

        resultat.put(InformationDossierAspectModele.NB_ACTES, 0);
        resultat.put(InformationDossierAspectModele.NB_ACTES_TRAITES, 0);
        resultat.put(InformationDossierAspectModele.NB_ACTES_EN_ERREURS,0);

        return resultat;
    }

    /**
     * Initie les données pour le type 'stockageactes59:dossierActes'.
     * @return Le jeu de données pour le type 'stockageactes59:dossierActes'.
     */
    public HashMap<QName, Serializable> initierDossierActes() {
        HashMap<QName, Serializable> resultat = initierInformationDossier();
        resultat.put(ContentModel.PROP_DESCRIPTION, "Dossier de stockage des actes du département du Nord.");
        resultat.put(ContentModel.PROP_TITLE, "Actes");
        resultat.put(ContentModel.PROP_NAME, "Actes");
        return resultat;
    }


    public Map<QName, Serializable> initierDossierAnnee(String annee) {
        HashMap<QName, Serializable> resultat = initierInformationDossier();
        resultat.put(ContentModel.PROP_DESCRIPTION,
                String.format("Dossier stockant les actes du département du Nord pour l'année %s.", annee)
        );
        resultat.put(ContentModel.PROP_NAME, annee);
        resultat.put(ContentModel.PROP_TITLE, annee);
        resultat.put(DossierAnneeTypeModele.ANNEE, Integer.parseInt(annee));
        return resultat;
    }

    public Map<QName, Serializable> initierDossierMois(String mois, String annee) {
        HashMap<QName, Serializable> resultat = initierInformationDossier();
        resultat.put(ContentModel.PROP_DESCRIPTION,
                String.format("Dossier stockant les actes du département du Nord pour le mois de %s pour l'année %s.",
                        MOIS[Integer.parseInt(mois)- 1], annee
        ));

        resultat.put(ContentModel.PROP_TITLE, mois);
        resultat.put(ContentModel.PROP_NAME, mois);
        resultat.put(DossierMoisTypeModele.MOIS, Integer.parseInt(mois));
        resultat.put(DossierMoisTypeModele.ANNEE, Integer.parseInt(annee));

        return resultat;
    }

    public Map<QName, Serializable> initierDossierJour(String jour, String mois, String annee) {
        HashMap<QName, Serializable> resultat = initierInformationDossier();
        resultat.put(ContentModel.PROP_DESCRIPTION,
                String.format("Dossier stockant les actes du département du Nord pour la journée du %s %s %s.",
                        jour, MOIS[Integer.parseInt(mois) - 1], annee)
        );
        resultat.put(ContentModel.PROP_TITLE, jour);
        resultat.put(ContentModel.PROP_NAME, jour);
        resultat.put(DossierJourTypeModele.JOUR, Integer.parseInt(jour));
        resultat.put(DossierJourTypeModele.MOIS, Integer.parseInt(mois));
        resultat.put(DossierJourTypeModele.ANNEE, Integer.parseInt(annee));

        return resultat;
    }

    /**
     * Initialise les propriétés d'un dossier de type 'stockageactes59:dossierActe'.
     * @param identifiant Le numéro de l'acte.
     * @param jour Le jour de l'acte.
     * @param mois Le mois de l'acte.
     * @param annee L'année de l'acte.
     * @return La liste des propriétés du type 'stockageactes59:dossierActe'.
     */
    public Map<QName, Serializable> initierDossierActe(
            DossierinfosAspectHelperModele dossier, String identifiant, String jour, String mois, String annee
    ) {
        HashMap<QName, Serializable> resultat = initierInformationDossier();

        resultat.put(ContentModel.PROP_DESCRIPTION,
                String.format(
                        "Dossier %s numéro %s du %s %s %s",
                        dossier.getTypedossier().equals("acte") ? "de l'arrêté" : "de la délibération",
                        identifiant, jour, MOIS[Integer.parseInt(mois) - 1], annee
                )
        );

        resultat.put(ContentModel.PROP_TITLE, identifiant);
        resultat.put(ContentModel.PROP_NAME, identifiant);

        resultat.put(DossierActeTypeModele.MOIS, mois);
        resultat.put(DossierActeTypeModele.JOUR, jour);
        resultat.put(DossierActeTypeModele.ANNEE, annee);

        resultat.put(DossierActeTypeModele.IDENTIFIANT, identifiant);
        resultat.put(DossierActeTypeModele.OBJET, dossier.getObjet());
        resultat.put(DossierActeTypeModele.RESUME, dossier.getResume());
        resultat.put(DossierActeTypeModele.DATE, dossier.getDatedossier());
        resultat.put(DossierActeTypeModele.SIGNATAIRE, dossier.getSignataire());
        resultat.put(DossierActeTypeModele.SIGLE_DIRECTION, dossier.getOrgasigle());
        resultat.put(DossierActeTypeModele.TYPOLOGIE_DOSSIER, dossier.getTypedossier().toUpperCase());

        return resultat;
    }

    public Map<QName, Serializable> initierDossierTypologie(String type, String jour, String mois, String annee) {
        HashMap<QName, Serializable> resultat = initierInformationDossier();
        resultat.put(ContentModel.PROP_DESCRIPTION,
                String.format(
                        "Dossier stockant les %ss du département du Nord pour la journée du %s %s %s.",
                        type, jour, MOIS[Integer.parseInt(mois)], annee
                )
        );

        // Ajout des propriétés spécifiques.
        resultat.put(ContentModel.PROP_NAME, type);
        resultat.put(ContentModel.PROP_TITLE, type);

        resultat.put(DossierTypologieTypeModele.TYPOLOGIE_DOSSIER, type);
        resultat.put(DossierTypologieTypeModele.MOIS, Integer.parseInt(mois));
        resultat.put(DossierTypologieTypeModele.JOUR, Integer.parseInt(jour));
        resultat.put(DossierTypologieTypeModele.ANNEE, Integer.parseInt(annee));

        return resultat;
    }
}

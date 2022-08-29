package org.cd59.affichagedesactes.metiers.action.stockage;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.docinfos.DocinfosAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectHelperModele;
import org.cd59.affichagedesactes.metiers.action.source.IActionMetier;
import org.cd59.affichagedesactes.metiers.regle.RegleMetierDossierActe;
import org.cd59.affichagedesactes.metiers.regle.RegleMetierErreur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Classe exécutant l'action métier de stocker dans le plan de classement l'acte.
 */
public class StockerActeSASActionMetier implements IActionMetier {

    /**
     * Le logger de la classe.
     */
    private static final Logger logger = LoggerFactory.getLogger(StockerActeSASActionMetier.class);

    /**
     * Les règles métier pour l'attribution et retrait des aspects d'erreurs.
     */
    private final RegleMetierErreur regleMetierErreur;

    /**
     * Les règles métier pour les dossiers d'acte.
     */
    private final RegleMetierDossierActe regleMetierDossierActe;

    private final ServiceRegistry serviceRegistry;

    /**
     * Initialise une nouvelle instance de la classe {@link StockerActeSASActionMetier} classe.
     * @param serviceRegistry Le gestionnaire de service Alfresco.
     */
    public StockerActeSASActionMetier(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        this.regleMetierErreur = new RegleMetierErreur(this.serviceRegistry);
        this.regleMetierDossierActe = new RegleMetierDossierActe(this.serviceRegistry);
    }

    @Override
    public void executer(NodeRef nodeRef) {
        // Vérification des préconditions.
        if(!this.verifierPreconditionsActe(nodeRef)) return;
        // Rangement de l'acte.
        this.regleMetierDossierActe.rangerDossierActe(new DossierinfosAspectHelperModele(this.serviceRegistry, nodeRef));

    }

    /**
     * Méthode permettant de vérifier les préconditions d'un acte.
     * @param nodeRef La référence vers l'acte à vérifier.
     * @return <c>true</c> si les préconditions sont respectés ; sinon <c>false</c>.
     */
    private boolean verifierPreconditionsActe(NodeRef nodeRef) {
        DossierinfosAspectHelperModele dossier = new DossierinfosAspectHelperModele(this.serviceRegistry, nodeRef);
        if(!dossier.hasAspect() || !dossier.getDossiercomplet()) return false;
        return (this.verifierDossier(dossier) && this.verifierContenuDossier(dossier));
    }

    /**
     * Méthode permettant de vérifier le dossier d'acte.
     * @param dossier le dossier d'acte à vérifier.
     * @return <c>true</c> si le dossier respecte les préconditions de traitements ; sinon <c>false</c>.
     */
    private boolean verifierDossier(DossierinfosAspectHelperModele dossier) {
        if( !dossier.estAspectValide() ) {
            this.regleMetierErreur.ajouterErreurDossierActeSAS(
                    dossier.getNoeud(), this.obtenirMessageErreurDossierActe(dossier)
            );
            return false;
        }
        return true;
    }

    /**
     * Méthode permettant de vérifier le contenu d'un dossier.
     * @param dossier Le dossier dont on souhaite vérifier le contenu.
     * @return <c>true</c> si le contenu répond aux critères de traitement ; sinon <c>false</c>.
     */
    private boolean verifierContenuDossier(DossierinfosAspectHelperModele dossier) {
        boolean resultat = true;
        int nbActeOriginal = 0;

        // Parcours du contenu du dossier.
        for(NodeRef nodeRef : dossier.getContenu()) {
            DocinfosAspectHelperModele document = new DocinfosAspectHelperModele(this.serviceRegistry, nodeRef);

            // Vérification des préconditions fichier
            if(!this.verifierPreconditionsFichierActe(document)) resultat = false;

            // Vérification qu'il n'y ai qu'un seul acte original.
            else if(document.getTypedocument() != null && document.getTypedocument().equals("ACTE_ORIGINAL"))
                nbActeOriginal++;
        }

        if(!resultat) return false;

        else if( nbActeOriginal > 1)
            this.regleMetierErreur.ajouterErreurDossierActeSAS(dossier.getNoeud(),
                    "Le dossier doit avoir seulement un fichier typé en 'ACTE_ORIGINAL'." );

        else if(nbActeOriginal < 1)
            this.regleMetierErreur.ajouterErreurDossierActeSAS(dossier.getNoeud(),
                    "Le dossier doit avoir un fichier typé en 'ACTE_ORIGINAL'." );

        else this.regleMetierErreur.retirerErreurDossierActe(dossier.getNoeud());

        // Retour du resultat.
        return true;
    }

    /**
     * Méthode permettant de vérifier les préconditions d'un fichier d'acte.
     * @param document Le document à vérifier.
     * @return <c>true</c> si les préconditions pour un fichier du dossier d'acte sont respectées ; sinon <c>false</c>.
     */
    private boolean verifierPreconditionsFichierActe(DocinfosAspectHelperModele document) {
        if(document.estAspectValide() && this.verifierTypeFichierActe(document)) {
            this.regleMetierErreur.retirerErreurFichierActeSAS(document.getNoeud());
            return true;
        }
        this.regleMetierErreur.ajouterErreurFichierActeSAS(
                document.getNoeud(), this.obtenirMessageErreurFichierActe(document)
        );
        return false;
    }

    /**
     * Méthode permettant de vérifier le type d'un fichier d'acte.
     * @param document Le document que l'on souhaite vérifier.
     * @return <c>true</c> si le type du fichier est valide ; sinon <c>false</c>.
     */
    private boolean verifierTypeFichierActe(DocinfosAspectHelperModele document) {
        logger.info("Vérification du type d'acte du fichier " + document.obtenirNom());

        // Vérification de la possession de l'aspect.
        if(!document.hasAspect()) {
            logger.info(String.format("Le fichier d'acte '%s' n'a pas l'aspect requis.", document.obtenirNom()));
            return false;
        }

        // Vérification du type.
        String type = document.getTypedocument();
        if(type.equals("ACTE_ORIGINAL") || type.equals("ANNEXE"))
        {
            logger.info(String.format("Le type du fichier d'acte '%s' est valide (%s)", document.obtenirNom(),
                    document.getTypedocument()));
            return true;
        }

        logger.info(String.format("Le type du fichier d'acte '%s' n'est pas valide (%s)", document.obtenirNom(),
                document.getTypedocument()));

        return false;
    }

    /** Méthode permettant d'obtenir le message d'erreur pour un document dans un dossier d'acte.
     * @param document Le document pour lequel on souhaite récupérer le message d'erreur.
     * @return Le message d'erreur pour un document dans un dossier d'acte. */
    private String obtenirMessageErreurFichierActe(DocinfosAspectHelperModele document) {
        if(!document.hasAspect()) return "Le document n'a pas l'aspect (actes59:doc) requis pour être traité.";

        else if(!this.verifierTypeFichierActe(document))
            return "Le type du document n'est pas valide.<br />Les types valides sont : \"Délibération\", " +
                    "\"DELIBERATION\", \"Arrêté\" ou bien \"ARRETE\".";

        return "Aucune erreur n'a été détecté.";
    }

    /**
     * Méthode permettant d'obtenir le message d'erreur pour un dossier d'acte.
     * @param dossier Le dossier pour lequel on souhaite récupérer le message d'erreur.
     * @return Le message d'erreur pour un dossier d'acte.
     */
    private String obtenirMessageErreurDossierActe(DossierinfosAspectHelperModele dossier) {
        if(!dossier.hasAspect()) return "Le document n'a pas l'aspect (actes59:dossier) requis pour être traité.";

        StringBuilder message = new StringBuilder();
        if(dossier.estSignataireValide()) message.append("Le signataire du dossier n'est pas renseigné.<br/>");
        if(dossier.estObjetValide()) message.append("L'objet du dossier n'est pas renseigné.<br/>");
        if(dossier.estOrgasigleValide()) message.append("Le signe de la direction n'est pas renseigné.<br/>");
        if(dossier.estTypedossierValide()) message.append("Le type du dossier n'est pas renseigné.<br/>");
        if(dossier.estDatedossierValide()) message.append("La date du dossier n'est pas renseigné.<br/>");

        return message.toString();
    }

}

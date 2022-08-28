package org.cd59.affichagedesactes.metiers.action.stockage;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.docinfos.DocinfosAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuracte.ConteneurActeTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuracte.ConteneurActeTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneurtype.ConteneurTypeTypeHelperModele;
import org.cd59.affichagedesactes.metiers.action.source.IActionMetier;
import org.cd59.affichagedesactes.metiers.factory.DossierFactory;
import org.cd59.affichagedesactes.metiers.regle.RegleMetierDossierActe;
import org.cd59.affichagedesactes.metiers.regle.RegleMetierErreur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Classe exécutant l'action métier de stocker dans le plan de classement l'acte.
 */
public class StockerActeActionMetier implements IActionMetier {

    /**
     * Le logger de la classe.
     */
    private static Logger logger = LoggerFactory.getLogger(StockerActeActionMetier.class);

    /**
     * Les règles métier pour l'attribution et retrait des aspects d'erreurs.
     * */
    private final RegleMetierErreur regleMetierErreur;

    /**
     * Le gestionnaire de nœud d'Alfresco.
     */
    private final NodeService nodeService;

    /**
     * Le gestionnaire de dossiers et fichier d'Alfresco.
     */
    private final FileFolderService fileFolderService;

    /**
     * Initialise une nouvelle instance de la classe {@link StockerActeActionMetier} classe.
     * @param serviceRegistry Le gestionnaire de service Alfresco.
     */
    public StockerActeActionMetier(ServiceRegistry serviceRegistry) {
        this.nodeService = serviceRegistry.getNodeService();
        this.fileFolderService = serviceRegistry.getFileFolderService();
        this.regleMetierErreur = new RegleMetierErreur(this.nodeService);
    }

    @Override
    public void executer(NodeRef nodeRef) {
        // Vérification des préconditions.
        if(!this.verifierPreconditionsActe(nodeRef)) return;
        // Rangement de l'acte.
        this.rangerActe(nodeRef);
    }

    /**
     * Méthdoe permettant de ranger un acte dans l'arborescence.
     * @param nodeRef Node dossier à ranger.
     */
    private void rangerActe(NodeRef nodeRef) {
        DossierinfosAspectHelperModele dossier = new DossierinfosAspectHelperModele(this.nodeService, nodeRef);
        // Récupération/Création du numéro du dossier de destination.
        ConteneurTypeTypeHelperModele dossierType = this.obtenirDossierDestinationActe(dossier);
        
        // Création d'un numéro d'acte.
        String numeroActe = RegleMetierDossierActe.obtenirNumeroActe(dossier);
        if(this.dossierActeExiste(dossier, dossierType, numeroActe)) return;

        ConteneurActeTypeHelperModele dossierActe = DossierFactory.creerDossierActe(
                this.nodeService, dossierType, dossier, numeroActe
        );

        for(NodeRef childNode : dossier.getContenu())
            RegleMetierDossierActe.deplacerDocumentDuSAS(
                    this.fileFolderService, this.nodeService, dossierActe, childNode
            );
    }

    /**
     * Méthode permettant de vérifier si un acte existe déjà dans le dossier de destination.
     * @param dossier Le dossier à déplacer.
     * @param type Le type de dossier.
     * @param numeroActe Le numéro de l'acte.
     * @return <c>true</c> Si le numéro d'acte en paramètre existe déjà dans le dossier. Sinon <c>false</c>.
     */
    private boolean dossierActeExiste(
            DossierinfosAspectHelperModele dossier, ConteneurTypeTypeHelperModele type, String numeroActe
    ) {
        boolean resultat = (type.searchNoeudDossierParNom(ConteneurActeTypeModele.NOM, numeroActe) != null);
        if(resultat) {
            this.regleMetierErreur.ajouterErreurDossierActeSAS(dossier.getNoeud(),
                    String.format("Le dossier %s existe déjà dans l'arborescence Actes/%s/%s/%s/%s", numeroActe,
                            type.getDateAnnee(), type.getDateMois(), type.getDateJour(), type.getConteneurActeType()
                    )
            );
        }
        return resultat;
    }

    /**
     * Méthode permettant de récupérer le dossier de destination d'un acte.
     * @param dossier Le dossier que l'on souhaite déplacer.
     * @return Le dossier de destination de l'acte.
     */
    private ConteneurTypeTypeHelperModele obtenirDossierDestinationActe(DossierinfosAspectHelperModele dossier) {
        // Récupération du dossier racine.
        NodeRef racine = dossier.getAncetre(2);

        // Récupération d'un calendrier pour parser la date.
        Calendar calendrier = new GregorianCalendar();
        calendrier.setTime(dossier.getDatedossier());

        return DossierFactory.obtenirDossierType(this.nodeService,
                DossierFactory.obtenirDossierJour(this.nodeService,
                    DossierFactory.obtenirDossierMois(this.nodeService,
                            DossierFactory.obtenirDossierAnnee(this.nodeService,
                                    DossierFactory.obtenirDossierActes(this.nodeService, dossier.getAncetre(2)),
                                    calendrier.get(Calendar.YEAR)
                            ),calendrier.get(Calendar.MONTH)
                    ), calendrier.get(Calendar.DAY_OF_MONTH)
                ), dossier.getTypedossier()
        );
    }

    /**
     * Méthode permettant de vérifier les préconditions d'un acte.
     * @param nodeRef La référence vers l'acte à vérifier.
     * @return <c>true</c> si les préconditions sont respectés ; sinon <c>false</c>.
     */
    private boolean verifierPreconditionsActe(NodeRef nodeRef) {
        DossierinfosAspectHelperModele dossier = new DossierinfosAspectHelperModele(this.nodeService, nodeRef);
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
            DocinfosAspectHelperModele document = new DocinfosAspectHelperModele(this.nodeService, nodeRef);

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

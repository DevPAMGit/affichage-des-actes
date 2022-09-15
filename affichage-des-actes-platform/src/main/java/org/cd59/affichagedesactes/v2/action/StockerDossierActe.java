package org.cd59.affichagedesactes.v2.action;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.docinfos.DocinfosAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.type.sas.SasTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.type.sas.SasTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.acteoriginal.ActeOriginalTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossieracte.DossierActeTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierannee.DossierAnneeTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierjour.DossierJourTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossiermois.DossierMoisTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossiertypologie.DossierTypologieTypeModele;
import org.cd59.affichagedesactes.v2.action.source.ActionMetier;
import org.cd59.affichagedesactes.v2.application.ApplicationErreurSAS;
import org.cd59.affichagedesactes.v2.application.ApplicationErreurStockage;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Action métier permettant de stocker des actes ans l'arborescence.
 */
public class StockerDossierActe extends ActionMetier {

    /**
     * Le nœud du dossier d'acte à stocker.
     */
    private final NodeRef noeudDossierActe;

    /**
     * Le nœud de l'acte original.
     */
    private NodeRef noeudActeOriginal;

    /**
     * Les nœuds d'annexes.
     */
    private List<NodeRef> annexes;

    /**
     * Le gestionnaire des erreurs sur les dossiers du SAS.
     */
    private final ApplicationErreurSAS applicationErreurSAS;

    /**
     * Le gestionnaire des erreurs sur les dossiers de stockage.
     */
    private final ApplicationErreurStockage applicationErreurStockage;

    /**
     * Initialise une nouvelle instance de la classe {@link ActionMetier}.
     * @param serviceRegistry Le registre de service Alfresco.
     */
    public StockerDossierActe(ServiceRegistry serviceRegistry, NodeRef noeudDossierActe) {
        super(serviceRegistry);
        this.noeudDossierActe = noeudDossierActe;
        this.applicationErreurSAS = new ApplicationErreurSAS(this);
        this.applicationErreurStockage = new ApplicationErreurStockage(this);
    }

    @Override
    public void executer() {
        // Vérification des préconditions.
        if(!this.verifierPreconditions()) return;

        String nomDossierActeStockage = this.obtenirNomDossierActeStockage();
        if(nomDossierActeStockage == null) return;

        synchronized (MutexAction.MUTEX_ARBORESCENCE) {

            // Récupération du dossier d'acte.
            NodeRef dossierActes = this.obtenirDossierActes();

            // Recherche nom du dossier d'acte pour stockage dans le dossier de stockage des actes.
            ResultSet resultatRecherche = this.registryService.getSearchService().query(
                    dossierActes.getStoreRef(), SearchService.LANGUAGE_CMIS_STRICT,
                    String.format("select * from stockageactes59:dossierActe where cmis:name = '%s'",
                            nomDossierActeStockage)
            );

            // Le dossier existe déjà.
            /*if(resultatRecherche != null && resultatRecherche.length() > 0) {
                System.out.println(nomDossierActeStockage + " EXISTE DEJA");
                this.applicationErreurSAS.ajouterErreur(this.noeudDossierActe,
                        String.format("Il existe déjà un dossier nommé '%s' dans l'arborescence des actes.",
                                nomDossierActeStockage)
                );

            // Le dossier n'existe pas.
            }else*/
            if(resultatRecherche == null || resultatRecherche.length() == 0){
                // Retrait de l'erreur si elle existe.
                // this.applicationErreurSAS.retirerErreurInfosDossierActeSas(this.noeudDossierActe);
                NodeRef nodeRef = this.obtenirDossierActe(dossierActes, nomDossierActeStockage);

                try{
                    // Déplacement de document d'acte.
                    this.deplacerActe(nomDossierActeStockage, nodeRef);
                    // Déplacement des annexes.
                    this.deplacerAnnexes(nomDossierActeStockage, nodeRef);

                }catch (Exception e) {

                    NodeService nodeService = this.registryService.getNodeService();
                    for(ChildAssociationRef associationRef : nodeService.getChildAssocs(nodeRef))
                        try {
                            this.registryService.getFileFolderService().move(associationRef.getChildRef(),
                                    this.noeudDossierActe, (String) nodeService.getProperty(associationRef.getChildRef(),
                                            ContentModel.PROP_NAME));
                        }catch (Exception e1) {
                            e1.printStackTrace();
                            return;
                        }

                    this.registryService.getFileFolderService().delete(nodeRef);
                    /*this.applicationErreurSAS.ajouterErreur(this.noeudDossierActe,
                            "Une erreur est survenue lors du déplacement des fichiers.");*/

                    this.taggerIncomplet();
                }
            }
        }
    }

    private void deplacerAnnexes(String identifiant, NodeRef destination) throws FileNotFoundException, NoSuchAlgorithmException, IOException {
        NodeService nodeService = this.registryService.getNodeService();
        int cpt = 1;
        // Déplacement du fichier
        if(this.annexes != null)
            for(NodeRef nodeRef : this.annexes) {
                String nom = String.format("%s_ACTE_ANNEXE_%s", identifiant, this.entierSurNChiffres(cpt, 2));
                this.registryService.getFileFolderService().move(nodeRef, destination, nom);
                // Modification des métadonnées.
                nodeService.setType(nodeRef, ActeOriginalTypeModele.NOM);
                nodeService.setProperty(nodeRef, ContentModel.PROP_TITLE, nom);
                nodeService.setProperty(nodeRef, ActeOriginalTypeModele.EMPREINTE, this.obtenirEmpreinte(nodeRef));

                nodeService.removeAspect(nodeRef, DossierinfosAspectModele.NOM);

                cpt++;
            }
    }

    private void deplacerActe(String identifiant, NodeRef destination) throws FileNotFoundException, NoSuchAlgorithmException, IOException {
        NodeService nodeService = this.registryService.getNodeService();
        // Déplacement du fichier
        this.registryService.getFileFolderService().move(this.noeudActeOriginal, destination, String.format("%s_ACTE_ORIGINAL", identifiant));
        // Modification des métadonnées.
        nodeService.setType(this.noeudActeOriginal, ActeOriginalTypeModele.NOM);
        nodeService.setProperty(this.noeudActeOriginal, ContentModel.PROP_TITLE, String.format("%s_ACTE_ORIGINAL", identifiant));
        nodeService.setProperty(this.noeudActeOriginal, ActeOriginalTypeModele.EMPREINTE, this.obtenirEmpreinte(this.noeudActeOriginal));

        nodeService.removeAspect(this.noeudActeOriginal, DossierinfosAspectModele.NOM);
    }

    private String obtenirEmpreinte(NodeRef nodeRef) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return Base64.getEncoder().encodeToString(
                digest.digest(this.registryService.getFileFolderService().getReader(nodeRef).getContentInputStream().readAllBytes())
        );
    }

    private NodeRef obtenirDossierActe(NodeRef dossierActes, String identifiant) {
        String mois = this.obtenirMoisActe();
        String jour = this.obtenirJourActe();
        String annee = this.obtenirAnneeActe();
        String type = ((String) this.registryService.getNodeService().
                getProperty(this.noeudDossierActe, DossierinfosAspectModele.TYPEDOSSIER))
                .replace("é", "e").toUpperCase();


        NodeRef dossierAnnee = this.obtenirNoeudDossierAnnee(dossierActes, annee);
        NodeRef dossierMois = this.obtenirNoeudDossierMois(dossierAnnee, annee, mois);
        NodeRef dossierJour = this.obtenirNoeudDossierJour(dossierMois, annee, mois, jour);
        NodeRef dossierType = this.obtenirNoeudDossierType(type, dossierJour, annee, mois, jour);

        return this.obtenirNoeudDossierActe(dossierType, annee, mois, jour, identifiant);
    }

    private NodeRef obtenirNoeudDossierType(String type, NodeRef dossierJour, String annee, String mois, String jour) {
        NodeRef resultat = this.obtenirNoeud(dossierJour, type.replace("é", "e").toUpperCase());
        if(resultat == null) return null;

        NodeService nodeService = this.registryService.getNodeService();

        if(!nodeService.getType(resultat).isMatch(DossierTypologieTypeModele.NOM)) {
            nodeService.setType(resultat, DossierTypologieTypeModele.NOM);
            nodeService.setProperties(resultat, this.applicationErreurStockage.initierDossierTypologie(type, jour, mois, annee));
        }

        return resultat;
    }

    /**
     * Récupère le nœud référençant l'acte.
     * @param nodeRef La référence du nœud dans lequel le nœud doit être créé ou recherché.
     * @param annee L'année à laquelle la date de l'acte fait référence.
     * @param mois Le mois auquel la date de l'acte fait référence.
     * @param jour Le jour auquel la date de l'acte fait référence.
     * @param identifiant Le nom du dossier d'acte pour stockage.
     */
    private NodeRef obtenirNoeudDossierActe(
            NodeRef nodeRef, String annee, String mois, String jour, String identifiant
    ) {
        NodeRef resultat = this.obtenirNoeud(nodeRef, identifiant);
        if(resultat == null) return null;

        NodeService nodeService = this.registryService.getNodeService();

        if(!nodeService.getType(resultat).isMatch(DossierActeTypeModele.NOM)){
            // Type du nœud.
            nodeService.setType(resultat, DossierActeTypeModele.NOM);
            // Initialisation des propriétés.
            nodeService.setProperties(resultat, this.applicationErreurStockage.initierDossierActe(
                    new DossierinfosAspectHelperModele(this.registryService, this.noeudDossierActe), identifiant, jour,
                    mois, annee)
            );
        }

        // Retour du noeud.
        return resultat;
    }


    /**
     * Récupère le nœud référençant le jour de l'acte.
     * @param nodeRef La référence du nœud de mois dans lequel le dossier du jour devrait se trouver.
     * @param annee L'année à laquelle la date de l'acte fait référence.
     * @param mois Le mois auquel la date de l'acte fait référence.
     * @param jour Le jour auquel la date de l'acte fait référence.
     */
    public NodeRef obtenirNoeudDossierJour(NodeRef nodeRef, String annee, String mois, String jour) {
        NodeRef resultat = this.obtenirNoeud(nodeRef, annee);
        if(resultat == null) return null;

        NodeService nodeService = this.registryService.getNodeService();

        // Vérification que le nœud est bien du type 'stockageactes59:dossierJour'.
        if(!nodeService.getType(resultat).isMatch(DossierJourTypeModele.NOM)) {
            // Typage du nœud.
            nodeService.setType(resultat, DossierJourTypeModele.NOM);
            // Initialisation des propriétés du nœud.
            nodeService.setProperties(resultat, this.applicationErreurStockage.initierDossierJour(jour, mois, annee));
        }

        // Retour du noeud.
        return resultat;
    }

    /**
     * Méthode permettant de récupérer le nœud pour un dossier d'acte pour un mois dans une année.
     * @param nodeRef Le nœud dans lequel créer le dossier d'année.
     * @param mois L'année à laquelle on fait référence.
     * @return Le nœud de l'année de l'acte.
     */
    private NodeRef obtenirNoeudDossierMois(NodeRef nodeRef, String annee, String mois) {
        NodeRef resultat = this.obtenirNoeud(nodeRef, annee);
        if(resultat == null) return null;

        NodeService nodeService = this.registryService.getNodeService();

        // Vérification que le nœud est bien du type 'stockageactes59:dossierMois'.
        if(!nodeService.getType(resultat).isMatch(DossierMoisTypeModele.NOM)) {
            // Initialisation du type.
            nodeService.setType(resultat, DossierMoisTypeModele.NOM);
            // Initialisation des propriétés.
            nodeService.setProperties(resultat, this.applicationErreurStockage.initierDossierMois(mois, annee));
        }

        // Retour du résultat.
        return resultat;
    }

    /**
     * Méthode permettant de récupérer le nœud pour un dossier d'acte d'une année.
     * @param nodeRef Le nœud dans lequel créer le dossier d'année.
     * @param annee L'année à laquelle on fait référence.
     * @return Le nœud de l'année de l'acte.
     */

    private NodeRef obtenirNoeudDossierAnnee(NodeRef nodeRef, String annee) {
        NodeRef resultat = this.obtenirNoeud(nodeRef, annee);
        if(resultat == null) return null;

        NodeService nodeService = this.registryService.getNodeService();

        // Vérification que le nœud est bien du type 'stockageactes59:dossierAnnee'.
        if(!nodeService.getType(resultat).isMatch(DossierAnneeTypeModele.NOM)) {
            // Typage du nœud.
            nodeService.setType(resultat, DossierAnneeTypeModele.NOM);
            // Initialisation des propriétés.
            nodeService.setProperties(resultat, this.applicationErreurStockage.initierDossierAnnee(annee));
        }

        // Retour du résultat
        return resultat;
    }

    /**
     * Récupère ou crée un nœud dans un nœud en paramètre.
     * @param nodeRef Le nœud dans lequel créé le nouveau.
     * @param nom Le nom du nœud.
     * @return Le nœud trouvé ou créé.
     */
    private NodeRef obtenirNoeud(NodeRef nodeRef, String nom) {
        // Vérification des préconditions de traitements.
        if(nodeRef == null || nom == null || nom.trim().isEmpty()) return null;

        // Recherche du nœud par son nom.
        NodeRef resultat = this.registryService.getFileFolderService().searchSimple(nodeRef, nom);

        // Le nœud n'existe pas : création.
        if(resultat == null)
            resultat = this.registryService.getNodeService().createNode(nodeRef,ContentModel.ASSOC_CONTAINS,
                    QName.createQName(nom), ContentModel.TYPE_FOLDER).getChildRef();

        return resultat;
    }

    /**
     * Méthode permettant de récupérer ou générer le nom du dossier de stockage de l'acte.
     * @return Le nom du dossier de stockage de l'acte.
     */
    private String obtenirNomDossierActeStockage() {
        DossierinfosAspectHelperModele dossier = new DossierinfosAspectHelperModele(this.registryService, this.noeudDossierActe);
        if(!this.verifierPreconditionDossier()) return null;
        String numeroActe = dossier.getNumeroacte();
        if(numeroActe == null || numeroActe.isEmpty()) numeroActe = this.genererNumeroActe(dossier.getNoeudParent());
        if(numeroActe == null) return null;

        return String.format("%s_%s_%s_%s",
                dossier.getTypedossier().replace("é", "e").substring(0,3).toUpperCase(),
                dossier.getOrgasigle().toUpperCase(), obtenirAnneeActe(), numeroActe);
    }

    /**
     * Permet d'obtenir l'année du dossier d'acte.
     * @return L'année du dossier d'acte.
     */
    private String obtenirAnneeActe(){
        Calendar calendrier = new GregorianCalendar();
        calendrier.setTime(((Date)this.registryService.getNodeService().getProperty(this.noeudDossierActe, DossierinfosAspectModele.DATEDOSSIER)));
        return Integer.toString(calendrier.get(Calendar.YEAR));
    }

    /**
     * Permet d'obtenir le mois de l'année du dossier d'acte.
     * @return Le mois de l'année du dossier d'acte.
     */
    private String obtenirMoisActe(){
        Calendar calendrier = new GregorianCalendar();

        if(this.noeudDossierActe == null) {
            System.out.append("NOEUD EST NULL");
        }else {
            System.out.append("NOEUD EST PAS NULL");
        }

        calendrier.setTime(((Date)this.registryService.getNodeService().getProperty(this.noeudDossierActe, DossierinfosAspectModele.DATEDOSSIER)));
        return this.entierSurNChiffres(calendrier.get(Calendar.MONTH), 2);
    }

    /**
     * Permet d'obtenir le jour de la date de l'acte.
     * @return Le jour de la date de l'acte.
     */
    private String obtenirJourActe() {
        Calendar calendrier = new GregorianCalendar();
        calendrier.setTime(((Date)this.registryService.getNodeService().getProperty(this.noeudDossierActe, DossierinfosAspectModele.DATEDOSSIER)));
        return this.entierSurNChiffres(calendrier.get(Calendar.DAY_OF_MONTH), 2);
    }

    /**
     * Génère un numéro d'acte.
     * @param nodeRef Le nœud SAS permettant de générer un numéro d'acte.
     * @return Un numéro d'acte.
     */
    private String genererNumeroActe(NodeRef nodeRef) {
        // Vérification que le dossier est bien un dossier SAS.
        SasTypeHelperModele dossierSAS = new SasTypeHelperModele(this.registryService, nodeRef);
        if(!dossierSAS.hasType()) {
            this.applicationErreurSAS.ajouterErreur(this.noeudDossierActe, "Le dossier n'est pas situé dans un dossier SAS. Il est impossible de récupérer un numéro d'acte.");
            return null;
        }else this.applicationErreurSAS.retirerErreurInfosDossierActeSas(this.noeudDossierActe);

        String resultat;
        synchronized (MutexAction.MUTEX_COMPTEUR_SAS) {
            Serializable nActe = dossierSAS.getPropriete(SasTypeModele.COMPTEUR);
            if(nActe == null) {
                dossierSAS.setCompteur(1);
                nActe = dossierSAS.getCompteur();
            }

            dossierSAS.setCompteur( ((int)nActe) +1 );
            resultat = this.entierSurNChiffres( (int)nActe, 4 );
        }

        return resultat;
    }

    /**
     * Convertit un entier en chaîne de caractère et ajoute des zéros avant le nombre si besoin est.
     * @param entier L'entier à convertir.
     * @param taille La taille de la chaîne de caractères demandée.
     * @return Une chaine de caractères de la taille demandée.
     */
    private String entierSurNChiffres(int entier, int taille) {
        StringBuilder resultat = new StringBuilder();

        // Récupération de l'entier au format chaine.
        String entierEnChaine = Integer.toString(entier);
        // Calcule de nombre de zéros nécessaires et initialisation de l'index.
        int max = taille - entierEnChaine.length(), index = 0;

        // Initialisation du résultat avec les zéros nécessaires.
        while(index < max) {
            resultat.append("0");
            index++;
        }

        // Ajout du nombre en plus
        resultat.append(entierEnChaine);
        return resultat.toString();
    }

    /**
     * Récupère le nœud qui contiendra tous les actes du département.
     * @return Le nœud qui contiendra tous les actes du département.
     */
    private NodeRef obtenirDossierActes() {
        NodeService nodeService = this.registryService.getNodeService();
        // Récupération de la racine.
        NodeRef racine = new AlfrescoModeleHelper(this.registryService, this.noeudDossierActe).getAncetre(2);

        // Recherche d'un dossier acte à la racine.
        ResultSet resultatRecherche = this.registryService.getSearchService().query(this.noeudDossierActe.getStoreRef(),
                SearchService.LANGUAGE_CMIS_STRICT, "select * from stockageactes59:dossierActes");

        NodeRef resultat;
        // Il n'est pas trouvé : Création du dossier d'acte.
        if(resultatRecherche == null || resultatRecherche.length() == 0) {
            resultat = nodeService.createNode(racine, ContentModel.ASSOC_CONTAINS, QName.createQName("Actes"), ContentModel.TYPE_FOLDER).getChildRef();
            nodeService.setProperties(resultat, this.applicationErreurStockage.initierDossierActes());

        // Récupération du resultat.
        }else resultat = resultatRecherche.getNodeRef(0);

        // Retour du résultat.
        return resultat;
    }

    /** Vérification des préconditions du dossier. */
    private boolean verifierPreconditions() {
        return (this.verifierPreconditionDossier() && this.verifierPreconditionFichiers());
    }

    /**
     * Vérifie les nœuds enfants du dossier d'actes
     * @return <c>true</c> si les nœuds enfants sont valides ; sinon <c>false</c>.
     */
    private boolean verifierPreconditionFichiers() {
        boolean resultat = this.verifierPreconditionActeAnnexe();
        if(!this.verifierPreconditionActeOriginal()) resultat = false;
        if(!this.verifierPreconditionFichierInutile()) resultat = false;

        return resultat;
    }

    /**
     * Valide les préconditions pour les fichiers annexes.
     * @return <c>true</c> si le fichier annexe est valide.
     */
    private boolean verifierPreconditionFichierInutile() {
        List<NodeRef> nodeRefs = new DossierinfosAspectHelperModele(this.registryService, this.noeudDossierActe).getContenu();

        boolean resultat = true;

        for(NodeRef nodeRef : nodeRefs) {
            DocinfosAspectHelperModele document = new DocinfosAspectHelperModele(this.registryService, nodeRef);

            if(!document.hasAspect())
                this.applicationErreurSAS.ajouterErreur(this.noeudDossierActe,
                        "Le dossier contient des fichiers qui ne sont pas typés.");
            else if( document.getTypedocument() == null)
                this.applicationErreurSAS.ajouterErreur(this.noeudDossierActe,
                        "Le dossier ne contient des fichiers dont le type n'est pas renseigné.");
            else if(!document.getTypedocument().equals("ACTE_ORIGINAL") && !document.getTypedocument().equals("ANNEXE"))
                this.applicationErreurSAS.ajouterErreur(this.noeudDossierActe,
                        "Le dossier ne contient des fichiers dont le type n'est pas valide.");
        }

        return resultat;
    }

    /**
     * Valide les préconditions pour les fichiers annexes.
     * @return <c>true</c> si le fichier annexe est valide.
     */
    private boolean verifierPreconditionActeAnnexe() {
        ResultSet resultatRecherche = this.registryService.getSearchService()
                .query(this.noeudDossierActe.getStoreRef(), SearchService.LANGUAGE_CMIS_STRICT,
                        "select * from actes59:docinfos where actes59:typedocument = 'ANNEXE'");
        if(resultatRecherche != null && resultatRecherche.length() > 0)
            this.annexes = resultatRecherche.getNodeRefs();
        else
            this.annexes = new ArrayList<>();


        return true;
    }

    /**
     * Valide les préconditions pour un nœud fichier de type acte original.
     * @return <c>true</c> si les préconditions de l'acte original sont respectées. Sinon <c>false</c>.
     */
    private boolean verifierPreconditionActeOriginal() {
        List<NodeRef> nodeRefs = null;
        ResultSet resultatRecherche = this.registryService.getSearchService()
                .query(this.noeudDossierActe.getStoreRef(), SearchService.LANGUAGE_CMIS_STRICT,
                        "select * from actes59:docinfos where actes59:typedocument = 'ACTE_ORIGINAL'");
        if(resultatRecherche!= null) nodeRefs = resultatRecherche.getNodeRefs();

        boolean resultat = true;

        if(nodeRefs == null || nodeRefs.size() == 0) {
            this.applicationErreurSAS.ajouterErreur(
                    this.noeudDossierActe, "Le dossier ne contient pas le fichier d'acte original."
            );
            resultat = false;
        }else if(nodeRefs.size() > 1) {
            for (NodeRef nodeRef : nodeRefs)
                this.applicationErreurSAS.ajouterErreur(
                        nodeRef, "Il ne doit y avoir qu'un seul fichier d'acte original."
                );
            resultat = false;
        }else {
            this.noeudActeOriginal = nodeRefs.get(0);
            this.applicationErreurSAS.retirerErreur(nodeRefs.get(0));
        }
        return resultat;
    }

    /**
     * Vérifie les préconditions d'un dossier d'acte du SAS.
     * @return <c>true</c> si le dossier est valide. Sinon <c>false</c>.
     */
    private boolean verifierPreconditionDossier() {
        DossierinfosAspectHelperModele dossier = new DossierinfosAspectHelperModele(
                this.registryService, this.noeudDossierActe
        );
        if(!dossier.hasAspect() && !dossier.estAspectValide()) {
            this.applicationErreurSAS.ajouterErreur(
                    this.noeudDossierActe,
                    this.obtenirMessageErreurDossierActe(
                            new DossierinfosAspectHelperModele(this.registryService, this.noeudDossierActe)
                    )
            );
            this.taggerIncomplet();
            return false;
            // Retrait de l'erreur s'il y avait.
        }else this.applicationErreurSAS.retirerErreurInfosDossierActeSas(this.noeudDossierActe);

        // Le dossier n'est à traiter que s'il est complet.
        return dossier.getDossiercomplet();
    }

    /**
     * Remet le nœud du dossier en non complet pout traitement.
     */
    private void taggerIncomplet() {
        DossierinfosAspectHelperModele dossier = new DossierinfosAspectHelperModele(
                this.registryService, this.noeudDossierActe
        );
        if(dossier.hasAspect()) dossier.setDossiercomplet(false);
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

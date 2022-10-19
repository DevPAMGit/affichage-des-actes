package org.cd59.affichagedesactes.v2.action.stockage;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.cd59.affichagedesactes.v2.action.MutexAction;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.cd59.affichagedesactes.v2.action.source.ActionMetier;
import org.cd59.affichagedesactes.v2.action.source.exception.ActionMetierException;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.type.sas.SasTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.docinfos.DocinfosAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.affichage59.aspect.affichage.AffichageAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossieractes.DossierActesTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierannee.DossierAnneeTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierjour.DossierJourTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossiermois.DossierMoisTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossiertypologie.DossierTypologieTypeModele;
import org.cd59.affichagedesactes.v2.action.source.exception.AspectException;
import org.cd59.affichagedesactes.v2.action.source.exception.NoeudNullException;
import org.cd59.affichagedesactes.v2.action.source.exception.PreconditionException;
import org.cd59.affichagedesactes.v2.action.stockage.modele.DossierActeModele;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Action métier permettant de stocker un acte.
 */
public class StockerDossierActeSas extends ActionMetier {

    /**
     * Le logger de la classe.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StockerDossierActeSas.class);

    /**
     * Le nœud source (père du nœud SAS).
     */
    private NodeRef noeudSource;

    /**
     * Le nœud du dossier de SAS.
     */
    private NodeRef noeudSas;

    /**
     * Le nœud possédant l'aspect 'actes59:dossierinfos'.
     */
    private final NodeRef noeudDossier;

    /**
     * Liste des nœuds pour les annexes.
     */
    private List<NodeRef> noeudsAnnexes;

    /**
     * Le nœud du fichier d'acte
     */
    private NodeRef noeudFichier;

    /**
     * Modèle pour le dossier d'acte.
     */
    private final DossierActeModele dossierModele;

    /**
     * Initialise une nouvelle instance de la classe {@link StockerDossierActeSas}.
     * @param serviceRegistry Le registre de service Alfresco.
     * @param noeudDossier Le nœud référençant un dossier d'acte.
     */
    public StockerDossierActeSas(ServiceRegistry serviceRegistry, NodeRef noeudDossier) {
        super(serviceRegistry);
        this.noeudFichier = null;
        this.noeudDossier = noeudDossier;
        this.dossierModele = new DossierActeModele();
    }

    @Override
    public void executer() {
        try{

            // Vérification des préconditions.
            try{

                // Le dossier d'acte
                this.verifierPreconditionDossierActe();

                // Le fichier d'acte.
                this.verifierPreconditionFichierActe();

                // Les autres fichiers.
                this.verifierPreconditionContenuDossier();

            }catch (PreconditionException e1) {
                LOGGER.error(e1.getMessage(), e1);
                this.modifierPropriete(this.noeudDossier, DossierinfosAspectModele.DOSSIERCOMPLET, false);
                return;
            }

            NodeRef dossierActe;
            synchronized (MutexAction.MUTEX_ARBORESCENCE) {

                NodeRef actes = this.obtenirDossierActes();

                // Vérification que le dossier d'acte n'existe pas sur l'arborescence.
                List<NodeRef> acte = this.rechercherNoeuds(actes,
                        String.format(
                                "select * from actes59:dossierinfos " +
                                "where cmis:name = '%s' " +
                                "and IN_TREE('%s')", this.dossierModele.numero, actes.getId()));

                if(acte.size() > 0)
                    throw new PreconditionException(String.format("Le dossier %s existe déjà l'arborescence.",
                            this.dossierModele.numero)
                    );

                // Création du dossier d'acte.
                dossierActe = this.creerDossierActe(actes);
            }

            // Déplacement des documents.
            this.deplacerActe(dossierActe);
            this.deplacerAnnexes(dossierActe);

        }catch (Exception e3) {
            LOGGER.error(e3.getMessage(), e3);

            try { this.modifierPropriete(this.noeudDossier, DossierinfosAspectModele.DOSSIERCOMPLET, false); }
            catch (Exception e5) { LOGGER.error(e5.getMessage(), e5); }

            throw new RuntimeException(e3);
        }
    }

    private void deplacerAnnexes( NodeRef destination) throws FileNotFoundException, NoSuchAlgorithmException, IOException {
        NodeService nodeService = this.registryService.getNodeService();
        int cpt = 1;
        // Déplacement du fichier
        if(this.noeudsAnnexes != null)
            for(NodeRef nodeRef : this.noeudsAnnexes) {

                // Initialisation du nom du nœud.
                String nom;
                if( this.noeudsAnnexes.size() > 1  )
                    nom = String.format("%s_ANNEXE_%s", this.dossierModele.numero,
                            this.entierSurNChiffres(cpt, 2));
                else nom = String.format("%s_ANNEXE", this.dossierModele.numero);

                // Déplacement du nœud.
                this.registryService.getFileFolderService().move(nodeRef, destination, nom);

                // Modification des métadonnées.
                nodeService.setProperty(nodeRef, ContentModel.PROP_TITLE, nom);
                nodeService.setProperty(nodeRef, ContentModel.PROP_DESCRIPTION, String.format("Annexe de la %s %s.",
                        this.dossierModele.type, this.dossierModele.numero));
                nodeService.setProperty(nodeRef, DocinfosAspectModele.EMPREINTE , this.obtenirEmpreinte(nodeRef));

                cpt++;
            }
    }

    private void deplacerActe(NodeRef destination) throws FileNotFoundException, NoSuchAlgorithmException, IOException {
        NodeService nodeService = this.registryService.getNodeService();

        // Déplacement du fichier
        this.registryService.getFileFolderService().move(
                this.noeudFichier, destination, String.format("%s_ACTE_ORIGINAL", this.dossierModele.identifiant)
        );

        // Modification des métadonnées.
        nodeService.setProperty(this.noeudFichier, ContentModel.PROP_TITLE, String.format("%s_ACTE_ORIGINAL", this.dossierModele.identifiant));
        nodeService.setProperty(this.noeudFichier, DocinfosAspectModele.EMPREINTE,this.obtenirEmpreinte(this.noeudFichier));
    }

    /**
     * Crée le dossier d'acte.
     * @return Un nœud référençant le dossier d'acte.
     * @throws ActionMetierException Si le nœud, la requete ou le type est null.
     */
    private NodeRef creerDossierActe(NodeRef actes) throws ActionMetierException, FileNotFoundException {
        NodeRef annee = this.obtenirDossierAnnee(actes);
        NodeRef mois = this.obtenirDossierMois(annee);
        NodeRef jour = this.obtenirDossierJour(mois);
        NodeRef type = this.obtenirDossierType(jour);

        return this.deplacerDossierActe(type);
    }

    /**
     * Permet de récupérer le nœud concernant l'acte.
     * @param nodeRef Le nœud dans lequel créer le dossier 'acte'.
     * @return Le nœud de l'acte.
     * @throws ActionMetierException Si le nœud, la requete ou le type est null.
     */
    private NodeRef deplacerDossierActe(NodeRef nodeRef) throws ActionMetierException, FileNotFoundException {
        // Déplacement du dossier d'acte.
        this.registryService.getFileFolderService().move(
                this.noeudDossier, nodeRef, String.format("%s_ACTE_ORIGINAL", this.dossierModele.identifiant)
        );

        // Initialisation des propriétés.
        HashMap<QName, Serializable> proprietes = new HashMap<>(this.registryService.getNodeService().getProperties(this.noeudDossier));
        proprietes.put(ContentModel.PROP_TITLE, this.dossierModele.identifiant);
        proprietes.put(ContentModel.PROP_NAME, this.dossierModele.identifiant);
        proprietes.put(ContentModel.PROP_DESCRIPTION,
                String.format("Dossier %s numéro %s du %s %s %d",
                        this.dossierModele.type.equals("ACTE") ? "de l'arrêté" : "de la délibération",
                        this.dossierModele.identifiant, this.entierSurNChiffres(this.dossierModele.jour, 2),
                        this.dossierModele.obtenirMois(), this.dossierModele.annee)
        );
        proprietes.put(DossierinfosAspectModele.ANNEE, Integer.toString(this.dossierModele.annee));
        proprietes.put(DossierinfosAspectModele.NUMEROACTE, this.dossierModele.numero);
        proprietes.put(DossierinfosAspectModele.IDDOSSIER, this.dossierModele.identifiant);

        this.miseAJourAspect(this.noeudDossier, DossierinfosAspectModele.NOM, proprietes);

        return this.noeudDossier;
    }

    /**
     * Permet de récupérer les propriétés de base pour l'initialisation de l'aspect 'affichage59:affichage'.
     * @return La liste des propriétés initialisées.
     */
    private HashMap<QName, Serializable> obtenirProprieteAffichageBase() {
        HashMap<QName, Serializable> proprietes = new HashMap<>();
        proprietes.put(AffichageAspectModele.TENTATIVE_ENVOI, null);
        proprietes.put(AffichageAspectModele.ETAT, "Prêt à être envoyer");

        return proprietes;
    }

    /**
     * Permet de récupérer le nœud du type concernant l'acte.
     * @param nodeRef Le nœud dans lequel créer le dossier 'type'.
     * @return Le nœud de du type concernant l'acte.
     * @throws ActionMetierException Si le nœud, la requete ou le type est null.
     */
    private NodeRef obtenirDossierType(NodeRef nodeRef) throws ActionMetierException {
        // Recherche du dossier d'acte.
        String type = this.dossierModele.type;
        List<NodeRef> recherche = this.rechercherNoeuds(nodeRef,
                String.format(
                        "select * from stockageactes59:dossierTypologie " +
                        "where cmis:name = '%s' " +
                        "and IN_TREE('%s') "
                        , type, nodeRef.getId()
                )
        );

        if(recherche.size() > 0) return recherche.get(0);

        NodeRef resultat = this.creerDossier(nodeRef, type);
        this.modifierType(resultat, DossierTypologieTypeModele.NOM);

        HashMap<QName, Serializable> proprietes = new HashMap<>();
        proprietes.put(ContentModel.PROP_TITLE, type);
        proprietes.put(ContentModel.PROP_NAME, type);
        proprietes.put(ContentModel.PROP_DESCRIPTION,
                String.format("Dossier stockant les %ss du département du Nord pour la journée du %s %s %d.",
                        type.toLowerCase(), this.entierSurNChiffres(this.dossierModele.jour, 2),
                        this.dossierModele.obtenirMois(), this.dossierModele.annee)
        );
        proprietes.put(DossierTypologieTypeModele.ANNEE, this.dossierModele.annee);
        proprietes.put(DossierTypologieTypeModele.MOIS, this.dossierModele.annee);
        proprietes.put(DossierTypologieTypeModele.JOUR, this.dossierModele.jour);
        proprietes.put(DossierTypologieTypeModele.TYPOLOGIE_DOSSIER, type);

        this.modifierProprietes(resultat, proprietes);

        return resultat;
    }

    /**
     * Permet de récupérer le jour concernant l'acte.
     * @param nodeRef Le nœud dans lequel créer le dossier 'jour'.
     * @return Le nœud de du jour concernant l'acte.
     * @throws ActionMetierException Si le nœud, la requete ou le type est null.
     */
    private NodeRef obtenirDossierJour(NodeRef nodeRef) throws ActionMetierException {
        // Recherche du dossier d'acte.
        String jour = this.entierSurNChiffres(this.dossierModele.jour, 2);
        List<NodeRef> recherche = this.rechercherNoeuds(nodeRef,
                String.format(
                        "select * from stockageactes59:dossierJour " +
                        "where cmis:name = '%s' and IN_TREE('%s')", jour, nodeRef.getId())
        );

        if(recherche.size() > 0) return recherche.get(0);

        NodeRef resultat = this.creerDossier(nodeRef, jour);
        this.modifierType(resultat, DossierJourTypeModele.NOM);

        HashMap<QName, Serializable> proprietes = new HashMap<>();
        proprietes.put(ContentModel.PROP_TITLE, jour);
        proprietes.put(ContentModel.PROP_NAME, jour);
        proprietes.put(ContentModel.PROP_DESCRIPTION,
                String.format("Dossier stockant les actes du département du Nord pour la journée du %s %s %d.",
                        jour, this.dossierModele.obtenirMois(), this.dossierModele.annee)
        );
        proprietes.put(DossierJourTypeModele.ANNEE, this.dossierModele.annee);
        proprietes.put(DossierJourTypeModele.MOIS, this.dossierModele.annee);
        proprietes.put(DossierJourTypeModele.JOUR, this.dossierModele.jour);

        this.modifierProprietes(resultat, proprietes);

        return resultat;
    }

    /**
     * Permet de récupérer de l'année concernant l'acte.
     * @param nodeRef Le nœud dans lequel créer le dossier 'année'.
     * @return Le nœud de l'année concernant l'acte.
     * @throws ActionMetierException Si le nœud, la requete ou le type est null.
     */
    private NodeRef obtenirDossierMois(NodeRef nodeRef) throws ActionMetierException {
        // Recherche du dossier d'acte.
        String mois = this.entierSurNChiffres(this.dossierModele.mois, 2);
        List<NodeRef> recherche = this.rechercherNoeuds(nodeRef,
                String.format(
                        "select * from stockageactes59:dossierMois " +
                        "where cmis:name = '%s' " +
                        "and IN_TREE('%s')", mois, nodeRef.getId())
        );

        if(recherche.size() > 0) return recherche.get(0);

        NodeRef resultat = this.creerDossier(nodeRef, mois);
        this.modifierType(resultat, DossierMoisTypeModele.NOM);

        HashMap<QName, Serializable> proprietes = new HashMap<>();
        proprietes.put(ContentModel.PROP_TITLE, mois);
        proprietes.put(ContentModel.PROP_NAME, mois);
        proprietes.put(ContentModel.PROP_DESCRIPTION,
                String.format("Dossier stockant les actes du département du Nord pour le mois de %s pour l'année %d.",
                        this.dossierModele.obtenirMois(), this.dossierModele.annee)
        );
        proprietes.put(DossierMoisTypeModele.ANNEE, this.dossierModele.annee);
        proprietes.put(DossierMoisTypeModele.MOIS, this.dossierModele.mois);

        this.modifierProprietes(resultat, proprietes);

        return resultat;
    }

    /**
     * Permet de récupérer de l'année concernant l'acte.
     * @param nodeRef Le nœud dans lequel créer le dossier 'année'.
     * @return Le nœud de l'année concernant l'acte.
     * @throws ActionMetierException Si le nœud, la requete ou le type est null.
     */
    private NodeRef obtenirDossierAnnee(NodeRef nodeRef) throws ActionMetierException {
        // Recherche du dossier d'acte.
        String annee = Integer.toString(this.dossierModele.annee);
        List<NodeRef> recherche = this.rechercherNoeuds(nodeRef,
                String.format(
                        "select * from stockageactes59:dossierAnnee " +
                        "where  cmis:name = '%s' " +
                        "and IN_TREE('%s')", annee, nodeRef.getId())
        );

        if(recherche.size() > 0) return recherche.get(0);

        NodeRef resultat = this.creerDossier(nodeRef, annee);
        this.modifierType(resultat, DossierAnneeTypeModele.NOM);

        HashMap<QName, Serializable> proprietes = new HashMap<>();
        proprietes.put(ContentModel.PROP_TITLE, annee);
        proprietes.put(ContentModel.PROP_NAME, annee);
        proprietes.put(ContentModel.PROP_DESCRIPTION, String.format("Dossier stockant les actes du département " +
                "du Nord pour l'année %s.", annee)
        );
        proprietes.put(DossierAnneeTypeModele.ANNEE, this.dossierModele.annee);

        this.modifierProprietes(resultat, proprietes);

        return resultat;
    }

    /**
     * Permet de récupérer le nœud contenant les dossiers d'actes.
     * @return Le nœud de l'année concernant l'acte.
     * @throws ActionMetierException Si le nœud, la requete ou le type est null.
     */
    private NodeRef obtenirDossierActes() throws ActionMetierException {
        // Recherche du dossier d'acte.
        List<NodeRef> recherche = this.rechercherNoeuds(this.noeudSource,
                String.format(
                    "select * from stockageactes59:dossierActes where IN_TREE('%s')", this.noeudSource.getId()
                )
        );
        if(recherche.size() > 0) return recherche.get(0);

        NodeRef resultat = this.creerDossier(this.noeudSource, "Actes");
        this.modifierType(resultat, DossierActesTypeModele.NOM);

        HashMap<QName, Serializable> proprietes = new HashMap<>();
        proprietes.put(ContentModel.PROP_TITLE, "Actes");
        proprietes.put(ContentModel.PROP_NAME, "Actes");
        proprietes.put(ContentModel.PROP_DESCRIPTION, "Dossier stockant les actes du département du Nord.");
        proprietes.put(DossierAnneeTypeModele.ANNEE, this.dossierModele.annee);

        this.modifierProprietes(resultat, proprietes);

        return resultat;
    }

    /**
     * Vérifie les préconditions sur le dossier d'acte.
     * @throws ActionMetierException Si le nœud de dossier est null, s'il n'a pas l'aspect requis.
     */
    private void verifierPreconditionDossierActe() throws ActionMetierException {
        // Vérification que le nœud de dossier est l'aspect requis.
        if(!this.aAspect(this.noeudDossier, DossierinfosAspectModele.NOM))
            throw new AspectException(
                    String.format("%s:%s", DossierinfosAspectModele.PREFIX, DossierinfosAspectModele.NOM.getLocalName())
            );

        this.noeudSas = this.obtenirNoeudParent(this.noeudDossier);
        this.noeudSource = this.obtenirAncetre(this.noeudDossier, 2);

        // Vérification des données.
        this.dossierModele.type = this.obtenirType();
        this.dossierModele.objet = this.obtenirObjet();
        this.dossierModele.source = this.obtenirSource();
        this.dossierModele.resume = this.obtenirResume();
        this.dossierModele.date = this.obtenirDateDossier();
        this.dossierModele.signataire = this.obtenirSignataire();
        this.dossierModele.estDossierComplet = this.obtenirDossierComplet();
        this.dossierModele.sigleOrganisation = this.obtenirSigleOrganisation();

        if(!this.dossierModele.estValide())
            throw new PreconditionException(this.dossierModele.obtenirMessageErreur());

        // Initialisation du numéro d'acte/
        this.dossierModele.numero = this.obtenirNumeroActe();
        this.dossierModele.identifiant = this.obtenirIdDossier();
    }

    /**
     * Indique la source du document.
     * @return La valeur de la source du dossier.
     */
    private String obtenirSource() throws ActionMetierException {
        try {
            // Récupération de la valeur de la source.
            String source = this.obtenirValeurProprieteEnChaine(this.noeudDossier, DossierinfosAspectModele.SOURCE);

            // Récupération et transformation de la valeur.
            if(source == null || source.trim().isEmpty()) return "manuel";
            else return source.toLowerCase();

        }catch (NoeudNullException e) {
            throw new NoeudNullException("Le noeud du dossier d'acte ne peut être null.");
        }
    }

    /**
     * Permet de récupérer l'identifiant d'un dossier.
     * @return La valeur de l'identifiant du dossier.
     * @throws ActionMetierException Si le nœud ou la propriété est null ou vide.
     */
    private String obtenirIdDossier() throws ActionMetierException {
        if(!this.dossierModele.estDeSourceManuelle())
            return this.obtenirValeurProprieteEnChaine(this.noeudDossier, DossierinfosAspectModele.IDDOSSIER);

        return String.format("%s_%s", this.dossierModele.type.substring(0,3)
                .replaceAll("[éèêë]", "e").toUpperCase(), this.dossierModele.numero);
    }

    /**
     * Vérifie les préconditions sur le fichier d'acte.
     * @throws ActionMetierException Si le nœud ou la requete est null.
     */
    private void verifierPreconditionFichierActe() throws ActionMetierException {
        // Vérification qu'il y a UN fichier d'acte dans le dossier.
        List<NodeRef> acte = this.rechercherNoeuds(this.noeudDossier,
                String.format(
                    "select * from actes59:docinfos " +
                    "where actes59:typedocument = 'ACTE_ORIGINAL' and IN_TREE('%s')", this.noeudDossier.getId()
                )
        );

        // Il ne peut y avoir aucun fichier d'acte.
        if(acte.size() == 0)
            throw new PreconditionException("Le dossier ne contient aucun fichier de type acte " +
                    "(d'aspect 'actes59:docinfos' et de 'actes59:typedocument' à 'ACTE_ORIGINAL').");

        // Il ne peut y avoir plus d'UN fichier d'acte.
        else if(acte.size() > 1)
            throw new PreconditionException(
                    String.format("Le dossier ne contient %d fichiers de type acte (d'aspect 'actes59:docinfos' " +
                            "et de 'actes59:typedocument' à 'ACTE_ORIGINAL').", acte.size())
            );

        // Initialisation du modele.
        this.noeudFichier = acte.get(0);
    }

    /**
     * Vérifie le contenu du dossier d'acte.
     * @throws ActionMetierException Si le nœud ou la requete est null.
     */
    private void verifierPreconditionContenuDossier() throws ActionMetierException {
        // Récupération de toutes les annexes.
        this.noeudsAnnexes = this.rechercherNoeuds(this.noeudDossier,
                String.format(
                    "select * from actes59:docinfos " +
                    "where actes59:typedocument = 'ANNEXE' " +
                    "and IN_TREE('%s')", this.noeudDossier.getId()
                )
        );

        // Récupération des nœuds non typés.
        List<NodeRef> noeudsNonTypes = this.rechercherNoeuds(this.noeudDossier,
                String.format(
                    "select * from actes59:docinfos " +
                    "where actes59:typedocument <> 'ANNEXE' " +
                    "and actes59:typedocument <> 'ACTE_ORIGINAL'" +
                    "and IN_TREE('%s')", this.noeudDossier.getId()
                )
        );

        if(noeudsNonTypes.size() > 0)
            throw new PreconditionException(String.format("Le dossier comporte %d fichiers non typés (qui ne sont ni " +
                    "des 'ANNEXE' ou 'ACTE_ORIGINAL')", noeudsNonTypes.size()));
    }

    /**
     * Récupère le signataire.
     * @return Le signataire le l'acte.
     * @throws ActionMetierException Si le nœud ou la propriété est null ou vide.
     */
    private Date obtenirDateDossier() throws ActionMetierException {
        try {
            return this.obtenirValeurProprieteEnDate(this.noeudDossier, DossierinfosAspectModele.DATEDOSSIER);
        }catch (NoeudNullException e) {
            throw new NoeudNullException("Le noeud du dossier d'acte ne peut pas être null.");
        }

    }

    /**
     * Récupère le résumé de l'acte.
     * @return le résumé de l'acte.
     * @throws ActionMetierException Si le nœud ou la propriété est null ou vide.
     */
    private String obtenirResume() throws ActionMetierException {
        try {
            return this.obtenirValeurProprieteEnChaine(this.noeudDossier, DossierinfosAspectModele.RESUME);
        }catch (NoeudNullException e) {
            throw new NoeudNullException("Le noeud du dossier d'acte ne peut pas être null.");
        }

    }

    /**
     * Récupère le signataire.
     * @return Le signataire le l'acte.
     * @throws ActionMetierException Si le nœud ou la propriété est null ou vide.
     */
    private String obtenirSignataire() throws ActionMetierException {
        try {
            return this.obtenirValeurProprieteEnChaine(this.noeudDossier, DossierinfosAspectModele.SIGNATAIRE);
        }catch (NoeudNullException e) {
            throw new NoeudNullException("Le noeud du dossier d'acte ne peut pas être null.");
        }

    }

    /**
     * Récupère la valeur de la propriété 'actes59:dossiercomplet' de l'aspect 'actes59:dossierinfos'.
     * @return La valeur de la propriété 'actes59:dossiercomplet' de l'aspect 'actes59:dossierinfos'.
     * @throws ActionMetierException Si le nœud ou la propriété est null ou vide.
     */
    private boolean obtenirDossierComplet() throws ActionMetierException {
        try {
            return this.obtenirValeurProprieteEnBooleen(this.noeudDossier, DossierinfosAspectModele.DOSSIERCOMPLET);
        }catch (NoeudNullException e) {
            throw new NoeudNullException("Le noeud du dossier d'acte ne peut pas être null.");
        }
    }

    /**
     * Récupère l'objet.
     * @return L'objet de l'acte.
     * @throws ActionMetierException Si le nœud ou la propriété est null ou vide.
     */
    private String obtenirObjet() throws ActionMetierException {
        try {
            return this.obtenirValeurProprieteEnChaine(this.noeudDossier, DossierinfosAspectModele.OBJET);
        }catch (NoeudNullException e) {
            throw new NoeudNullException("Le noeud du dossier d'acte ne peut pas être null.");
        }
    }

    /**
     * Récupère le sigle de l'organisation de l'acte.
     * @return Le signe de l'organisation de l'acte.
     * @throws ActionMetierException Si le nœud ou la propriété est null ou vide.
     */
    private String obtenirSigleOrganisation() throws ActionMetierException {
        try {
            return this.obtenirValeurProprieteEnChaine(this.noeudDossier, DossierinfosAspectModele.ORGASIGLE);
        }catch (NoeudNullException e) {
            throw new NoeudNullException("Le noeud du dossier d'acte ne peut pas être null.");
        }
    }

    /**
     * Récupère le type de l'acte.
     * @return Le type de l'acte.
     * @throws ActionMetierException Si le nœud ou la propriété est null ou vide.
     */
    private String obtenirType() throws ActionMetierException {
        try {
            return this.obtenirValeurProprieteEnChaine(this.noeudDossier, DossierinfosAspectModele.TYPEDOSSIER);
        }catch (NoeudNullException e) {
            throw new NoeudNullException("Le noeud du dossier d'acte ne peut pas être null.");
        }
    }

    /**
     * Récupère le numéro de l'acte.
     * @return Le numéro de l'acte.
     * @throws ActionMetierException Si le nœud ou la propriété est null ou vide.
     */
    private String obtenirNumeroActe() throws ActionMetierException {
        try {

            // Récupération du numéro
            String numero = this.obtenirValeurProprieteEnChaine(this.noeudDossier, DossierinfosAspectModele.NUMEROACTE);

            // Vérification si le numéro est manuel ou null.
            if(numero == null || numero.trim().isEmpty() || this.dossierModele.estDeSourceManuelle()) {
                numero = String.format("%s_%d_%s", this.dossierModele.sigleOrganisation, this.dossierModele.annee,
                        this.genererNumeroActe());
            }else{

                // Formatage du numéro d'acte (suppression des caractères interdits).
                numero = numero.replaceAll("[\\*<>/\\?:\\|]", "_").trim();

                // Formatage du numéro d'acte (suppression des caractères interdits) : Retrait du '.' en de fin.
                if (numero.endsWith(".")) numero = numero.substring(0, numero.length() - 1);
            }

            return numero;

        }catch (NoeudNullException e) {
            throw new NoeudNullException("Le noeud du dossier d'acte ne peut pas être null.");
        }
    }

    /**
     * Génère un numéro d'acte.
     * @return Un numéro d'acte.
     * @throws ActionMetierException Si l'aspect ou le nœud sont null.
     */
    private String genererNumeroActe() throws ActionMetierException {
        if(!this.aType(this.noeudSas, SasTypeModele.NOM))
            throw new PreconditionException("Le dossier d'acte n'est pas dans un dossier de type SAS (actes59:sas).");

        // Synchronisation sur une mutex.
        String resultat;
        synchronized (MutexAction.MUTEX_COMPTEUR_SAS) {
            // Récupération de la valeur.
            int valeur = this.obtenirValeurProprieteEnEntier(this.noeudSas, SasTypeModele.COMPTEUR);
            // Mise du compteur sur 4 lettres.
            resultat = this.entierSurNChiffres(valeur, 4);
            // Incrémentation de la propriété.
            if((valeur+1) == 9999) valeur = -1;
            this.modifierPropriete(this.noeudSas, SasTypeModele.COMPTEUR, (valeur+1));
        }

        // Retour du résultat.
        return String.format("M%s", resultat);
    }

}

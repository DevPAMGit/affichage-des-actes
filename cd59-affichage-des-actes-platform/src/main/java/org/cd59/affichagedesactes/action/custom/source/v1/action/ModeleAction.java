package org.cd59.affichagedesactes.action.custom.source.v1.action;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.NoeudNullException;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.QNameNullException;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.RequeteNullException;
import org.cd59.affichagedesactes.action.custom.source.v1.logger.LoggerAction;
import org.alfresco.service.ServiceRegistry;
// import org.cd59.affichagedesactes.action.custom.source.v1.loggeraction.IActionAnnulation;
import org.cd59.affichagedesactes.action.custom.source.v1.loggeraction.IActionAnnulationFactory;
import org.cd59.affichagedesactes.utilitaire.UtilitaireChaineDeCaracteres;

import java.io.*;
import java.util.*;

/**
 * Classe d'aide pour les actions personnalisées.
 */
public abstract class ModeleAction extends LoggerAction implements IModeleNoeudAction {
    /**
     * Le registre de services Alfresco.
     */
    protected ServiceRegistry serviceRegistry;

    /**
     * Le service de noeud Alfresco.
     */
    private NodeService nodeService;

    /**
     * Le service de fichiers et dossiers.
     */
    private FileFolderService fileFolderService;

    /**
     * Initialise une nouvelle instance de la classe {@link ModeleAction}.
     * @param serviceRegistry La nouvelle valeur du paramètre 'registryService'.
     */
    public ModeleAction(ServiceRegistry serviceRegistry) {
        this.setRegistryService(serviceRegistry);
    }

    /**
     * Modifie la valeur du paramètre de la classe 'registryService'.
     * @param serviceRegistry La nouvelle valeur du paramètre 'registryService'.
     */
    public void setRegistryService(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        this.nodeService = this.serviceRegistry.getNodeService();
        this.fileFolderService = this.serviceRegistry.getFileFolderService();
    }

    @Override
    public List<NodeRef> requeterNoeuds(NodeRef nodeRef, String requete) throws PreRequisException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException();
        if(requete == null || requete.isEmpty()) throw new RequeteNullException();

        // Lancement de la requête.
        ResultSet resultat = this.serviceRegistry.getSearchService().query(
                nodeRef.getStoreRef(), SearchService.LANGUAGE_CMIS_STRICT, requete
        );

        // Gestion et retour du résultat.
        if(resultat == null || resultat.length() == 0) return new ArrayList<>();
        return resultat.getNodeRefs();
    }

    @Override
    public void setType(NodeRef nodeRef, QName qName) throws PreRequisException, NoSuchMethodException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException();
        if(qName == null) throw new QNameNullException();


        // Récupération du type actuel.
        QName type = this.nodeService.getType(nodeRef);

        // Modification du type.
        this.nodeService.setType(nodeRef, qName);

        // Ajout de la méthode d'annulation.
        this.ajouterAnnulation(
                IActionAnnulationFactory.creerActionAnnulation(
                        this.nodeService,
                        this.serviceRegistry.getNodeService().getClass().getMethod("setType", NodeRef.class, QName.class),
                        nodeRef, qName, type
                )
        );
    }

    @Override
    public void setPropriete(NodeRef nodeRef, QName propriete, Serializable valeur)
            throws PreRequisException, NoSuchMethodException {

        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException();
        if(propriete == null) throw new QNameNullException();

        // Récupération de l'ancienne valeur.
        Serializable ancienneValeur = this.nodeService.getProperty(nodeRef, propriete);

        // Modification de la propriété.
        this.nodeService.setProperty(nodeRef, propriete, valeur);

        // Ajout de l'action d'annulation.
        this.ajouterAnnulation(IActionAnnulationFactory.creerActionAnnulation(
                this.nodeService,
                this.nodeService.getClass().getMethod("setProperty", NodeRef.class, QName.class, Serializable.class),
                nodeRef, propriete, ancienneValeur
        ));
    }

    @Override
    public void modifierProprietes(NodeRef nodeRef, HashMap<QName, Serializable> valeurs)
            throws PreRequisException, NoSuchMethodException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException();
        if(valeurs == null) return;

        // Récupération des précédentes valeurs.
        Map<QName, Serializable> proprietes = this.nodeService.getProperties(nodeRef);

        // Modification des valeurs des propriétés
        this.nodeService.setProperties(nodeRef, valeurs);

        // Ajout de l'action d'annulation.
        this.ajouterAnnulation(IActionAnnulationFactory.creerActionAnnulation(
                this.nodeService,
                this.nodeService.getClass().getMethod("setProperties", NodeRef.class, Map.class),
                nodeRef, proprietes
        ));
    }

    @Override
    public NodeRef creerFichier(NodeRef nodeRef, String nom) throws PreRequisException, NoSuchMethodException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException();
        // Vérification du nom de fichier
        if(UtilitaireChaineDeCaracteres.etreNullOuVide(nom))
            throw new PreRequisException("Le nom du fichier à créer ne peut être null ou vide.");

        // Création du fichier.
        NodeRef fichier = this.nodeService.createNode(nodeRef, ContentModel.ASSOC_CONTAINS,
                QName.createQName(nom), ContentModel.TYPE_CONTENT).getChildRef();

        // Ajout de l'action d'annulation.
        this.ajouterAnnulation(
                IActionAnnulationFactory.creerActionAnnulation(
                        this.nodeService, nodeService.getClass().getMethod("deleteNode", NodeRef.class),
                        fichier
                )
        );

        // Retour du fichier.
        return fichier;
    }

    @Override
    public NodeRef creerFichierType(NodeRef nodeRef, QName qName, String nom)
            throws PreRequisException, NoSuchMethodException {
        // Création du fichier.
        NodeRef fichier = this.creerFichier(nodeRef, nom);
        // Modification du type du fichier.
        this.setType(fichier, qName);
        // Retour du nœud du fichier.
        return fichier;
    }

    @Override
    public NodeRef creerDossier(NodeRef nodeRef, String nom) throws PreRequisException, NoSuchMethodException {
        // 1. Vérification du nœud de destination.
        if(nodeRef == null) throw new NoeudNullException();
        // 2. Vérification de la chaîne de caractères.
        if(UtilitaireChaineDeCaracteres.etreNullOuVide(nom))
            throw new PreRequisException("Le nom du dossier à créer ne peut être null ou vide.");

        // Création du dossier.
        NodeRef noeudDossier = this.nodeService.createNode(nodeRef, ContentModel.ASSOC_CONTAINS,
                QName.createQName(nom), ContentModel.TYPE_FOLDER).getChildRef();

        // Ajout de l'action d'annulation.
        this.ajouterAnnulation(
                IActionAnnulationFactory.creerActionAnnulation(
                        nodeService,
                        nodeService.getClass().getMethod("deleteNode", NodeRef.class),
                        noeudDossier
                )
        );

        // Retour du résultat.
        return noeudDossier;
    }

    @Override
    public NodeRef creerDossierType(NodeRef nodeRef, QName qName, String nom)
            throws PreRequisException, NoSuchMethodException {
        // Création du dossier.
        NodeRef resultat = this.creerDossier(nodeRef, nom);
        // Modification du type du dossier.
        this.setType(resultat, qName);
        // retour du resultat.
        return resultat;
    }

    @Override
    public NodeRef creerDossierType(NodeRef nodeRef, QName qName, String nom, HashMap<QName, Serializable> metadonnees)
            throws PreRequisException, NoSuchMethodException {
        // Création du dossier.
        NodeRef resultat = this.creerDossierType(nodeRef, qName, nom);

        // Modification des propriétés du nouveau dossier.
        this.modifierProprietes(resultat, metadonnees);

        // Retour du résultat.
        return resultat;
    }

    @Override
    public void supprimerNoeud(NodeRef nodeRef) /*throws NoSuchMethodException, PreRequisException*/ {
        if(nodeRef == null) return;

        /*
        Set<QName> aspects = this.nodeService.getAspects(nodeRef);
        QName type = this.nodeService.getType(nodeRef);

        NodeRef noeudParent = this.getNoeudParent(nodeRef);
        String nom = (String)this.nodeService.getProperty(nodeRef, ContentModel.PROP_NAME);
        Map<QName, Serializable> proprietes = this.nodeService.getProperties(nodeRef);
        proprietes.put(ContentModel.PROP_NAME, nom);
        proprietes.put(ContentModel.PROP_TITLE, this.nodeService.getProperty(nodeRef, ContentModel.PROP_TITLE));
        proprietes.put(ContentModel.PROP_DESCRIPTION, this.nodeService.getProperty(nodeRef, ContentModel.PROP_DESCRIPTION));

        IActionAnnulation creation;

        if(this.fileFolderService.getFileInfo(nodeRef).isFolder())
            creation = IActionAnnulationFactory.creerActionAnnulation(
                    this.nodeService,
                    this.nodeService.getClass().getMethod("createNode", NodeRef.class, QName.class, QName.class, QName.class),
                    noeudParent, ContentModel.ASSOC_CONTAINS, QName.createQName(nom), ContentModel.TYPE_FOLDER
            );

        else
            creation = IActionAnnulationFactory.creerActionAnnulation(
                    this.nodeService,
                    this.nodeService.getClass().getMethod("createNode", NodeRef.class, QName.class, QName.class, QName.class),
                    noeudParent, ContentModel.ASSOC_CONTAINS, QName.createQName(nom), ContentModel.TYPE_CONTENT
            );

        // Ajout de l'annulation : modification des propriétés.
        if(proprietes.size() > 0)
            this.ajouterAnnulation(
                    IActionAnnulationFactory.creerActionAnnulation(
                            creation, this.nodeService,
                            this.nodeService.getClass().getMethod("setProperties", NodeRef.class, Map.class),
                            proprietes
                    )
            );

        // Ajout de l'annulation : modification du fichier typé.
        if(type!= null)
            this.ajouterAnnulation(
                IActionAnnulationFactory.creerActionAnnulation(
                    creation, this.nodeService,
                    this.nodeService.getClass().getMethod("setType", NodeRef.class, QName.class), type)
            );

        if(aspects != null && aspects.size() > 0)
            for(QName aspect : aspects)
                this.ajouterAnnulation(
                    IActionAnnulationFactory.creerActionAnnulation(
                        creation, this.nodeService,
                        this.nodeService.getClass().getMethod("addAspect", NodeRef.class, QName.class, Map.class),
                        aspect, new HashMap<QName, Serializable>()
                    )
                );

        // Ajout de l'annulation : création du fichier typé.
        this.ajouterAnnulation(creation);
        */
        // Suppression du nœud.
        this.serviceRegistry.getFileFolderService().delete(nodeRef);
    }

    @Override
    public boolean avoirAspect(NodeRef nodeRef, QName aspect) throws PreRequisException {
        // Vérification des préconditions.
        if(aspect == null) throw new QNameNullException();
        if(nodeRef == null) throw new NoeudNullException();

        return this.serviceRegistry.getNodeService().hasAspect(nodeRef, aspect);
    }

    @Override
    public boolean avoirType(NodeRef nodeRef, QName qName) throws PreRequisException {
        // Vérification des préconditions.
        if(qName == null) throw new QNameNullException();
        if(nodeRef == null) throw new NoeudNullException();

        // Retour du resultat.
        return this.nodeService.getType(nodeRef).isMatch(qName);
    }

    @Override
    public String getProprieteChaine(NodeRef nodeRef, QName qName) throws PreRequisException {
        // Vérification si le nœud est null.
        if(nodeRef == null) throw new NoeudNullException();
        // Vérification si la propriété est null.
        if(qName == null) throw new QNameNullException();

        // Récupération de la valeur.
        Serializable valeur = this.serviceRegistry.getNodeService().getProperty(nodeRef, qName);
        if(valeur == null) return null;

        return (String) valeur;
    }

    @Override
    public int getProprieteEntier(NodeRef nodeRef, QName qName) throws PreRequisException {
        //Vérification que la propriété soit non null.
        if(nodeRef == null) throw new NoeudNullException();
        //Vérification que la propriété soit non null.
        if(qName == null) throw new QNameNullException();

        // Récupération de la valeur.
        Serializable valeur = this.serviceRegistry.getNodeService().getProperty(nodeRef, qName);
        if(valeur == null) return 0;

        return (int) valeur;
    }

    @Override
    public Date getProprieteDate(NodeRef nodeRef, QName qName) throws PreRequisException {
        // Vérification que le nœud soit non null.
        if(nodeRef == null) throw new NoeudNullException();
        // Vérification que la propriété soit non null.
        if(qName == null) throw new QNameNullException();

        // Récupération de la valeur.
        Serializable valeur = this.serviceRegistry.getNodeService().getProperty(nodeRef, qName);
        if(valeur == null) return null;

        return (Date) valeur;
    }

    @Override
    public boolean obtenirProprieteEnBooleen(NodeRef nodeRef, QName qName) throws PreRequisException {
        /// Vérification que le nœud soit non null.
        if(nodeRef == null) throw new NoeudNullException();
        // Vérification que la propriété soit non null.
        if(qName == null) throw new QNameNullException();

        // Récupération de la valeur.
        Serializable serializable = this.serviceRegistry.getNodeService().getProperty(nodeRef, qName);
        if(serializable == null) return false;

        return (boolean) serializable;
    }

    @Override
    public NodeRef getNoeudParent(NodeRef nodeRef) throws PreRequisException {
        // Vérification que le nœud ne soit pas null.
        if (nodeRef == null) throw new NoeudNullException();
        // Récupération du nœud parent.
        return this.serviceRegistry.getNodeService().getPrimaryParent(nodeRef).getParentRef();
    }

    @Override
    public byte[] getContenu(NodeRef nodeRef) throws IOException {
        // Vérification des préconditions.
        if(nodeRef == null) return null;

        // Vérification si c'est un fichier.
        if(this.fileFolderService.getFileInfo(nodeRef).isFolder()) return null;
        // Retour du contenu.
        return this.fileFolderService.getReader(nodeRef).getContentInputStream().readAllBytes();
    }

    @Override
    public void deplacer(NodeRef destination, NodeRef nodeRef) throws FileNotFoundException, PreRequisException, NoSuchMethodException {
        NodeRef parent = this.getNoeudParent(nodeRef);
        String nom = (String)this.nodeService.getProperty(nodeRef, ContentModel.PROP_NAME);

        this.fileFolderService.move(nodeRef, destination, nom);

        this.ajouterAnnulation(
                IActionAnnulationFactory.creerActionAnnulation(
                        this.fileFolderService,
                        this.fileFolderService.getClass().getMethod("move", NodeRef.class, NodeRef.class, String.class),
                        nodeRef, parent, nom
                )
        );
    }

    /**
     * Copie un fichier vers un nœud de destination.
     * @param destination               Le nœud de destination.
     * @param nodeRef                   le nœud à copier.
     * @param nom                       Le nom de la copie du fichier.
     * @throws PreRequisException       Si l'un des nœuds en paramètres est null ou vide ou bien si le nom de fichier de
     *                                  la copie est null ou vide.
     * @throws FileNotFoundException    Si le nœud à copier n'a pas été trouvé.
     * @throws  NoSuchMethodException   Levée lorsqu'une méthode particulière est introuvable.
     */
    public NodeRef copier(NodeRef destination, NodeRef nodeRef, String nom)
            throws PreRequisException, FileNotFoundException, NoSuchMethodException {
        if(destination == null) throw new NoeudNullException();
        if(nodeRef == null) throw new NoeudNullException();
        if(UtilitaireChaineDeCaracteres.etreNullOuVide(nom))
            throw new PreRequisException("Veuillez initialiser Le nom de la copie du fichier.");

        // Copie du fichier.
        NodeRef nodeRef1 = this.fileFolderService.copy(nodeRef, destination, nom).getNodeRef();

        // Ajout de l'action d'annulation.
        this.ajouterAnnulation(IActionAnnulationFactory.creerActionAnnulation(
                this.nodeService,
                this.nodeService.getClass().getMethod("deleteNode", NodeRef.class),
                nodeRef1
        ));

        // Retour du resultat.
        return nodeRef1;
    }

    /**
     * Execute l'action.
     * @throws Exception Si une exception quelconque est lancée.
     */
    public abstract void executer() throws Exception;
}

package org.cd59.affichagedesactes.v2.action.source;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.io.IOUtils;
import org.cd59.affichagedesactes.v2.action.source.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Source pour toutes les actions métiers
 */
public abstract class ActionMetier {

    /**
     * Le registre de service d'Alfresco.
     */
    public final ServiceRegistry registryService;

    /**
     * Initialise une nouvelle instance de la classe {@link ActionMetier}.
     * @param serviceRegistry Le registre de service Alfresco.
     */
    public ActionMetier(ServiceRegistry serviceRegistry) {
        this.registryService = serviceRegistry;
    }

    /**
     * Execute l'action métier.
     */
    public abstract void executer();

    /**
     * Le logger de la classe.
     */
    private final static Logger  LOGGER = LoggerFactory.getLogger(ActionMetier.class);

    /**
     * Permet de récupérer le nom du fichier.
     * @param nodeRef Le nœud dont on souhaite récupérer le nom.
     * @throws NomNoeudVideException Si le nom du nœud est vide ou null.
     */
    protected String obtenirNomNoeud(NodeRef nodeRef) throws NomNoeudVideException {
        NodeService nodeService = this.registryService.getNodeService();
        // Récupération de la propriété.
        Serializable valeur =  nodeService.getProperty(nodeRef, ContentModel.PROP_NAME);
        // Vérification que le nom du nœud est valide.
        if(valeur == null || ((String) valeur).isEmpty())
            throw new NomNoeudVideException("Le nom du noeud est null ou vide.");
        // Retour du résultat.
        return String.format("%s%s", valeur, ".pdf");
    }

    /**
     * Permet de récupérer le contenu d'un nœud de type fichier.
     * @param nodeRef Le nœud de type fichier.
     * @return Le contenu du fichier.
     * @throws IOException Si une erreur d'E/S est lancée durant la lecture du fichier.
     * @throws NoeudNullException Si le nœud est null ou vide.
     */
    protected byte[] obtenirContenuFichier(NodeRef nodeRef) throws IOException, NoeudNullException {
        // Vérification du nœud.
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null.");

        ByteArrayOutputStream resultat = new ByteArrayOutputStream();
        // Récupération du contenu.
        ContentReader contentReader = this.registryService.getContentService().getReader(nodeRef,
                ContentModel.PROP_CONTENT
        );

        return IOUtils.toByteArray(new BufferedInputStream(contentReader.getContentInputStream()));
    }

    /**
     * Récupère la valeur d'une propriété d'un nœud en chaîne de caractères.
     * @param nodeRef Le nœud dont on souhaite récupérer la valeur de la propriété.
     * @param propriete La propriété que l'on souhaite récupérer.
     * @return La valeur de la propriété ou null.
     * @throws ActionMetierException Si le nœud ou la propriété est null ou vide.
     */
    protected String obtenirValeurProprieteEnChaine(NodeRef nodeRef, QName propriete) throws ActionMetierException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null ou vide.");
        if(propriete == null) throw new QNameNullException("La propriété en paramètre ne peut être null ou vide.");

        // Récupération de la valeur.
        Serializable serializable = this.registryService.getNodeService().getProperty(nodeRef, propriete);
        if(serializable == null) return null;
        return (String) serializable;
    }

    /**
     * Récupère la valeur d'une propriété d'un nœud en entier.
     * @param nodeRef Le nœud dont on souhaite récupérer la valeur de la propriété.
     * @param propriete La propriété que l'on souhaite récupérer.
     * @return La valeur de la propriété ou 0.
     * @throws ActionMetierException Si le nœud ou la propriété est null ou vide.
     */
    protected int obtenirValeurProprieteEnEntier(NodeRef nodeRef, QName propriete) throws ActionMetierException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null ou vide.");
        if(propriete == null) throw new QNameNullException("La propriété en paramètre ne peut être null ou vide.");

        // Récupération de la valeur.
        Serializable serializable = this.registryService.getNodeService().getProperty(nodeRef, propriete);
        if(serializable == null) return 0;
        return (int) serializable;
    }

    /**
     * Récupère la valeur d'une propriété d'un nœud en booléen.
     * @param nodeRef Le nœud dont on souhaite récupérer la valeur de la propriété.
     * @param propriete La propriété que l'on souhaite récupérer.
     * @return La valeur de la propriété ou null.
     * @throws ActionMetierException Si le nœud ou la propriété est null ou vide.
     */
    protected boolean obtenirValeurProprieteEnBooleen(NodeRef nodeRef, QName propriete) throws ActionMetierException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null ou vide.");
        if(propriete == null) throw new QNameNullException("La propriété en paramètre ne peut être null ou vide.");

        // Récupération de la valeur.
        Serializable serializable = this.registryService.getNodeService().getProperty(nodeRef, propriete);
        if(serializable == null) return false;
        return (boolean) serializable;
    }

    /**
     * Récupère la valeur d'une propriété d'un nœud en chaîne de caractères.
     * @param nodeRef Le nœud dont on souhaite récupérer la valeur de la propriété.
     * @param propriete La propriété que l'on souhaite récupérer.
     * @return La valeur de la propriété ou null.
     * @throws ActionMetierException Si le nœud ou la propriété en paramètre est null ou vide.
     */
    protected Date obtenirValeurProprieteEnDate(NodeRef nodeRef, QName propriete) throws ActionMetierException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null ou vide.");
        if(propriete == null) throw new QNameNullException("La propriété en paramètre ne peut être null ou vide.");

        // Récupération de la valeur.
        Serializable serializable = this.registryService.getNodeService().getProperty(nodeRef, propriete);
        if(serializable == null) return null;
        return (Date) serializable;
    }

    /**
     * Modifie la valeur d'une propriété d'un nœud.
     * @param nodeRef Le nœud dont on souhaite modifier la valeur de la propriété.
     * @param propriete La propriété a modifié.
     * @param valeur La nouvelle valeur de la propriété.
     * @throws ActionMetierException Si le nœud ou la propriété en paramètre est null ou vide.
     */
    protected void modifierPropriete(NodeRef nodeRef, QName propriete, Serializable valeur)
            throws ActionMetierException {
        if(nodeRef == null)
            throw new NoeudNullException(
                    "Le noeud en paramètre pour modification de propriété ne peut être null ou vide.");
        if(propriete == null) throw new QNameNullException("La propriété à modifier ne peut être null ou vide.");

        this.registryService.getNodeService().setProperty(nodeRef, propriete, valeur);
    }

    /**
     *
     */
    protected void modifierProprietes(NodeRef nodeRef, HashMap<QName, Serializable> proprietes) throws ActionMetierException {
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null ou vide.");
        if(proprietes == null)
            throw new QNameNullException("La liste des propriétés en paramètre ne peuvent être null ou vide.");

        this.registryService.getNodeService().setProperties(nodeRef, proprietes);
    }


    protected void miseAJourAspect(NodeRef nodeRef, QName aspect, HashMap<QName, Serializable> proprietes)
            throws ActionMetierException {

        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null.");
        if(proprietes == null)
            throw new QNameNullException("La liste des propriétés en paramètre ne peuvent être null ou vide.");
        if(aspect == null) throw new QNameNullException("L'aspect ne peut être null.");

        NodeService nodeService = this.registryService.getNodeService();

        // Mise à jour des propriétés si l'aspect est déjà présent sur le nœud.
        if(nodeService.hasAspect(nodeRef, aspect)) {
            nodeService.setProperties(nodeRef, proprietes);
            LOGGER.info(String.format("L'aspect '%s' existe déjà : mise à jour.", aspect.getLocalName()));
        }
        // Création de l'aspect et initialisation des propriétés.
        else nodeService.addAspect(nodeRef, aspect, proprietes);
    }

    /**
     * Supprime un aspect d'un noeud.
     * @param nodeRef Le nœud dont on souhaite supprimer l'aspect.
     * @param aspect L'aspect à supprimer.
     * @throws ActionMetierException Si l'aspect ou le nœud sont null.
     */
    protected void supprimerAspect(NodeRef nodeRef, QName aspect) throws ActionMetierException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null.");
        if(aspect == null) throw new QNameNullException("L'aspect ne peut être null.");

        NodeService nodeService = this.registryService.getNodeService();
        if(nodeService.hasAspect(nodeRef, aspect))
            nodeService.removeAspect(nodeRef, aspect);

    }

    protected boolean aType(NodeRef nodeRef, QName type) throws ActionMetierException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null.");
        if(type == null) throw new QNameNullException("Le type ne peut être null.");

        return this.registryService.getNodeService().getType(nodeRef).isMatch(type);
    }

    /**
     * Indique si le nœud à un aspect.
     * @param nodeRef Le nœud que l'on souhaite vérifier.
     * @param aspect L'aspect à vérifier.
     * @throws ActionMetierException Si l'aspect ou le nœud sont null.
     */
    protected boolean aAspect(NodeRef nodeRef, QName aspect) throws ActionMetierException {
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null.");
        if(aspect == null) throw new QNameNullException("L'aspect ne peut être null.");

        return this.registryService.getNodeService().hasAspect(nodeRef, aspect);
    }

    /**
     * Recherche des nœuds dans un nœud.
     * @param nodeRef Le nœud dans lequel chercher.
     * @param requete La requête cmis à executer.
     * @return La liste de nœuds resultant de la recherche.
     * @throws ActionMetierException Si le nœud ou la requete est null.
     */
    protected List<NodeRef> rechercherNoeuds(NodeRef nodeRef, String requete) throws ActionMetierException {
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null.");
        if(requete == null || requete.isEmpty()) throw new RequeteRechercheNullException();

        ResultSet resultat = this.registryService.getSearchService().query(
                 nodeRef.getStoreRef(), SearchService.LANGUAGE_CMIS_STRICT, requete
        );

         if(resultat == null || resultat.length() == 0) return new ArrayList<>();
         return resultat.getNodeRefs();
    }

    /**
     * Recherche des nœuds dans un nœud.
     * @param nodeRef Le nœud dans lequel chercher.
     * @param nom Le nom de l'élément à trouver.
     * @return Le nœud resultant de la recherche.
     * @throws ActionMetierException Si le nœud ou la requete est null.
     */
    protected NodeRef rechercherSimple(NodeRef nodeRef, String nom) throws ActionMetierException {
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null.");
        if(nom == null || nom.isEmpty()) throw new RequeteRechercheNullException();

        return this.registryService.getFileFolderService().searchSimple(nodeRef, nom);
    }

    /**
     * Récupère le nœud parent de celui en paramètre.
     * @param nodeRef Le nœud dont on souhaite récupérer le parent.
     * @throws NoeudNullException Si le nœud en paramètre est null.
     */
    public NodeRef obtenirNoeudParent(NodeRef nodeRef) throws NoeudNullException {
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null.");
        return this.registryService.getNodeService().getPrimaryParent(nodeRef).getParentRef();
    }

    /**
     * Récupère un ancêtre de du nœud en paramètre.
     * @param nodeRef Le nœud dont on souhaite récupérer l'ancêtre.
     * @param generation La génération antérieure à laquelle on souhaite accéder.
     * @throws NoeudNullException Si le nœud en paramètre est null.
     */
    public NodeRef obtenirAncetre(NodeRef nodeRef, int generation) throws NoeudNullException {
        NodeRef ancetre = nodeRef;
        for(int i=0; i<generation; i++)
            ancetre = this.obtenirNoeudParent(ancetre);
        return ancetre;
    }

    /**
     * Convertit un entier en chaîne de caractère et ajoute des zéros avant le nombre si besoin est.
     * @param entier L'entier à convertir.
     * @param taille La taille de la chaîne de caractères demandée.
     * @return Une chaine de caractères de la taille demandée.
     */
    protected String entierSurNChiffres(int entier, int taille) {
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
     * Crée un dossier dans le nœud en paramètre.
     * @param nodeRef Le nœud dans lequel créer le nœud.
     * @param nom Le nom du nouveau à créer.
     * @return Le nouveau nœud créer.
     */
    protected NodeRef creerDossier(NodeRef nodeRef, String nom)
            throws ActionMetierException {

        if(nodeRef == null) throw new NoeudNullException("Le noeud père du nouveau ne peut pas être null.");

        if(nom == null || nom.isEmpty())
            throw new ActionMetierException("Le nom du dossier à créer ne peut pas être null.");

        return this.registryService.getNodeService().createNode(nodeRef, ContentModel.ASSOC_CONTAINS,
                QName.createQName(nom), ContentModel.TYPE_FOLDER).getChildRef();
    }

    /**
     * Modifie le type du dossier.
     * @param nodeRef Le nœud du dossier à créer.
     * @param type Le type du nœud.
     * @throws ActionMetierException Si le type ou le nœud sont null.
     */
    protected void modifierType(NodeRef nodeRef, QName type) throws ActionMetierException {
        if(nodeRef == null) throw new NoeudNullException("Le noeud ne peut être null.");
        if(type == null) throw new QNameNullException("Le type ne peut être null.");

        this.registryService.getNodeService().setType(nodeRef, type);
    }

    protected String obtenirEmpreinte(NodeRef nodeRef) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return Base64.getEncoder().encodeToString(
                digest.digest(this.registryService.getFileFolderService().getReader(nodeRef).getContentInputStream().readAllBytes())
        );
    }

    /**
     * Permet de supprimer une nœud.
     * @param nodeRef Le nœud que l'on souhaite supprimer.
     */
    protected void supprimerNoeud(NodeRef nodeRef) {
        if(nodeRef == null) return;
        this.registryService.getNodeService().deleteNode(nodeRef);
    }

}

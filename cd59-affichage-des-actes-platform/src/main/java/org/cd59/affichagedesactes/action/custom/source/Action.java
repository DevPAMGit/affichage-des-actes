package org.cd59.affichagedesactes.action.custom.source;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.NoeudNullException;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.QNameNullException;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.RequeteNullException;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;
import org.cd59.affichagedesactes.utilitaire.UtilitaireChaineDeCaracteres;

import java.io.Serializable;
import java.util.*;

public abstract class Action {

    /**
     * Le registre des services d'Alfresco.
     */
    protected final ServiceRegistry serviceRegistry;

    /**
     * Initialise une nouvelle instance de la classe Action.
     * @param serviceRegistry Le registre de service d'Alfresco.
     */
    protected Action(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public abstract void executer() throws Exception;

    /**
     * Indique si le nœud à un aspect.
     * @param aspect L'aspect à vérifier.
     * @throws PreRequisException Si l'aspect est null.
     */
    protected boolean avoirAspect(NodeRef nodeRef, QName aspect) throws PreRequisException {
        // Vérification des oré
        if(aspect == null) throw new QNameNullException();
        if(nodeRef == null) throw new NoeudNullException();

        return this.serviceRegistry.getNodeService().hasAspect(nodeRef, aspect);
    }

    /**
     * Retourne les propriétés du nœud en paramètre.
     * @param nodeRef Le nœud dont il faut récupérer les propriétés.
     * @return Les propriétés du nœud en paramètre.
     * @throws PreRequisException Si les prérequis de la méthode ne sont pas respecté.
     */
    public Map<QName, Serializable> getProprietes(NodeRef nodeRef) throws PreRequisException {
        // Test des prérequis de la méthode.
        if(nodeRef == null) throw new NoeudNullException();
        // retour du résultat.
        return this.serviceRegistry.getNodeService().getProperties(nodeRef);
    }

    /**
     * Récupère le nœud parent de celui en paramètre.
     * @param nodeRef Le nœud dont on souhaite récupérer le parent.
     * @throws PreRequisException Si le nœud en paramètre est null.
     */
    protected NodeRef getNoeudParent(NodeRef nodeRef) throws PreRequisException {
        if (nodeRef == null) throw new NoeudNullException();
        return this.serviceRegistry.getNodeService().getPrimaryParent(nodeRef).getParentRef();
    }

    /**
     * Recherche des nœuds à l'aide d'une requête.
     * @param nodeRef Le nœud dans lequel on lance la recherche.
     * @param requete La requête.
     * @return Une liste de nœuds répondant aux critères de la recherche.
     * @throws PreRequisException Si le nœud ou la requête sont null.
     */
    protected List<NodeRef> requeterNoeuds(NodeRef nodeRef, String requete) throws PreRequisException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException();
        if(requete == null || requete.isEmpty()) throw new RequeteNullException();

        // System.err.println(String.format("========================================================================\n%s\n========================================================================", requete));

        // Lancement de la requête.
        ResultSet resultat = this.serviceRegistry.getSearchService().query(
                nodeRef.getStoreRef(), SearchService.LANGUAGE_CMIS_STRICT, requete
        );

        // Gestion et retour du résultat.
        if(resultat == null || resultat.length() == 0) return new ArrayList<>();
        return resultat.getNodeRefs();
    }

    protected void setType(NodeRef nodeRef, QName qName) throws PreRequisException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException();
        if(qName == null) throw new QNameNullException();

        this.serviceRegistry.getNodeService().setType(nodeRef, qName);
    }

    protected boolean getProprieteEnBooleen(NodeRef nodeRef, QName propriete) throws PreRequisException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException();
        if(propriete == null) throw new QNameNullException();

        // Récupération de la valeur.
        Serializable serializable = this.serviceRegistry.getNodeService().getProperty(nodeRef, propriete);
        if(serializable == null) return false;
        return (boolean) serializable;
    }

    protected void modifierProprietes(NodeRef nodeRef, HashMap<QName, Serializable> valeurs) throws PreRequisException {
        if(nodeRef == null) throw new NoeudNullException();
        if(valeurs == null) return;

        this.serviceRegistry.getNodeService().setProperties(nodeRef, valeurs);
    }

    /**
     * Crée un dossier dans le nœud en paramètre.
     * @param nodeRef Le nœud dans lequel créer le nœud.
     * @param nom Le nom du nouveau à créer.
     * @return Le nouveau nœud créer.
     * @throws PreRequisException PreRequisException Si le nœud ou le nom du dossier sont null.
     */
    protected NodeRef creerDossier(NodeRef nodeRef, String nom) throws PreRequisException {
        // Vérification des préconditions.
        // 1. Vérification du nœud de destination.
        if(nodeRef == null)
            throw new NoeudNullException();

        // 2. Vérification de la chaîne de caractères.
        if(UtilitaireChaineDeCaracteres.etreNullOuVide(nom))
            throw new PreRequisException("Le nom du dossier à créer ne peut être null ou vide.");

        // Création du dossier.
        return this.serviceRegistry.getNodeService().createNode(nodeRef, ContentModel.ASSOC_CONTAINS,
                QName.createQName(nom), ContentModel.TYPE_FOLDER).getChildRef();
    }

    /**
     * Crée un dossier et le type.
     * @param nodeRef Le nœud de destination.
     * @param qName Le type du nouveau dossier.
     * @param nom Le nom du nouveau dossier.
     * @return Le {@link NodeRef} du nouveau dossier.
     * @throws PreRequisException Si l'un des paramètres est null ou vide.
     */
    protected NodeRef creerDossierType(NodeRef nodeRef, QName qName, String nom) throws PreRequisException {
        NodeRef resultat = this.creerDossier(nodeRef, nom);
        this.setType(resultat, qName);
        return resultat;
    }

    /**
     * Crée un dossier, le type et initialise les propriétés.
     * @param nodeRef Le nœud de destination.
     * @param qName Le type du nouveau dossier.
     * @param nom Le nom du nouveau dossier.
     * @param metadonnees Les propriétés du nouveau dossier.
     * @return Le {@link NodeRef} du nouveau dossier.
     * @throws PreRequisException Si l'un des paramètres est null ou vide.
     */
    protected NodeRef creerDossierType(
            NodeRef nodeRef, QName qName, String nom, HashMap<QName, Serializable> metadonnees
    ) throws PreRequisException {
        // Création du dossier.
        NodeRef resultat = this.creerDossierType(nodeRef, qName, nom);
        // Modification des propriétés du nouveau dossier.
        this.modifierProprietes(resultat, metadonnees);
        // Retour du résultat.
        return resultat;
    }

    /**
     * Modifie la valeur d'une propriété d'un nœud.
     * @param nodeRef Le nœud dont il faut modifier la propriété
     * @param propriete La propriété a modifié.
     * @param valeur La nouvelle valeur de la propriété.
     * @throws PreRequisException Si le nœud ou la propriété en paramètre est null ou vide.
     */
    protected void setPropriete(NodeRef nodeRef, QName propriete, Serializable valeur) throws PreRequisException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException();
        if(propriete == null) throw new QNameNullException();
        // Retour du résultat.
        this.serviceRegistry.getNodeService().setProperty(nodeRef, propriete, valeur);
    }
}

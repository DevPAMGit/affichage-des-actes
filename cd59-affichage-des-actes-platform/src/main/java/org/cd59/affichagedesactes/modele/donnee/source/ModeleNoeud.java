package org.cd59.affichagedesactes.modele.donnee.source;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.action.custom.source.v1.action.IModeleNoeudAction;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;
import org.cd59.affichagedesactes.modele.donnee.exception.NoeudNullException;
import org.cd59.affichagedesactes.modele.donnee.exception.RegistreServiceNullException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * Classe personnalisée pour une modèle de nœud.
 */
public class ModeleNoeud {

    /**
     * Le nœud en modèle.
     */
    protected final NodeRef nodeRef;

    /**
     * Le service de registre du nœud.
     */
    // protected final ServiceRegistry serviceRegistry;
    protected IModeleNoeudAction modeleNoeudAction;

    /**
     * Initialise une nouvelle instance de la classe {@link ModeleNoeud}.
     // @param serviceRegistry Le service de registre du nœud.
     * @param nodeRef Le nœud source.
     * @throws ModeleException Si le registre de services ou le nœud sont null.
     */
    public ModeleNoeud(/*ServiceRegistry serviceRegistry*/ IModeleNoeudAction modeleNoeudAction, NodeRef nodeRef) throws ModeleException {
        // Vérification des préconditions.
        // 1. Vérification du registre de services.
        if(modeleNoeudAction == null)
            throw new RegistreServiceNullException(this.getClass().getSimpleName());

        // 2. Vérification du nœud de référence.
        if(nodeRef == null)
            throw new NoeudNullException(this.getClass().getSimpleName());

        // Initialisation des propriétés.
        // this.serviceRegistry = serviceRegistry;
        this.modeleNoeudAction = modeleNoeudAction;
        this.nodeRef = nodeRef;
    }

    /**
     * Méthode permettant de récupérer la valeur d'une propriété en chaîne de caractères.
     * @param qName La propriété dont il faut récupérer la valeur.
     * @return La valeur de la propriété.
     * @throws PreRequisException   Si le nœud ou la propriété est null.
     */
    protected String getProprieteChaine(QName qName) throws PreRequisException {
        return this.modeleNoeudAction.getProprieteChaine(this.nodeRef, qName);
    }

    /**
     * Méthode permettant de récupérer la valeur d'une propriété en entier.
     * @param qName La propriété dont il faut récupérer la valeur.
     * @return La valeur de la propriété.
     * @throws PreRequisException   Si le nœud ou la propriété est null.
     */
    public int getProprieteEntier(QName qName) throws PreRequisException {
        return this.modeleNoeudAction.getProprieteEntier(this.nodeRef, qName);
    }

    /**
     * Méthode permettant de récupérer la valeur d'une propriété au format {@link Date}.
     * @param qName La propriété dont il faut récupérer la valeur.
     * @return La valeur de la propriété au format {@link Date}.
     * @throws PreRequisException   Si le nœud ou la propriété est null.
     */
    protected Date getProprieteDate(QName qName) throws ModeleException, PreRequisException {
        return this.modeleNoeudAction.getProprieteDate(this.nodeRef, qName);
    }

    /**
     * Récupère la valeur d'une propriété d'un nœud en booléen.
     * @param qName La propriété que l'on souhaite récupérer.
     * @return La valeur de la propriété ou null.
     * @throws PreRequisException   Si le nœud ou la propriété est null.
     */
    protected boolean obtenirProprieteEnBooleen(QName qName) throws PreRequisException {
        return this.modeleNoeudAction.obtenirProprieteEnBooleen(this.nodeRef, qName);
    }

    /**
     * Indique si le nœud à un type donné.
     * @param qName Le type.
     * @return <c>true</c> si le nœud est typé du même type que celui en paramètre. Sinon <c>false</c>.
     * @throws PreRequisException   Si le nœud ou la propriété est null.
     */
    protected boolean avoirType(QName qName) throws PreRequisException {
        return this.modeleNoeudAction.avoirType(this.nodeRef, qName);
    }

    /**
     * Indique si le nœud à un aspect.
     * @param qName L'aspect à vérifier.
     * @throws PreRequisException   Si le nœud ou la propriété est null.
     */
    protected boolean avoirAspect(QName qName) throws PreRequisException {
        return this.modeleNoeudAction.avoirAspect(this.nodeRef, qName);
    }

    /**
     * Modifie la valeur d'une propriété d'un nœud.
     * @param propriete La propriété a modifié.
     * @param valeur La nouvelle valeur de la propriété.
     * @throws PreRequisException   Si le nœud ou la propriété est null.
     */
    protected void setPropriete(QName propriete, Serializable valeur) throws PreRequisException, NoSuchMethodException {
        this.modeleNoeudAction.setPropriete(this.nodeRef, propriete, valeur);
    }

    /**
     * Récupère le nœud parent de celui en paramètre.
     * @param nodeRef Le nœud dont on souhaite récupérer le parent.
     * @throws PreRequisException   Si le nœud ou la propriété est null.
     */
    protected NodeRef getNoeudParent(NodeRef nodeRef) throws PreRequisException {
        return this.modeleNoeudAction.getNoeudParent(nodeRef);
    }

    /**
     * Lit le contenu du nœud.
     * @return Le contenu du nœud au format de tableau d'octets.
     * @throws IOException Si une erreur entrée/sortie à lieu.
     */
    protected byte[] lire() throws IOException {
        return this.modeleNoeudAction.getContenu(this.nodeRef);
    }

    /**
     * Récupère le nœud du modèle.
     * @return Le nœud du modèle.
     */
    public NodeRef getNoeud() {
        return this.nodeRef;
    }

    /**
     * Supprimer le nœud référencé.
     * @throws PreRequisException Si le noeud est null.
     * @throws NoSuchMethodException
     */
    public void supprimerNoeud() throws NoSuchMethodException, PreRequisException {
        this.modeleNoeudAction.supprimerNoeud(this.nodeRef);
    }
}

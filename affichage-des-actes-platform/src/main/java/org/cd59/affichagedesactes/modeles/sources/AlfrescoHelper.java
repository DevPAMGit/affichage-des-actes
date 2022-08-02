package org.cd59.affichagedesactes.modeles.sources; 

import java.util.ArrayList;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;

import java.util.Map;
import java.util.List;
import java.io.Serializable;

/** Classe permettant de simplifier la gestion des nœuds Alfresco. */
public class AlfrescoHelper {

	/** Le service de gestion des nœuds d'Alfresco. */
	protected NodeService serviceNoeud;

	/** Initialise une nouvelle instance de la classe {@link AlfrescoHelper}.
	* @param serviceNoeud Le service de gestion des nœuds d'Alfresco. */
	public AlfrescoHelper(NodeService serviceNoeud) {
		this.serviceNoeud = serviceNoeud;
	}

	/** Permet de vérifier qu'un nœud possède l'aspect désigné en paramètre.
	* @param noeud Le nœud dont on souhaite vérifier la présence de l'aspect.
	* @param aspect L'aspect dont on souhaite vérifier la présence.
	* @return <c>true</c> si l'aspect est présent, sinon <c>false</c>. */
	public boolean hasAspect(NodeRef noeud, QName aspect){
		return this.serviceNoeud.hasAspect(noeud, aspect);
	}

	/** Ajoute un aspect à un nœud.
	* @param noeud Le nœud auquel on souhaite ajouter l'aspect.
	* @param aspect L'aspect à ajouter.
	* @param valeurs Les valeurs de l'aspect à sa création. */
	public void addAspect(NodeRef noeud, QName aspect, Map<QName, Serializable> valeurs) {
		if(this.hasAspect(noeud, aspect)) this.majProprietes(noeud, valeurs);
		else this.serviceNoeud.addAspect(noeud, aspect, valeurs);
	}

	/** Supprime un aspect d'un noeud.
	* @param noeud Le nœud auquel on souhaite retirer l'aspect.
	* @param aspect L'aspect que l'on souhaite retirer du nœud. */
	public void supprimeAspect(NodeRef noeud, QName aspect) {
		if(!this.hasAspect(noeud, aspect)) return;
		this.serviceNoeud.removeAspect(noeud, aspect);
	}

	/** Vérifie si le nœud est du type en en paramètre.
	* @param noeud Le nœud dont on souhaite savoir s'il est du type en paramètre.	* @param type Le type de nœud attendu.
	* return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.*/
	public boolean hasType(NodeRef noeud, QName type) {
		return this.serviceNoeud.getType(noeud).isMatch(type);
	}

	/** Ajoute un type au nœud.
	* @param noeud Le nœud auquel on ajoute un type.
	* @param type Le type à ajouter. */
	public void addType(NodeRef noeud, QName type){
		if(this.hasType(noeud, type))
			return;
		this.serviceNoeud.setType(noeud, type);
	}

	/** Permet d'obtenir la valeur d'une propriété d'un nœud.
	* @param noeud Le nœud dont on souhaite récupérer la valeur de la propriété.
	* @param propriete La propriété dont on souhaite récupérer la valeur.
	* @return La valeur de la propriété. */
	protected Serializable getPropriete(NodeRef noeud, QName propriete){
		return this.serviceNoeud.getProperty(noeud, propriete);
	}

	/** Modifie la valeur d'une propriété d'un nœud en paramètre.
	* @param noeud Le nœud dont on souhaite modifier la propriété.
	* @param propriete La propriété dont on souhaite modifier la valeur.*/
	protected void majPropriete(NodeRef noeud, QName propriete, Serializable valeur){
		this.serviceNoeud.setProperty(noeud, propriete, valeur);
	}

	/** Modifie la valeur d'une propriété d'un nœud en paramètre.
	* @param noeud Le nœud dont on souhaite modifier la valeur des propriétés.
	* @param valeurs La propriété dont on souhaite modifier la valeur. */
	protected void majProprietes(NodeRef noeud, Map<QName, Serializable> valeurs){
		this.serviceNoeud.setProperties(noeud, valeurs);
	}

	/** Permet de récupérer le nœud parent au nœud mis en paramètre.
	* @param noeud Le nœud dont on souhaite récupérer le parent.
	* @return Une instance de type {@link NodeRef} représentant le parent du nœud parent. */
	protected NodeRef getNoeudParent(NodeRef noeud){
		return this.serviceNoeud.getPrimaryParent(noeud).getParentRef();
	}

	/** Permet de récupérer un ancêtre du nœud mis en paramètre.
	* @param noeud Le nœud dont on souhaite récupérer l'ancêtre'.
	* @param generation La génération antérieure à laquelle on souhaite accéder.
	* @return Une instance de type {@link NodeRef} représentant l'ancêtre nœud en paramètre. */
	protected NodeRef obtenirAncetre(NodeRef noeud, int generation) {
		NodeRef ancetre = noeud;
		for (int i=0; i<generation; i++)
			ancetre = this.getNoeudParent(ancetre);
		return ancetre;
	}

	/** Permet d'obtenir la liste des nœuds contenus dans le nœud en paramètre.
	* @param noeud Le nœud dont on veut récupérer le contenu.
	* @return La liste des nœuds contenue dans le nœud. */
	protected List<NodeRef> obtenirContenu(NodeRef noeud) {
		ArrayList<NodeRef> noeuds = new ArrayList<>();
		for(ChildAssociationRef child : this.serviceNoeud.getChildAssocs(noeud))
			noeuds.add(child.getChildRef());
		return noeuds;
	}

}
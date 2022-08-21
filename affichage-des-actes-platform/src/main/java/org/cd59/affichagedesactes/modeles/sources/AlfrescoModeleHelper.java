package org.cd59.affichagedesactes.modeles.sources;


import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/** Classe permettant de simplifier la gestion des modèles liées à des nœuds Alfresco. */
public class AlfrescoModeleHelper extends AlfrescoHelper {

	/** Le nœud modèle de référence. */
	protected final NodeRef noeud;

	/** Initialise une nouvelle instance de la classe {@link AlfrescoModeleHelper}.
	* @param serviceNoeud Le service de gestion des nœuds d'Alfresco.
	* @param noeud Le nœud de référence. */
	public AlfrescoModeleHelper(NodeService serviceNoeud, NodeRef noeud) {
		super(serviceNoeud);
		this.noeud = noeud;
	}

	/** Permet de vérifier que le nœud du modèle possède l'aspect désigné en paramètre.
	* @param aspect L'aspect dont on souhaite vérifier la présence.
	* @return <c>true</c> si l'aspect est présent, sinon <c>false</c>. */
	public boolean hasAspect(QName aspect){
		return this.hasAspect(this.noeud, aspect);
	}

	/** Ajoute un aspect à un nœud.
	* @param aspect L'aspect à ajouter.
	* @param valeurs Les valeurs de l'aspect à sa création. */
	public void addAspect(QName aspect, Map<QName, Serializable> valeurs) {
		this.addAspect(this.noeud, aspect, valeurs);
	}

	/** Supprime un aspect d'un noeud.
	* @param aspect L'aspect que l'on souhaite retirer du nœud. */
	public void supprimeAspect(QName aspect) {
		this.supprimeAspect(this.noeud, aspect);
	}

	/** Vérifie si le nœud du modèle est du type en en paramètre.
	* @param type Le type de nœud attendu.
	* return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>. */
	public boolean hasType(QName type) {
		return this.hasType(this.noeud, type);
	}

	/** Ajoute un type au nœud du modèle.
	* @param type Le type à ajouter. */
	public void addType(QName type){
		this.addType(this.noeud, type);
	}

	/** Permet d'obtenir la valeur d'une propriété du nœud modèle.
	* @param propriete La propriété dont on souhaite récupérer la valeur.
	* @return La valeur de la propriété. */
	public Serializable getPropriete(QName propriete){
		return this.getPropriete(this.noeud, propriete);
	}

	/** Modifie la valeur d'une propriété du nœud modèle nœud.
	 * @param propriete La propriété dont on souhaite modifier la valeur. */
	public void majPropriete(QName propriete, Serializable valeur){
		this.majPropriete(this.noeud, propriete, valeur);
	}

	/** Modifie la valeur d'une propriété du nœud modèle.
	* @param valeurs La propriété dont on souhaite modifier la valeur. */
	public void majProprietes(Map<QName, Serializable> valeurs){
		this.majProprietes(this.noeud, valeurs);
	}

	/** Permet de récupérer le nœud parent au nœud modèle.
	* @return Une instance de type {@link NodeRef} représentant le parent du nœud parent. */
	public NodeRef getNoeudParent(){
		return this.getNoeudParent(this.noeud);
	}

	/** Permet de récupérer un ancêtre du nœud du modèle.
	* @param generation La génération antérieure à laquelle on souhaite accéder.
	* @return Une instance de type {@link NodeRef} représentant l'ancêtre nœud du modèle. */
	public NodeRef getAncetre(int generation) {
		return  this.getAncetre(this.noeud, generation);
	}

	/** Permet d'obtenir la liste des nœuds contenus dans le nœud du modèle.
	* @return La liste des nœuds contenue dans le nœud. */
	public List<NodeRef> getContenu() {
		return this.getContenu(this.noeud);
	}

	/** Permet de rechercher un nœud par son nom dans le nœud modèle.
	 * @param type Le type du nœud rechercher.
	 * @param nom Le nom du nœud à rechercher.
	 * @return NodeRef Le nœud avec le nom indiqué en paramètre ou null. */
	public NodeRef searchNoeudDossierParNom(QName type, String nom) {
		return this.serviceNoeud.getChildByName(this.noeud, type, nom);
	}

	/** Permet de créer un dossier dans un nœud modèle s'il n'existe pas.
	 * @param nom Le nom du dossier.
	 * @return NodeRef Le nouveau nœud créer. */
	public NodeRef creerDossier(String nom) {
		return this.creerDossier(this.noeud, nom);
	}

	/** Permet de créer un dossier dans un nœud en modèle s'il n'existe pas avec des métadonnées.
	 * @param nom Le nom du dossier.
	 * @param metadonnees Les métadonnées du nouveau nœud.
	 * @return NodeRef Le nouveau nœud créer. */
	public NodeRef creerDossierAvecDonnees(String nom, Map<QName, Serializable> metadonnees) {
		return this.creerDossierAvecDonnees(this.noeud, nom, metadonnees);
	}

}
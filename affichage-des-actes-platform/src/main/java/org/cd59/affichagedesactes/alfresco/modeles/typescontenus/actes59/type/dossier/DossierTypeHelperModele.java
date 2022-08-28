package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.type.dossier ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link DossierTypeHelperModele}.*/
public class DossierTypeHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link DossierTypeHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public DossierTypeHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(DossierTypeModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, DossierTypeModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, DossierTypeModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(DossierTypeModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(DossierTypeModele.NOM);
	}

}

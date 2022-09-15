package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.type.sas ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.ServiceRegistry;import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link SasTypeHelperModele}.*/
public class SasTypeHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link SasTypeHelperModele}. 
	 * @param serviceRegistry Le registre des services. 
	 * @param noeud Le nœud de référence. */
	public SasTypeHelperModele(ServiceRegistry serviceRegistry, NodeRef noeud){
		super(serviceRegistry, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(SasTypeModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, SasTypeModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, SasTypeModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(SasTypeModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(SasTypeModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:compteur'. 
	 * @return int La valeur de la propriété 'actes59:compteur'. */
	public int getCompteur() { 
		return (int) this.getPropriete(SasTypeModele.COMPTEUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:compteur'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:compteur'. */ 
	public void setCompteur(int valeur) { 
		this.majPropriete(SasTypeModele.COMPTEUR, valeur);
	}

}
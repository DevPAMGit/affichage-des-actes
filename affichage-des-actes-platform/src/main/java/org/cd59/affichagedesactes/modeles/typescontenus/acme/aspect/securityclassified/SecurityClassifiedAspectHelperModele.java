package org.cd59.affichagedesactes.modeles.typescontenus.acme.aspect.securityclassified ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link SecurityClassifiedAspectHelperModele}.*/
public class SecurityClassifiedAspectHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link SecurityClassifiedAspectHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public SecurityClassifiedAspectHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(SecurityClassifiedAspectModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, SecurityClassifiedAspectModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, SecurityClassifiedAspectModele.NOM, valeurs);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'acme:securityClassification'. 
	 * @return String La valeur de la propriété 'acme:securityClassification'. */
	public String getSecurityClassification() { 
		return (String) this.getPropriete(SecurityClassifiedAspectModele.SECURITY_CLASSIFICATION);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'acme:securityClassification'. 
	 * @param valeur La nouvelle valeur de la propriété 'acme:securityClassification'. */ 
	public void setSecurityClassification(String valeur) { 
		this.majPropriete(SecurityClassifiedAspectModele.SECURITY_CLASSIFICATION, valeur);
	}

}

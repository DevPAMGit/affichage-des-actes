package org.cd59.affichagedesactes.modeles.typescontenus.acme.aspects.securityclassified;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour l'aspect 'acme:securityClassified'. */
 public class SecurityclassifiedAspectHelperModele extends AlfrescoModeleHelper {

 	/** Initialise une nouvelle instance de la classe {@link SecurityclassifiedAspectHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public SecurityclassifiedAspectHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède l'aspect désigné en paramètre.
	 * @return <c>true</c> si l'aspect est présent, sinon <c>false</c>. */ 
	public boolean hasAspect(){ 
		return this.hasAspect(SecurityclassifiedAspectModele.NOM);
	}

	/** Supprime un aspect du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, SecurityclassifiedAspectModele.NOM); 
	}

	/** Ajoute un aspect à un nœud.
	 * @param valeurs Les valeurs de l'aspect à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, SecurityclassifiedAspectModele.NOM, valeurs);
;	}

	/** Méthode permettant de récupérer la valeur de la propriété 'acme:securityClassified'. 
	 * @return String La valeur de la propriété 'acme:securityClassified'.  */
	public String getSecurityclassification() { 
		return (String) this.getPropriete(SecurityclassifiedAspectModele.SECURITYCLASSIFICATION);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'acme:securityClassified'. 
	 * @param valeur La nouvelle valeur de la propriété 'acme:securityClassified'. */ 
	public void setSecurityclassification(String valeur) { 
		this.majPropriete(SecurityclassifiedAspectModele.SECURITYCLASSIFICATION, valeur);
	}

}

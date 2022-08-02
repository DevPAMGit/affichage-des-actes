package org.cd59.affichagedesactes.modeles.typescontenus.acme.types.document;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;

import java.util.Date;

/** Classe modèle d'aide personnalisée pour le type 'acme:document'. */
 public class DocumentTypeHelperModele extends AlfrescoModeleHelper {

 	/** Initialise une nouvelle instance de la classe {@link DocumentTypeHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public DocumentTypeHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède l'aspect désigné en paramètre.
	 * @return <c>true</c> si l'aspect est présent, sinon <c>false</c>. */ 
	public boolean hasType(){ 
		return this.hasType(DocumentTypeModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'acme:document'. 
	 * @return String La valeur de la propriété 'acme:document'.  */
	public String getDocumentid() { 
		return (String) this.getPropriete(DocumentTypeModele.DOCUMENTID);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'acme:document'. 
	 * @param valeur La nouvelle valeur de la propriété 'acme:document'.	 * @return String a valeur de la propriété 'acme:document'.  */
	public void setDocumentid(String valeur) { 
		this.majPropriete(DocumentTypeModele.DOCUMENTID, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'acme:document'. 
	 * @return String La valeur de la propriété 'acme:document'.  */
	public String getSecurityclassification() { 
		return (String) this.getPropriete(DocumentTypeModele.SECURITYCLASSIFICATION);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'acme:document'. 
	 * @param valeur La nouvelle valeur de la propriété 'acme:document'.	 * @return String a valeur de la propriété 'acme:document'.  */
	public void setSecurityclassification(String valeur) { 
		this.majPropriete(DocumentTypeModele.SECURITYCLASSIFICATION, valeur);
	}

}

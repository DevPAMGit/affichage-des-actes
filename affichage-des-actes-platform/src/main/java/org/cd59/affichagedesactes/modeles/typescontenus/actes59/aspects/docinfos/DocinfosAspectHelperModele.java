package org.cd59.affichagedesactes.modeles.typescontenus.actes59.aspects.docinfos;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour l'aspect 'actes59:docinfos'. */
 public class DocinfosAspectHelperModele extends AlfrescoModeleHelper {

 	/** Initialise une nouvelle instance de la classe {@link DocinfosAspectHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public DocinfosAspectHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède l'aspect désigné en paramètre.
	 * @return <c>true</c> si l'aspect est présent, sinon <c>false</c>. */ 
	public boolean hasAspect(){ 
		return this.hasAspect(DocinfosAspectModele.NOM);
	}

	/** Supprime un aspect du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, DocinfosAspectModele.NOM); 
	}

	/** Ajoute un aspect à un nœud.
	 * @param valeurs Les valeurs de l'aspect à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, DocinfosAspectModele.NOM, valeurs);
;	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:docinfos'. 
	 * @return String La valeur de la propriété 'actes59:docinfos'.  */
	public String getEmpreinte() { 
		return (String) this.getPropriete(DocinfosAspectModele.EMPREINTE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:docinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:docinfos'. */ 
	public void setEmpreinte(String valeur) { 
		this.majPropriete(DocinfosAspectModele.EMPREINTE, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:docinfos'. 
	 * @return String La valeur de la propriété 'actes59:docinfos'.  */
	public String getTypedocument() { 
		return (String) this.getPropriete(DocinfosAspectModele.TYPEDOCUMENT);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:docinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:docinfos'. */ 
	public void setTypedocument(String valeur) { 
		this.majPropriete(DocinfosAspectModele.TYPEDOCUMENT, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:docinfos' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estTypedocumentValide() { 
		return ( this.getTypedocument() != null && !this.getTypedocument().isBlank());
	}

	/** Méthode permettant de vérifier la validité de l'aspect 'actes59:docinfos'. 
	 * @return <c>true</c> si l'aspect à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		         && this.estTypedocumentValide()
		);
	}

}

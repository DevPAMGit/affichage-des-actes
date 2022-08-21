package org.cd59.affichagedesactes.modeles.typescontenus.actes59.aspect.docinfos ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link DocinfosAspectHelperModele}.*/
public class DocinfosAspectHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link DocinfosAspectHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public DocinfosAspectHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(DocinfosAspectModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, DocinfosAspectModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, DocinfosAspectModele.NOM, valeurs);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:empreinte'. 
	 * @return String La valeur de la propriété 'actes59:empreinte'. */
	public String getEmpreinte() { 
		return (String) this.getPropriete(DocinfosAspectModele.EMPREINTE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:empreinte'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:empreinte'. */ 
	public void setEmpreinte(String valeur) { 
		this.majPropriete(DocinfosAspectModele.EMPREINTE, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:typedocument'. 
	 * @return String La valeur de la propriété 'actes59:typedocument'. */
	public String getTypedocument() { 
		return (String) this.getPropriete(DocinfosAspectModele.TYPEDOCUMENT);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:typedocument'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:typedocument'. */ 
	public void setTypedocument(String valeur) { 
		this.majPropriete(DocinfosAspectModele.TYPEDOCUMENT, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:typedocument' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estTypedocumentValide() { 
		return ( this.getTypedocument() != null && !this.getTypedocument().isBlank());
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'actes59:docinfos'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estTypedocumentValide()
		);
	}

}
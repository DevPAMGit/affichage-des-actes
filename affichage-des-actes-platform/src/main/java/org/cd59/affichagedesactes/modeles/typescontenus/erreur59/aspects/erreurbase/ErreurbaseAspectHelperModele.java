package org.cd59.affichagedesactes.modeles.typescontenus.erreur59.aspects.erreurbase;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour l'aspect 'erreur59:erreurBase'. */
 public class ErreurbaseAspectHelperModele extends AlfrescoModeleHelper {

 	/** Initialise une nouvelle instance de la classe {@link ErreurbaseAspectHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public ErreurbaseAspectHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède l'aspect désigné en paramètre.
	 * @return <c>true</c> si l'aspect est présent, sinon <c>false</c>. */ 
	public boolean hasAspect(){ 
		return this.hasAspect(ErreurbaseAspectModele.NOM);
	}

	/** Supprime un aspect du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, ErreurbaseAspectModele.NOM); 
	}

	/** Ajoute un aspect à un nœud.
	 * @param valeurs Les valeurs de l'aspect à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, ErreurbaseAspectModele.NOM, valeurs);
;	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:erreurBase'. 
	 * @return String La valeur de la propriété 'erreur59:erreurBase'.  */
	public String getMessageerreur() { 
		return (String) this.getPropriete(ErreurbaseAspectModele.MESSAGEERREUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:erreurBase'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:erreurBase'. */ 
	public void setMessageerreur(String valeur) { 
		this.majPropriete(ErreurbaseAspectModele.MESSAGEERREUR, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:erreurBase' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estMessageerreurValide() { 
		return ( this.getMessageerreur() != null && !this.getMessageerreur().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:erreurBase'. 
	 * @return Date La valeur de la propriété 'erreur59:erreurBase'.  */
	public Date getDateerreur() { 
		return (Date) this.getPropriete(ErreurbaseAspectModele.DATEERREUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:erreurBase'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:erreurBase'. */ 
	public void setDateerreur(Date valeur) { 
		this.majPropriete(ErreurbaseAspectModele.DATEERREUR, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:erreurBase' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateerreurValide() { 
		return (this.getDateerreur() != null);	}

	/** Méthode permettant de vérifier la validité de l'aspect 'erreur59:erreurBase'. 
	 * @return <c>true</c> si l'aspect à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		         && this.estMessageerreurValide()
		         && this.estDateerreurValide()
		);
	}

}

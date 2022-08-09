package org.cd59.affichagedesactes.modeles.typescontenus.erreur59.aspects.erreurdossier;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour l'aspect 'erreur59:erreurDossier'. */
 public class ErreurdossierAspectHelperModele extends AlfrescoModeleHelper {

 	/** Initialise une nouvelle instance de la classe {@link ErreurdossierAspectHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public ErreurdossierAspectHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède l'aspect désigné en paramètre.
	 * @return <c>true</c> si l'aspect est présent, sinon <c>false</c>. */ 
	public boolean hasAspect(){ 
		return this.hasAspect(ErreurdossierAspectModele.NOM);
	}

	/** Supprime un aspect du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, ErreurdossierAspectModele.NOM); 
	}

	/** Ajoute un aspect à un nœud.
	 * @param valeurs Les valeurs de l'aspect à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, ErreurdossierAspectModele.NOM, valeurs);
;	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:erreurDossier'. 
	 * @return int La valeur de la propriété 'erreur59:erreurDossier'.  */
	public int getNbfichierenerreur() { 
		return (int) this.getPropriete(ErreurdossierAspectModele.NBFICHIERENERREUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:erreurDossier'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:erreurDossier'. */ 
	public void setNbfichierenerreur(int valeur) { 
		this.majPropriete(ErreurdossierAspectModele.NBFICHIERENERREUR, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:erreurDossier' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estNbfichierenerreurValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:erreurDossier'. 
	 * @return String La valeur de la propriété 'erreur59:erreurDossier'.  */
	public String getMessageerreur() { 
		return (String) this.getPropriete(ErreurdossierAspectModele.MESSAGEERREUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:erreurDossier'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:erreurDossier'. */ 
	public void setMessageerreur(String valeur) { 
		this.majPropriete(ErreurdossierAspectModele.MESSAGEERREUR, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:erreurDossier' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estMessageerreurValide() { 
		return ( this.getMessageerreur() != null && !this.getMessageerreur().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:erreurDossier'. 
	 * @return Date La valeur de la propriété 'erreur59:erreurDossier'.  */
	public Date getDateerreur() { 
		return (Date) this.getPropriete(ErreurdossierAspectModele.DATEERREUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:erreurDossier'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:erreurDossier'. */ 
	public void setDateerreur(Date valeur) { 
		this.majPropriete(ErreurdossierAspectModele.DATEERREUR, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:erreurDossier' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateerreurValide() { 
		return (this.getDateerreur() != null);	}

	/** Méthode permettant de vérifier la validité de l'aspect 'erreur59:erreurDossier'. 
	 * @return <c>true</c> si l'aspect à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		         && this.estNbfichierenerreurValide()
		         && this.estMessageerreurValide()
		         && this.estDateerreurValide()
		);
	}

}

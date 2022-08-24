package org.cd59.affichagedesactes.modeles.typescontenus.erreur59.aspect.erreurdossier ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link ErreurDossierAspectHelperModele}.*/
public class ErreurDossierAspectHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link ErreurDossierAspectHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public ErreurDossierAspectHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(ErreurDossierAspectModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, ErreurDossierAspectModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, ErreurDossierAspectModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(ErreurDossierAspectModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(ErreurDossierAspectModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:nbFichierEnErreur'. 
	 * @return int La valeur de la propriété 'erreur59:nbFichierEnErreur'. */
	public int getNbFichierEnErreur() { 
		return (int) this.getPropriete(ErreurDossierAspectModele.NB_FICHIER_EN_ERREUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:nbFichierEnErreur'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:nbFichierEnErreur'. */ 
	public void setNbFichierEnErreur(int valeur) { 
		this.majPropriete(ErreurDossierAspectModele.NB_FICHIER_EN_ERREUR, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:nbFichierEnErreur' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estNbFichierEnErreurValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:messageErreur'. 
	 * @return String La valeur de la propriété 'erreur59:messageErreur'. */
	public String getMessageErreur() { 
		return (String) this.getPropriete(ErreurDossierAspectModele.MESSAGE_ERREUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:messageErreur'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:messageErreur'. */ 
	public void setMessageErreur(String valeur) { 
		this.majPropriete(ErreurDossierAspectModele.MESSAGE_ERREUR, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:messageErreur' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estMessageErreurValide() { 
		return ( this.getMessageErreur() != null && !this.getMessageErreur().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:dateErreur'. 
	 * @return Date La valeur de la propriété 'erreur59:dateErreur'. */
	public Date getDateErreur() { 
		return (Date) this.getPropriete(ErreurDossierAspectModele.DATE_ERREUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:dateErreur'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:dateErreur'. */ 
	public void setDateErreur(Date valeur) { 
		this.majPropriete(ErreurDossierAspectModele.DATE_ERREUR, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:dateErreur' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateErreurValide() { 
		return (this.getDateErreur() != null);	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'erreur59:erreurDossier'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estNbFichierEnErreurValide()
		&& this.estMessageErreurValide()
		&& this.estDateErreurValide());
	}

}

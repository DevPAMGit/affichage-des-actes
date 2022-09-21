package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.affichage59.aspect.affichage ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.ServiceRegistry;import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link AffichageAspectHelperModele}.*/
public class AffichageAspectHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link AffichageAspectHelperModele}. 
	 * @param serviceRegistry Le registre des services. 
	 * @param noeud Le nœud de référence. */
	public AffichageAspectHelperModele(ServiceRegistry serviceRegistry, NodeRef noeud){
		super(serviceRegistry, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(AffichageAspectModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, AffichageAspectModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, AffichageAspectModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(AffichageAspectModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(AffichageAspectModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'affichage59:etat'. 
	 * @return String La valeur de la propriété 'affichage59:etat'. */
	public String getEtat() { 
		return (String) this.getPropriete(AffichageAspectModele.ETAT);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'affichage59:etat'. 
	 * @param valeur La nouvelle valeur de la propriété 'affichage59:etat'. */ 
	public void setEtat(String valeur) { 
		this.majPropriete(AffichageAspectModele.ETAT, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'affichage59:etat' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estEtatValide() { 
		return ( this.getEtat() != null && !this.getEtat().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'affichage59:tentativeEnvoi'. 
	 * @return Date La valeur de la propriété 'affichage59:tentativeEnvoi'. */
	public Date getTentativeEnvoi() { 
		return (Date) this.getPropriete(AffichageAspectModele.TENTATIVE_ENVOI);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'affichage59:tentativeEnvoi'. 
	 * @param valeur La nouvelle valeur de la propriété 'affichage59:tentativeEnvoi'. */ 
	public void setTentativeEnvoi(Date valeur) { 
		this.majPropriete(AffichageAspectModele.TENTATIVE_ENVOI, valeur);
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'affichage59:affichage'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estEtatValide());
	}

}

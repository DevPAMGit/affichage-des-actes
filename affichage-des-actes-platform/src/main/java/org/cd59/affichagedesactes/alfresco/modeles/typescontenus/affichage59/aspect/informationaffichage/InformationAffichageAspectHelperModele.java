package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.affichage59.aspect.informationaffichage ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.ServiceRegistry;import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link InformationAffichageAspectHelperModele}.*/
public class InformationAffichageAspectHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link InformationAffichageAspectHelperModele}. 
	 * @param serviceRegistry Le registre des services. 
	 * @param noeud Le nœud de référence. */
	public InformationAffichageAspectHelperModele(ServiceRegistry serviceRegistry, NodeRef noeud){
		super(serviceRegistry, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(InformationAffichageAspectModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, InformationAffichageAspectModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, InformationAffichageAspectModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(InformationAffichageAspectModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(InformationAffichageAspectModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'affichage59:dateAffichage'. 
	 * @return Date La valeur de la propriété 'affichage59:dateAffichage'. */
	public Date getDateAffichage() { 
		return (Date) this.getPropriete(InformationAffichageAspectModele.DATE_AFFICHAGE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'affichage59:dateAffichage'. 
	 * @param valeur La nouvelle valeur de la propriété 'affichage59:dateAffichage'. */ 
	public void setDateAffichage(Date valeur) { 
		this.majPropriete(InformationAffichageAspectModele.DATE_AFFICHAGE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'affichage59:dateAffichage' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateAffichageValide() { 
		return (this.getDateAffichage() != null);	}

	/** Méthode permettant de récupérer la valeur de la propriété 'affichage59:urlAffichage'. 
	 * @return String La valeur de la propriété 'affichage59:urlAffichage'. */
	public String getUrlAffichage() { 
		return (String) this.getPropriete(InformationAffichageAspectModele.URL_AFFICHAGE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'affichage59:urlAffichage'. 
	 * @param valeur La nouvelle valeur de la propriété 'affichage59:urlAffichage'. */ 
	public void setUrlAffichage(String valeur) { 
		this.majPropriete(InformationAffichageAspectModele.URL_AFFICHAGE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'affichage59:urlAffichage' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estUrlAffichageValide() { 
		return ( this.getUrlAffichage() != null && !this.getUrlAffichage().isBlank());
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'affichage59:informationAffichage'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estDateAffichageValide()
		&& this.estUrlAffichageValide());
	}

}

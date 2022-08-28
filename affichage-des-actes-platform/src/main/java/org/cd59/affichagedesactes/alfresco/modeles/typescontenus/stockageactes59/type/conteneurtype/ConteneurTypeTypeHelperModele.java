package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneurtype ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link ConteneurTypeTypeHelperModele}.*/
public class ConteneurTypeTypeHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link ConteneurTypeTypeHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public ConteneurTypeTypeHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(ConteneurTypeTypeModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, ConteneurTypeTypeModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, ConteneurTypeTypeModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(ConteneurTypeTypeModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(ConteneurTypeTypeModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:conteneurActeType'. 
	 * @return String La valeur de la propriété 'stockageactes59:conteneurActeType'. */
	public String getConteneurActeType() { 
		return (String) this.getPropriete(ConteneurTypeTypeModele.CONTENEUR_ACTE_TYPE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:conteneurActeType'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:conteneurActeType'. */ 
	public void setConteneurActeType(String valeur) { 
		this.majPropriete(ConteneurTypeTypeModele.CONTENEUR_ACTE_TYPE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:conteneurActeType' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estConteneurActeTypeValide() { 
		return ( this.getConteneurActeType() != null && !this.getConteneurActeType().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateJour'. 
	 * @return int La valeur de la propriété 'stockageactes59:dateJour'. */
	public int getDateJour() { 
		return (int) this.getPropriete(ConteneurTypeTypeModele.DATE_JOUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateJour'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:dateJour'. */ 
	public void setDateJour(int valeur) { 
		this.majPropriete(ConteneurTypeTypeModele.DATE_JOUR, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:dateJour' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateJourValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateMois'. 
	 * @return int La valeur de la propriété 'stockageactes59:dateMois'. */
	public int getDateMois() { 
		return (int) this.getPropriete(ConteneurTypeTypeModele.DATE_MOIS);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateMois'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:dateMois'. */ 
	public void setDateMois(int valeur) { 
		this.majPropriete(ConteneurTypeTypeModele.DATE_MOIS, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:dateMois' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateMoisValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateAnnee'. 
	 * @return int La valeur de la propriété 'stockageactes59:dateAnnee'. */
	public int getDateAnnee() { 
		return (int) this.getPropriete(ConteneurTypeTypeModele.DATE_ANNEE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateAnnee'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:dateAnnee'. */ 
	public void setDateAnnee(int valeur) { 
		this.majPropriete(ConteneurTypeTypeModele.DATE_ANNEE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:dateAnnee' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateAnneeValide() { 
		return true;
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'stockageactes59:conteneurType'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estConteneurActeTypeValide()
		&& this.estDateJourValide()
		&& this.estDateMoisValide()
		&& this.estDateAnneeValide());
	}

}

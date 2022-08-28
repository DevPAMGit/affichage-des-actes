package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuractejour ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link ConteneurActeJourTypeHelperModele}.*/
public class ConteneurActeJourTypeHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link ConteneurActeJourTypeHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public ConteneurActeJourTypeHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(ConteneurActeJourTypeModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, ConteneurActeJourTypeModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, ConteneurActeJourTypeModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(ConteneurActeJourTypeModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(ConteneurActeJourTypeModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateJour'. 
	 * @return int La valeur de la propriété 'stockageactes59:dateJour'. */
	public int getDateJour() { 
		return (int) this.getPropriete(ConteneurActeJourTypeModele.DATE_JOUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateJour'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:dateJour'. */ 
	public void setDateJour(int valeur) { 
		this.majPropriete(ConteneurActeJourTypeModele.DATE_JOUR, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:dateJour' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateJourValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateMois'. 
	 * @return int La valeur de la propriété 'stockageactes59:dateMois'. */
	public int getDateMois() { 
		return (int) this.getPropriete(ConteneurActeJourTypeModele.DATE_MOIS);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateMois'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:dateMois'. */ 
	public void setDateMois(int valeur) { 
		this.majPropriete(ConteneurActeJourTypeModele.DATE_MOIS, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:dateMois' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateMoisValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateAnnee'. 
	 * @return int La valeur de la propriété 'stockageactes59:dateAnnee'. */
	public int getDateAnnee() { 
		return (int) this.getPropriete(ConteneurActeJourTypeModele.DATE_ANNEE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateAnnee'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:dateAnnee'. */ 
	public void setDateAnnee(int valeur) { 
		this.majPropriete(ConteneurActeJourTypeModele.DATE_ANNEE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:dateAnnee' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateAnneeValide() { 
		return true;
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'stockageactes59:conteneurActeJour'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estDateJourValide()
		&& this.estDateMoisValide()
		&& this.estDateAnneeValide());
	}

}

package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierjour ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.ServiceRegistry;import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link DossierJourTypeHelperModele}.*/
public class DossierJourTypeHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link DossierJourTypeHelperModele}. 
	 * @param serviceRegistry Le registre des services. 
	 * @param noeud Le nœud de référence. */
	public DossierJourTypeHelperModele(ServiceRegistry serviceRegistry, NodeRef noeud){
		super(serviceRegistry, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(DossierJourTypeModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, DossierJourTypeModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, DossierJourTypeModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(DossierJourTypeModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(DossierJourTypeModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:jour'. 
	 * @return int La valeur de la propriété 'stockageactes59:jour'. */
	public int getJour() { 
		return (int) this.getPropriete(DossierJourTypeModele.JOUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:jour'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:jour'. */ 
	public void setJour(int valeur) { 
		this.majPropriete(DossierJourTypeModele.JOUR, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:jour' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estJourValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:mois'. 
	 * @return int La valeur de la propriété 'stockageactes59:mois'. */
	public int getMois() { 
		return (int) this.getPropriete(DossierJourTypeModele.MOIS);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:mois'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:mois'. */ 
	public void setMois(int valeur) { 
		this.majPropriete(DossierJourTypeModele.MOIS, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:mois' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estMoisValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:annee'. 
	 * @return int La valeur de la propriété 'stockageactes59:annee'. */
	public int getAnnee() { 
		return (int) this.getPropriete(DossierJourTypeModele.ANNEE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:annee'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:annee'. */ 
	public void setAnnee(int valeur) { 
		this.majPropriete(DossierJourTypeModele.ANNEE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:annee' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estAnneeValide() { 
		return true;
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'stockageactes59:dossierJour'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estJourValide()
		&& this.estMoisValide()
		&& this.estAnneeValide());
	}

}

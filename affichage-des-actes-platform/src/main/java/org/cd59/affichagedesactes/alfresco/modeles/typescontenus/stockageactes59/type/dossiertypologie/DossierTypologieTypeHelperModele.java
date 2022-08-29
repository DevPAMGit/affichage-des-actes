package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossiertypologie ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.ServiceRegistry;import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link DossierTypologieTypeHelperModele}.*/
public class DossierTypologieTypeHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link DossierTypologieTypeHelperModele}. 
	 * @param serviceRegistry Le registre des services. 
	 * @param noeud Le nœud de référence. */
	public DossierTypologieTypeHelperModele(ServiceRegistry serviceRegistry, NodeRef noeud){
		super(serviceRegistry, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(DossierTypologieTypeModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, DossierTypologieTypeModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, DossierTypologieTypeModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(DossierTypologieTypeModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(DossierTypologieTypeModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:typologieDossier'. 
	 * @return String La valeur de la propriété 'stockageactes59:typologieDossier'. */
	public String getTypologieDossier() { 
		return (String) this.getPropriete(DossierTypologieTypeModele.TYPOLOGIE_DOSSIER);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:typologieDossier'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:typologieDossier'. */ 
	public void setTypologieDossier(String valeur) { 
		this.majPropriete(DossierTypologieTypeModele.TYPOLOGIE_DOSSIER, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:typologieDossier' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estTypologieDossierValide() { 
		return ( this.getTypologieDossier() != null && !this.getTypologieDossier().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:jour'. 
	 * @return int La valeur de la propriété 'stockageactes59:jour'. */
	public int getJour() { 
		return (int) this.getPropriete(DossierTypologieTypeModele.JOUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:jour'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:jour'. */ 
	public void setJour(int valeur) { 
		this.majPropriete(DossierTypologieTypeModele.JOUR, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:jour' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estJourValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:mois'. 
	 * @return int La valeur de la propriété 'stockageactes59:mois'. */
	public int getMois() { 
		return (int) this.getPropriete(DossierTypologieTypeModele.MOIS);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:mois'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:mois'. */ 
	public void setMois(int valeur) { 
		this.majPropriete(DossierTypologieTypeModele.MOIS, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:mois' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estMoisValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:annee'. 
	 * @return int La valeur de la propriété 'stockageactes59:annee'. */
	public int getAnnee() { 
		return (int) this.getPropriete(DossierTypologieTypeModele.ANNEE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:annee'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:annee'. */ 
	public void setAnnee(int valeur) { 
		this.majPropriete(DossierTypologieTypeModele.ANNEE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:annee' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estAnneeValide() { 
		return true;
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'stockageactes59:dossierTypologie'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estTypologieDossierValide()
		&& this.estJourValide()
		&& this.estMoisValide()
		&& this.estAnneeValide());
	}

}

package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.aspect.informationdossier ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.ServiceRegistry;import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link InformationDossierAspectHelperModele}.*/
public class InformationDossierAspectHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link InformationDossierAspectHelperModele}. 
	 * @param serviceRegistry Le registre des services. 
	 * @param noeud Le nœud de référence. */
	public InformationDossierAspectHelperModele(ServiceRegistry serviceRegistry, NodeRef noeud){
		super(serviceRegistry, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(InformationDossierAspectModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, InformationDossierAspectModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, InformationDossierAspectModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(InformationDossierAspectModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(InformationDossierAspectModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:nbActes'. 
	 * @return int La valeur de la propriété 'stockageactes59:nbActes'. */
	public int getNbActes() { 
		return (int) this.getPropriete(InformationDossierAspectModele.NB_ACTES);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:nbActes'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:nbActes'. */ 
	public void setNbActes(int valeur) { 
		this.majPropriete(InformationDossierAspectModele.NB_ACTES, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:nbActes' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estNbActesValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:nbActesEnErreurs'. 
	 * @return int La valeur de la propriété 'stockageactes59:nbActesEnErreurs'. */
	public int getNbActesEnErreurs() { 
		return (int) this.getPropriete(InformationDossierAspectModele.NB_ACTES_EN_ERREURS);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:nbActesEnErreurs'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:nbActesEnErreurs'. */ 
	public void setNbActesEnErreurs(int valeur) { 
		this.majPropriete(InformationDossierAspectModele.NB_ACTES_EN_ERREURS, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:nbActesEnErreurs' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estNbActesEnErreursValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:nbActesTraites'. 
	 * @return int La valeur de la propriété 'stockageactes59:nbActesTraites'. */
	public int getNbActesTraites() { 
		return (int) this.getPropriete(InformationDossierAspectModele.NB_ACTES_TRAITES);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:nbActesTraites'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:nbActesTraites'. */ 
	public void setNbActesTraites(int valeur) { 
		this.majPropriete(InformationDossierAspectModele.NB_ACTES_TRAITES, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:nbActesTraites' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estNbActesTraitesValide() { 
		return true;
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'stockageactes59:informationDossier'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estNbActesValide()
		&& this.estNbActesEnErreursValide()
		&& this.estNbActesTraitesValide());
	}

}

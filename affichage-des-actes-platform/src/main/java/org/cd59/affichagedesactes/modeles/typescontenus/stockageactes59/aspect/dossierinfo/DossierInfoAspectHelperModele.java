package org.cd59.affichagedesactes.modeles.typescontenus.stockageactes59.aspect.dossierinfo ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link DossierInfoAspectHelperModele}.*/
public class DossierInfoAspectHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link DossierInfoAspectHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public DossierInfoAspectHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(DossierInfoAspectModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, DossierInfoAspectModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, DossierInfoAspectModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(DossierInfoAspectModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(DossierInfoAspectModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:nbDossiersPresents'. 
	 * @return int La valeur de la propriété 'stockageactes59:nbDossiersPresents'. */
	public int getNbDossiersPresents() { 
		return (int) this.getPropriete(DossierInfoAspectModele.NB_DOSSIERS_PRESENTS);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:nbDossiersPresents'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:nbDossiersPresents'. */ 
	public void setNbDossiersPresents(int valeur) { 
		this.majPropriete(DossierInfoAspectModele.NB_DOSSIERS_PRESENTS, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:nbDossiersEnErreur'. 
	 * @return int La valeur de la propriété 'stockageactes59:nbDossiersEnErreur'. */
	public int getNbDossiersEnErreur() { 
		return (int) this.getPropriete(DossierInfoAspectModele.NB_DOSSIERS_EN_ERREUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:nbDossiersEnErreur'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:nbDossiersEnErreur'. */ 
	public void setNbDossiersEnErreur(int valeur) { 
		this.majPropriete(DossierInfoAspectModele.NB_DOSSIERS_EN_ERREUR, valeur);
	}

}

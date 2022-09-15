package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosdossiersas ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.ServiceRegistry;import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link InfosDossierSasAspectHelperModele}.*/
public class InfosDossierSasAspectHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link InfosDossierSasAspectHelperModele}. 
	 * @param serviceRegistry Le registre des services. 
	 * @param noeud Le nœud de référence. */
	public InfosDossierSasAspectHelperModele(ServiceRegistry serviceRegistry, NodeRef noeud){
		super(serviceRegistry, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(InfosDossierSasAspectModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, InfosDossierSasAspectModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, InfosDossierSasAspectModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(InfosDossierSasAspectModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(InfosDossierSasAspectModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:nbDossiersActesEnErreurs'. 
	 * @return int La valeur de la propriété 'erreur59:nbDossiersActesEnErreurs'. */
	public int getNbDossiersActesEnErreurs() { 
		return (int) this.getPropriete(InfosDossierSasAspectModele.NB_DOSSIERS_ACTES_EN_ERREURS);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:nbDossiersActesEnErreurs'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:nbDossiersActesEnErreurs'. */ 
	public void setNbDossiersActesEnErreurs(int valeur) { 
		this.majPropriete(InfosDossierSasAspectModele.NB_DOSSIERS_ACTES_EN_ERREURS, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:nbDossiersActesEnErreurs' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estNbDossiersActesEnErreursValide() { 
		return true;
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'erreur59:infosDossierSas'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estNbDossiersActesEnErreursValide());
	}

}

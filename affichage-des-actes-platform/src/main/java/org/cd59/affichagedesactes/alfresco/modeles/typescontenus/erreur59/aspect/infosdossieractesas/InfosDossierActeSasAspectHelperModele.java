package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosdossieractesas ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.ServiceRegistry;import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link InfosDossierActeSasAspectHelperModele}.*/
public class InfosDossierActeSasAspectHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link InfosDossierActeSasAspectHelperModele}. 
	 * @param serviceRegistry Le registre des services. 
	 * @param noeud Le nœud de référence. */
	public InfosDossierActeSasAspectHelperModele(ServiceRegistry serviceRegistry, NodeRef noeud){
		super(serviceRegistry, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(InfosDossierActeSasAspectModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, InfosDossierActeSasAspectModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, InfosDossierActeSasAspectModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(InfosDossierActeSasAspectModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(InfosDossierActeSasAspectModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:nbFichierEnErreurs'. 
	 * @return int La valeur de la propriété 'erreur59:nbFichierEnErreurs'. */
	public int getNbFichierEnErreurs() { 
		return (int) this.getPropriete(InfosDossierActeSasAspectModele.NB_FICHIER_EN_ERREURS);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:nbFichierEnErreurs'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:nbFichierEnErreurs'. */ 
	public void setNbFichierEnErreurs(int valeur) { 
		this.majPropriete(InfosDossierActeSasAspectModele.NB_FICHIER_EN_ERREURS, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:nbFichierEnErreurs' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estNbFichierEnErreursValide() { 
		return true;
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'erreur59:infosDossierActeSas'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estNbFichierEnErreursValide());
	}

}

package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.affichage59.aspect.erreur ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.ServiceRegistry;import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link ErreurAspectHelperModele}.*/
public class ErreurAspectHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link ErreurAspectHelperModele}. 
	 * @param serviceRegistry Le registre des services. 
	 * @param noeud Le nœud de référence. */
	public ErreurAspectHelperModele(ServiceRegistry serviceRegistry, NodeRef noeud){
		super(serviceRegistry, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(ErreurAspectModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, ErreurAspectModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, ErreurAspectModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(ErreurAspectModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(ErreurAspectModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'affichage59:erreurAffichageMessage'. 
	 * @return String La valeur de la propriété 'affichage59:erreurAffichageMessage'. */
	public String getErreurAffichageMessage() { 
		return (String) this.getPropriete(ErreurAspectModele.ERREUR_AFFICHAGE_MESSAGE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'affichage59:erreurAffichageMessage'. 
	 * @param valeur La nouvelle valeur de la propriété 'affichage59:erreurAffichageMessage'. */ 
	public void setErreurAffichageMessage(String valeur) { 
		this.majPropriete(ErreurAspectModele.ERREUR_AFFICHAGE_MESSAGE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'affichage59:erreurAffichageMessage' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estErreurAffichageMessageValide() { 
		return ( this.getErreurAffichageMessage() != null && !this.getErreurAffichageMessage().isBlank());
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'affichage59:erreur'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estErreurAffichageMessageValide());
	}

}

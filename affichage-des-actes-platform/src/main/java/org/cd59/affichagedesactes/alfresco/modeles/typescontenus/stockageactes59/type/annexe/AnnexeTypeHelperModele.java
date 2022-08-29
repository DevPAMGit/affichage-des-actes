package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.annexe ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.ServiceRegistry;import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link AnnexeTypeHelperModele}.*/
public class AnnexeTypeHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link AnnexeTypeHelperModele}. 
	 * @param serviceRegistry Le registre des services. 
	 * @param noeud Le nœud de référence. */
	public AnnexeTypeHelperModele(ServiceRegistry serviceRegistry, NodeRef noeud){
		super(serviceRegistry, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(AnnexeTypeModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, AnnexeTypeModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, AnnexeTypeModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(AnnexeTypeModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(AnnexeTypeModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:typologieFichier'. 
	 * @return String La valeur de la propriété 'stockageactes59:typologieFichier'. */
	public String getTypologieFichier() { 
		return (String) this.getPropriete(AnnexeTypeModele.TYPOLOGIE_FICHIER);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:typologieFichier'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:typologieFichier'. */ 
	public void setTypologieFichier(String valeur) { 
		this.majPropriete(AnnexeTypeModele.TYPOLOGIE_FICHIER, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:typologieFichier' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estTypologieFichierValide() { 
		return ( this.getTypologieFichier() != null && !this.getTypologieFichier().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:empreinte'. 
	 * @return String La valeur de la propriété 'stockageactes59:empreinte'. */
	public String getEmpreinte() { 
		return (String) this.getPropriete(AnnexeTypeModele.EMPREINTE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:empreinte'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:empreinte'. */ 
	public void setEmpreinte(String valeur) { 
		this.majPropriete(AnnexeTypeModele.EMPREINTE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:empreinte' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estEmpreinteValide() { 
		return ( this.getEmpreinte() != null && !this.getEmpreinte().isBlank());
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'stockageactes59:annexe'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estTypologieFichierValide()
		&& this.estEmpreinteValide());
	}

}

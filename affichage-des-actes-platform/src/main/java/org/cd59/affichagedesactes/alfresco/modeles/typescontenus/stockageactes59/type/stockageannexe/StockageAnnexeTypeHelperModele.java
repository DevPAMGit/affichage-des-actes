package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.stockageannexe ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link StockageAnnexeTypeHelperModele}.*/
public class StockageAnnexeTypeHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link StockageAnnexeTypeHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public StockageAnnexeTypeHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(StockageAnnexeTypeModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, StockageAnnexeTypeModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, StockageAnnexeTypeModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(StockageAnnexeTypeModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(StockageAnnexeTypeModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:typeFichier'. 
	 * @return String La valeur de la propriété 'stockageactes59:typeFichier'. */
	public String getTypeFichier() { 
		return (String) this.getPropriete(StockageAnnexeTypeModele.TYPE_FICHIER);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:typeFichier'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:typeFichier'. */ 
	public void setTypeFichier(String valeur) { 
		this.majPropriete(StockageAnnexeTypeModele.TYPE_FICHIER, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:typeFichier' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estTypeFichierValide() { 
		return ( this.getTypeFichier() != null && !this.getTypeFichier().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:empreinteFichier'. 
	 * @return String La valeur de la propriété 'stockageactes59:empreinteFichier'. */
	public String getEmpreinteFichier() { 
		return (String) this.getPropriete(StockageAnnexeTypeModele.EMPREINTE_FICHIER);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:empreinteFichier'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:empreinteFichier'. */ 
	public void setEmpreinteFichier(String valeur) { 
		this.majPropriete(StockageAnnexeTypeModele.EMPREINTE_FICHIER, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:empreinteFichier' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estEmpreinteFichierValide() { 
		return ( this.getEmpreinteFichier() != null && !this.getEmpreinteFichier().isBlank());
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'stockageactes59:stockageAnnexe'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estTypeFichierValide()
		&& this.estEmpreinteFichierValide());
	}

}

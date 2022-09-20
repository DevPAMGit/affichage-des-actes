package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosbasesas ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.ServiceRegistry;import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link InfosBaseSasAspectHelperModele}.*/
public class InfosBaseSasAspectHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link InfosBaseSasAspectHelperModele}. 
	 * @param serviceRegistry Le registre des services. 
	 * @param noeud Le nœud de référence. */
	public InfosBaseSasAspectHelperModele(ServiceRegistry serviceRegistry, NodeRef noeud){
		super(serviceRegistry, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(InfosBaseSasAspectModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, InfosBaseSasAspectModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, InfosBaseSasAspectModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(InfosBaseSasAspectModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(InfosBaseSasAspectModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:dateInfoSas'. 
	 * @return Date La valeur de la propriété 'erreur59:dateInfoSas'. */
	public Date getDateInfoSas() { 
		return (Date) this.getPropriete(InfosBaseSasAspectModele.DATE_INFO_SAS);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:dateInfoSas'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:dateInfoSas'. */ 
	public void setDateInfoSas(Date valeur) { 
		this.majPropriete(InfosBaseSasAspectModele.DATE_INFO_SAS, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:dateInfoSas' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateInfoSasValide() { 
		return (this.getDateInfoSas() != null);	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:messageInfoSas'. 
	 * @return String La valeur de la propriété 'erreur59:messageInfoSas'. */
	public String getMessageInfoSas() { 
		return (String) this.getPropriete(InfosBaseSasAspectModele.MESSAGE_INFO_SAS);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:messageInfoSas'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:messageInfoSas'. */ 
	public void setMessageInfoSas(String valeur) { 
		this.majPropriete(InfosBaseSasAspectModele.MESSAGE_INFO_SAS, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:messageInfoSas' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estMessageInfoSasValide() { 
		return ( this.getMessageInfoSas() != null && !this.getMessageInfoSas().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:etatInfoSas'. 
	 * @return String La valeur de la propriété 'erreur59:etatInfoSas'. */
	public String getEtatInfoSas() { 
		return (String) this.getPropriete(InfosBaseSasAspectModele.ETAT_INFO_SAS);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:etatInfoSas'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:etatInfoSas'. */ 
	public void setEtatInfoSas(String valeur) { 
		this.majPropriete(InfosBaseSasAspectModele.ETAT_INFO_SAS, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:etatInfoSas' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estEtatInfoSasValide() { 
		return ( this.getEtatInfoSas() != null && !this.getEtatInfoSas().isBlank());
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'erreur59:infosBaseSas'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estDateInfoSasValide()
		&& this.estMessageInfoSasValide()
		&& this.estEtatInfoSasValide());
	}

}

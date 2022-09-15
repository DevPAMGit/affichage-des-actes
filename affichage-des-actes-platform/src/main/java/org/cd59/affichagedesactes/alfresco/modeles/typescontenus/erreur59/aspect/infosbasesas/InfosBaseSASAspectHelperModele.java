package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosbasesas ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.ServiceRegistry;import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link InfosBaseSASAspectHelperModele}.*/
public class InfosBaseSASAspectHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link InfosBaseSASAspectHelperModele}. 
	 * @param serviceRegistry Le registre des services. 
	 * @param noeud Le nœud de référence. */
	public InfosBaseSASAspectHelperModele(ServiceRegistry serviceRegistry, NodeRef noeud){
		super(serviceRegistry, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(InfosBaseSASAspectModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, InfosBaseSASAspectModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, InfosBaseSASAspectModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(InfosBaseSASAspectModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(InfosBaseSASAspectModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:dateInfoSAS'. 
	 * @return int La valeur de la propriété 'erreur59:dateInfoSAS'. */
	public int getDateInfoSAS() { 
		return (int) this.getPropriete(InfosBaseSASAspectModele.DATE_INFO_SA_S);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:dateInfoSAS'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:dateInfoSAS'. */ 
	public void setDateInfoSAS(int valeur) { 
		this.majPropriete(InfosBaseSASAspectModele.DATE_INFO_SA_S, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:dateInfoSAS' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateInfoSASValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:messageInfoSAS'. 
	 * @return String La valeur de la propriété 'erreur59:messageInfoSAS'. */
	public String getMessageInfoSAS() { 
		return (String) this.getPropriete(InfosBaseSASAspectModele.MESSAGE_INFO_SA_S);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:messageInfoSAS'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:messageInfoSAS'. */ 
	public void setMessageInfoSAS(String valeur) { 
		this.majPropriete(InfosBaseSASAspectModele.MESSAGE_INFO_SA_S, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:messageInfoSAS' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estMessageInfoSASValide() { 
		return ( this.getMessageInfoSAS() != null && !this.getMessageInfoSAS().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:etatInfoSAS'. 
	 * @return String La valeur de la propriété 'erreur59:etatInfoSAS'. */
	public String getEtatInfoSAS() { 
		return (String) this.getPropriete(InfosBaseSASAspectModele.ETAT_INFO_SA_S);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'erreur59:etatInfoSAS'. 
	 * @param valeur La nouvelle valeur de la propriété 'erreur59:etatInfoSAS'. */ 
	public void setEtatInfoSAS(String valeur) { 
		this.majPropriete(InfosBaseSASAspectModele.ETAT_INFO_SA_S, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'erreur59:etatInfoSAS' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estEtatInfoSASValide() { 
		return ( this.getEtatInfoSAS() != null && !this.getEtatInfoSAS().isBlank());
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'erreur59:infosBaseSAS'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estDateInfoSASValide()
		&& this.estMessageInfoSASValide()
		&& this.estEtatInfoSASValide());
	}

}

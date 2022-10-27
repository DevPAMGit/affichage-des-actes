package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.ServiceRegistry;import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link DossierinfosAspectHelperModele}.*/
public class DossierinfosAspectHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link DossierinfosAspectHelperModele}. 
	 * @param serviceRegistry Le registre des services. 
	 * @param noeud Le nœud de référence. */
	public DossierinfosAspectHelperModele(ServiceRegistry serviceRegistry, NodeRef noeud){
		super(serviceRegistry, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(DossierinfosAspectModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, DossierinfosAspectModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, DossierinfosAspectModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(DossierinfosAspectModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(DossierinfosAspectModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:resume'. 
	 * @return String La valeur de la propriété 'actes59:resume'. */
	public String getResume() { 
		return (String) this.getPropriete(DossierinfosAspectModele.RESUME);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:resume'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:resume'. */ 
	public void setResume(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.RESUME, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:erreurinternet'. 
	 * @return String La valeur de la propriété 'actes59:erreurinternet'. */
	public String getErreurinternet() { 
		return (String) this.getPropriete(DossierinfosAspectModele.ERREURINTERNET);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:erreurinternet'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:erreurinternet'. */ 
	public void setErreurinternet(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.ERREURINTERNET, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:signataire'. 
	 * @return String La valeur de la propriété 'actes59:signataire'. */
	public String getSignataire() { 
		return (String) this.getPropriete(DossierinfosAspectModele.SIGNATAIRE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:signataire'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:signataire'. */ 
	public void setSignataire(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.SIGNATAIRE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:signataire' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estSignataireValide() { 
		return ( this.getSignataire() != null && !this.getSignataire().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossiercomplet'. 
	 * @return boolean La valeur de la propriété 'actes59:dossiercomplet'. */
	public boolean getDossiercomplet() { 
		return (boolean) this.getPropriete(DossierinfosAspectModele.DOSSIERCOMPLET);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossiercomplet'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossiercomplet'. */ 
	public void setDossiercomplet(boolean valeur) { 
		this.majPropriete(DossierinfosAspectModele.DOSSIERCOMPLET, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:source'. 
	 * @return String La valeur de la propriété 'actes59:source'. */
	public String getSource() { 
		return (String) this.getPropriete(DossierinfosAspectModele.SOURCE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:source'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:source'. */ 
	public void setSource(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.SOURCE, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:annee'. 
	 * @return String La valeur de la propriété 'actes59:annee'. */
	public String getAnnee() { 
		return (String) this.getPropriete(DossierinfosAspectModele.ANNEE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:annee'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:annee'. */ 
	public void setAnnee(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.ANNEE, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:statutaffichage'. 
	 * @return boolean La valeur de la propriété 'actes59:statutaffichage'. */
	public boolean getStatutaffichage() { 
		return (boolean) this.getPropriete(DossierinfosAspectModele.STATUTAFFICHAGE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:statutaffichage'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:statutaffichage'. */ 
	public void setStatutaffichage(boolean valeur) { 
		this.majPropriete(DossierinfosAspectModele.STATUTAFFICHAGE, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:objet'. 
	 * @return String La valeur de la propriété 'actes59:objet'. */
	public String getObjet() { 
		return (String) this.getPropriete(DossierinfosAspectModele.OBJET);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:objet'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:objet'. */ 
	public void setObjet(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.OBJET, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:objet' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estObjetValide() { 
		return ( this.getObjet() != null && !this.getObjet().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dateaffichageged'. 
	 * @return Date La valeur de la propriété 'actes59:dateaffichageged'. */
	public Date getDateaffichageged() { 
		return (Date) this.getPropriete(DossierinfosAspectModele.DATEAFFICHAGEGED);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dateaffichageged'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dateaffichageged'. */ 
	public void setDateaffichageged(Date valeur) { 
		this.majPropriete(DossierinfosAspectModele.DATEAFFICHAGEGED, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:orgasigle'. 
	 * @return String La valeur de la propriété 'actes59:orgasigle'. */
	public String getOrgasigle() { 
		return (String) this.getPropriete(DossierinfosAspectModele.ORGASIGLE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:orgasigle'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:orgasigle'. */ 
	public void setOrgasigle(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.ORGASIGLE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:orgasigle' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estOrgasigleValide() { 
		return ( this.getOrgasigle() != null && !this.getOrgasigle().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:numeroacte'. 
	 * @return String La valeur de la propriété 'actes59:numeroacte'. */
	public String getNumeroacte() { 
		return (String) this.getPropriete(DossierinfosAspectModele.NUMEROACTE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:numeroacte'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:numeroacte'. */ 
	public void setNumeroacte(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.NUMEROACTE, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:typedossier'. 
	 * @return String La valeur de la propriété 'actes59:typedossier'. */
	public String getTypedossier() { 
		return (String) this.getPropriete(DossierinfosAspectModele.TYPEDOSSIER);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:typedossier'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:typedossier'. */ 
	public void setTypedossier(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.TYPEDOSSIER, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:typedossier' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estTypedossierValide() { 
		return ( this.getTypedossier() != null && !this.getTypedossier().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:urlaffichage'. 
	 * @return String La valeur de la propriété 'actes59:urlaffichage'. */
	public String getUrlaffichage() { 
		return (String) this.getPropriete(DossierinfosAspectModele.URLAFFICHAGE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:urlaffichage'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:urlaffichage'. */ 
	public void setUrlaffichage(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.URLAFFICHAGE, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:iddossier'. 
	 * @return String La valeur de la propriété 'actes59:iddossier'. */
	public String getIddossier() { 
		return (String) this.getPropriete(DossierinfosAspectModele.IDDOSSIER);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:iddossier'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:iddossier'. */ 
	public void setIddossier(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.IDDOSSIER, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dateaffichageinternet'. 
	 * @return Date La valeur de la propriété 'actes59:dateaffichageinternet'. */
	public Date getDateaffichageinternet() { 
		return (Date) this.getPropriete(DossierinfosAspectModele.DATEAFFICHAGEINTERNET);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dateaffichageinternet'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dateaffichageinternet'. */ 
	public void setDateaffichageinternet(Date valeur) { 
		this.majPropriete(DossierinfosAspectModele.DATEAFFICHAGEINTERNET, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:datedossier'. 
	 * @return Date La valeur de la propriété 'actes59:datedossier'. */
	public Date getDatedossier() { 
		return (Date) this.getPropriete(DossierinfosAspectModele.DATEDOSSIER);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:datedossier'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:datedossier'. */ 
	public void setDatedossier(Date valeur) { 
		this.majPropriete(DossierinfosAspectModele.DATEDOSSIER, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:datedossier' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDatedossierValide() { 
		return (this.getDatedossier() != null);	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:estEnRefMultiple'. 
	 * @return boolean La valeur de la propriété 'actes59:estEnRefMultiple'. */
	public boolean getEstEnRefMultiple() { 
		return (boolean) this.getPropriete(DossierinfosAspectModele.EST_EN_REF_MULTIPLE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:estEnRefMultiple'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:estEnRefMultiple'. */ 
	public void setEstEnRefMultiple(boolean valeur) { 
		this.majPropriete(DossierinfosAspectModele.EST_EN_REF_MULTIPLE, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:etatStockageDossier'. 
	 * @return String La valeur de la propriété 'actes59:etatStockageDossier'. */
	public String getEtatStockageDossier() { 
		return (String) this.getPropriete(DossierinfosAspectModele.ETAT_STOCKAGE_DOSSIER);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:etatStockageDossier'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:etatStockageDossier'. */ 
	public void setEtatStockageDossier(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.ETAT_STOCKAGE_DOSSIER, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:etatEnvoiDossier'. 
	 * @return String La valeur de la propriété 'actes59:etatEnvoiDossier'. */
	public String getEtatEnvoiDossier() { 
		return (String) this.getPropriete(DossierinfosAspectModele.ETAT_ENVOI_DOSSIER);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:etatEnvoiDossier'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:etatEnvoiDossier'. */ 
	public void setEtatEnvoiDossier(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.ETAT_ENVOI_DOSSIER, valeur);
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'actes59:dossierinfos'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estSignataireValide()
		&& this.estObjetValide()
		&& this.estOrgasigleValide()
		&& this.estTypedossierValide()
		&& this.estDatedossierValide());
	}

}

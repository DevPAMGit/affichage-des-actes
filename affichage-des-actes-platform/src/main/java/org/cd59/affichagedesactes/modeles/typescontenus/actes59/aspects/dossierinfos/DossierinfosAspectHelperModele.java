package org.cd59.affichagedesactes.modeles.typescontenus.actes59.aspects.dossierinfos;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour l'aspect 'actes59:dossierinfos'. */
 public class DossierinfosAspectHelperModele extends AlfrescoModeleHelper {

 	/** Initialise une nouvelle instance de la classe {@link DossierinfosAspectHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public DossierinfosAspectHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède l'aspect désigné en paramètre.
	 * @return <c>true</c> si l'aspect est présent, sinon <c>false</c>. */ 
	public boolean hasAspect(){ 
		return this.hasAspect(DossierinfosAspectModele.NOM);
	}

	/** Supprime un aspect du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, DossierinfosAspectModele.NOM); 
	}

	/** Ajoute un aspect à un nœud.
	 * @param valeurs Les valeurs de l'aspect à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, DossierinfosAspectModele.NOM, valeurs);
;	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return String La valeur de la propriété 'actes59:dossierinfos'.  */
	public String getResume() { 
		return (String) this.getPropriete(DossierinfosAspectModele.RESUME);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setResume(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.RESUME, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return String La valeur de la propriété 'actes59:dossierinfos'.  */
	public String getErreurinternet() { 
		return (String) this.getPropriete(DossierinfosAspectModele.ERREURINTERNET);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setErreurinternet(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.ERREURINTERNET, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return String La valeur de la propriété 'actes59:dossierinfos'.  */
	public String getSignataire() { 
		return (String) this.getPropriete(DossierinfosAspectModele.SIGNATAIRE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setSignataire(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.SIGNATAIRE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:dossierinfos' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estSignataireValide() { 
		return ( this.getSignataire() != null && !this.getSignataire().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return boolean La valeur de la propriété 'actes59:dossierinfos'.  */
	public boolean getDossiercomplet() { 
		return (boolean) this.getPropriete(DossierinfosAspectModele.DOSSIERCOMPLET);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setDossiercomplet(boolean valeur) { 
		this.majPropriete(DossierinfosAspectModele.DOSSIERCOMPLET, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return String La valeur de la propriété 'actes59:dossierinfos'.  */
	public String getSource() { 
		return (String) this.getPropriete(DossierinfosAspectModele.SOURCE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setSource(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.SOURCE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:dossierinfos' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estSourceValide() { 
		return ( this.getSource() != null && !this.getSource().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return String La valeur de la propriété 'actes59:dossierinfos'.  */
	public String getAnnee() { 
		return (String) this.getPropriete(DossierinfosAspectModele.ANNEE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setAnnee(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.ANNEE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:dossierinfos' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estAnneeValide() { 
		return ( this.getAnnee() != null && !this.getAnnee().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return boolean La valeur de la propriété 'actes59:dossierinfos'.  */
	public boolean getStatutaffichage() { 
		return (boolean) this.getPropriete(DossierinfosAspectModele.STATUTAFFICHAGE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setStatutaffichage(boolean valeur) { 
		this.majPropriete(DossierinfosAspectModele.STATUTAFFICHAGE, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return String La valeur de la propriété 'actes59:dossierinfos'.  */
	public String getObjet() { 
		return (String) this.getPropriete(DossierinfosAspectModele.OBJET);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setObjet(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.OBJET, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:dossierinfos' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estObjetValide() { 
		return ( this.getObjet() != null && !this.getObjet().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return Date La valeur de la propriété 'actes59:dossierinfos'.  */
	public Date getDateaffichageged() { 
		return (Date) this.getPropriete(DossierinfosAspectModele.DATEAFFICHAGEGED);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setDateaffichageged(Date valeur) { 
		this.majPropriete(DossierinfosAspectModele.DATEAFFICHAGEGED, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return String La valeur de la propriété 'actes59:dossierinfos'.  */
	public String getOrgasigle() { 
		return (String) this.getPropriete(DossierinfosAspectModele.ORGASIGLE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setOrgasigle(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.ORGASIGLE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:dossierinfos' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estOrgasigleValide() { 
		return ( this.getOrgasigle() != null && !this.getOrgasigle().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return String La valeur de la propriété 'actes59:dossierinfos'.  */
	public String getNumeroacte() { 
		return (String) this.getPropriete(DossierinfosAspectModele.NUMEROACTE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setNumeroacte(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.NUMEROACTE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:dossierinfos' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estNumeroacteValide() { 
		return ( this.getNumeroacte() != null && !this.getNumeroacte().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return String La valeur de la propriété 'actes59:dossierinfos'.  */
	public String getTypedossier() { 
		return (String) this.getPropriete(DossierinfosAspectModele.TYPEDOSSIER);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setTypedossier(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.TYPEDOSSIER, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:dossierinfos' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estTypedossierValide() { 
		return ( this.getTypedossier() != null && !this.getTypedossier().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return String La valeur de la propriété 'actes59:dossierinfos'.  */
	public String getUrlaffichage() { 
		return (String) this.getPropriete(DossierinfosAspectModele.URLAFFICHAGE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setUrlaffichage(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.URLAFFICHAGE, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return String La valeur de la propriété 'actes59:dossierinfos'.  */
	public String getIddossier() { 
		return (String) this.getPropriete(DossierinfosAspectModele.IDDOSSIER);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setIddossier(String valeur) { 
		this.majPropriete(DossierinfosAspectModele.IDDOSSIER, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:dossierinfos' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estIddossierValide() { 
		return ( this.getIddossier() != null && !this.getIddossier().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return Date La valeur de la propriété 'actes59:dossierinfos'.  */
	public Date getDateaffichageinternet() { 
		return (Date) this.getPropriete(DossierinfosAspectModele.DATEAFFICHAGEINTERNET);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setDateaffichageinternet(Date valeur) { 
		this.majPropriete(DossierinfosAspectModele.DATEAFFICHAGEINTERNET, valeur);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @return Date La valeur de la propriété 'actes59:dossierinfos'.  */
	public Date getDatedossier() { 
		return (Date) this.getPropriete(DossierinfosAspectModele.DATEDOSSIER);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'actes59:dossierinfos'. 
	 * @param valeur La nouvelle valeur de la propriété 'actes59:dossierinfos'. */ 
	public void setDatedossier(Date valeur) { 
		this.majPropriete(DossierinfosAspectModele.DATEDOSSIER, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'actes59:dossierinfos' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDatedossierValide() { 
		return (this.getDatedossier() != null);	}

	/** Méthode permettant de vérifier la validité de l'aspect 'actes59:dossierinfos'. 
	 * @return <c>true</c> si l'aspect à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		         && this.estSignataireValide()
		         && this.estSourceValide()
		         && this.estAnneeValide()
		         && this.estObjetValide()
		         && this.estOrgasigleValide()
		         && this.estNumeroacteValide()
		         && this.estTypedossierValide()
		         && this.estIddossierValide()
		         && this.estDatedossierValide()
		);
	}

}

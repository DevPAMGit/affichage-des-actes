package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossieracte ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.ServiceRegistry;import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link DossierActeTypeHelperModele}.*/
public class DossierActeTypeHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link DossierActeTypeHelperModele}. 
	 * @param serviceRegistry Le registre des services. 
	 * @param noeud Le nœud de référence. */
	public DossierActeTypeHelperModele(ServiceRegistry serviceRegistry, NodeRef noeud){
		super(serviceRegistry, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(DossierActeTypeModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, DossierActeTypeModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, DossierActeTypeModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(DossierActeTypeModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(DossierActeTypeModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:identifiant'. 
	 * @return String La valeur de la propriété 'stockageactes59:identifiant'. */
	public String getIdentifiant() { 
		return (String) this.getPropriete(DossierActeTypeModele.IDENTIFIANT);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:identifiant'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:identifiant'. */ 
	public void setIdentifiant(String valeur) { 
		this.majPropriete(DossierActeTypeModele.IDENTIFIANT, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:identifiant' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estIdentifiantValide() { 
		return ( this.getIdentifiant() != null && !this.getIdentifiant().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:sigleDirection'. 
	 * @return String La valeur de la propriété 'stockageactes59:sigleDirection'. */
	public String getSigleDirection() { 
		return (String) this.getPropriete(DossierActeTypeModele.SIGLE_DIRECTION);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:sigleDirection'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:sigleDirection'. */ 
	public void setSigleDirection(String valeur) { 
		this.majPropriete(DossierActeTypeModele.SIGLE_DIRECTION, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:sigleDirection' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estSigleDirectionValide() { 
		return ( this.getSigleDirection() != null && !this.getSigleDirection().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:objet'. 
	 * @return String La valeur de la propriété 'stockageactes59:objet'. */
	public String getObjet() { 
		return (String) this.getPropriete(DossierActeTypeModele.OBJET);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:objet'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:objet'. */ 
	public void setObjet(String valeur) { 
		this.majPropriete(DossierActeTypeModele.OBJET, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:objet' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estObjetValide() { 
		return ( this.getObjet() != null && !this.getObjet().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:date'. 
	 * @return Date La valeur de la propriété 'stockageactes59:date'. */
	public Date getDate() { 
		return (Date) this.getPropriete(DossierActeTypeModele.DATE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:date'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:date'. */ 
	public void setDate(Date valeur) { 
		this.majPropriete(DossierActeTypeModele.DATE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:date' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateValide() { 
		return (this.getDate() != null);	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:signataire'. 
	 * @return String La valeur de la propriété 'stockageactes59:signataire'. */
	public String getSignataire() { 
		return (String) this.getPropriete(DossierActeTypeModele.SIGNATAIRE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:signataire'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:signataire'. */ 
	public void setSignataire(String valeur) { 
		this.majPropriete(DossierActeTypeModele.SIGNATAIRE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:signataire' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estSignataireValide() { 
		return ( this.getSignataire() != null && !this.getSignataire().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:typologieDossier'. 
	 * @return String La valeur de la propriété 'stockageactes59:typologieDossier'. */
	public String getTypologieDossier() { 
		return (String) this.getPropriete(DossierActeTypeModele.TYPOLOGIE_DOSSIER);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:typologieDossier'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:typologieDossier'. */ 
	public void setTypologieDossier(String valeur) { 
		this.majPropriete(DossierActeTypeModele.TYPOLOGIE_DOSSIER, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:typologieDossier' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estTypologieDossierValide() { 
		return ( this.getTypologieDossier() != null && !this.getTypologieDossier().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:jour'. 
	 * @return int La valeur de la propriété 'stockageactes59:jour'. */
	public int getJour() { 
		return (int) this.getPropriete(DossierActeTypeModele.JOUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:jour'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:jour'. */ 
	public void setJour(int valeur) { 
		this.majPropriete(DossierActeTypeModele.JOUR, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:jour' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estJourValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:mois'. 
	 * @return int La valeur de la propriété 'stockageactes59:mois'. */
	public int getMois() { 
		return (int) this.getPropriete(DossierActeTypeModele.MOIS);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:mois'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:mois'. */ 
	public void setMois(int valeur) { 
		this.majPropriete(DossierActeTypeModele.MOIS, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:mois' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estMoisValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:annee'. 
	 * @return int La valeur de la propriété 'stockageactes59:annee'. */
	public int getAnnee() { 
		return (int) this.getPropriete(DossierActeTypeModele.ANNEE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:annee'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:annee'. */ 
	public void setAnnee(int valeur) { 
		this.majPropriete(DossierActeTypeModele.ANNEE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:annee' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estAnneeValide() { 
		return true;
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'stockageactes59:dossierActe'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estIdentifiantValide()
		&& this.estSigleDirectionValide()
		&& this.estObjetValide()
		&& this.estDateValide()
		&& this.estSignataireValide()
		&& this.estTypologieDossierValide()
		&& this.estJourValide()
		&& this.estMoisValide()
		&& this.estAnneeValide());
	}

}

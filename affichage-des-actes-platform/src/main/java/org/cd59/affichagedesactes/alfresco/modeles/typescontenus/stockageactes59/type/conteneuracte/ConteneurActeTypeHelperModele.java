package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuracte ;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Classe modèle d'aide personnalisée pour le type de contenu {@link ConteneurActeTypeHelperModele}.*/
public class ConteneurActeTypeHelperModele extends AlfrescoModeleHelper {

	/** Initialise une nouvelle instance de la classe {@link ConteneurActeTypeHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public ConteneurActeTypeHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède le type de contenu désigné en paramètre.
	 * @return <c>true</c> si le type de contenu est présent, sinon <c>false</c>. */
	public boolean hasAspect() { 
		return this.hasAspect(ConteneurActeTypeModele.NOM);
	}

	/** Supprime un type de contenu du nœud. */
	public void supprimeAspect() { 
		this.supprimeAspect(this.noeud, ConteneurActeTypeModele.NOM); 
	}

	/** Ajoute un type de contenu à un nœud.
	 * @param valeurs Les valeurs du type de contenu à sa création. */
	public void addAspect(Map<QName,Serializable> valeurs) {
		this.addAspect(this.noeud, ConteneurActeTypeModele.NOM, valeurs);
	}

	/**
	* Vérifie si le nœud du modèle est du type en en paramètre.* 
	@return <c>true</c> si le nœud est du type en paramètre sinon <c>false</c>.
	*/
	public boolean hasType() {
		return this.hasType(ConteneurActeTypeModele.NOM);
	}

	/**
	* Ajoute le type au nœud.
	*/
	public void addType(){
		this.addType(ConteneurActeTypeModele.NOM);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:stockageIdentifiant'. 
	 * @return String La valeur de la propriété 'stockageactes59:stockageIdentifiant'. */
	public String getStockageIdentifiant() { 
		return (String) this.getPropriete(ConteneurActeTypeModele.STOCKAGE_IDENTIFIANT);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:stockageIdentifiant'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:stockageIdentifiant'. */ 
	public void setStockageIdentifiant(String valeur) { 
		this.majPropriete(ConteneurActeTypeModele.STOCKAGE_IDENTIFIANT, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:stockageIdentifiant' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estStockageIdentifiantValide() { 
		return ( this.getStockageIdentifiant() != null && !this.getStockageIdentifiant().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:stockageDirectionSigle'. 
	 * @return String La valeur de la propriété 'stockageactes59:stockageDirectionSigle'. */
	public String getStockageDirectionSigle() { 
		return (String) this.getPropriete(ConteneurActeTypeModele.STOCKAGE_DIRECTION_SIGLE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:stockageDirectionSigle'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:stockageDirectionSigle'. */ 
	public void setStockageDirectionSigle(String valeur) { 
		this.majPropriete(ConteneurActeTypeModele.STOCKAGE_DIRECTION_SIGLE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:stockageDirectionSigle' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estStockageDirectionSigleValide() { 
		return ( this.getStockageDirectionSigle() != null && !this.getStockageDirectionSigle().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:stockageObjet'. 
	 * @return String La valeur de la propriété 'stockageactes59:stockageObjet'. */
	public String getStockageObjet() { 
		return (String) this.getPropriete(ConteneurActeTypeModele.STOCKAGE_OBJET);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:stockageObjet'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:stockageObjet'. */ 
	public void setStockageObjet(String valeur) { 
		this.majPropriete(ConteneurActeTypeModele.STOCKAGE_OBJET, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:stockageObjet' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estStockageObjetValide() { 
		return ( this.getStockageObjet() != null && !this.getStockageObjet().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:stockageDate'. 
	 * @return Date La valeur de la propriété 'stockageactes59:stockageDate'. */
	public Date getStockageDate() { 
		return (Date) this.getPropriete(ConteneurActeTypeModele.STOCKAGE_DATE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:stockageDate'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:stockageDate'. */ 
	public void setStockageDate(Date valeur) { 
		this.majPropriete(ConteneurActeTypeModele.STOCKAGE_DATE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:stockageDate' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estStockageDateValide() { 
		return (this.getStockageDate() != null);	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:stockageSignataire'. 
	 * @return String La valeur de la propriété 'stockageactes59:stockageSignataire'. */
	public String getStockageSignataire() { 
		return (String) this.getPropriete(ConteneurActeTypeModele.STOCKAGE_SIGNATAIRE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:stockageSignataire'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:stockageSignataire'. */ 
	public void setStockageSignataire(String valeur) { 
		this.majPropriete(ConteneurActeTypeModele.STOCKAGE_SIGNATAIRE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:stockageSignataire' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estStockageSignataireValide() { 
		return ( this.getStockageSignataire() != null && !this.getStockageSignataire().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:conteneurActeType'. 
	 * @return String La valeur de la propriété 'stockageactes59:conteneurActeType'. */
	public String getConteneurActeType() { 
		return (String) this.getPropriete(ConteneurActeTypeModele.CONTENEUR_ACTE_TYPE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:conteneurActeType'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:conteneurActeType'. */ 
	public void setConteneurActeType(String valeur) { 
		this.majPropriete(ConteneurActeTypeModele.CONTENEUR_ACTE_TYPE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:conteneurActeType' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estConteneurActeTypeValide() { 
		return ( this.getConteneurActeType() != null && !this.getConteneurActeType().isBlank());
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateJour'. 
	 * @return int La valeur de la propriété 'stockageactes59:dateJour'. */
	public int getDateJour() { 
		return (int) this.getPropriete(ConteneurActeTypeModele.DATE_JOUR);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateJour'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:dateJour'. */ 
	public void setDateJour(int valeur) { 
		this.majPropriete(ConteneurActeTypeModele.DATE_JOUR, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:dateJour' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateJourValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateMois'. 
	 * @return int La valeur de la propriété 'stockageactes59:dateMois'. */
	public int getDateMois() { 
		return (int) this.getPropriete(ConteneurActeTypeModele.DATE_MOIS);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateMois'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:dateMois'. */ 
	public void setDateMois(int valeur) { 
		this.majPropriete(ConteneurActeTypeModele.DATE_MOIS, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:dateMois' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateMoisValide() { 
		return true;
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateAnnee'. 
	 * @return int La valeur de la propriété 'stockageactes59:dateAnnee'. */
	public int getDateAnnee() { 
		return (int) this.getPropriete(ConteneurActeTypeModele.DATE_ANNEE);
	}

	/** Méthode permettant de récupérer la valeur de la propriété 'stockageactes59:dateAnnee'. 
	 * @param valeur La nouvelle valeur de la propriété 'stockageactes59:dateAnnee'. */ 
	public void setDateAnnee(int valeur) { 
		this.majPropriete(ConteneurActeTypeModele.DATE_ANNEE, valeur);
	}

	/** Méthode permettant de vérifier si la valeur de la propriété 'stockageactes59:dateAnnee' est valide. 
	 * @return <c>true</c> si la valeur est valide; sinon <c>false</c>'.  */
	public boolean estDateAnneeValide() { 
		return true;
	}

	/** Méthode permettant de vérifier la validité de le type de contenu 'stockageactes59:conteneurActe'. 
	 * @return <c>true</c> si le type de contenu à toutes ces propriétés valides sinon <c>false</c>. */ 
	public boolean estAspectValide() { 
		return ( this.hasAspect()
		&& this.estStockageIdentifiantValide()
		&& this.estStockageDirectionSigleValide()
		&& this.estStockageObjetValide()
		&& this.estStockageDateValide()
		&& this.estStockageSignataireValide()
		&& this.estConteneurActeTypeValide()
		&& this.estDateJourValide()
		&& this.estDateMoisValide()
		&& this.estDateAnneeValide());
	}

}

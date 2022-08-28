package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuracte ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link ConteneurActeTypeModele}. */
public class ConteneurActeTypeModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "stockageactes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/stockageacte/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "conteneurActe");

	/** Modèle pour la propriété 'stockageactes59:stockageIdentifiant'. */
	public final static QName STOCKAGE_IDENTIFIANT = QName.createQName( URI , "stockageIdentifiant");

	/** Modèle pour la propriété 'stockageactes59:stockageDirectionSigle'. */
	public final static QName STOCKAGE_DIRECTION_SIGLE = QName.createQName( URI , "stockageDirectionSigle");

	/** Modèle pour la propriété 'stockageactes59:stockageObjet'. */
	public final static QName STOCKAGE_OBJET = QName.createQName( URI , "stockageObjet");

	/** Modèle pour la propriété 'stockageactes59:stockageDate'. */
	public final static QName STOCKAGE_DATE = QName.createQName( URI , "stockageDate");

	/** Modèle pour la propriété 'stockageactes59:stockageSignataire'. */
	public final static QName STOCKAGE_SIGNATAIRE = QName.createQName( URI , "stockageSignataire");

	/** Modèle pour la propriété 'stockageactes59:conteneurActeType'. */
	public final static QName CONTENEUR_ACTE_TYPE = QName.createQName( URI , "conteneurActeType");

	/** Modèle pour la propriété 'stockageactes59:dateJour'. */
	public final static QName DATE_JOUR = QName.createQName( URI , "dateJour");

	/** Modèle pour la propriété 'stockageactes59:dateMois'. */
	public final static QName DATE_MOIS = QName.createQName( URI , "dateMois");

	/** Modèle pour la propriété 'stockageactes59:dateAnnee'. */
	public final static QName DATE_ANNEE = QName.createQName( URI , "dateAnnee");

}
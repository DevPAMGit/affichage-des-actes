package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossieracte ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link DossierActeTypeModele}. */
public class DossierActeTypeModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "stockageactes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/stockageacte/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "dossierActe");

	/** Modèle pour la propriété 'stockageactes59:identifiant'. */
	public final static QName IDENTIFIANT = QName.createQName( URI , "identifiant");

	/** Modèle pour la propriété 'stockageactes59:sigleDirection'. */
	public final static QName SIGLE_DIRECTION = QName.createQName( URI , "sigleDirection");

	/** Modèle pour la propriété 'stockageactes59:objet'. */
	public final static QName OBJET = QName.createQName( URI , "objet");

	/** Modèle pour la propriété 'stockageactes59:date'. */
	public final static QName DATE = QName.createQName( URI , "date");

	/** Modèle pour la propriété 'stockageactes59:signataire'. */
	public final static QName SIGNATAIRE = QName.createQName( URI , "signataire");

	/** Modèle pour la propriété 'stockageactes59:typologieDossier'. */
	public final static QName TYPOLOGIE_DOSSIER = QName.createQName( URI , "typologieDossier");

	/** Modèle pour la propriété 'stockageactes59:jour'. */
	public final static QName JOUR = QName.createQName( URI , "jour");

	/** Modèle pour la propriété 'stockageactes59:mois'. */
	public final static QName MOIS = QName.createQName( URI , "mois");

	/** Modèle pour la propriété 'stockageactes59:annee'. */
	public final static QName ANNEE = QName.createQName( URI , "annee");

}
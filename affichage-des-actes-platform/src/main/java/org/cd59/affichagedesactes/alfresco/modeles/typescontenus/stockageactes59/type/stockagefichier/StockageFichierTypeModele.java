package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.stockagefichier ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link StockageFichierTypeModele}. */
public class StockageFichierTypeModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "stockageactes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/stockageacte/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "stockageFichier");

	/** Modèle pour la propriété 'stockageactes59:typeFichier'. */
	public final static QName TYPE_FICHIER = QName.createQName( URI , "typeFichier");

	/** Modèle pour la propriété 'stockageactes59:empreinteFichier'. */
	public final static QName EMPREINTE_FICHIER = QName.createQName( URI , "empreinteFichier");

}
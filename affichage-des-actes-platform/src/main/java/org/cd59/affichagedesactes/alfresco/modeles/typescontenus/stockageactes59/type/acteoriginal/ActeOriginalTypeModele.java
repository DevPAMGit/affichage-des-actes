package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.acteoriginal ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link ActeOriginalTypeModele}. */
public class ActeOriginalTypeModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "stockageactes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/stockageacte/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "acteOriginal");

	/** Modèle pour la propriété 'stockageactes59:typologieFichier'. */
	public final static QName TYPOLOGIE_FICHIER = QName.createQName( URI , "typologieFichier");

	/** Modèle pour la propriété 'stockageactes59:empreinte'. */
	public final static QName EMPREINTE = QName.createQName( URI , "empreinte");

}
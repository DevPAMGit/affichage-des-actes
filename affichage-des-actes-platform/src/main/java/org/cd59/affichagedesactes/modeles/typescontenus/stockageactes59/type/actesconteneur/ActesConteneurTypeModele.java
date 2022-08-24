package org.cd59.affichagedesactes.modeles.typescontenus.stockageactes59.type.actesconteneur ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link ActesConteneurTypeModele}. */
public class ActesConteneurTypeModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "stockageactes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/stockageacte/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "actesConteneur");

}
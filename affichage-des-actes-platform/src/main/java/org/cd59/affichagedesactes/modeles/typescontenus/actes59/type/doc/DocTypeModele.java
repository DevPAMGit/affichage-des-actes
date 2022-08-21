package org.cd59.affichagedesactes.modeles.typescontenus.actes59.type.doc ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link DocTypeModele}. */
public class DocTypeModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "actes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/actes/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "doc");

}
package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.type.dossier ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link DossierTypeModele}. */
public class DossierTypeModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "actes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/actes/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "dossier");

}
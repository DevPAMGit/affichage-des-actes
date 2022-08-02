package org.cd59.affichagedesactes.modeles.typescontenus.actes59.types.dossier;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type 'actes59:dossier'. */
 public class DossierTypeModele {

 	/** Le prefix du type de contenu. */
	public final static String PREFIX = "actes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/actes/1.0.";

	/** Le nom du type. */
	public final static QName NOM = QName.createQName( URI , "dossier");

}

package org.cd59.affichagedesactes.modeles.typescontenus.actes59.aspect.docinfos ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link DocinfosAspectModele}. */
public class DocinfosAspectModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "actes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/actes/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "docinfos");

	/** Modèle pour la propriété 'actes59:empreinte'. */
	public final static QName EMPREINTE = QName.createQName( URI , "empreinte");

	/** Modèle pour la propriété 'actes59:typedocument'. */
	public final static QName TYPEDOCUMENT = QName.createQName( URI , "typedocument");

}
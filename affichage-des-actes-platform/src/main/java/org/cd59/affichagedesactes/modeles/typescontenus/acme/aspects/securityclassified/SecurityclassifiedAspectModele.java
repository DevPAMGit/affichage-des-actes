package org.cd59.affichagedesactes.modeles.typescontenus.acme.aspects.securityclassified;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour l'aspect 'acme:securityClassified'. */
 public class SecurityclassifiedAspectModele {

 	/** Le prefix du type de contenu. */
	public final static String PREFIX = "acme";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://www.acme.org/model/content/1.0";

	/** Le nom de l'aspect. */
	public final static QName NOM = QName.createQName( URI , "securityClassified");

	/** Modèle pour la propriété 'acme:securityClassification'. */
	public final static QName SECURITYCLASSIFICATION = QName.createQName( URI , "securityClassification");

}

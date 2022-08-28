package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.acme.aspect.securityclassified ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link SecurityClassifiedAspectModele}. */
public class SecurityClassifiedAspectModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "acme";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://www.acme.org/model/content/1.0";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "securityClassified");

	/** Modèle pour la propriété 'acme:securityClassification'. */
	public final static QName SECURITY_CLASSIFICATION = QName.createQName( URI , "securityClassification");

}
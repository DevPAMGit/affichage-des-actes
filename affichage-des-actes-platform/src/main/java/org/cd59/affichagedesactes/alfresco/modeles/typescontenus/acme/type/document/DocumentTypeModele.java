package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.acme.type.document ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link DocumentTypeModele}. */
public class DocumentTypeModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "acme";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://www.acme.org/model/content/1.0";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "document");

	/** Modèle pour la propriété 'acme:documentId'. */
	public final static QName DOCUMENT_ID = QName.createQName( URI , "documentId");

	/** Modèle pour la propriété 'acme:securityClassification'. */
	public final static QName SECURITY_CLASSIFICATION = QName.createQName( URI , "securityClassification");

}
package org.cd59.affichagedesactes.modele.alfresco.type;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link SasTypeModele}. */
public class SasTypeModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "actes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/actes/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "sas");

	/** Modèle pour la propriété 'actes59:compteur'. */
	public final static QName COMPTEUR = QName.createQName( URI , "compteur");

}
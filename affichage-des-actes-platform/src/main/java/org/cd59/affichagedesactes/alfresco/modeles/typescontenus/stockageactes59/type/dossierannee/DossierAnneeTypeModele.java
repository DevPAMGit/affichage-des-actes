package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierannee ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link DossierAnneeTypeModele}. */
public class DossierAnneeTypeModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "stockageactes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/stockageacte/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "dossierAnnee");

	/** Modèle pour la propriété 'stockageactes59:annee'. */
	public final static QName ANNEE = QName.createQName( URI , "annee");

}
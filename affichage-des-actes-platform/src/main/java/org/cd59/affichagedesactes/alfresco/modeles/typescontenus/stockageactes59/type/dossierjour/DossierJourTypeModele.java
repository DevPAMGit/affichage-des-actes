package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierjour ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link DossierJourTypeModele}. */
public class DossierJourTypeModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "stockageactes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/stockageacte/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "dossierJour");

	/** Modèle pour la propriété 'stockageactes59:jour'. */
	public final static QName JOUR = QName.createQName( URI , "jour");

	/** Modèle pour la propriété 'stockageactes59:mois'. */
	public final static QName MOIS = QName.createQName( URI , "mois");

	/** Modèle pour la propriété 'stockageactes59:annee'. */
	public final static QName ANNEE = QName.createQName( URI , "annee");

}
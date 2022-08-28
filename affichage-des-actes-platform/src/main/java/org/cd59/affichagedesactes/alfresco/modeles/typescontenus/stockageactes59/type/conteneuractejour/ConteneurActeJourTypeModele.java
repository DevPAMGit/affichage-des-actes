package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuractejour ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link ConteneurActeJourTypeModele}. */
public class ConteneurActeJourTypeModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "stockageactes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/stockageacte/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "conteneurActeJour");

	/** Modèle pour la propriété 'stockageactes59:dateJour'. */
	public final static QName DATE_JOUR = QName.createQName( URI , "dateJour");

	/** Modèle pour la propriété 'stockageactes59:dateMois'. */
	public final static QName DATE_MOIS = QName.createQName( URI , "dateMois");

	/** Modèle pour la propriété 'stockageactes59:dateAnnee'. */
	public final static QName DATE_ANNEE = QName.createQName( URI , "dateAnnee");

}
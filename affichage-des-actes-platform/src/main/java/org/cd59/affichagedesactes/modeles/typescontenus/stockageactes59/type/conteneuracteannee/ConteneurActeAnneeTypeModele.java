package org.cd59.affichagedesactes.modeles.typescontenus.stockageactes59.type.conteneuracteannee ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link ConteneurActeAnneeTypeModele}. */
public class ConteneurActeAnneeTypeModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "stockageactes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/stockageacte/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "conteneurActeAnnee");

	/** Modèle pour la propriété 'stockageactes59:dateAnnee'. */
	public final static QName DATE_ANNEE = QName.createQName( URI , "dateAnnee");

}
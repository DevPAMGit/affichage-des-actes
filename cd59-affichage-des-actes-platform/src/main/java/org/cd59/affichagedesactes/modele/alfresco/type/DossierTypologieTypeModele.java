package org.cd59.affichagedesactes.modele.alfresco.type;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link DossierTypologieTypeModele}. */
public class DossierTypologieTypeModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "stockageactes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/stockageacte/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "dossierTypologie");

	/** Modèle pour la propriété 'stockageactes59:typologieDossier'. */
	public final static QName TYPOLOGIE_DOSSIER = QName.createQName( URI , "typologieDossier");

	/** Modèle pour la propriété 'stockageactes59:jour'. */
	public final static QName JOUR = QName.createQName( URI , "jour");

	/** Modèle pour la propriété 'stockageactes59:mois'. */
	public final static QName MOIS = QName.createQName( URI , "mois");

	/** Modèle pour la propriété 'stockageactes59:annee'. */
	public final static QName ANNEE = QName.createQName( URI , "annee");

}
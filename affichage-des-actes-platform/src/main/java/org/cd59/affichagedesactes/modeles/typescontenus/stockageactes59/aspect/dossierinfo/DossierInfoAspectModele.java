package org.cd59.affichagedesactes.modeles.typescontenus.stockageactes59.aspect.dossierinfo ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link DossierInfoAspectModele}. */
public class DossierInfoAspectModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "stockageactes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/stockageacte/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "dossierInfo");

	/** Modèle pour la propriété 'stockageactes59:nbDossiersPresents'. */
	public final static QName NB_DOSSIERS_PRESENTS = QName.createQName( URI , "nbDossiersPresents");

	/** Modèle pour la propriété 'stockageactes59:nbDossiersEnErreur'. */
	public final static QName NB_DOSSIERS_EN_ERREUR = QName.createQName( URI , "nbDossiersEnErreur");

}
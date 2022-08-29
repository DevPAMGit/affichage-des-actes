package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.aspect.informationdossier ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link InformationDossierAspectModele}. */
public class InformationDossierAspectModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "stockageactes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/stockageacte/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "informationDossier");

	/** Modèle pour la propriété 'stockageactes59:nbActes'. */
	public final static QName NB_ACTES = QName.createQName( URI , "nbActes");

	/** Modèle pour la propriété 'stockageactes59:nbActesEnErreurs'. */
	public final static QName NB_ACTES_EN_ERREURS = QName.createQName( URI , "nbActesEnErreurs");

	/** Modèle pour la propriété 'stockageactes59:nbActesTraites'. */
	public final static QName NB_ACTES_TRAITES = QName.createQName( URI , "nbActesTraites");

}
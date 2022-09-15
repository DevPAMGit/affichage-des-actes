package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosdossiersas ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link InfosDossierSasAspectModele}. */
public class InfosDossierSasAspectModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "erreur59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/erreur59/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "infosDossierSas");

	/** Modèle pour la propriété 'erreur59:nbDossiersActesEnErreurs'. */
	public final static QName NB_DOSSIERS_ACTES_EN_ERREURS = QName.createQName( URI , "nbDossiersActesEnErreurs");

}
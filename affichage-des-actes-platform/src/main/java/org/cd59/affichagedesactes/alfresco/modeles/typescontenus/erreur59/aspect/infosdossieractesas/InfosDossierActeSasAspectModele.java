package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosdossieractesas ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link InfosDossierActeSasAspectModele}. */
public class InfosDossierActeSasAspectModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "erreur59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/erreur59/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "infosDossierActeSas");

	/** Modèle pour la propriété 'erreur59:nbFichierEnErreurs'. */
	public final static QName NB_FICHIER_EN_ERREURS = QName.createQName( URI , "nbFichierEnErreurs");

}
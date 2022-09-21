package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.affichage59.aspect.erreur ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link ErreurAspectModele}. */
public class ErreurAspectModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "affichage59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/affichage59/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "erreur");

	/** Modèle pour la propriété 'affichage59:erreurAffichageMessage'. */
	public final static QName ERREUR_AFFICHAGE_MESSAGE = QName.createQName( URI , "erreurAffichageMessage");

}
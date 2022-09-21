package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.affichage59.aspect.affichage ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link AffichageAspectModele}. */
public class AffichageAspectModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "affichage59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/affichage59/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "affichage");

	/** Modèle pour la propriété 'affichage59:etat'. */
	public final static QName ETAT = QName.createQName( URI , "etat");

	/** Modèle pour la propriété 'affichage59:tentativeEnvoi'. */
	public final static QName TENTATIVE_ENVOI = QName.createQName( URI , "tentativeEnvoi");

}
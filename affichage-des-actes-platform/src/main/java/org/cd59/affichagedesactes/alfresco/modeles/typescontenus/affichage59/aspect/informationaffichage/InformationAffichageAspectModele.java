package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.affichage59.aspect.informationaffichage ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link InformationAffichageAspectModele}. */
public class InformationAffichageAspectModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "affichage59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/affichage59/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "informationAffichage");

	/** Modèle pour la propriété 'affichage59:dateAffichage'. */
	public final static QName DATE_AFFICHAGE = QName.createQName( URI , "dateAffichage");

	/** Modèle pour la propriété 'affichage59:urlAffichage'. */
	public final static QName URL_AFFICHAGE = QName.createQName( URI , "urlAffichage");

}
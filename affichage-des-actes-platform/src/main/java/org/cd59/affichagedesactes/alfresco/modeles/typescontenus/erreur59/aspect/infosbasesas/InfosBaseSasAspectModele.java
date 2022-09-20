package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosbasesas ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link InfosBaseSasAspectModele}. */
public class InfosBaseSasAspectModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "erreur59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/erreur59/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "infosBaseSas");

	/** Modèle pour la propriété 'erreur59:dateInfoSas'. */
	public final static QName DATE_INFO_SAS = QName.createQName( URI , "dateInfoSas");

	/** Modèle pour la propriété 'erreur59:messageInfoSas'. */
	public final static QName MESSAGE_INFO_SAS = QName.createQName( URI , "messageInfoSas");

	/** Modèle pour la propriété 'erreur59:etatInfoSas'. */
	public final static QName ETAT_INFO_SAS = QName.createQName( URI , "etatInfoSas");

}
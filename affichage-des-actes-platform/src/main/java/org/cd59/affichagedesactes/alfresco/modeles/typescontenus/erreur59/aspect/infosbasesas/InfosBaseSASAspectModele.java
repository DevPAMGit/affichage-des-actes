package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosbasesas ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link InfosBaseSASAspectModele}. */
public class InfosBaseSASAspectModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "erreur59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/erreur59/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "infosBaseSAS");

	/** Modèle pour la propriété 'erreur59:dateInfoSAS'. */
	public final static QName DATE_INFO_SA_S = QName.createQName( URI , "dateInfoSAS");

	/** Modèle pour la propriété 'erreur59:messageInfoSAS'. */
	public final static QName MESSAGE_INFO_SA_S = QName.createQName( URI , "messageInfoSAS");

	/** Modèle pour la propriété 'erreur59:etatInfoSAS'. */
	public final static QName ETAT_INFO_SA_S = QName.createQName( URI , "etatInfoSAS");

}
package org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.erreurbase ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link ErreurBaseAspectModele}. */
public class ErreurBaseAspectModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "erreur59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/erreur59/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "erreurBase");

	/** Modèle pour la propriété 'erreur59:messageErreur'. */
	public final static QName MESSAGE_ERREUR = QName.createQName( URI , "messageErreur");

	/** Modèle pour la propriété 'erreur59:dateErreur'. */
	public final static QName DATE_ERREUR = QName.createQName( URI , "dateErreur");

	/** Modèle pour la propriété 'erreur59:etat'. */
	public final static QName ETAT = QName.createQName( URI , "etat");

}
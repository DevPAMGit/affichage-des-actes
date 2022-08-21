package org.cd59.affichagedesactes.modeles.typescontenus.erreur59.aspect.erreurdossier ;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour le type de contenu {@link ErreurDossierAspectModele}. */
public class ErreurDossierAspectModele {

	/** Le prefix du type de contenu. */
	public final static String PREFIX = "erreur59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/erreur59/1.0.";

	/** Le nom du type de contenu. */
	public final static QName NOM = QName.createQName( URI , "erreurDossier");

	/** Modèle pour la propriété 'erreur59:nbFichierEnErreur'. */
	public final static QName NB_FICHIER_EN_ERREUR = QName.createQName( URI , "nbFichierEnErreur");

	/** Modèle pour la propriété 'erreur59:messageErreur'. */
	public final static QName MESSAGE_ERREUR = QName.createQName( URI , "messageErreur");

	/** Modèle pour la propriété 'erreur59:dateErreur'. */
	public final static QName DATE_ERREUR = QName.createQName( URI , "dateErreur");

}
package org.cd59.affichagedesactes.modeles.typescontenus.erreur59.aspects.erreurdossier;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour l'aspect 'erreur59:erreurDossier'. */
 public class ErreurdossierAspectModele {

 	/** Le prefix du type de contenu. */
	public final static String PREFIX = "erreur59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/erreur59/1.0.";

	/** Le nom de l'aspect. */
	public final static QName NOM = QName.createQName( URI , "erreurDossier");

	/** Modèle pour la propriété 'erreur59:nbFichierEnErreur'. */
	public final static QName NBFICHIERENERREUR = QName.createQName( URI , "nbFichierEnErreur");

	/** Modèle pour la propriété 'erreur59:messageErreur'. */
	public final static QName MESSAGEERREUR = QName.createQName( URI , "messageErreur");

	/** Modèle pour la propriété 'erreur59:dateErreur'. */
	public final static QName DATEERREUR = QName.createQName( URI , "dateErreur");

}

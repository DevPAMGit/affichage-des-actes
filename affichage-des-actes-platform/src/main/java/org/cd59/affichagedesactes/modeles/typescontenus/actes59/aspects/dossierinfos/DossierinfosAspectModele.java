package org.cd59.affichagedesactes.modeles.typescontenus.actes59.aspects.dossierinfos;

import org.alfresco.service.namespace.QName;

/** Classe modèle personnalisée pour l'aspect 'actes59:dossierinfos'. */
 public class DossierinfosAspectModele {

 	/** Le prefix du type de contenu. */
	public final static String PREFIX = "actes59";

	/** L'URI du type de contenu. */
	public final static String URI =  "http://cd59.fr/model/actes/1.0.";

	/** Le nom de l'aspect. */
	public final static QName NOM = QName.createQName( URI , "dossierinfos");

	/** Modèle pour la propriété 'actes59:resume'. */
	public final static QName RESUME = QName.createQName( URI , "resume");

	/** Modèle pour la propriété 'actes59:erreurinternet'. */
	public final static QName ERREURINTERNET = QName.createQName( URI , "erreurinternet");

	/** Modèle pour la propriété 'actes59:signataire'. */
	public final static QName SIGNATAIRE = QName.createQName( URI , "signataire");

	/** Modèle pour la propriété 'actes59:dossiercomplet'. */
	public final static QName DOSSIERCOMPLET = QName.createQName( URI , "dossiercomplet");

	/** Modèle pour la propriété 'actes59:source'. */
	public final static QName SOURCE = QName.createQName( URI , "source");

	/** Modèle pour la propriété 'actes59:annee'. */
	public final static QName ANNEE = QName.createQName( URI , "annee");

	/** Modèle pour la propriété 'actes59:statutaffichage'. */
	public final static QName STATUTAFFICHAGE = QName.createQName( URI , "statutaffichage");

	/** Modèle pour la propriété 'actes59:objet'. */
	public final static QName OBJET = QName.createQName( URI , "objet");

	/** Modèle pour la propriété 'actes59:dateaffichageged'. */
	public final static QName DATEAFFICHAGEGED = QName.createQName( URI , "dateaffichageged");

	/** Modèle pour la propriété 'actes59:orgasigle'. */
	public final static QName ORGASIGLE = QName.createQName( URI , "orgasigle");

	/** Modèle pour la propriété 'actes59:numeroacte'. */
	public final static QName NUMEROACTE = QName.createQName( URI , "numeroacte");

	/** Modèle pour la propriété 'actes59:typedossier'. */
	public final static QName TYPEDOSSIER = QName.createQName( URI , "typedossier");

	/** Modèle pour la propriété 'actes59:urlaffichage'. */
	public final static QName URLAFFICHAGE = QName.createQName( URI , "urlaffichage");

	/** Modèle pour la propriété 'actes59:iddossier'. */
	public final static QName IDDOSSIER = QName.createQName( URI , "iddossier");

	/** Modèle pour la propriété 'actes59:dateaffichageinternet'. */
	public final static QName DATEAFFICHAGEINTERNET = QName.createQName( URI , "dateaffichageinternet");

	/** Modèle pour la propriété 'actes59:datedossier'. */
	public final static QName DATEDOSSIER = QName.createQName( URI , "datedossier");

}

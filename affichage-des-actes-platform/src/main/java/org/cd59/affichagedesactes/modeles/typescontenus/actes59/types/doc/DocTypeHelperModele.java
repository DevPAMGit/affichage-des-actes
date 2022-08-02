package org.cd59.affichagedesactes.modeles.typescontenus.actes59.types.doc;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;

import java.util.Date;

/** Classe modèle d'aide personnalisée pour le type 'actes59:doc'. */
 public class DocTypeHelperModele extends AlfrescoModeleHelper {

 	/** Initialise une nouvelle instance de la classe {@link DocTypeHelperModele}. 
	 * @param serviceNoeud Le service de gestion des nœuds d'Alfresco. 
	 * @param noeud Le nœud de référence. */
	public DocTypeHelperModele(NodeService serviceNoeud, NodeRef noeud){
		super(serviceNoeud, noeud);
	}

	/** Permet de vérifier que le nœud du modèle possède l'aspect désigné en paramètre.
	 * @return <c>true</c> si l'aspect est présent, sinon <c>false</c>. */ 
	public boolean hasType(){ 
		return this.hasType(DocTypeModele.NOM);
	}

}

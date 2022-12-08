package org.cd59.affichagedesactes.modele.donnee.aspect.document.source;

import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.action.custom.source.v1.action.IModeleNoeudAction;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class ModeleDocumentAnnexe extends ModeleDocument {
    /**
     * Initialise une nouvelle instance de la classe {@link ModeleDocumentAnnexe}.
     *
     // @param serviceRegistry Le service de registre du nœud.
     * @param nodeRef         Le nœud source.
     * @throws ModeleException      Si le registre de services ou le nœud sont null.
     * @throws IOException          Si une erreur entrée/sortie à lieu lors de la lecture du nœud.
     * @throws NoSuchFieldException Si l'algorithme de hachage n'existe pas.
     */
    public ModeleDocumentAnnexe(IModeleNoeudAction modeleNoeudAction, NodeRef nodeRef)
            throws ModeleException, IOException, NoSuchAlgorithmException, PreRequisException, NoSuchMethodException {
        super(modeleNoeudAction, nodeRef);

        // Vérification que le type du document est de type acte original.
        if(this.type != ModeleDocumentType.ANNEXE)
            throw new ModeleException("Au moins un document du dossier n'est ni une annexe ni un acte original.");
    }
}

package org.cd59.affichagedesactes.modele.donnee.aspect.document.stockage;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.action.custom.source.v1.action.IModeleNoeudAction;
import org.cd59.affichagedesactes.modele.donnee.aspect.document.source.ModeleDocumentActe;
import org.cd59.affichagedesactes.modele.donnee.aspect.document.source.ModeleDocumentType;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.stockage.ModeleDossierDateStockage;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class ModeleDocumentActeStockage extends ModeleDocumentActe {
    /**
     * Initialise une nouvelle instance de la classe {@link ModeleDocumentActeStockage}.
     *
     * @param serviceRegistry Le service de registre du nœud.
     * @param nodeRef         Le nœud source.
     * @param identifiant     L'identifiant du dossier d'acte.
     * @throws ModeleException      Si le registre de services ou le nœud sont null.
     * @throws IOException          Si une erreur entrée/sortie à lieu lors de la lecture du nœud.
     * @throws NoSuchFieldException Si l'algorithme de hachage n'existe pas.
     */
    public ModeleDocumentActeStockage(/*ServiceRegistry serviceRegistry*/
            IModeleNoeudAction modeleNoeudAction, NodeRef nodeRef, String identifiant, ModeleDossierDateStockage date)
            throws ModeleException, IOException, NoSuchAlgorithmException, PreRequisException, NoSuchMethodException {
        super(/*serviceRegistry*/ modeleNoeudAction, nodeRef);

        this.setPropriete(ContentModel.PROP_NAME, String.format("%s_%s", identifiant, this.type.valeur));
        this.setPropriete(ContentModel.PROP_TITLE, String.format("%s_%s", identifiant, this.type.valeur));
        this.setPropriete(ContentModel.PROP_DESCRIPTION, String.format("Acte original pour le dossier %s du %s %s %d",
                identifiant, date.jourChaine, date.nomMois, date.annee));
    }
}

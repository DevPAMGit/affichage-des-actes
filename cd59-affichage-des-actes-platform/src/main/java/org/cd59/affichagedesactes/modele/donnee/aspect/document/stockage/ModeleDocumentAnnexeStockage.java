package org.cd59.affichagedesactes.modele.donnee.aspect.document.stockage;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.action.custom.source.v1.action.IModeleNoeudAction;
import org.cd59.affichagedesactes.modele.donnee.aspect.document.source.ModeleDocumentAnnexe;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.stockage.ModeleDossierDateStockage;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;
import org.cd59.affichagedesactes.utilitaire.UtilitaireChaineDeCaracteres;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Modèle de classe pour les documents annexes pour stockage.
 */
public class ModeleDocumentAnnexeStockage extends ModeleDocumentAnnexe {
    /**
     * Initialise une nouvelle instance de la classe {@link ModeleDocumentAnnexeStockage}.
     * @param nodeRef                   Le nœud source.
     * @param identifiant               L'identifiant du dossier d'acte.
     * @throws ModeleException          Si le registre de services ou le nœud sont null.
     * @throws IOException              Si une erreur entrée/sortie à lieu lors de la lecture du nœud.
     * @throws NoSuchAlgorithmException Si l'algorithme de hachage n'existe pas.
     */
    public ModeleDocumentAnnexeStockage(IModeleNoeudAction modeleNoeudAction, NodeRef nodeRef, String identifiant,
            ModeleDossierDateStockage date)
            throws ModeleException, IOException, NoSuchAlgorithmException, PreRequisException, NoSuchMethodException {
        super(modeleNoeudAction, nodeRef);

        String nomActuel = this.modeleNoeudAction.getProprieteChaine(nodeRef, ContentModel.PROP_NAME);
        int dernierePosition = nomActuel.lastIndexOf('.');
        String extension = "";
        if(dernierePosition > -1)
            extension = nomActuel.substring(dernierePosition);

        this.setPropriete(ContentModel.PROP_NAME, String.format("%s_%s%s", identifiant, this.type.valeur, extension));
        this.setPropriete(ContentModel.PROP_TITLE, String.format("%s_%s%s", identifiant, this.type.valeur, extension));
        this.setPropriete(ContentModel.PROP_DESCRIPTION, String.format("Annexe pour le dossier %s du %s %s %d",
                identifiant, date.jourChaine, date.nomMois, date.annee));
    }

    /**
     * Initialise une nouvelle instance de la classe {@link ModeleDocumentAnnexeStockage}.
     *
     * @param nodeRef                   Le nœud source.
     * @param identifiant               L'identifiant du dossier d'acte.
     * @param numero                    Numéro de l'annexe.
     * @throws ModeleException          Si le registre de services ou le nœud sont null.
     * @throws IOException              Si une erreur entrée/sortie à lieu lors de la lecture du nœud.
     * @throws NoSuchAlgorithmException Si l'algorithme de hachage n'existe pas.
     */
    public ModeleDocumentAnnexeStockage(IModeleNoeudAction modeleNoeudAction, NodeRef nodeRef, String identifiant,
                                        ModeleDossierDateStockage date, int numero)
            throws ModeleException, IOException, NoSuchAlgorithmException, PreRequisException, NoSuchMethodException {
        super(modeleNoeudAction, nodeRef);

        this.setPropriete(ContentModel.PROP_NAME, String.format("%s_%s_%s", identifiant, this.type.valeur,
                UtilitaireChaineDeCaracteres.entierSurNChiffres(numero,2)));
        this.setPropriete(ContentModel.PROP_TITLE, String.format("%s_%s", identifiant, this.type.valeur));
        this.setPropriete(ContentModel.PROP_DESCRIPTION, String.format("Annexe n°%d pour le dossier %s du %s %s %d",
                numero, identifiant, date.jourChaine, date.nomMois, date.annee));
    }
}

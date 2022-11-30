package org.cd59.affichagedesactes.modele.donnee.aspect.dossier.envoie;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.action.custom.source.v1.action.IModeleNoeudAction;
import org.cd59.affichagedesactes.modele.alfresco.aspect.DocinfosAspectModele;
import org.cd59.affichagedesactes.modele.alfresco.aspect.DossierinfosAspectModele;
import org.cd59.affichagedesactes.modele.donnee.aspect.document.source.ModeleDocument;
import org.cd59.affichagedesactes.modele.donnee.aspect.document.source.ModeleDocumentAnnexe;
import org.cd59.affichagedesactes.modele.donnee.aspect.document.source.ModeleDocumentType;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.ModeleDossier;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.ModeleDossierDate;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModeleDossierEnvoi extends ModeleDossier {

    /**
     * La date du dossier d'acte.
     */
    public final ModeleDossierDate date;

    /**
     * L'identifiant du dossier.
     */
    public final String identifiant;

    /**
     * L'acte original signé.
     */
    private ModeleDocument acteSigne;

    /**
     * Initialise une nouvelle instance de la classe {@link ModeleDossierEnvoi}.
     *
     // @param serviceRegistry Le service de registre du nœud.
     * @param nodeRef         Le nœud source.
     * @throws ModeleException Si le registre de services ou le nœud sont null.
     */
    public ModeleDossierEnvoi(/*ServiceRegistry serviceRegistry*/ IModeleNoeudAction modeleNoeudAction, NodeRef nodeRef)
            throws ModeleException, PreRequisException {
        super(/*serviceRegistry*/ modeleNoeudAction, nodeRef);

        this.date = new ModeleDossierDate(this.getProprieteDate(DossierinfosAspectModele.DATEDOSSIER));
        this.identifiant = this.getProprieteChaine(DossierinfosAspectModele.IDDOSSIER);
    }

    @Override
    public void initFichierActe(List<NodeRef> nodeRefList)
            throws ModeleException, IOException, NoSuchAlgorithmException, PreRequisException, NoSuchMethodException {
        // 1. Erreur s'il n'y a aucuns nœuds.
        if(nodeRefList == null || nodeRefList.size() == 0)
            throw new ModeleException("Le dossier d'acte ne contient aucun fichier d'acte original.");
        // 2. Erreur s'il y a plus d'un fichier d'acte.
        if(nodeRefList.size() > 1)
            throw new ModeleException("Le dossier d'acte ne contient plus d'un fichier d'acte original.");

        // Traitement du fichier d'acte.
        this.acteOriginal = new ModeleDocument(/*this.serviceRegistry*/ this.modeleNoeudAction, nodeRefList.get(0));
    }

    @Override
    public void setFichierAnnexes(List<NodeRef> nodeRefList)
            throws ModeleException, IOException, NoSuchAlgorithmException, PreRequisException, NoSuchMethodException {
        this.annexes = new ArrayList<>();
        if(nodeRefList != null && nodeRefList.size() > 0)
            this.annexes.add(new ModeleDocumentAnnexe(/*this.serviceRegistry*/ this.modeleNoeudAction, nodeRefList.get(0)));
    }

    /**
     * Récupère le modèle du fichier de l'acte original.
     * @return Le modèle du fichier de l'acte original.
     */
    public ModeleDocument getActeOriginal() {
        return this.acteOriginal;
    }

    /**
     * Modifie la valeur de l'acte signé.
     * @param acteSigne La nouvelle valeur de l'acte signé.
     */
    public void setActeSigne(NodeRef acteSigne)
            throws ModeleException, IOException, NoSuchAlgorithmException, PreRequisException, NoSuchMethodException {
        this.acteSigne = new ModeleDocument(this.modeleNoeudAction, acteSigne);
    }

    /**
     * Récupère le modèle de l'acte signé.
     * @return Le modèle de l'acte signé.
     */
    public ModeleDocument getActeSigne(){
        return this.acteSigne;
    }

    /**
     * Modifie la date d'affichage de l'acte sur site distant.
     * @param date                      La date d'affichage de l'acte sur internet.
     * @throws PreRequisException       Si le nœud ou la propriété en paramètre est null ou vide.
     * @throws NoSuchMethodException    Levée lorsqu'une méthode particulière est introuvable.
     */
    public void setDateAffichageInternet(Date date) throws PreRequisException, NoSuchMethodException {
        this.modeleNoeudAction.setPropriete(this.nodeRef, DossierinfosAspectModele.DATEAFFICHAGEINTERNET, date);
    }

    /**
     * Modifie la date d'affichage de l'acte en GED (???).
     * @param                           date La date d'affichage de l'acte en GED (???).
     * @throws PreRequisException       Si le nœud ou la propriété en paramètre est null ou vide.
     * @throws NoSuchMethodException    Levée lorsqu'une méthode particulière est introuvable.
     */
    public void setDateAffichageGED(Date date) throws PreRequisException, NoSuchMethodException {
        this.modeleNoeudAction.setPropriete(this.nodeRef, DossierinfosAspectModele.DATEAFFICHAGEGED, date);
    }

    /**
     *
     * @param urlAffichage
     * @throws PreRequisException       Si le nœud ou la propriété en paramètre est null ou vide.
     * @throws NoSuchMethodException    Levée lorsqu'une méthode particulière est introuvable.
     */
    public void setUrlAffichage(String urlAffichage) throws PreRequisException, NoSuchMethodException {
        this.modeleNoeudAction.setPropriete(this.nodeRef, DossierinfosAspectModele.URLAFFICHAGE, urlAffichage);
    }
}

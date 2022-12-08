package org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.action.custom.source.v1.action.IModeleNoeudAction;
import org.cd59.affichagedesactes.modele.alfresco.aspect.DossierinfosAspectModele;
import org.cd59.affichagedesactes.modele.donnee.aspect.document.source.ModeleDocument;
import org.cd59.affichagedesactes.modele.donnee.aspect.document.source.ModeleDocumentAnnexe;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.type.ModeleDossierType;
import org.cd59.affichagedesactes.modele.donnee.source.ModeleNoeud;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;
import org.cd59.affichagedesactes.utilitaire.UtilitaireChaineDeCaracteres;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Modèle pour un dossier d'acte.
 */
public abstract class ModeleDossier extends ModeleNoeud {

    /**
     * L'objet du dossier.
     */
    public final String objet;

    /**
     * Le résumé du dossier.
     */
    public final String resume;

    /**
     * Le signataire du dossier.
     */
    public final String signataire;

    /**
     * Les annexes du dossier.
     */
    protected ArrayList<ModeleDocumentAnnexe> annexes;

    /**
     * Le numéro du dossier d'acte.
     */
    private final String numero;

    /**
     * La typologie du dossier.
     */
    public final ModeleDossierType typologie;

    /**
     * L'acte original di dossier.
     */
    protected ModeleDocument acteOriginal;

    /**
     * Initialise une nouvelle instance de la classe {@link ModeleNoeud}.
     // @param serviceRegistry Le service de registre du nœud.
     * @param nodeRef         Le nœud source.
     * @throws ModeleException Si le registre de services ou le nœud sont null.
     */
    public ModeleDossier(IModeleNoeudAction modeleNoeudAction, NodeRef nodeRef)
            throws ModeleException, PreRequisException {
        super(modeleNoeudAction, nodeRef);

        this.objet = this.getProprieteChaine(DossierinfosAspectModele.OBJET);
        this.resume = this.getProprieteChaine(DossierinfosAspectModele.RESUME);
        this.numero = this.getProprieteChaine(DossierinfosAspectModele.SIGNATAIRE);
        this.signataire = this.getProprieteChaine(DossierinfosAspectModele.SIGNATAIRE);

        this.typologie = new ModeleDossierType(this.getProprieteChaine(DossierinfosAspectModele.TYPEDOSSIER));
    }

    /**
     * Récupère la valeur du numéro d'acte.
     * @return La valeur du numéro d'acte.
     */
    public String getNumero() {
        return this.numero;
    }

    /**
     * Vérifie les préconditions du dossier.
     * @throws ModeleException Si les préconditions ne sont pas respectées.
     */
    protected void checkPreconditions() throws ModeleException {
        // Initialisation du message d'erreur.
        StringBuilder message = new StringBuilder();

        if(UtilitaireChaineDeCaracteres.etreNullOuVide(this.signataire))
            message.append("Le signataire n'est pas renseigné. ");

        if(UtilitaireChaineDeCaracteres.etreNullOuVide(this.objet))
            message.append("L'objet n'est pas renseigné. ");

        if(message.length() > 0)
            throw new ModeleException(message.toString());
    }

    /**
     * Récupère les nœuds des annexes du dossier.
     * @return Les nœuds des annexes du dossier.
     */
    public ArrayList<ModeleDocumentAnnexe> getAnnexes() {
        return this.annexes;
    }

    /**
     * Initialise le fichier d'acte du dossier.
     * @param nodeRefList La liste des fichiers d'actes de la requête.
     * @throws ModeleException Si la taille de la liste n'est pas égale à un.
     */
    public abstract void initFichierActe(List<NodeRef> nodeRefList) throws ModeleException, IOException, NoSuchAlgorithmException, PreRequisException, NoSuchMethodException;

    public abstract void setFichierAnnexes(List<NodeRef> requeterNoeuds) throws ModeleException, IOException, NoSuchAlgorithmException, PreRequisException, NoSuchMethodException;

    /**
     * Modifie l'état d'envoi du dossier.
     * @param etat Le nouvel etat du dossier.
     */
    public void setEtatEnvoi(ModeleDossierEtatEnvoi etat) throws ModeleException, PreRequisException, NoSuchMethodException {
        this.setPropriete(DossierinfosAspectModele.ETAT_ENVOI_DOSSIER, etat.valeur);
    }

    /**
     * Modifie le message d'erreur du dossier.
     * @param message Le message d'erreur.
     * @throws ModeleException Si la propriété à modifier est null.
     */
    public void setMessageErreur(String message) throws ModeleException, PreRequisException, NoSuchMethodException {
        this.setPropriete(DossierinfosAspectModele.ERREURINTERNET, message);
    }
}

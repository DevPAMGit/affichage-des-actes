package org.cd59.affichagedesactes.logique;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.cd59.affichagedesactes.logique.factory.ErreurFactory;
import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;
import org.cd59.affichagedesactes.modeles.typescontenus.erreur59.aspect.erreurbase.ErreurBaseAspectHelperModele;
import org.cd59.affichagedesactes.modeles.typescontenus.erreur59.aspect.erreurdossier.ErreurDossierAspectHelperModele;

import java.util.Date;

/**
 * Helper pour les actions récurrentes dans le projet des affichages des actes.
 */
public class AffichageDesActesSourceLogique {
    /**
     * Le service de gestion des nœuds Alfresco.
     */
    protected final NodeService nodeService;

    /**
     * Paramètre servant à synchroniser les {@link Thread} lors d'une intervention sur le dossier SAS.
     */
    private final Object lockDossierSAS = new Object();

    /**
     * Initialise une nouvelle instance de la classe {@link AffichageDesActesSourceLogique}.
     * @param nodeService Le service de gestion des nœuds Alfresco.
     * */
    public AffichageDesActesSourceLogique(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    /**
     * Retire toutes les erreurs du dossier d'acte du SAS.
     * @param nodeRef Nœud de référence vers le dossier d'acte.
     */
    public void razErreurDossierActeSas(NodeRef nodeRef) {
        for(NodeRef childNode : new AlfrescoModeleHelper(this.nodeService, nodeRef).getContenu())
            this.supprimerErreurFichierActeSas(childNode);
    }

    /** Méthode permettant d'ajouter un aspect d'erreur sur un fichier  */
    public void ajouterErreurFichierActeSas(NodeRef nodeRef, String message) {
        this.ajouterErreurBase(nodeRef, message);
        this.ajouterErreurDossierActeSas(this.nodeService.getPrimaryParent(nodeRef).getParentRef());
    }

    /**
     * Méthode permettant de supprimer l'aspect d'erreur sur le dossier de l'acte.
     * @param nodeRef Le nœud de référence vers le dossier de l'acte.
     * @param message Le message d'erreur.
     */
    public void ajouterErreurDossierActeSas(NodeRef nodeRef, String message) {
        this.ajouterErreurBase(nodeRef, message);
        this.ajouterErreurDossierSas(this.nodeService.getPrimaryParent(nodeRef).getParentRef());
    }

    /**
     * Méthode permettant de supprimer l'aspect d'erreur sur le dossier de SAS.
     * @param nodeRef Le nœud de référence vers le dossier "SAS".
     */
    private void ajouterErreurDossierActeSas(NodeRef nodeRef) {
        ErreurDossierAspectHelperModele erreurDossier = new ErreurDossierAspectHelperModele(this.nodeService, nodeRef);
        int nbContenuEnErreur = 0;
        if(erreurDossier.hasAspect()) nbContenuEnErreur += erreurDossier.getNbFichierEnErreur() + 1;
        else nbContenuEnErreur++;
        this.ajouterErreurDossier(nodeRef, nbContenuEnErreur);
        this.ajouterErreurDossierSas(this.nodeService.getPrimaryParent(nodeRef).getParentRef());
    }

    /**
     * Méthode permettant de supprimer l'aspect d'erreur sur le dossier de SAS.
     * @param nodeRef Le nœud de référence vers le dossier "SAS".
     */
    private void ajouterErreurDossierSas(NodeRef nodeRef) {
        synchronized (this.lockDossierSAS) {
            ErreurDossierAspectHelperModele erreurDossier = new ErreurDossierAspectHelperModele(this.nodeService, nodeRef);
            int nbContenuEnErreur = 0;
            if(erreurDossier.hasAspect()) nbContenuEnErreur += erreurDossier.getNbFichierEnErreur() + 1;
            else nbContenuEnErreur++;
            this.ajouterErreurDossier(nodeRef, nbContenuEnErreur);
        }
    }

    /**
     * Méthode permettant d'ajouter un aspect d'erreur de base sur un nœud.
     * @param nodeRef La référence vers le contenu auquel on souhaite ajouter l'aspect d'erreur.
     * @param message Le message d'erreur.
     */
    private void ajouterErreurBase(NodeRef nodeRef, String message) {
        ErreurBaseAspectHelperModele erreurBase = new ErreurBaseAspectHelperModele(this.nodeService, nodeRef);
        if(!erreurBase.hasAspect()) erreurBase.addAspect(ErreurFactory.obtenirParametreAspectErreurBase(message));
        else {
            erreurBase.setDateErreur(new Date());
            erreurBase.setMessageErreur(message);
        }
    }

    /**
     * Méthode permettant d'ajouter un aspect d'erreur de dossier sur un nœud.
     * @param nodeRef La référence vers le contenu auquel on souhaite ajouter l'aspect d'erreur.
     * @param nbContenuEnErreur Le nombre d'éléments en erreur dans le dossier.
     */
    private void ajouterErreurDossier(NodeRef nodeRef, int nbContenuEnErreur) {
        ErreurDossierAspectHelperModele erreurDossier = new ErreurDossierAspectHelperModele(this.nodeService, nodeRef);
        // Si le noeud ne possède pas l'aspect d'erreur : ajout de celui.
        if(!erreurDossier.hasAspect())
            erreurDossier.addAspect(ErreurFactory.getParametresAspectDossierErreur(nbContenuEnErreur));
        // Sinon mise à jour de l'aspect.
        else {
            erreurDossier.setDateErreur(new Date());
            erreurDossier.setMessageErreur(ErreurFactory.obtenirDossierErreurMessage(nbContenuEnErreur));
        }
    }

    /**
     * Méthode permettant de supprimer l'aspect d'erreur sur le dossier d'acte du SAS.
     * @param nodeRef Le nœud de référence vers le dossier d'acte dans le SAS.
     */
    public void supprimerErreurFichierActeSas(NodeRef nodeRef) {
        this.supprimerErreurBase(nodeRef);
        this.supprimerErreurDossierActeSas(this.nodeService.getPrimaryParent(nodeRef).getParentRef());
    }

    /**
     * Méthode permettant de supprimer l'aspect d'erreur sur le dossier d'acte du SAS.
     * @param nodeRef Le nœud de référence vers le dossier d'acte dans le SAS.
     */
    private void supprimerErreurDossierActeSas(NodeRef nodeRef) {
        this.supprimerErreurDossier(nodeRef);
        ErreurDossierAspectHelperModele erreurDossier = new ErreurDossierAspectHelperModele(this.nodeService, nodeRef);
        if(!erreurDossier.hasAspect() || erreurDossier.getNbFichierEnErreur() == 0 )
            this.supprimerErreurDossierSas(this.nodeService.getPrimaryParent(nodeRef).getParentRef());
    }

    /**
     * Méthode permettant de supprimer l'aspect d'erreur sur le dossier de SAS.
     * @param nodeRef Le nœud de référence vers le dossier "SAS".
     */
    private void supprimerErreurDossierSas(NodeRef nodeRef) {
        synchronized (this.lockDossierSAS) {
            this.supprimerErreurDossier(nodeRef);
        }
    }

     /**
      *  Méthode permettant de supprimer l'aspect d'erreur de base.
      * @param nodeRef Le nœud de référence vers le contenu ayant l'aspect d'erreur.
      */
    private void supprimerErreurBase(NodeRef nodeRef) {
        ErreurBaseAspectHelperModele erreurBase = new ErreurBaseAspectHelperModele(this.nodeService, nodeRef);
        // Vérification que le nœud possède l'aspect d'erreur de dossier.
        if(!erreurBase.hasAspect()) return ;
        // Récupération du nombre de fichiers en erreur après suppression du contenu en erreur.
        erreurBase.supprimeAspect();
    }

    /**
     * Méthode permettant de gérer le nom
     * @param nodeRef Le nœud de référence vers le dossier dont on veut supprimer l'aspect d'erreur.
     */
    private void supprimerErreurDossier(NodeRef nodeRef) {
        ErreurDossierAspectHelperModele erreurDossier = new ErreurDossierAspectHelperModele(this.nodeService, nodeRef);
        // Vérification que le nœud possède l'aspect d'erreur de dossier.
        if(!erreurDossier.hasAspect()) return;
        // Récupération du nombre de fichiers en erreur après suppression du contenu en erreur.
        int nbContenuEnErreur = erreurDossier.getNbFichierEnErreur() - 1;
        // Si le nombre tombe à zéro : Suppression de l'aspect d'erreur.
        if(nbContenuEnErreur == 0) erreurDossier.supprimeAspect();
        // Décrémentation du nombre de fichiers en erreur dans le dossier.
        else erreurDossier.setNbFichierEnErreur(nbContenuEnErreur);
    }
}

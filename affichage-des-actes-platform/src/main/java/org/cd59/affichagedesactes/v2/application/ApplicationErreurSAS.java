package org.cd59.affichagedesactes.v2.application;

import org.alfresco.service.namespace.QName;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.docinfos.DocinfosAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosdossieractesas.InfosDossierActeSasAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosfichieractesas.InfosFichierActeSasAspectHelperModele;
import org.cd59.affichagedesactes.v2.action.MutexAction;
import org.cd59.affichagedesactes.v2.action.source.ActionMetier;
import org.cd59.affichagedesactes.v2.application.source.ApplicationSource;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.type.sas.SasTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosbasesas.InfosBaseSASAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosdossiersas.InfosDossierSasAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosdossieractesas.InfosDossierActeSasAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosdossiersas.InfosDossierSasAspectHelperModele;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * Classe permettant la gestion des aspects d'erreurs dans le dossier SAS.
 */
public class ApplicationErreurSAS extends ApplicationSource {

    /**
     * Initialise une nouvelle instance de la classe {@link ApplicationSource}.
     * @param action L'action à laquelle est reliée l'application.
     */
    public ApplicationErreurSAS(ActionMetier action) {
        super(action);
    }

    /**
     * Ajoute un aspect d'erreur à un nœud.
     * @param nodeRef Le nœud auquel on souhaite ajouter une erreur.
     * @param message Le message à lier à l'erreur.
     */
    public void ajouterErreur(NodeRef nodeRef, String message) {
        if(nodeRef == null || this.estPasBienType(nodeRef)) return;

        if(this.aSasType(nodeRef)) this.incrementerErreurInfosDossierSas(nodeRef);
        else if(this.aDossierAspect(nodeRef)) this.incrementerErreurInfosDossierActeSas(nodeRef, message);
        else if(this.aDocumentInfosAspect(nodeRef)) this.incrementerErreurInfosFichierActeSas(nodeRef, message);
    }

    /**
     * Ajoute un aspect d'erreur à un nœud.
     * @param nodeRef Le nœud auquel on souhaite ajouter une erreur.
     */
    public void ajouterErreur(NodeRef nodeRef) {
        if(nodeRef == null || this.estPasBienType(nodeRef)) return;

        if(this.aSasType(nodeRef)) this.incrementerErreurInfosDossierSas(nodeRef);
        else if(this.aDossierAspect(nodeRef)) this.incrementerErreurInfosDossierActeSas(nodeRef);
        else if(this.aDocumentInfosAspect(nodeRef)) this.incrementerErreurInfosFichierActeSas(nodeRef, null);
    }

    /**
     * Ajoute l'aspect 'erreur59 : infosDossierSas' à un nœud.
     * @param nodeRef Le nœud de référence vers le dossier.
     */
    private void incrementerErreurInfosDossierSas(NodeRef nodeRef) {
        if(nodeRef == null) return;

        synchronized (MutexAction.MUTEX_INFO_SAS) {
            InfosDossierSasAspectHelperModele dossierSas = new InfosDossierSasAspectHelperModele(this.action.registryService, nodeRef);
            if(!dossierSas.hasAspect())
                dossierSas.addAspect(obtenirParametreInfoDossierSas(1));
            else
                dossierSas.majProprietes(
                        obtenirParametreInfoDossierSas(dossierSas.getNbDossiersActesEnErreurs() + 1)
                );
        }
    }

    /**
     * Ajoute l'aspect 'erreur59:infosDossierActeSas' à un nœud.
     * @param nodeRef Le nœud de référence vers le dossier.
     */
    private void incrementerErreurInfosDossierActeSas(NodeRef nodeRef) {
        if(nodeRef == null) return;

        InfosDossierActeSasAspectHelperModele dossierActe = new InfosDossierActeSasAspectHelperModele(this.action.registryService, nodeRef);
        if(!dossierActe.hasAspect()) {
            dossierActe.addAspect(obtenirParametreInfosDossierActeSas(1));
            this.ajouterErreur(dossierActe.getNoeudParent());
        }else {
            dossierActe.majProprietes(obtenirParametreInfosDossierActeSas(
                    dossierActe.getNbFichierEnErreurs() + 1)
            );
        }
    }

    /**
     * Ajoute l'aspect 'erreur59:infosDossierActeSas' à un nœud.
     * @param nodeRef Le nœud de référence vers le dossier.
     * @param message Le message lié à l'erreur.
     */
    private void incrementerErreurInfosDossierActeSas(NodeRef nodeRef, String message) {
        if(nodeRef == null) return;

        InfosDossierActeSasAspectHelperModele dossierActe = new InfosDossierActeSasAspectHelperModele(this.action.registryService, nodeRef);
        if(!dossierActe.hasAspect()) {
            dossierActe.addAspect(obtenirParametreInfosDossierActeSas(message, 0));
            this.ajouterErreur(dossierActe.getNoeudParent());
        }else {
            String etatAvant = (String) dossierActe.getPropriete(InfosBaseSASAspectModele.ETAT_INFO_SA_S);
            dossierActe.majProprietes(obtenirParametreInfosDossierActeSas(message, dossierActe.getNbFichierEnErreurs()));
            String etatApres = (String) dossierActe.getPropriete(InfosBaseSASAspectModele.ETAT_INFO_SA_S);
            if(!etatAvant.equals(etatApres)) this.ajouterErreur(dossierActe.getNoeudParent());
        }
    }

    /**
     * Ajoute l'aspect 'erreur59:infosDossierActeSas' à un nœud.
     * @param message message d'erreur du nœud.
     * @param nodeRef Le nœud de référence vers le dossier.
     */
    private void incrementerErreurInfosFichierActeSas(NodeRef nodeRef, String message) {
        if(nodeRef == null) return;

        InfosFichierActeSasAspectHelperModele fichierActe = new InfosFichierActeSasAspectHelperModele(this.action.registryService, nodeRef);
        fichierActe.majProprietes( obtenirParametreInfosFichierActeSas(message, true) );
        if(!fichierActe.hasAspect()) this.ajouterErreur(fichierActe.getNoeudParent());
    }

    /**
     * Vérifie que le dossier est bien typé pour recevoir l'aspect.
     * @param nodeRef Le nœud à tester.
     * @return <c>true</c> si le dossier est bien typé. Sinon <c>false</c>.
     */
    private boolean estPasBienType(NodeRef nodeRef) {
        return this.aDossierAspect(nodeRef) || this.aDocumentInfosAspect(nodeRef) ||
                this.aSasType(nodeRef);
    }

    /**
     * Méthode permettant de vérifier si le nœud possède l'aspect 'actes59 : sas'.
     * @param nodeRef Le nœud que l'on souhaite vérifier.
     * @return <c>true</c> si le nœud à l'aspect sinon faux.
     */
    private boolean aSasType(NodeRef nodeRef) {
        SasTypeHelperModele document = new SasTypeHelperModele(this.action.registryService, nodeRef);
        return document.hasType();
    }

    /**
     * Méthode permettant de vérifier si le nœud possède l'aspect 'actes59 : dossier'.
     * @param nodeRef Le nœud que l'on souhaite vérifier.
     * @return <c>true</c> si le nœud à l'aspect sinon faux.
     */
    private boolean aDossierAspect(NodeRef nodeRef) {
        DossierinfosAspectHelperModele document = new DossierinfosAspectHelperModele(this.action.registryService,
                nodeRef);
        return document.hasAspect();
    }

    /**
     * Méthode permettant de vérifier si le nœud possède l'aspect 'actes59 : docinfos'.
     * @param nodeRef Le nœud a vérifié.
     * @return <c>true</c> si le nœud possède l'aspect. Sinon <c>false</c>.
     */
    private boolean aDocumentInfosAspect(NodeRef nodeRef) {
        DocinfosAspectHelperModele dossier = new DocinfosAspectHelperModele(this.action.registryService, nodeRef);
        return dossier.hasAspect();
    }

    /**
     * Récupère le paramètre d'un aspect 'erreur59:infosBaseSAS'.
     * @param message La valeur du message de l'aspect.
     * @param etat La valeur de l'état de l'aspect.
     * @return Une table contenant les propriétés d'un aspect 'erreur59:infosBaseSAS'.
     */
    private static HashMap<QName, Serializable> obtenirParametreInfoBaseSas(String message, String etat) {
        HashMap<QName, Serializable> resultat = new HashMap<>();
        resultat.put(InfosBaseSASAspectModele.DATE_INFO_SA_S, new Date());
        resultat.put(InfosBaseSASAspectModele.MESSAGE_INFO_SA_S, message);
        resultat.put(InfosBaseSASAspectModele.ETAT_INFO_SA_S, etat);
        return resultat;
    }

    /**
     * Récupère le paramètre d'un aspect 'erreur59 : infosDossierSas'.
     * @param dossierEnErreurs Le nombre de dossiers en erreurs.
     * @return Une table contenant les propriétés d'un aspect 'erreur59 : infosDossierSas'.
     */
    private static HashMap<QName, Serializable> obtenirParametreInfoDossierSas(int dossierEnErreurs) {
        String pluriel = dossierEnErreurs > 1 ? "s" : "";
        int nbDossiers = dossierEnErreurs + 1;
        String message, etat;

        if(dossierEnErreurs == 0) {
            message = "Il n'y a a aucun dossier d'acte en erreur.";
            etat = "OK";
        } else {
            message = String.format("Il y a %d dossier%s d'acte%s en erreur%s.",
                    dossierEnErreurs, pluriel, pluriel, pluriel);
            etat = "ERREUR";
        }

        HashMap<QName, Serializable> resultat = obtenirParametreInfoBaseSas(message, etat);
        resultat.put(InfosDossierSasAspectModele.NB_DOSSIERS_ACTES_EN_ERREURS, nbDossiers);

        return resultat;
    }

    /**
     * Récupère le paramètre d'un aspect 'erreur59:infosDossierActeSas'.
     * @return Une table contenant les propriétés d'un aspect 'erreur59:infosDossierActeSas'.
     */
    private static HashMap<QName, Serializable> obtenirParametreInfosDossierActeSas(int fichierEnErreurs) {
        String pluriel = fichierEnErreurs > 1 ? "s" : "";
        int nbFichiers = fichierEnErreurs + 1;
        String message, etat;

        if(nbFichiers == 0) {
            message = "Il n'y a a aucun fichiers d'acte en erreur.";
            etat = "OK";
        } else {
            message = String.format("Il y a %d fichier%s d'acte en erreur%s.", nbFichiers, pluriel, pluriel);
            etat = "ERREUR";
        }

        HashMap<QName, Serializable> resultat = obtenirParametreInfoBaseSas(message, etat);
        resultat.put(InfosDossierActeSasAspectModele.NB_FICHIER_EN_ERREURS, nbFichiers);

        return resultat;
    }

    /**
     * Récupère le paramètre d'un aspect 'erreur59:infosDossierActeSas'.
     * @param message Le message de l'erreur dossier acte.
     * @param fichierEnErreurs Le nombre de fichiers en erreurs.
     * @return Une table contenant les propriétés d'un aspect 'erreur59:infosDossierActeSas'.
     */
    private static HashMap<QName, Serializable> obtenirParametreInfosDossierActeSas(
            String message, int fichierEnErreurs
    ) {
        String pluriel = fichierEnErreurs > 1 ? "s" : "";
        int nbFichiers = fichierEnErreurs + 1;
        String etat;

        if(nbFichiers == 0) etat = "OK";
         else etat = "ERREUR";

        HashMap<QName, Serializable> resultat = obtenirParametreInfoBaseSas(message, etat);
        resultat.put(InfosDossierActeSasAspectModele.NB_FICHIER_EN_ERREURS, nbFichiers);

        return resultat;
    }

    /**
     * Récupère le paramètre d'un aspect 'erreur59:infosDossierActeSas'.
     * @return Une table contenant les propriétés d'un aspect 'erreur59:infosDossierActeSas'.
     */
    private static HashMap<QName, Serializable> obtenirParametreInfosFichierActeSas(String message, boolean enErreur) {
        String etat;

        if(!enErreur) etat = "OK";
        else etat = "ERREUR";

        return obtenirParametreInfoBaseSas(message, etat);
    }

    /**
     * Retire une erreur d'un nœud du projet.
     * @param nodeRef Le nœud dont on souhaite retirer l'erreur.
     */
    public void retirerErreur(NodeRef nodeRef) {
        if(nodeRef == null || this.estPasBienType(nodeRef)) return;

        if(this.aSasType(nodeRef)) this.decrementerErreurInfosDossierSas(nodeRef);
        else if(this.aDossierAspect(nodeRef)) this.decrementerErreurInfosDossierActeSas(nodeRef);
        else if(this.aDocumentInfosAspect(nodeRef)) this.decrementerErreurInfosFichierActeSas(nodeRef);
    }

    /**
     * Retire l'erreur d'un nœud de type fichier.
     * @param nodeRef Le nœud dont on souhaite retirer l'erreur.
     */
    private void decrementerErreurInfosFichierActeSas(NodeRef nodeRef) {
        if(nodeRef == null) return;

        InfosFichierActeSasAspectHelperModele fichierActe = new InfosFichierActeSasAspectHelperModele(this.action.registryService, nodeRef);

        if(!fichierActe.hasAspect()) {
            fichierActe.majProprietes(obtenirParametreInfosFichierActeSas("Le fichier est prêt à partir en stockage.", false));
            return;
        }

        String etat = (String) this.action.registryService.getNodeService().getProperty(nodeRef, InfosBaseSASAspectModele.ETAT_INFO_SA_S);
        fichierActe.majProprietes(obtenirParametreInfosFichierActeSas("Le fichier est prêt à partir en stockage.", false));
        String nouvelEtat = (String) this.action.registryService.getNodeService().getProperty(nodeRef, InfosBaseSASAspectModele.ETAT_INFO_SA_S);

        if(!nouvelEtat.equals(etat)) this.retirerErreur(fichierActe.getNoeudParent());
    }

    /**
     * Décrémente l'erreur le nœud typé 'sas'.
     * @param nodeRef Le nœud typé 'sas'.
     */
    private void decrementerErreurInfosDossierSas(NodeRef nodeRef) {
        if(nodeRef == null) return;

        synchronized (MutexAction.MUTEX_INFO_SAS) {
            InfosDossierSasAspectHelperModele dossierSas = new InfosDossierSasAspectHelperModele(this.action.registryService, nodeRef);
            if(!dossierSas.hasAspect())
                dossierSas.addAspect(obtenirParametreInfoDossierSas(0));
            else {
                int nbDossierActesEnErreur = dossierSas.getNbDossiersActesEnErreurs();
                if(nbDossierActesEnErreur > 0)
                    dossierSas.majProprietes(
                            obtenirParametreInfoDossierSas(nbDossierActesEnErreur-1)
                    );
            }
        }
    }

    /**
     * Retire l'aspect 'erreur59:infosDossierActeSas' à un nœud.
     * @param nodeRef Le nœud de référence vers le dossier.
     */
    private void decrementerErreurInfosDossierActeSas(NodeRef nodeRef) {
        if(nodeRef == null) return;

        InfosDossierActeSasAspectHelperModele dossierActe = new InfosDossierActeSasAspectHelperModele(
                this.action.registryService, nodeRef
        );
        int nbFichiersEnErreurs;
        if(!dossierActe.hasAspect()) {
            dossierActe.addAspect(obtenirParametreInfosDossierActeSas(0));
            // this.retirerErreur(dossierActe.getNoeudParent());
        } else {
            nbFichiersEnErreurs = dossierActe.getNbFichierEnErreurs();
            int nbFichiersEnErreursApres = nbFichiersEnErreurs;

            if(nbFichiersEnErreursApres > 0) nbFichiersEnErreursApres -= 1;
            String etatAvant = (String) dossierActe.getPropriete(InfosBaseSASAspectModele.ETAT_INFO_SA_S);

            dossierActe.majProprietes(obtenirParametreInfosDossierActeSas(nbFichiersEnErreursApres));
            String etatApres = (String) dossierActe.getPropriete(InfosBaseSASAspectModele.ETAT_INFO_SA_S);

            if(!etatAvant.equals(etatApres)) this.retirerErreur(dossierActe.getNoeudParent());
        }

    }

    /**
     * Retire l'aspect 'erreur59:infosDossierActeSas' à un nœud.
     * @param nodeRef Le nœud de référence vers le dossier.
     */
    public void retirerErreurInfosDossierActeSas(NodeRef nodeRef) {
        if(nodeRef == null) return;

        InfosDossierActeSasAspectHelperModele dossierActe = new InfosDossierActeSasAspectHelperModele(
                this.action.registryService, nodeRef
        );
        int nbFichiersEnErreurs;
        if(!dossierActe.hasAspect()) {
            dossierActe.addAspect(obtenirParametreInfosDossierActeSas(0));
            // this.retirerErreur(dossierActe.getNoeudParent());
        } else {
            String etatAvant = (String) dossierActe.getPropriete(InfosBaseSASAspectModele.ETAT_INFO_SA_S);
            dossierActe.majProprietes(obtenirParametreInfosDossierActeSas(dossierActe.getNbFichierEnErreurs()));
            String etatApres = (String) dossierActe.getPropriete(InfosBaseSASAspectModele.ETAT_INFO_SA_S);
            if(!etatAvant.equals(etatApres)) this.retirerErreur(dossierActe.getNoeudParent());
        }

    }
}

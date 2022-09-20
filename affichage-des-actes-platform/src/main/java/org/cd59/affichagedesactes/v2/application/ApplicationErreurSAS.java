package org.cd59.affichagedesactes.v2.application;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;

import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.docinfos.DocinfosAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.dossieracteinfo.DossierActeInfoAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.fichieracteinfo.FichierActeInfoAspectModele;
import org.cd59.affichagedesactes.v2.action.source.ActionMetier;
import org.cd59.affichagedesactes.v2.application.source.ApplicationSource;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.infosbasesas.InfosBaseSasAspectModele;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.io.Serializable;
import java.util.List;

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
     * Permet d'initialiser/ou mettre à jour les propriétés d'un nœud qui possède l'aspect 'actes59:dossierinfos' avec
     * l'aspect 'erreur59:etatInfoSas' avec pour valeur propriété 'erreur59:etatInfoSas' à 'ERREUR'.
     * @param nodeRef Le nœud dont on souhaite modifier les propriétés d'aspects.
     * @param message La valeur du de la propriété 'messageInfoSas:message' de l'aspect 'messageInfoSas:infosBaseSas'.
     * @param enErreur Indique si l'information est en erreur.
     */
    public void miseAJourInfoBaseSasDossierInfos(NodeRef nodeRef, String message, boolean enErreur) {
        // Vérification des préconditions.
        if(nodeRef == null || !this.aAspectDossierInfos(nodeRef)) return;
        // Ajout de l'aspect en erreur.
        this.ajouterInfoBaseSas(nodeRef, message, enErreur);
    }

    /**
     * Permet d'initialiser/ou mettre à jour les propriétés d'un nœud qui possède l'aspect 'actes59:dossierinfos' avec
     * l'aspect 'erreur59:etatInfoSas' avec pour valeur propriété 'erreur59:etatInfoSas' à 'ERREUR'.
     * @param nodeRef Le nœud dont on souhaite modifier les propriétés d'aspects.
     */
    public void miseAJourInfoBaseSasDossierInfos(NodeRef nodeRef) {
        // Vérification des préconditions.
        if(nodeRef == null || !this.aAspectDossierInfos(nodeRef)) return;
        // Ajout de l'aspect en erreur.
        // Récupération du nombre de fichiers en erreur.
        List<NodeRef> nodeRefList;
        ResultSet nodeRefResult = this.action.registryService.getSearchService()
                .query(nodeRef.getStoreRef(), SearchService.LANGUAGE_CMIS_STRICT,
                        "select * from erreur59:fichierActeInfo where erreur59:etatSasListe = 'ERREUR'"
                );
        if(nodeRefResult == null || nodeRefResult.length() == 0) nodeRefList = new ArrayList<>();
        else nodeRefList = nodeRefResult.getNodeRefs();

        String pluriel = nodeRefList.size() > 1 ? "s" : "";

        this.ajouterInfoBaseSas(nodeRef,
                String.format("Il y a %d fichier%s en erreur%s.", nodeRefList.size(), pluriel), (nodeRefList.size() > 0)
        );
    }

    /**
     * Permet d'initialiser/ou mettre à jour les propriétés d'un nœud qui possède l'aspect 'actes59:dossierinfos' avec
     * l'aspect 'erreur59:etatInfoSas' avec pour valeur propriété 'erreur59:etatInfoSas' à 'ERREUR'.
     * @param nodeRef Le nœud dont on souhaite modifier les propriétés d'aspects.
     */
    public void miseAJourInfoBaseSasDocInfosEnErreur(NodeRef nodeRef, String message) {
        if(nodeRef == null) return;
        this.ajouterInfoBaseSas(nodeRef, message, true);
    }

    /**
     * Permet d'initialiser/ou mettre à jour les propriétés d'un nœud qui possède l'aspect 'actes59:dossierinfos' avec
     * l'aspect 'erreur59:etatInfoSas' avec pour valeur propriété 'erreur59:etatInfoSas' à 'ERREUR'.
     * @param nodeRef Le nœud dont on souhaite modifier les propriétés d'aspects.
     */
    public void miseAJourInfoBaseSasDocInfosEnSucces(NodeRef nodeRef) {
        if(nodeRef == null) return;
        this.ajouterInfoBaseSas(nodeRef, "Le fichier est prêt à être stocker", false);
    }

    /**
     * Méthode permettant d'initialiser les propriétés pour l'aspect 'erreur59:etatInfoSas' avec pour valeur pour la
     * propriété 'erreur59:etatInfoSas' à 'ERREUR'.
     * @param nodeRef Le nœud dont on souhaite modifier les propriétés d'aspects.
     * @param enErreur Indique si l'information est en erreur.
     * @param message La valeur du de la propriété 'messageInfoSas:message' de l'aspect 'messageInfoSas:infosBaseSas'
     */
    public void ajouterInfoBaseSas(NodeRef nodeRef, String message, boolean enErreur) {
        // Vérification des préconditions.
        if(nodeRef == null) return;
        // Ajout de l'aspect et initialisation des propriétés.
        if(this.aAspectEtatInfoSas(nodeRef))
            this.action.registryService.getNodeService().addAspect(nodeRef, InfosBaseSasAspectModele.NOM,
                    this.obtenirParametreInfoBaseSas(message, enErreur));
        // Mise à jour des propriétés
        else
            this.action.registryService.getNodeService().setProperties(
                    nodeRef, obtenirParametreInfoBaseSas(message, enErreur)
            );
    }

    /**
     * Méthode permettant d'initialiser les propriétés pour l'aspect 'erreur59:etatInfoSas'.
     * @param message La valeur de la propriété 'erreur59:messageInfoSas'.
     * @param enErreur Indique si l'information est en erreur.
     * @return Un {@link HashMap} permettant d'initier les paramètres de l'aspect 'erreur59:etatInfoSas'.
     */
    private HashMap<QName, Serializable> obtenirParametreInfoBaseSas(String message, boolean enErreur) {
        HashMap<QName, Serializable> resultat = new HashMap<>();
        resultat.put(InfosBaseSasAspectModele.DATE_INFO_SAS, new Date());
        resultat.put(InfosBaseSasAspectModele.MESSAGE_INFO_SAS, message);
        resultat.put(InfosBaseSasAspectModele.ETAT_INFO_SAS, enErreur ? "ERREUR" : "OK");
        return resultat;
    }

    /**
     * Vérification de l'existence de l'aspect 'erreur59:etatInfoSas'.
     * @param nodeRef Le nœud sur lequel on souhaite vérifier l'état.
     * @return <c>true</c> si l'aspect existe. <c>false</c> sinon.
     */
    private boolean aAspectEtatInfoSas(NodeRef nodeRef) {
        return this.action.registryService.getNodeService().hasAspect(nodeRef, InfosBaseSasAspectModele.NOM);
    }

    /**
     * Vérifie que le noeud a l'aspect 'actes59:dossierinfos'.
     * @param nodeRef Le nœud sur lequel on souhaite vérifier l'état.
     * @return <c>true</c> si l'aspect existe; <c>false</c> sinon.
     */
    private boolean aAspectDossierInfos(NodeRef nodeRef) {
        // Vérification des préconditions.
        if( nodeRef == null) return false;
        // Vérification que l'aspect est présent.
        return this.action.registryService.getNodeService().hasAspect(nodeRef, DossierinfosAspectModele.NOM);
    }

    /**
     * Récupère l'aspect d'information adéquate pour un nœud.
     * @param nodeRef Le nœud dont on souhaite récupérer ... adéquate.
     * @return L'aspect adéquat à l'aspect du nœud.
     */
    private QName obtenirAspectInfoValide(NodeRef nodeRef) {
        NodeService nodeService = this.action.registryService.getNodeService();
        if(nodeService.hasAspect(nodeRef, DossierinfosAspectModele.NOM)) return DossierActeInfoAspectModele.NOM;
        if(nodeService.hasAspect(nodeRef, DocinfosAspectModele.NOM)) return FichierActeInfoAspectModele.NOM;
        return null;
    }
}

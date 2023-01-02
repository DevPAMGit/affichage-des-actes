package org.cd59.affichagedesactes.cron.stockage;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.SearchService;
import org.cd59.affichagedesactes.action.custom.stockage.StockerDossierActeAction;
import org.cd59.affichagedesactes.action.custom.stockage.StockerDossierActeRequete;
import org.cd59.affichagedesactes.modele.alfresco.aspect.DossierinfosAspectModele;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.ModeleDossierEtatEnvoi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Classe d'exécution du cron de stockage d'acte.
 */
public class ScheduledEnvoiActeJobExecuter {
    /**
     * Le registre des services Alfresco.
     */
    private ServiceRegistry serviceRegistry;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledEnvoiActeJobExecuter.class);

    /**
     * Modifie le paramètre de classe "serviceRegistry".
     * @param serviceRegistry Le registre de service.
     */
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    /**
     * Le chemin vers le l'espace de stockage.
     */
    private static final StoreRef STOREREF = new StoreRef("workspace://SpacesStore");

    /**
     *
     */
    private final static String REQUETE_RECHERCHE_SAS = "select * from actes59:sas";

    /**
     * Modifie les propriétés du nœud pour indiquer une erreur.
     * @param nodeRef Le nœud dont on souhaite modifier les propriétés.
     * @param message Le message d'erreur à afficher.
     */
    private void setErreur(NodeRef nodeRef, String message) {
        // Le service de gestion des nœuds.
        NodeService nodeService = this.serviceRegistry.getNodeService();

        // Vérification de l'aspect.
        if(!nodeService.hasAspect(nodeRef, DossierinfosAspectModele.NOM))
            return;

        // Modification du message.
        nodeService.setProperty(nodeRef, DossierinfosAspectModele.ERREURINTERNET, message);
        // Modification de l'état d'envoi.
        nodeService.setProperty(nodeRef, DossierinfosAspectModele.ETAT_ENVOI_DOSSIER, ModeleDossierEtatEnvoi.ERREUR.valeur);
    }

    /**
     * Execute le script.
     */
    public void execute(){
        SearchService searchService = this.serviceRegistry.getSearchService();

        LOGGER.error("BEFORE SAS QUERY");

        // Recherche du dossier d'actes.
        List<NodeRef> rechercheActes = searchService.query(STOREREF,
                SearchService.LANGUAGE_CMIS_STRICT, REQUETE_RECHERCHE_SAS).getNodeRefs();

        LOGGER.error("AFTER SAS QUERY");

        if(rechercheActes == null || rechercheActes.size() == 0) return;

        NodeRef dossiersActe = rechercheActes.get(0);

        LOGGER.error("BEFORE SEARCH IN SAS QUERY");

        // Recherche des dossiers d'actes en possibilités d'envoi.
        List<NodeRef> actes = searchService.query(STOREREF,
                SearchService.LANGUAGE_CMIS_STRICT, String.format(
                        StockerDossierActeRequete.RECHERCHE_DOSSIER_ACTE_STOCKABLE,
                        dossiersActe.getId())).getNodeRefs();

        LOGGER.error("AFTER SEARCH IN SAS QUERY " + actes.size());

        for(NodeRef nodeRef : actes){
            try {
                // Execution de l'action.
                new StockerDossierActeAction(serviceRegistry, nodeRef).executer();
            // Initialisation de l'erreur.
            }catch (Exception e) {
                this.setErreur(nodeRef, e.getMessage());
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}

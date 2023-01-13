package org.cd59.affichagedesactes.cron.stockage;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.SearchService;
import org.cd59.affichagedesactes.action.custom.stockage.StockerDossierActeAction;
import org.cd59.affichagedesactes.modele.alfresco.aspect.DossierinfosAspectModele;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.ModeleDossierEtatEnvoi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;


/**
 * Classe d'exécution du cron de stockage d'acte.
 */
public class ScheduledStockageActeJobExecuter {
    /**
     * Le registre des services Alfresco.
     */
    private ServiceRegistry serviceRegistry;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledStockageActeJobExecuter.class);

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
        NodeService nodeService = this.serviceRegistry.getNodeService();

        List<NodeRef> rechercheSas = searchService.query(STOREREF,
                SearchService.LANGUAGE_CMIS_STRICT, REQUETE_RECHERCHE_SAS).getNodeRefs();

        if(rechercheSas == null || rechercheSas.size() == 0) return;

        NodeRef dossiersSas = rechercheSas.get(0);

        for(ChildAssociationRef child : nodeService.getChildAssocs(dossiersSas)) {
            NodeRef childNode = child.getChildRef();
            if(nodeService.hasAspect(childNode, DossierinfosAspectModele.NOM)) {
                Serializable serializable = nodeService.getProperty(childNode, DossierinfosAspectModele.DOSSIERCOMPLET);
                if(serializable != null && ((boolean) serializable))
                    try {
                        new StockerDossierActeAction(serviceRegistry, childNode).executer();
                    }catch (Exception e) {
                        this.setErreur(childNode, e.getMessage());
                        LOGGER.error(e.getMessage(), e);
                    }

            }
        }
    }
}

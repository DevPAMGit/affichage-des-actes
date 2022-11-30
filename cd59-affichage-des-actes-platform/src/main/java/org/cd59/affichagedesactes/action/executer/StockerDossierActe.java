package org.cd59.affichagedesactes.action.executer;

import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.cd59.affichagedesactes.action.custom.stockage.StockerDossierActeAction;
import org.cd59.affichagedesactes.modele.alfresco.aspect.DossierinfosAspectModele;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.ModeleDossierEtatEnvoi;
import org.cd59.affichagedesactes.utilitaire.UtilitaireException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Action alfresco permettant de stocker les actes.
 */
public class StockerDossierActe extends ActionExecuterAbstractBase {

    /**
     * Le registre de service de l'action.
     */
    private ServiceRegistry serviceRegistry;

    /**
     * Le logger de la classe.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StockerDossierActe.class);

    /**
     * Méthode permettant d'initialiser le service de registre.
     * @param serviceRegistry La nouvelle valeur du registre de service.
     */
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

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
        nodeService.setProperty(nodeRef, DossierinfosAspectModele.ETAT_ENVOI_DOSSIER, ModeleDossierEtatEnvoi.ERREUR);
    }

    @Override
    protected void executeImpl(Action action, NodeRef nodeRef) {
        try {
            new StockerDossierActeAction(this.serviceRegistry, nodeRef).executer();
        }catch (UtilitaireException e) {
            this.setErreur(nodeRef, e.getMessage());
        } catch (Exception e) {
            this.setErreur(nodeRef, "Une erreur inattendue à eu lieu. Veuillez vous référer au log du serveur svp.");
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> list) {

    }
}

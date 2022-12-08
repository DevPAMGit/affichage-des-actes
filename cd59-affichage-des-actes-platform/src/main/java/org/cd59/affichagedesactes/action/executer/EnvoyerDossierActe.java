package org.cd59.affichagedesactes.action.executer;

import org.cd59.affichagedesactes.utilitaire.exception.UtilitaireException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;

import org.cd59.affichagedesactes.action.custom.envoi.EnvoyerDossierActeAction;
import org.cd59.affichagedesactes.modele.alfresco.aspect.DossierinfosAspectModele;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.ModeleDossierEtatEnvoi;

import java.util.List;

/**
 * Action d'envoi de dossier.
 */
public class EnvoyerDossierActe extends ActionExecuterAbstractBase {

    /**
     * Le registre de service d'Alfresco.
     */
    private ServiceRegistry serviceRegistry;

    /**
     * Le logger de la classe.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvoyerDossierActe.class);

    /**
     * Modifie la valeur du registre de service.
     * @param serviceRegistry La nouvelle valeur du registre de service.
     */
    public void setServiceRegistry(ServiceRegistry serviceRegistry){
        this.serviceRegistry = serviceRegistry;
    }

    /**
     * Modifie les propriétés du nœud pour indiquer une erreur.
     * @param nodeRef Le nœud dont on souhaite modifier les propriétés.
     * @param message Le message d'erreur à afficher.
     */
    private void setErreur(NodeRef nodeRef, String message) {
        // Le service de gestion des nœuds.
        NodeService  nodeService = this.serviceRegistry.getNodeService();

        // Vérification de l'aspect.
        if(!nodeService.hasAspect(nodeRef, DossierinfosAspectModele.NOM))
            return;

        // Modification du message.
        nodeService.setProperty(nodeRef, DossierinfosAspectModele.ERREURINTERNET, message);
        // Modification de l'état d'envoi.
        nodeService.setProperty(nodeRef, DossierinfosAspectModele.ETAT_ENVOI_DOSSIER, ModeleDossierEtatEnvoi.ERREUR.valeur);
    }

    @Override
    protected void executeImpl(Action action, NodeRef nodeRef) {
        try{
            // Execution de l'action.
            new EnvoyerDossierActeAction(serviceRegistry, nodeRef).executer();
        }catch (UtilitaireException e) {
            // Initialisation de l'erreur.
            this.setErreur(nodeRef, e.getMessage());
        }catch (Exception e) {
            this.setErreur(nodeRef, "Une erreur inattendue à eu lieu. Veuillez vous référer au log du serveur svp.");
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> list) {

    }
}

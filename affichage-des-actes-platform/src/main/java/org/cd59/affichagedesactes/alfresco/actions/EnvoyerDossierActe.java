package org.cd59.affichagedesactes.alfresco.actions;

import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.v2.action.EnvoyerDossierActeAction;

import java.util.List;

/**
 * Action permettant d'envoyer un dossier d'acte à l'affichage.
 */
public class EnvoyerDossierActe extends ActionExecuterAbstractBase {

    /**
     * Le registre des services Alfresco.
     */
    private ServiceRegistry serviceRegistry;

    /**
     * Modifie la valeur du paramètre de classe "serviceRegistry".
     * @param serviceRegistry La nouvelle valeur du paramètre de classe "serviceRegistry".
     * */
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    protected void executeImpl(Action action, NodeRef nodeRef) {
        // Execution de l'action d'envoi du fichier.
        new EnvoyerDossierActeAction(this.serviceRegistry, nodeRef).executer();
    }

    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> list) {

    }
}

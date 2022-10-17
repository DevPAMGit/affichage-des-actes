package org.cd59.affichagedesactes.alfresco.actions;

import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.repository.NodeRef;


import java.util.List;

/**
 * Action permettant de gérer le stockage d'un dossier dans le SAS.
 */
public class StockerDossierActeSas extends ActionExecuterAbstractBase {

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
        // new StockerDossierActe(this.serviceRegistry, nodeRef).executer();
        new org.cd59.affichagedesactes.v2.action.stockage.StockerDossierActeSas(this.serviceRegistry, nodeRef).executer();
    }

    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> list) {

    }
}
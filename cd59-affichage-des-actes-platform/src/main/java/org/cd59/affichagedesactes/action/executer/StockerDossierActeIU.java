package org.cd59.affichagedesactes.action.executer;

import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.modele.alfresco.aspect.DossierinfosAspectModele;

import java.util.List;

public class StockerDossierActeIU extends ActionExecuterAbstractBase {
    /**
     * Le registre de service de l'action.
     */
    private ServiceRegistry serviceRegistry;

    /**
     * Méthode permettant d'initialiser le service de registre.
     * @param serviceRegistry La nouvelle valeur du registre de service.
     */
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    protected void executeImpl(Action action, NodeRef nodeRef) {
        this.serviceRegistry.getNodeService().setProperty(nodeRef, DossierinfosAspectModele.DOSSIERCOMPLET, true);
    }

    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> list) {

    }
}

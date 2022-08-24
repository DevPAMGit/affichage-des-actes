package org.cd59.affichagedesactes.actions;

import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.logique.AffichageDesActesSourceLogique;

import java.util.List;

/**
 * Class d'action permettant de gérer la suppression d'un dossier d'acte dans le SAS.
 */
public class SupprimerDossierActeAction extends ActionExecuterAbstractBase {

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
        new AffichageDesActesSourceLogique(this.serviceRegistry.getNodeService()).razErreurDossierActeSas(nodeRef);
    }

    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> list) {

    }
}

package org.cd59.affichagedesactes.alfresco.actions;

import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectModele;

import java.util.List;

/**
 * Action permettant de stocker un dossier par
 */
public class StockerDossierActeSasIU extends ActionExecuterAbstractBase {

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
        // Préparation du dossier pour stockage.
        // Indication que le dossier est prêt à l'envoi.
        this.serviceRegistry.getNodeService().setProperty(
                nodeRef, DossierinfosAspectModele.ETAT_STOCKAGE_DOSSIER, "Prêt à être stocké"
        );
        // Indication que le dossier est complet.
        this.serviceRegistry.getNodeService().setProperty(
                nodeRef, DossierinfosAspectModele.DOSSIERCOMPLET, true
        );
        // Pise en charge du stockage par la règle dans le dossier SAS :).
    }

    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> list) {

    }
}

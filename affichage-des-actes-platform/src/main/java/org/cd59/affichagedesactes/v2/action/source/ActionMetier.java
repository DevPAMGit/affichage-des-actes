package org.cd59.affichagedesactes.v2.action.source;

import org.alfresco.service.ServiceRegistry;

/**
 * Source pour toutes les actions métiers
 */
public abstract class ActionMetier {

    /**
     * Le registre de service d'Alfresco.
     */
    public final ServiceRegistry registryService;

    /**
     * Initialise une nouvelle instance de la classe {@link ActionMetier}.
     * @param serviceRegistry Le registre de service Alfresco.
     */
    public ActionMetier(ServiceRegistry serviceRegistry) {
        this.registryService = serviceRegistry;
    }

    /**
     * Execute l'action métier.
     */
    public abstract void executer();

}

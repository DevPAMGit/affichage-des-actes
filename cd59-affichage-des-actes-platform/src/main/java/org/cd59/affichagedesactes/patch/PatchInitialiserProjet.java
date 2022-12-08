package org.cd59.affichagedesactes.patch;

import javax.imageio.spi.ServiceRegistry;

/**
 * Patch permettant d'initialiser le projet.
 */
public class PatchInitialiserProjet {
    /**
     * Le registre de service.
     */
    private ServiceRegistry serviceRegistry;

    /**
     * Modifier la valeur du registre de service.
     * @param serviceRegistry Le registre de service.
     */
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }
}

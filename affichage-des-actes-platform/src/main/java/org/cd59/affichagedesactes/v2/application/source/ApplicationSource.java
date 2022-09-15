package org.cd59.affichagedesactes.v2.application.source;

import org.cd59.affichagedesactes.v2.action.source.ActionMetier;

/**
 * Source de toutes les applications.
 */
public class ApplicationSource {
    /**
     * L'action à laquelle est reliée l'application
     */
    protected final ActionMetier action;

    /**
     * Initialise une nouvelle instance de la classe {@link ApplicationSource}.
     * @param action L'action à laquelle est reliée l'application.
     */
    public ApplicationSource(ActionMetier action) {
        this.action = action;
    }
}

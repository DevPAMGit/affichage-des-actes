package org.cd59.affichagedesactes.action.custom.source.v1.logger;

import org.cd59.affichagedesactes.action.custom.source.v1.loggeraction.IActionAnnulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

/**
 * Le logger d'action pour annulation.
 */
public class LoggerAction implements ILoggerAction {
    /**
     * Le logger de la classe.
     */
    private static final Logger logger = LoggerFactory.getLogger(LoggerAction.class);

    /**
     * La pile d'action permettant l'annulation des actions.
     */
    private final Stack<IActionAnnulation> actions;

    /**
     * Initialise une nouvelle instance de la classe {@link LoggerAction}.
     */
    public LoggerAction() {
        this.actions = new Stack<>();
    }

    /**
     * Méthode permettant d'ajouter une nouvelle action d'annulation dans la pile.
     * @param action L'action d'annulation à ajotuer.
     */
    public void ajouterAnnulation(IActionAnnulation action) {
        if(action == null) return;
        this.actions.push(action);
    }

    @Override
    public void annuler() {
        while(this.actions.size() > 0) {
            try { this.actions.pop().annuler(); }
            catch (InvocationTargetException | IllegalAccessException e) { logger.error(e.getMessage(), e); }
        }
    }
}

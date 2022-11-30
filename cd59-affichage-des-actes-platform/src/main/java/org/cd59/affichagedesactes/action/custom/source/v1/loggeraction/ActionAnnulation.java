package org.cd59.affichagedesactes.action.custom.source.v1.loggeraction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Action d'annulation.
 */
public class ActionAnnulation implements IActionAnnulation {
    /**
     * L'instance sur laquelle executer la méthode.
     */
    protected final Object instance;

    /**
     * La méthode a exécuter.
     */
    protected final Method methode;

    /**
     * Les arguments de la méthode à executer.
     */
    protected Object[] arguments;

    /**
     * Le résultat de l'action effectué.
     */
    protected Object resultat;

    /**
     * Initialise une nouvelle instance de la classe {@link ActionAnnulation}.
     * @param instance  L'instance sur laquelle executer la méthode.
     * @param methode   La méthode à executer
     * @param arguments Les arguments de la méthode à exécuter.
     */
    public ActionAnnulation(Object instance, Method methode, Object... arguments) {
        this.methode = methode;
        this.instance = instance;
        this.arguments = arguments;
    }

    @Override
    public void annuler() throws InvocationTargetException, IllegalAccessException {
        this.resultat = this.methode.invoke(this.instance, this.arguments);
    }

    @Override
    public Object getResultat() {
        return this.resultat;
    }
}

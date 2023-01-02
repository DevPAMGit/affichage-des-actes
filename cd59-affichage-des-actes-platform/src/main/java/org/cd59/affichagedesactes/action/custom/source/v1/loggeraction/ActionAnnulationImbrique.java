package org.cd59.affichagedesactes.action.custom.source.v1.loggeraction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

public class ActionAnnulationImbrique extends ActionAnnulation {
    /**
     * L'action précédemment exécutée.
     */
    private final IActionAnnulation action;
    /**
     * Initialise une nouvelle instance de la classe {@link ActionAnnulationImbrique}.
     * @param action    La précédente action exécutée.
     * @param instance  L'instance sur laquelle executer la méthode.
     * @param methode   La méthode à executer
     * @param arguments Les arguments de la méthode à exécuter.
     */
    public ActionAnnulationImbrique(IActionAnnulation action, Object instance, Method methode, Object... arguments) {
        super(instance, methode, arguments);
        this.action = action;
    }

    @Override
    public void annuler() throws InvocationTargetException, IllegalAccessException {
        ArrayList<Object> args = new ArrayList<>();
        args.add(this.action.getResultat());

        Collections.addAll(args, this.arguments);

        this.arguments = args.toArray();
        super.annuler();
    }
}

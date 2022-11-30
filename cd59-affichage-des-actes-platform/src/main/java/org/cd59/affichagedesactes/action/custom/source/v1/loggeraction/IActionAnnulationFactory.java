package org.cd59.affichagedesactes.action.custom.source.v1.loggeraction;

import java.lang.reflect.Method;

/**
 * Classe factory pour créer des actions d'annulations.
 */
public class IActionAnnulationFactory{
    /**
     * Crée une nouvelle action d'annulation.
     * @param instance  L'instance sur laquelle executer la méthode.
     * @param methode La méthode à executer.
     * @param arguments La liste des arguments de la méthode.
     * @return L'action d'annulation.
     */
    public static IActionAnnulation creerActionAnnulation(Object instance, Method methode, Object... arguments) {
        return new ActionAnnulation(instance, methode, arguments);
    }

    /**
     * Crée une nouvelle action d'annulation.
     * @param action    La précédente action exécutée.
     * @param instance  L'instance sur laquelle executer la méthode.
     * @param methode   La méthode à executer
     * @param arguments Les arguments de la méthode à exécuter.
     * @return L'action d'annulation.
     */
    public static IActionAnnulation creerActionAnnulation(
            IActionAnnulation action, Object instance, Method methode, Object... arguments
    ) {
        return new ActionAnnulationImbrique(action, instance, methode, arguments);
    }
}

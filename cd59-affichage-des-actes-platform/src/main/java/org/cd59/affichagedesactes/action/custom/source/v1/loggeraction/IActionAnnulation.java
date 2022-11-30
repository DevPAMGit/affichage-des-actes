package org.cd59.affichagedesactes.action.custom.source.v1.loggeraction;

import java.lang.reflect.InvocationTargetException;

/**
 * Interface contractuelle pour les logs d'actions.
 */
public interface IActionAnnulation {
    /**
     * Execute la méthode d'annulation.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    void annuler() throws InvocationTargetException, IllegalAccessException;

    /**
     * Récupère le résultat de l'action précédente.
     * @return Le résultat de l'action précédente.
     */
    Object getResultat();
}

package org.cd59.affichagedesactes.v2.action;

/**
 * Classe gérant les objets d'exclusions mutuelles des actions.
 */
public class MutexAction {

    /**
     * Objet d'exclusion mutuelle des actions sur les métadonnées d'information du dossier SAS.
     */
    public final static Object MUTEX_INFO_SAS = new Object();

    /**
     * Objet d'exclusion mutuelle des actions sur l'arborescence.
     */
    public static final Object MUTEX_ARBORESCENCE = new Object();

    /**
     * Objet d'exclusion mutuelle des actions sur le compteur du dossier SAS.
     */
    public static final Object MUTEX_COMPTEUR_SAS = new Object();
}

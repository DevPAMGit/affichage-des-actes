package org.cd59.affichagedesactes.action.custom.stockage;

public class StockerDossierActeMutex {
    /**
     * Mutex pour la création d'arborescence.
     */
    public final static Object MUTEX_ARBORESCENCE = new Object();

    /**
     * Mutex pour la manipulation du dossier SAS.
     */
    public final static Object MUTEX_SAS = new Object();
}

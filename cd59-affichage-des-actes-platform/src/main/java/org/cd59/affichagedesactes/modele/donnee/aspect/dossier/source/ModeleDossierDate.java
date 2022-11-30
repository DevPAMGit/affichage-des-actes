package org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source;

import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe modèle pour la date d'un dossier d'acte.
 */
public class ModeleDossierDate {
    /**
     * La date du dossier.
     */
    protected final Date date;

    /**
     * La date au format jour/mois/année.
     */
    public final String dateChaine;

    /**
     * Initialise une nouvelle instance de la classe {@link ModeleDossierDate}.
     * @param date La date du dossier d'acte.
     */
    public ModeleDossierDate(Date date) throws ModeleException {
        // Vérification des préconditions.
        if(date == null) throw new ModeleException("La date du dossier n'est pas renseignée.");

        // Initialisation du modèle.
        // 1. Initialisation de la date.
        this.date = date;

        // 2. Formatage de la date.
        this.dateChaine = new SimpleDateFormat("dd/MM/yyyy").format(this.date);
    }


}

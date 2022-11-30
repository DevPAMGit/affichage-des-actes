package org.cd59.affichagedesactes.utilitaire;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe utilitaire pour la manipulation de dates.
 */
public class UtilitaireDate {
    /**
     * le calendrier permettant une manipulation.
     */
    public static final GregorianCalendar CALENDRIER = new GregorianCalendar();

    /**
     * Table de correspondance des mois.
     */
    public static final String[] MOIS = {"janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août",
            "septembre", "octobre", "novembre", "décembre"};

    /**
     * Extrait le jour d'une {@link Date}.
     * @param date La date dont il faut extraire le jour.
     * @return Un entier correspondant au jour de la date dans le mois.
     */
    public static int extraireJour(Date date) {
        CALENDRIER.setTime(date);
        return CALENDRIER.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Extrait le mois d'une {@link Date}.
     * @param date La date dont il faut extraire le mois.
     * @return Un entier correspondant au mois de la date en paramètre.
     */
    public static int extraireMois(Date date){
        CALENDRIER.setTime(date);
        // Retourne l'index du mois d'où l'incrémentation de l'extraction.
        return CALENDRIER.get(Calendar.MONTH) + 1;
    }

    /**
     * Extrait l'année d'un {@link Date}.
     * @param date La date dont il faut extraire l'année.
     * @return Un entier correspondant à l'année de la date en paramètre.
     */
    public static int extraireAnnee(Date date) {
        CALENDRIER.setTime(date);
        return CALENDRIER.get(Calendar.YEAR);
    }

    /**
     * Extrait le nom du mois d'une {@link Date}.
     * @param date La date dont il faut extraire le nom du mois.
     * @return Une chaîne de caractères correspondant au nom du mois.
     */
    public static String extraireNomMois(Date date) {
        return MOIS[extraireMois(date) -1];
    }

    /**
     * Extrait le nom du mois d'une {@link Date}.
     * @param mois Le mois dont on souhaite le nom.
     * @return Une chaîne de caractères correspondant au nom du mois.
     */
    public static String extraireNomMois(int mois) {
        return MOIS[mois -1];
    }
}

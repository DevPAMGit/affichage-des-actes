package org.cd59.utils;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Librairie utilitaire pour les dates.
 */
public class DateUtils {

    /**
     * Le calendrier d'extraction.
     */
    private static GregorianCalendar CALENDRIER = new GregorianCalendar();

    /**
     * La correspondance d'un mois.
     */
    private static final String[] MOIS = {"janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août",
            "septembre", "octobre", "novembre", "décembre"};

    /**
     * Extrait le jour du mois d'une date.
     * @param date La date dont il faut faire une extraction.
     * @return Le jour du mois d'une date.
     */
    public static int obtenirJour(Date date) {
        CALENDRIER.setTime(date);
        return CALENDRIER.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Extrait le mois d'une date.
     * @param date La date dont il faut faire une extraction.
     * @return Le mois d'une date.
     */
    public static int obtenirMois(Date date) {
        CALENDRIER.setTime(date);
        return CALENDRIER.get(Calendar.MONTH) + 1;
    }

    /**
     * Extrait la correspondance du mois.
     * @param date La date dont il faut faire une extraction.
     * @return La correspondance du mois.
     */
    public static String obtenirCorrespondanceMois(Date date) {
        return MOIS[obtenirMois(date) - 1];
    }

    /**
     * Extrait l'année du mois d'une date.
     * @param date La date dont il faut faire une extraction.
     * @return L'année du mois d'une date.
     */
    public static int obtenirAnnee(Date date) {
        CALENDRIER.setTime(date);
        return CALENDRIER.get(Calendar.YEAR);
    }



}

package org.cd59.affichagedesactes.utilitaire;


import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe de teste pour la classe {@link UtilitaireDate}.
 */
public class TestDateUtilitaire {

    /**
     * Le jour attendu.
     */
    private static final int JOUR = 7;

    /**
     * Le mois attendu.
     */
    private static final int MOIS = 11;

    /**
     * Le nom du mois attendu.
     */
    private static final String NOM_MOIS = "novembre";

    /**
     * L'année attendue.
     */
    private static final int ANNEE = 2022;


    /**
     * La date à tester.
     */
    private static final Date DATE = new GregorianCalendar(ANNEE, Calendar.NOVEMBER, JOUR).getTime();


    /**
     * Méthode permettant de tester l'extraction du jour de la date en paramètre.
     */
    @Test
    public void testerExtractionJour(){
        Assert.assertEquals(JOUR, UtilitaireDate.extraireJour(DATE));
    }

    /**
     * Méthode permettant de tester l'extraction du mois de la date en paramètre.
     */
    @Test
    public void testerExtractionMois(){
        Assert.assertEquals(MOIS, UtilitaireDate.extraireMois(DATE));
    }

    /**
     * Méthode permettant de tester l'extraction du nom du mois de la date en paramètre.
     */
    @Test
    public void testerExtractionNomMois(){
        Assert.assertEquals(NOM_MOIS, UtilitaireDate.extraireNomMois(DATE));
    }

    /**
     * Méthode permettant de tester l'extraction de l'année de la date en paramètre.
     */
    @Test
    public void testerExtractionAnnee(){
        Assert.assertEquals(ANNEE, UtilitaireDate.extraireAnnee(DATE));
    }

}

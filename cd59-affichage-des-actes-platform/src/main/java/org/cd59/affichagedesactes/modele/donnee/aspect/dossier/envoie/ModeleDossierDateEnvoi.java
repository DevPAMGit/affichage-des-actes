package org.cd59.affichagedesactes.modele.donnee.aspect.dossier.envoie;

import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.ModeleDossierDate;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;
import org.cd59.affichagedesactes.utilitaire.UtilitaireChaineDeCaracteres;
import org.cd59.affichagedesactes.utilitaire.UtilitaireDate;

import java.util.Date;


public class ModeleDossierDateEnvoi extends ModeleDossierDate {

    /**
     * L'année de la date.
     */
    public final int annee;

    /**
     * L'année au format {@link String}
     */
    public final String anneeChaine;

    /**
     * Le mois en chiffres.
     */
    public final int mois;

    /**
     * Le mois en chaîne de caractère.
     */
    public final String moisChaine;

    /**
     * Le nom du mois
     */
    public final String nomMois;

    /**
     * Le jour en chiffre.
     */
    public final int jour;

    /**
     * Le jour sur deux caractères.
     */
    public final String jourChaine;

    /**
     * Initialise une nouvelle instance de la classe {@link ModeleDossierDate}.
     *
     * @param date La date du dossier d'acte.
     */
    public ModeleDossierDateEnvoi(Date date) throws ModeleException {
        super(date);

        // Extraction de l'année.
        this.annee = UtilitaireDate.extraireAnnee(this.date);
        this.anneeChaine = Integer.toString(this.annee);

        // Extraction du mois.
        this.mois = UtilitaireDate.extraireMois(this.date);
        this.nomMois = UtilitaireDate.extraireNomMois(this.date);
        this.moisChaine = UtilitaireChaineDeCaracteres.entierSurNChiffres(this.mois, 2);

        // Extraction du jour.
        this.jour = UtilitaireDate.extraireJour(this.date);
        this.jourChaine = UtilitaireChaineDeCaracteres.entierSurNChiffres(this.jour, 2);
    }
}

package org.cd59.affichagedesactes.v2.action.stockage.modele;


import org.cd59.utils.DateUtils;

import java.util.Date;


/**
 * Modele pour le dossier d'acte.
 */
public class DossierActeModele {

    /**
     * Le signataire de l'acte.
     */
    public String signataire;

    /**
     * Indique si le dossier est complet.
     */
    public boolean estDossierComplet;

    /**
     * L'objet de l'acte.
     */
    public String objet;

    /**
     * Le sigle de l'organisation.
     */
    public String sigleOrganisation;

    /**
     * Le type de l'acte.
     */
    public String type;

    /**
     * La date de l'acte.
     */
    public Date date;

    /**
     * Le jour de la date.
     */
    public int jour;

    /**
     * Le mois de la date.
     */
    public int mois;

    /**
     * L'année de la date/
     */
    public int annee;

    /**
     * Le message d'erreur.
     */
    public final StringBuilder message;

    /**
     * Le numéro de l'acte.
     */
    public String numero;

    /**
     * Le résumé de l'acte.
     */
    public String resume;

    /**
     * L'identifiant du dossier.
     */
    public String identifiant;

    /**
     * La source du dossier.
     */
    public String source;

    /**
     * Initialise une nouvelle instance de la classe {@link DossierActeModele}.
     */
    public DossierActeModele() {
        this.message = new StringBuilder();
    }

    /**
     * Vérifie que les données du modèle sont valides.
     * @return <c>true</c> si les données sont valides ; <c>faux</c> sinon.
     */
    public boolean estValide() {

        if(this.signataire == null || this.signataire.isEmpty())
            this.message.append("Le signataire n'est pas renseigné. ");

        if(this.objet == null || this.objet.isEmpty())
            this.message.append("L'objet' n'est pas renseigné. ");

        if(this.sigleOrganisation == null || this.sigleOrganisation.isEmpty())
            this.message.append("Le sigle de la direction n'est pas renseigné. ");

        if(this.type == null || this.type.isEmpty())
            this.message.append("Le type de la direction n'est pas renseigné. ");

        else if(!this.type.equals("Délibération") && !this.type.equals("Arrêté") && !this.type.equals("ARRETE")
                && !this.type.equals("DELIBERATION"))
            this.message.append("Le type de la direction n'est pas valide (une de ces valeur est attendue : " +
                    "Délibération, Arrêté, ARRETE, DELIBERATION).");

        // Formatage du type.
        else this.formatterTypeDossier();

        // Récupération des données de la date.
        if(this.date == null) this.message.append("La date n'est pas renseignée. ");
        else this.extraireDonneesDate();

        return (this.message.length() == 0);
    }

    /**
     * Indique si la source est manuelle ou non.
     * @return <c>true</c> si la source est manuelle, sinon <c>false</c>.
     */
    public boolean estDeSourceManuelle() {
        if(this.source == null || this.source.trim().isEmpty()) return false;
        return (this.source.equalsIgnoreCase("manuel"));
    }

    /**
     * Extrait les données de la date du dossier.
     */
    private void extraireDonneesDate() {
        this.jour = DateUtils.obtenirJour(this.date);
        this.mois = DateUtils.obtenirMois(this.date);
        this.annee = DateUtils.obtenirAnnee(this.date);
    }

    /**
     * Formate le type de dossier.
     */
    private void formatterTypeDossier() {
        this.type = this.type.replaceAll("[éeê]", "e").replaceAll("[ÉÊ]", "E").toUpperCase();
    }

    /**
     * Récupère le message d'erreur.
     * @return Le message d'erreur.
     */
    public String obtenirMessageErreur() {
        return this.message.toString();
    }

    public String obtenirMois() {
        if(this.date == null) return "";
        return DateUtils.obtenirCorrespondanceMois(this.date);
    }
}

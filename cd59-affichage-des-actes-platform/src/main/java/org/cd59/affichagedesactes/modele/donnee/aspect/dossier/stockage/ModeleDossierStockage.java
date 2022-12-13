package org.cd59.affichagedesactes.modele.donnee.aspect.dossier.stockage;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.action.custom.source.v1.action.IModeleNoeudAction;
import org.cd59.affichagedesactes.action.custom.stockage.StockerDossierActeDonneesFactory;
import org.cd59.affichagedesactes.modele.alfresco.aspect.DossierinfosAspectModele;
import org.cd59.affichagedesactes.modele.donnee.aspect.document.stockage.ModeleDocumentActeStockage;
import org.cd59.affichagedesactes.modele.donnee.aspect.document.stockage.ModeleDocumentAnnexeStockage;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.ModeleDossier;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.ModeleDossierEtatEnvoi;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.type.ModeleDossierTypologieEnumeration;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.stockage.multiple.IModeleDossierStockageMultiple;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;
import org.cd59.affichagedesactes.modele.donnee.source.ModeleNoeud;
import org.cd59.affichagedesactes.utilitaire.UtilitaireChaineDeCaracteres;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe modèle pour un dossier pour stockage.
 */
public class ModeleDossierStockage extends ModeleDossier implements IModeleDossierStockageMultiple {
    /**
     * La date du dossier.
     */
    public final ModeleDossierDateStockage date;

    /**
     * Le logiciel source du dépôt du dossier.
     */
    public final String source;

    /**
     * Le sigle de la direction émettrice du dossier d'acte.
     */
    public final String organisation;

    /**
     * Le dossier dans lequel est contenu de dossier d'acte.
     */
    public final SasModele sas;

    /**
     * Le numéro de du dossier.
     */
    public final String numero;

    /**
     * La valeur de la 'source' si celle-ci est null par défaut.
     */
    private final static String SOURCE_MANUEL = "manuel";

    /**
     * L'identifiant du dossier d'acte.
     */
    public final String identifiant;

    /**
     * Indique si le dossier existe en référence multiple.
     */
    private boolean referenceMultiple;

    /**
     * Le nom du dossier.
     */
    private String nom;

    /**
     * Initialise une nouvelle instance de la classe {@link ModeleNoeud}.
     // @param serviceRegistry Le service de registre du nœud.
     * @param nodeRef         Le nœud source.
     */
    public ModeleDossierStockage(IModeleNoeudAction modeleNoeudAction, NodeRef nodeRef)
            throws Exception {
        super(modeleNoeudAction, nodeRef);

        try { this.sas = new SasModele(modeleNoeudAction, this.getNoeudParent(nodeRef)); }
        catch (Exception e) { throw new Exception("Le dossier d'acte n'est pas contenu dans un dossier 'sas'."); }

        if(!this.avoirAspect(DossierinfosAspectModele.NOM))
            throw new ModeleException("Le dossier d'acte n'est pas d'aspect 'dossierinfos'.");

        this.organisation = this.getProprieteChaine(DossierinfosAspectModele.ORGASIGLE);
        this.referenceMultiple = this.obtenirProprieteEnBooleen(DossierinfosAspectModele.EST_EN_REF_MULTIPLE);
        this.date = new ModeleDossierDateStockage(this.getProprieteDate(DossierinfosAspectModele.DATEDOSSIER));

        // Mise à jour de l'an
        this.setPropriete(DossierinfosAspectModele.ANNEE, this.date.annee);

        // Formatage des données.
        this.source = this.formatterSource();
        this.numero = this.formatterNumero();
        this.identifiant = this.formatterIdentifiant();

        // Remise à zéro de l'état de stockage, l'état d'envoi, le message d'erreur.
        this.setMessageErreur(null);
        this.setEtatEnvoi(ModeleDossierEtatEnvoi.EN_ATTENTE);
        this.setEtatStockage(ModeleDossierEtatStockage.PRET_A_ETRE_STOCKE);

        // Modification de la description.
        this.setPropriete(ContentModel.PROP_DESCRIPTION, String.format("Dossier %s numéro %s du %s %s %d",
                this.typologie.typeMajuscule == ModeleDossierTypologieEnumeration.ARRETE ?
                "de l'arrêté" : "de la délibération", this.identifiant, this.date.jourChaine, this.date.nomMois,
                this.date.annee));
    }

    /**
     * Modifie la valeur de l'objet
     * @param objet La nouvelle valeur de l'objet.
     */
    private void setObjet(String objet) throws PreRequisException, NoSuchMethodException {
        this.setPropriete(DossierinfosAspectModele.OBJET, objet);
        this.objet = objet;
    }

    /**
     * Modifie la valeur de la référence
     * @param valeur La valeur de la référence multiple.
     // @throws ModeleException Si la propriété à modifier est null.
     */
    public void setReferenceMultiple(boolean valeur) throws /*ModeleException,*/ PreRequisException, NoSuchMethodException {
        this.referenceMultiple = valeur;
        this.setPropriete(DossierinfosAspectModele.EST_EN_REF_MULTIPLE, this.referenceMultiple);
    }

    /**
     * Modifie la complétude du dossier..
     * @param valeur La valeur de la complétude du dossier.
     // ModeleException Si la propriété à modifier est null.
     */
    public void setCompletude(boolean valeur) throws PreRequisException, NoSuchMethodException {
        this.setPropriete(DossierinfosAspectModele.ETAT_STOCKAGE_DOSSIER, valeur);
    }

    /**
     * Modifie l'état de stockage du nœud.
     * @param etat L'état de stockage du nœud.
     // @throws ModeleException Si la propriété à modifier est null.
     */
    public void setEtatStockage(ModeleDossierEtatStockage etat) throws /*ModeleException,*/ PreRequisException, NoSuchMethodException {
        this.setPropriete(DossierinfosAspectModele.ETAT_STOCKAGE_DOSSIER, etat.valeur);
    }

    /**
     * Récupère le nom du dossier d'acte.
     * @return Le nom du dossier d'acte.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Formate la donnée contenue dans la propriété source du nœud.
     * @return 'manuel' si la propriété du nœud est null. Sinon la valeur propriété du nœud.
     // @throws ModeleException Si une propriété du modèle à manipuler est null.
     */
    private String formatterSource() throws /*ModeleException,*/ PreRequisException, NoSuchMethodException {
        String source = this.getProprieteChaine(DossierinfosAspectModele.SOURCE);

        // Si la source est null alors elle est manuelle.
        if(UtilitaireChaineDeCaracteres.etreNullOuVide(source)) source = SOURCE_MANUEL;
        else source = source.toLowerCase();

        // Modifie la propriété 'source'.
        this.setPropriete(DossierinfosAspectModele.SOURCE, source);
        return source;
    }

    /**
     * Formate le numéro de l'acte afin de répondre au prérequis du dossier.
     * @return L'identifiant à formatter.
     */
    private String formatterNumero() throws ModeleException, PreRequisException, NoSuchMethodException {
        // Récupération du numéro d'acte.
        String numero = this.getProprieteChaine(DossierinfosAspectModele.NUMEROACTE);

        // Si la source est manuelle construction du numero.
        if(this.source.equals(SOURCE_MANUEL)) {

            // Vérification que le sigle de l'organisation soit renseigné pour générer le numéro d'acte
            if(UtilitaireChaineDeCaracteres.etreNullOuVide(this.organisation))
                // Erreur : L'organisation n'est pas renseignée.
                throw new ModeleException("Le sigle de la direction n'est pas renseigné.");

            // initialisation du numéro
            numero = String.format("%s_%d_%s", this.organisation, this.date.annee, this.sas.genererNumeroActe());

        // Si la source n'est pas manuelle elle doit être renseignée.
        } else if( UtilitaireChaineDeCaracteres.etreNullOuVide(numero) )
            throw new ModeleException("Le numéro de l'acte n'est pas renseigné alors que la source n'est pas manuelle.");

        // Formatage du numéro de l'acte pour le plier aux prérequis Alfresco.
        else {

            // Formatage du numéro d'acte (suppression des caractères interdits Alfresco 1).
            numero = numero.replaceAll("[\\*<>/\\?:\\|]", "_").trim();

            // Formatage du numéro d'acte (suppression des caractères interditsAlfresco 2) : Retrait du '.' en de fin.
            if (numero.endsWith(".")) numero = numero.substring(0, numero.length() - 1);
        }

        // Modification du nœud
        this.setPropriete(DossierinfosAspectModele.NUMEROACTE, numero);
        // Retour du résultat.
        return numero;
    }

    /**
     * Formate l'identifiant de l'acte afin de répondre au prérequis du dossier.
     * @return L'identifiant à formatter.
     */
    private String formatterIdentifiant() throws /*ModeleException,*/ PreRequisException, NoSuchMethodException {
        // Si la source est manuelle alors construction du
        if(this.source.equals(SOURCE_MANUEL)) {
            String identifiant = String.format("%s_%s", this.typologie.diminutif, this.numero);
            this.setPropriete(DossierinfosAspectModele.IDDOSSIER, identifiant);
            return identifiant;
        }

        return this.getProprieteChaine(DossierinfosAspectModele.IDDOSSIER);
    }

    @Override
    protected void checkPreconditions() throws ModeleException {
        StringBuilder message = new StringBuilder();

        // Vérification des préconditions de la classe mère.
        try { super.checkPreconditions(); }
        catch (ModeleException e) { message.append(e.getMessage()); }

        // L'objet est vérifié dans le constructeur parent.
        // Le signataire est vérifié dans le constructeur parent.
        // Le sigle de l'organisation est vérifié dans la construction du numéro de l'acte (formatterNumero).

        if(!this.source.equals(SOURCE_MANUEL)) {

            // Vérification de l'identifiant.
            if(UtilitaireChaineDeCaracteres.etreNullOuVide(this.identifiant))
                message.append("L'identifiant de l'acte n'est pas renseigné alors que la source n'est pas manuel. ");
        }

        // Erreur si des éléments sont présents dans le message.
        if(message.length() > 0) throw new ModeleException(message.toString());
    }

    @Override
    public void switchReferenceMultiple() throws /*ModeleException,*/ PreRequisException, NoSuchMethodException {
        this.referenceMultiple = !this.referenceMultiple;
        this.setReferenceMultiple(this.referenceMultiple);
    }

    @Override
    public void setNom(int numero) throws PreRequisException, NoSuchMethodException {
        this.nom = StockerDossierActeDonneesFactory.getNomDossier(this.identifiant, numero);
        this.setPropriete(ContentModel.PROP_NAME, this.nom);
        this.setPropriete(ContentModel.PROP_TITLE, this.nom);
    }

    @Override
    public String getIdentifiant() {
        return this.identifiant;
    }

    @Override
    public boolean getReferenceMultiple() {
        return this.referenceMultiple;
    }

    @Override
    public void initFichierActe(List<NodeRef> nodeRefList)
            throws ModeleException, IOException, NoSuchAlgorithmException, PreRequisException, NoSuchMethodException {
        // 1. Erreur s'il n'y a aucuns nœuds.
        if(nodeRefList == null || nodeRefList.size() == 0)
            throw new ModeleException("Le dossier d'acte ne contient aucun fichier d'acte original.");
        // 2. Erreur s'il y a plus d'un fichier d'acte.
        if(nodeRefList.size() > 1)
            throw new ModeleException("Le dossier d'acte ne contient plus d'un fichier d'acte original.");

        // Traitement du fichier d'acte.
        this.acteOriginal = new ModeleDocumentActeStockage(this.modeleNoeudAction, nodeRefList.get(0), this.identifiant, this.date);
    }

    @Override
    public void setFichierAnnexes(List<NodeRef> nodeRefList) throws ModeleException, IOException, NoSuchAlgorithmException, PreRequisException, NoSuchMethodException {
        // Vérification des preconditions.
        this.annexes = new ArrayList<>();

        int cpt = 0, max = (nodeRefList != null) ? nodeRefList.size() : 0;

        if(nodeRefList != null)
            for(NodeRef nodeRef : nodeRefList) {
                cpt++;
                if(max == 1)
                    this.annexes.add(new ModeleDocumentAnnexeStockage(
                            this.modeleNoeudAction, nodeRef, this.identifiant, this.date));
                else
                    this.annexes.add(new ModeleDocumentAnnexeStockage(
                            this.modeleNoeudAction, nodeRef, this.identifiant, this.date, cpt));
            }
    }

}

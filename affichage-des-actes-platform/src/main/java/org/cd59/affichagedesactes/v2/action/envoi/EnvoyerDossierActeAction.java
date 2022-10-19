package org.cd59.affichagedesactes.v2.action.envoi;


import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.docinfos.DocinfosAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.affichage59.aspect.erreur.ErreurAspectModele;
import org.cd59.affichagedesactes.v2.action.service.affichage.services.modele.EnvoiActeFichierModel;
import org.cd59.affichagedesactes.v2.action.service.affichage.AffichageDesActesWebservice;
import org.cd59.affichagedesactes.v2.action.envoi.exception.ResultatNonConformeException;
import org.cd59.affichagedesactes.v2.action.source.exception.ActionMetierException;
import org.cd59.affichagedesactes.v2.action.source.exception.NomNoeudVideException;
import org.cd59.affichagedesactes.v2.action.source.exception.NoeudNullException;
import org.cd59.affichagedesactes.v2.action.source.ActionMetier;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.ServiceRegistry;
import org.cd59.utils.DateUtils;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.io.*;

/**
 * Action métier permettant d'envoyer un dossier au site d'affichage des actes.
 */
public class EnvoyerDossierActeAction extends ActionMetier {

    /**
     * Le logger de la classe.
     */
    private final Logger LOGGER = LoggerFactory.getLogger(EnvoyerDossierActeAction.class);

    /**
     * L'hôte de destination des
     */
    private static final String HOTE = "XXXXXXXXXXXXX";

    /**
     * Le login du webservice.
     */
    private static final String LOGIN = "XXXXXXXXXXXXXXXXX";

    /**
     * Le mot de passe du webservice.
     */
    private static final String MOT_DE_PASSE = "XXXXXXXXXXXXXXXXXXXX";

    /**
     * Le nœud représentant le dossier d'acte à envoyer.
     */
    private final NodeRef dossierActe;

    /**
     * Le nœud représentant un acte original.
     */
    private NodeRef acteOriginal;

    /**
     * Le nœud représentant l'acte signé.
     */
    private NodeRef acte;

    /**
     * La liste de noeuds annexe.
     */
    private List<NodeRef> annexes;

    /**
     * Initialise une nouvelle instance de la classe {@link ActionMetier}.
     * @param serviceRegistry Le registre de service Alfresco.
     * @param nodeRef Le nœud du dossier d'acte.
     */
    public EnvoyerDossierActeAction(ServiceRegistry serviceRegistry, NodeRef nodeRef) {
        super(serviceRegistry);
        this.acte = null;
        this.annexes = null;
        this.acteOriginal = null;
        this.dossierActe = nodeRef;
    }

    @Override
    public void executer() {
        try {
            // Vérification des préconditions.
            if(!this.verifierPrecondition()) return;

            // Suppression de l'aspect d'erreur si celui-ci existe.
            this.miseAZeroAspectErreur();

            // Copie du fichier.
            this.copierActeOriginal();

            // Envoi du document.
            AffichageDesActesWebservice serviceWebAffichage = new AffichageDesActesWebservice(HOTE);
            JSONObject resultat = new JSONObject(serviceWebAffichage.envoyerActe(
                    LOGIN, MOT_DE_PASSE, this.obtenirDonneesFichierActe(), this.obtenirDonneesAnnexe(),
                    this.obtenirMetadonneesJson()));

            // Si le JSON a l'identifiant 'url_image' alors l'envoi est 'OK'.
            if(resultat.has("url_image")) this.modifierEtatEnvoiEnAffiche(resultat);

            // Sinon une erreur est survenue.
            else throw new ActionMetierException(resultat.getString("message"));

        // Erreur l'envoie est effectif, mais les données renvoyées sont non conformes.
        }catch(ResultatNonConformeException e1) {

            // Log de l'erreur.
            LOGGER.error(e1.getMessage(), e1);

            // Suppression du fichier signé s'il existe.
            if(this.acte != null) this.supprimerNoeud(this.acte);

            // Mise à jour des états du nœud.
            try { this.modifierPropriete(this.dossierActe, DossierinfosAspectModele.ERREURINTERNET, e1.getMessage()); }
            catch (Exception e3) { LOGGER.error(e3.getMessage(), e3); }

        // Erreur quelconque.
        }catch (Exception e2) {

            // Log de l'erreur.
            LOGGER.error(e2.getMessage(), e2);

            // Suppression du fichier signé s'il existe.
            if(this.acte != null) this.supprimerNoeud(this.acte);

            // Mise à jour des métadonnées d'envoi.
            try{ this.modifierPropriete(this.dossierActe, DossierinfosAspectModele.ERREURINTERNET, e2.getMessage()); }
            catch (Exception e4) { LOGGER.error(e4.getMessage(), e4); }
        }
    }

    /**
     * Met à jour les nœuds en 'affichés'.
     * @throws ActionMetierException Si le nœud ou la propriété en paramètre est null ou vide.
     */
    private void modifierEtatEnvoiEnAffiche(JSONObject resultatEnvoi) throws ActionMetierException {
        // Vérification des préconditions.
        // Il doit obligatoirement avoir un élément "url_image" dans le JSON.
        if(!resultatEnvoi.has("url_image"))
            throw new ResultatNonConformeException(
                    "Le retour du web service d'affichage n'a pas le lien vers l'acte affiché."
            );

        // Il doit obligatoirement avoir un élément "date" dans le JSON.
        if(!resultatEnvoi.has("date"))
            throw new ResultatNonConformeException(
                    "Le retour du web service d'affichage n'a pas la date d'affichage de l'acte."
            );

        // Récupération de l'URL d'affichage
        String url = resultatEnvoi.getString("url_image");

        // tentative de récupération de la date d'affichage.
        Date date;
        try { date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(resultatEnvoi.getString("date")); }

        // Erreur : La date n'est pas au format attendu : dd/MM/yyyy HH:mm
        catch (ParseException e) { throw new ResultatNonConformeException(
                    "La date d'affichage de l'acte n'est pas au format attendu (dd/MM/yyyy HH:mm).");
        }

        this.miseAJourEtatNoeudsEnAffiches(date, url);
    }

    /**
     * Supprime l'aspect d'erreur sur les nœuds.
     * @throws ActionMetierException Si l'aspect ou le nœud sont null.
     */
    private void miseAZeroAspectErreur() throws ActionMetierException {
        if(this.dossierActe != null)
            this.modifierPropriete(this.dossierActe, DossierinfosAspectModele.ERREURINTERNET, null);
    }

    /**
     * Met à jour les nœuds en 'affichés'.
     * @param date La date de l'acte.
     * @param url L'URL de l'acte.
     * @throws ActionMetierException Si le nœud ou la propriété en paramètre est null ou vide.
     */
    private void miseAJourEtatNoeudsEnAffiches(Date date, String url) throws ActionMetierException {
        // Mise à jour des métadonnées d'envoi.
        //  Sur le fichier d'acte.
        //if(this.acteOriginal != null) this.modifierEtatEnAffiche(this.acteOriginal, date, url);

        //  Sur le dossier d'acte.
        if(this.dossierActe != null) this.modifierEtatEnAffiche(this.dossierActe, date, url);

        // Sur l'annexe
        //if(this.annexes != null && this.annexes.size() > 0) this.modifierEtatEnAffiche(this.annexes.get(0), date, url);
    }

    /**
     * Met à jour l'état du nœud en envoyer, mais en erreur.
     * @throws ActionMetierException Si le nœud ou la propriété en paramètre est null ou vide.
     */
    private void miseAJourEtatNoeudsEnEnvoyeMaisErreur() throws ActionMetierException {
        // Mise à jour des métadonnées d'envoi.
        //  Sur le dossier d'acte.
        if(this.dossierActe != null)
            this.modifierEtatEnvoiEnEnvoyeMaisErreur(this.dossierActe);
    }

    /**
     * Met à jour l'état du nœud en erreur.
     * @param message Le message d'erreur.
     * @throws ActionMetierException Si le nœud ou la propriété en paramètre est null ou vide.
     */
    private void miseAJourEtatNoeudsEnErreur(String message) throws ActionMetierException {
        // Mise à jour des métadonnées d'envoi.
        //  Sur le dossier d'acte.
        if(this.dossierActe != null)
            this.modifierEtatEnErreur(this.dossierActe, message);
    }

    /**
     * Modifier l'état d'envoi d'un nœud en erreur.
     * @param nodeRef Le nœud dont il faut modifier l'état.
     * @throws ActionMetierException Si le nœud ou la propriété en paramètre est null ou vide.
     */
    private void modifierEtatEnvoiEnEnvoyeMaisErreur(NodeRef nodeRef) throws ActionMetierException {
        if(nodeRef == null) return;
        this.modifierPropriete(nodeRef, DossierinfosAspectModele.ERREURINTERNET,
                "Envoyé mais erreur lors la récupération du résultat du webservice");
    }

    /**
     * Met l'état d'envoi en erreur.
     * @param nodeRef Le nœud que l'on souhaite mettre en erreur.
     * @throws ActionMetierException Si le nœud ou la propriété en paramètre est null ou vide.
     */
    private void modifierEtatEnAffiche(NodeRef nodeRef, Date date, String url) throws ActionMetierException {

        HashMap<QName, Serializable> proprietes = new HashMap<>(this.registryService.getNodeService().getProperties(nodeRef));
        proprietes.put(DossierinfosAspectModele.URLAFFICHAGE, url);
        proprietes.put(DossierinfosAspectModele.STATUTAFFICHAGE, true);
        proprietes.put(DossierinfosAspectModele.DATEAFFICHAGEGED, date);
        proprietes.put(DossierinfosAspectModele.DATEAFFICHAGEINTERNET, date);

        this.miseAJourAspect(nodeRef, DossierinfosAspectModele.NOM, proprietes);
    }

    /**
     * Met l'état d'envoi en erreur.
     * @param nodeRef Le nœud que l'on souhaite mettre en erreur.
     * @param message Le message d'erreur.
     * @throws ActionMetierException Si le nœud ou la propriété en paramètre est null ou vide.
     */
    private void modifierEtatEnErreur(NodeRef nodeRef, String message) throws ActionMetierException {
        HashMap<QName, Serializable> proprietes = new HashMap<>(this.registryService.getNodeService().getProperties(nodeRef));
        proprietes.put(DossierinfosAspectModele.ERREURINTERNET , message);
        this.miseAJourAspect(nodeRef, ErreurAspectModele.NOM, proprietes);
    }

    /**
     * Permet de récupérer le contenu du fichier nœud de l'acte.
     * @throws ActionMetierException Si le nœud du fichier d'acte est null ou si son nom est null ou vide ou si le nœud
     * de l'acte est null.
     * @throws IOException IOException Si une erreur d'E/S à lieu durant la lecture du contenu du nœud.
     */
    private EnvoiActeFichierModel obtenirDonneesFichierActe() throws ActionMetierException, IOException {
        return new EnvoiActeFichierModel(this.obtenirNomNoeudFichierActe(), this.obtenirContenuFichierActe());
    }

    /**
     * Permet de récupérer le contenu du fichier nœud de l'acte.
     * @throws ActionMetierException Si le nœud du fichier d'acte est null ou si son nom est null ou vide ou si le nœud
     * de l'acte est null.
     * @throws IOException IOException Si une erreur d'E/S à lieu durant la lecture du contenu du nœud.
     */
    private EnvoiActeFichierModel obtenirDonneesAnnexe() throws ActionMetierException, IOException {
        if(this.annexes == null ||this.annexes.size() == 0) return null;
        return new EnvoiActeFichierModel(this.obtenirNomNoeudPremiereAnnexe(), this.obtenirContenuPremiereAnnexe());
    }

    /**
     * Permet la récupération du nom du nœud de la première annexe.
     * @return Le nom du nœud du fichier de la première annexe.
     * @throws ActionMetierException Si le nœud du fichier d'acte est null ou si son nom est null ou vide.
     */
    private String obtenirNomNoeudPremiereAnnexe() throws ActionMetierException {
        // Vérification des préconditions.
        if(this.annexes == null || this.annexes.size() == 0) return null;
        // Tentative de récupération du nom avec son extension.
        try{ return this.obtenirNomNoeud(this.annexes.get(0)); }
        // Si le nom du nœud est vide : modification du message.
        catch (NomNoeudVideException e) { throw new NomNoeudVideException("Le nom de l'annexe est null ou vide."); }
    }

    /**
     * Permet de récupérer le contenu de la première annexe.
     * @return Le contenu de la première annexe.
     * @throws IOException Si une erreur d'E/S à lieu durant la lecture du contenu du nœud.
     */
    private byte[] obtenirContenuPremiereAnnexe() throws IOException {
        try {
            if(this.annexes == null || this.annexes.size() ==0) return null;
            return this.obtenirContenuFichier(this.annexes.get(0));
        }
        catch (NoeudNullException e) { return null; }
    }

    /**
     * Permet la récupération du nom du nœud du fichier d'acte.
     * @return Le nom du nœud du fichier d'acte.
     * @throws ActionMetierException Si le nœud du fichier d'acte est null ou si son nom est null ou vide.
     */
    private String obtenirNomNoeudFichierActe() throws ActionMetierException {
        // Vérification que le nœud est non null ou vide.
        if(this.acte == null) throw new NoeudNullException("Le fichier d'acte est null ou vide.");
        // Tentative de récupération du nom avec son extension.
        try{ return this.obtenirNomNoeud(this.acte); }
        // Si le nom du nœud est vide : modification du message.
        catch (NomNoeudVideException e) {
            throw new NomNoeudVideException("Le nom du fichier d'acte est null ou vide.");
        }
    }

    /**
     * Permet de récupérer le contenu du fichier d'acte.
     * @return Le contenu du fichier d'acte.
     * @throws NoeudNullException Si le nœud de l'acte est null.
     * @throws IOException Si une erreur d'E/S à lieu durant la lecture du contenu du nœud.
     */
    private byte[] obtenirContenuFichierActe() throws NoeudNullException, IOException {
        // Vérification des préconditions.
        if(this.acte == null) throw new NoeudNullException("Le noeud du fichier d'acte ne peut être null ou vide");
        // Récupération du contenu.
        return this.obtenirContenuFichier(this.acte);
    }

    /**
     * Permet de récupérer les métadonnées du dossier d'acte.
     * @return Les métadonnées du dossier d'acte.
     */
    private JSONObject obtenirMetadonneesJson() throws ActionMetierException {
        JSONObject metadonnees = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date date = this.obtenirValeurProprieteEnDate(this.dossierActe, DossierinfosAspectModele.DATEDOSSIER);
        String dateChaine = date == null ? "" : sdf.format(date);

        metadonnees.put("objet", this.obtenirObjet());
        metadonnees.put("resume", this.obtenirValeurProprieteEnChaine(this.dossierActe,
                DossierinfosAspectModele.RESUME));
        metadonnees.put("empreinte", this.obtenirValeurProprieteEnChaine(this.acteOriginal,
                DocinfosAspectModele.EMPREINTE));
        metadonnees.put("signataire", this.obtenirValeurProprieteEnChaine(this.dossierActe,
                DossierinfosAspectModele.SIGNATAIRE));
        metadonnees.put("numero_acte", this.obtenirValeurProprieteEnChaine(this.dossierActe,
                DossierinfosAspectModele.NUMEROACTE));
        metadonnees.put("typologie", String.format("%s", this.obtenirValeurProprieteEnChaine(this.dossierActe,
                DossierinfosAspectModele.TYPEDOSSIER)));

        metadonnees.put("date", dateChaine);

        metadonnees.put("condition_1_rgaa", "1");
        metadonnees.put("condition_2_rgaa", "1");
        metadonnees.put("condition_3_rgaa", "1");
        metadonnees.put("condition_4_rgaa", "1");

        return metadonnees;
    }

    /**
     * Construit l'objet pour le Json d'envoi.
     * @return L'objet pour le Json d'envoi.
     */
    private String obtenirObjet() throws ActionMetierException {

        String typologie = this.obtenirValeurProprieteEnChaine(this.dossierActe, DossierinfosAspectModele.TYPEDOSSIER);
        String denomination = "";
        if( typologie != null && (typologie.equals("Délibération") || typologie.equals("DELIBERATION")) ) {
            typologie = "Délibération ";
            denomination = "Réunion du ";
        } else if( typologie != null && (typologie.equals("Arrêté") || typologie.equals("ARRETE")) ) {
            typologie = "Arrêté";
            denomination = "";
        }

        return String.format(
                "%s - %s-%s - %s %d %s %d",
                typologie,
                this.obtenirValeurProprieteEnChaine(this.dossierActe, DossierinfosAspectModele.ORGASIGLE),
                this.obtenirValeurProprieteEnChaine(this.dossierActe, DossierinfosAspectModele.NUMEROACTE),
                denomination,
                DateUtils.obtenirJour(this.obtenirValeurProprieteEnDate(this.dossierActe, DossierinfosAspectModele.DATEDOSSIER)),
                DateUtils.obtenirCorrespondanceMois(this.obtenirValeurProprieteEnDate(this.dossierActe, DossierinfosAspectModele.DATEDOSSIER)),
                DateUtils.obtenirAnnee(this.obtenirValeurProprieteEnDate(this.dossierActe, DossierinfosAspectModele.DATEDOSSIER))
        );
    }

    /**
     * Vérifier les préconditions de l'action.
     * @return <c>true</c> si les préconditions sont respectés; sinon <c>false</c>.
     */
    private boolean verifierPrecondition() throws ActionMetierException {
        // 1. Vérification Le nœud est un dossier de type stockageactes59:dossierActe.
        // Erreur le nœud ne contient pas le type : pas de traitement.
        if(!this.aAspect(this.dossierActe, DossierinfosAspectModele.NOM)) {
            this.miseAJourEtatNoeudsEnErreur(
                    String.format("Le dossier d'acte n'a pas le type requis (%s:%s).",
                            DossierinfosAspectModele.NOM.getPrefixString(), DossierinfosAspectModele.NOM.getLocalName())
            );
            return false;
        }

        // Vérifie que le dossier puisse partir.
        String etat = this.obtenirValeurProprieteEnChaine(this.dossierActe, DossierinfosAspectModele.URLAFFICHAGE);
        if(etat != null && !etat.isEmpty()) {
            this.miseAJourEtatNoeudsEnErreur("Le dossier a déjà une URL d'affichage.");
            return false;
        }

        // Vérification que le nœud contient un enfant de type Acte original.
        // Recherche de tous les actes originaux dans le dossier.
        List<NodeRef> resultatActe = this.rechercherNoeuds(this.dossierActe,
                String.format(
                    "select * from actes59:docinfos " +
                    "where actes59:typedocument = 'ACTE_ORIGINAL' " +
                    "and IN_TREE('%s')", this.dossierActe.getId()
                )
        );

        // Arrêt s'il n'y a pas de fichier de type 'stockageactes59:acteOriginal'.
        if(resultatActe == null || resultatActe.size() == 0) {
            this.miseAJourEtatNoeudsEnErreur("Le dossier n'a pas d'acte original.");
            return false;
        }

        // Initialisation de l'acte original.
        this.acteOriginal = resultatActe.get(0);

        // Récupération des annexes.
        List<NodeRef> resultatAnnexe = this.rechercherNoeuds(this.dossierActe,
                 String.format(
                    "select * from actes59:docinfos " +
                    "where actes59:typedocument = 'ANNEXE' " +
                    "and IN_TREE('%s')", this.dossierActe.getId()
                 )
        );
        if(resultatAnnexe == null ||resultatAnnexe.size() == 0) this.annexes = new ArrayList<>();
        else this.annexes = resultatAnnexe;

        return true;
    }

    /**
     * Copie l'acte et le signe.
     */
    public void copierActeOriginal() throws FileNotFoundException, ActionMetierException, NoSuchAlgorithmException, IOException {
        // Vérification de la précondition.
        // Si l'acte original n'existe pas : arrêt.
        if(this.acteOriginal == null || this.dossierActe == null)
            throw new ActionMetierException("L'acte original ou le dossier de l'acte ne sont pas renseignés.");

        String nom  = String.format("%s_ACTE",
                this.obtenirValeurProprieteEnChaine(this.dossierActe, DossierinfosAspectModele.IDDOSSIER));

        // Copie de l'acte original.
        this.acte = this.registryService.getFileFolderService().copy(this.acteOriginal, this.dossierActe, nom)
        .getNodeRef();

        // Modification des propriétés de l'acte.
        this.modifierPropriete(this.acte, ContentModel.PROP_TITLE, nom);
        this.modifierPropriete(this.acte, DocinfosAspectModele.TYPEDOCUMENT, "ACTE");
        this.modifierPropriete(this.acte, ContentModel.PROP_DESCRIPTION, "L'acte signé.");

        // Signature du document.
        this.tamponnerNoeud(this.acte);
        // Calcule de l'emprunte fichier.
        this.modifierPropriete(this.acte, DocinfosAspectModele.EMPREINTE, this.obtenirEmpreinte(this.acte));
    }
}


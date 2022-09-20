package org.cd59.affichagedesactes.v2.action;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.affichage59.aspect.affichage.AffichageAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.acteoriginal.ActeOriginalTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossieracte.DossierActeTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossieractes.DossierActesTypeHelperModele;
import org.cd59.affichagedesactes.v2.action.service.affichage.services.modele.EnvoiActeFichierModel;
import org.cd59.affichagedesactes.v2.action.source.exception.ActionMetierException;
import org.cd59.affichagedesactes.v2.action.source.exception.NoeudNullException;
import org.cd59.affichagedesactes.v2.action.source.exception.NomNoeudVideException;
import org.cd59.affichagedesactes.v2.action.service.affichage.AffichageDesActesWebservice;
import org.cd59.affichagedesactes.v2.action.source.ActionMetier;
import org.cd59.affichagedesactes.v2.application.ApplicationEnvoiSAS;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private static final String HOTE = "https://lenordged.spontaneit.fr";

    /**
     * Le login du webservice.
     */
    private static final String LOGIN = "";

    /**
     * Le mot de passe du webservice.
     */
    private static final String MOT_DE_PASSE = "";

    /**
     * Le nœud représentant le dossier d'acte à envoyer.
     */
    private final NodeRef dossierActe;

    /**
     * Le nœud représentant un acte original.
     */
    private NodeRef acteOriginal;

    /**
     * La liste de noeuds annexe.
     */
    private List<NodeRef> annexes;

    /**
     * Gestionnaire de nœuds pour l'aspect 'affichage59:affichage'.
     */
    private final ApplicationEnvoiSAS applicationEnvoiSAS;

    /**
     * Initialise une nouvelle instance de la classe {@link ActionMetier}.
     * @param serviceRegistry Le registre de service Alfresco.
     */
    public EnvoyerDossierActeAction(ServiceRegistry serviceRegistry, NodeRef nodeRef) {
        super(serviceRegistry);
        this.dossierActe = nodeRef;
        this.applicationEnvoiSAS = new ApplicationEnvoiSAS(this);
    }

    @Override
    public void executer() {
        // Vérification des préconditions.
        if(!this.verifierPrecondition()) return;

        // Envoi du document.
        try {
            AffichageDesActesWebservice serviceWebAffichage = new AffichageDesActesWebservice(HOTE);
            serviceWebAffichage.envoyerActe(LOGIN, MOT_DE_PASSE, this.obtenirDonneesFichierActe(),
                    this.obtenirDonneesAnnexe(), this.obtenirMetadonneesJson());
        }catch (Exception e) {
            // Log en cas d'erreur.
            LOGGER.error(e.getMessage(), e);
        }
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
        try { return this.obtenirContenuFichier(this.acteOriginal); }
        catch (NoeudNullException e) { return null; }
    }

    /**
     * Permet la récupération du nom du nœud du fichier d'acte.
     * @return Le nom du nœud du fichier d'acte.
     * @throws ActionMetierException Si le nœud du fichier d'acte est null ou si son nom est null ou vide.
     */
    private String obtenirNomNoeudFichierActe() throws ActionMetierException {
        // Vérification que le nœud est non null ou vide.
        if(this.acteOriginal == null) throw new NoeudNullException("Le fichier d'acte est null ou vide.");
        // Tentative de récupération du nom avec son extension.
        try{ return this.obtenirNomNoeud(this.acteOriginal); }
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
        if(this.acteOriginal == null) throw new NoeudNullException("Le noeud du fichier d'acte ne peut être null ou vide");
        // Récupération du contenu.
        return this.obtenirContenuFichier(this.acteOriginal);
    }

    /**
     * Permet de récupérer les métadonnées du dossier d'acte.
     * @return Les métadonnées du dossier d'acte.
     */
    private JSONObject obtenirMetadonneesJson() throws ActionMetierException {
        JSONObject metadonnees = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        metadonnees.append("objet", this.obtenirValeurProprieteEnChaine(this.dossierActe,
                DossierActeTypeModele.OBJET));
        metadonnees.append("resume",this.obtenirValeurProprieteEnChaine(this.dossierActe,
                DossierActeTypeModele.RESUME));
        metadonnees.append("empreinte", this.obtenirValeurProprieteEnChaine(this.acteOriginal,
                ActeOriginalTypeModele.EMPREINTE));
        metadonnees.append("signataire", this.obtenirValeurProprieteEnChaine(this.dossierActe,
                DossierActeTypeModele.SIGNATAIRE));
        metadonnees.append("numero_acte", this.obtenirValeurProprieteEnChaine(this.dossierActe,
                DossierActeTypeModele.IDENTIFIANT));
        metadonnees.append("typologie", this.obtenirValeurProprieteEnChaine(this.acteOriginal,
                ActeOriginalTypeModele.TYPOLOGIE_FICHIER));

        Date date = this.obtenirValeurProprieteEnDate(this.dossierActe, DossierActeTypeModele.DATE);
        if(date == null) metadonnees.append("date", "");
        else metadonnees.append("date", sdf.format(date));

        metadonnees.append("condition_1_rgaa", "1");
        metadonnees.append("condition_2_rgaa", "1");
        metadonnees.append("condition_3_rgaa", "1");
        metadonnees.append("condition_4_rgaa", "1");

        return metadonnees;
    }

    /**
     * Vérifier les préconditions de l'action.
     * @return <c>true</c> si les préconditions sont respectés; sinon <c>false</c>.
     */
    private boolean verifierPrecondition() {
        // 1. Vérification Le nœud est un dossier de type stockageactes59:dossierActe.
        DossierActesTypeHelperModele dossier = new DossierActesTypeHelperModele(this.registryService, this.dossierActe);

        // Erreur le nœud ne contient pas le type : pas de traitement.
        if(!dossier.hasType()) {
            this.applicationEnvoiSAS.modifierAspect(this.dossierActe, "Erreur lors de l'envoie", null);
            return false;
        }
        // Vérifie que le dossier puisse partir.
        String etat = new AffichageAspectHelperModele(this.registryService, this.dossierActe).getEtat();
        if(etat == null || !etat.equals("Prêt à être envoyer")) {
            this.applicationEnvoiSAS.modifierAspect(this.dossierActe, "Erreur lors de l'envoie", null);
            return false;
        }

        // Vérification que le nœud contient un enfant de type Acte original.
        SearchService searchService = this.registryService.getSearchService();
        ResultSet resultatActe = searchService.query(this.dossierActe.getStoreRef(),
                SearchService.LANGUAGE_CMIS_STRICT, "select * from stockageactes59:acteOriginal");

        // Arrêt s'il n'y a pas de fichier de type 'stockageactes59:acteOriginal'.
        if(resultatActe == null ||resultatActe.length() == 0) {
            this.applicationEnvoiSAS.modifierAspect(this.dossierActe, "Erreur lors de l'envoie", null);
            return false;
        }
        // Initialisation de l'acte original.
        this.acteOriginal = resultatActe.getNodeRef(0);

        // Récupération des annexes.
        ResultSet resultatAnnexe = searchService.query(this.dossierActe.getStoreRef(),
                SearchService.LANGUAGE_CMIS_STRICT, "select * from stockageactes59:annexe");
        if(resultatAnnexe == null ||resultatAnnexe.length() == 0) this.annexes = new ArrayList<>();
        else this.annexes = resultatAnnexe.getNodeRefs();

        return true;
    }
}


package org.cd59.affichagedesactes.action.custom.envoi;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.*;
import org.alfresco.util.TempFileProvider;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.cd59.affichagedesactes.action.custom.envoi.service.affichage.AffichageDesActesWebservice;
import org.cd59.affichagedesactes.action.custom.envoi.service.affichage.services.modele.EnvoiActeFichierModel;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.action.custom.source.v1.action.ModeleAction;
import org.cd59.affichagedesactes.modele.alfresco.aspect.DocinfosAspectModele;
import org.cd59.affichagedesactes.modele.donnee.aspect.document.source.ModeleDocument;
import org.cd59.affichagedesactes.modele.donnee.aspect.document.source.ModeleDocumentAnnexe;
import org.cd59.affichagedesactes.modele.donnee.aspect.document.source.ModeleDocumentType;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.envoie.ModeleDossierEnvoi;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.ModeleDossierEtatEnvoi;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.type.ModeleDossierTypologieEnumeration;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;
import org.cd59.affichagedesactes.utilitaire.UtilitaireChaineDeCaracteres;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Action personnalisée permettant d'envoyer un dossier d'acte.
 */
public class EnvoyerDossierActeAction extends ModeleAction {
    /**
     * L'hôte du webservice.
     */
    private static final String HOTE = "https://lenordged.spontaneit.fr";

    /**
     * Le login du webservice.
     */
    private static final String LOGIN = "8b5Ex34Vw7EeweHq";

    /**
     * Le mot de passe du webservice.
     */
    private static final String MOT_DE_PASSE = "s7pYCFhkUVGdes4Q";

    /**
     * Le modèle de données pour l'envoi d'acte.
     */
    private final ModeleDossierEnvoi modele;

    /**
     * Le logger de la classe.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvoyerDossierActeAction.class);

    /**
     * Initialise une nouvelle instance de la classe {@link EnvoyerDossierActeAction}.
     * @param serviceRegistry Le registre de service d'Alfresco.
     */
    public EnvoyerDossierActeAction(ServiceRegistry serviceRegistry, NodeRef nodeRef)
            throws ModeleException, PreRequisException, IOException, NoSuchAlgorithmException, NoSuchMethodException {
        super(serviceRegistry);

        NodeService nodeService = this.serviceRegistry.getNodeService();
        this.modele = new ModeleDossierEnvoi(this, nodeRef);

        ArrayList<NodeRef> annexes = new ArrayList<>();
        ArrayList<NodeRef> acteOriginal = new ArrayList<>();

        for(ChildAssociationRef child : this.serviceRegistry.getNodeService().getChildAssocs(nodeRef)) {

            NodeRef childNode = child.getChildRef();

            if (nodeService.hasAspect(childNode, DocinfosAspectModele.NOM)) {

                Serializable typeDocument = nodeService.getProperty(childNode, DocinfosAspectModele.TYPEDOCUMENT);

                if(typeDocument == null)
                    throw new ModeleException("Le dossier contient un fichier non typé");

                if( "ACTE_ORIGINAL".equals(typeDocument) ) acteOriginal.add(childNode);
                else if( "ANNEXE".equals(typeDocument) ) annexes.add(childNode);

            }else
                throw new PreRequisException( String.format("\"Le dossier d'acte contient un fichier non typé %s.",
                        DocinfosAspectModele.NOM.getLocalName()) );

        }

        this.modele.initFichierActe(acteOriginal);
        this.modele.setFichierAnnexes(annexes);
    }

    @Override
    public void executer() throws Exception {
        try {
            this.modele.setMessageErreur(null);
            this.modele.setEtatEnvoi(ModeleDossierEtatEnvoi.PRET_A_ETRE_ENVOYE);

            this.modele.setActeSigne(this.copierActeOriginal());

            ModeleDocument acteSigne = this.modele.getActeSigne();

            List<ModeleDocumentAnnexe> listeAnnexes = this.modele.getAnnexes();
            ModeleDocument annexe = (listeAnnexes == null || listeAnnexes.size() == 0) ? null :
                    this.modele.getAnnexes().get(0);

            AffichageDesActesWebservice webservice = new AffichageDesActesWebservice(HOTE);
            JSONObject resultat = new JSONObject(webservice.envoyerActe(
                    LOGIN, MOT_DE_PASSE, new EnvoiActeFichierModel(acteSigne.nom, acteSigne.contenu),
                    annexe != null ? new EnvoiActeFichierModel(annexe.nom, annexe.contenu) : null, this.getMetadonnees()
            ));

            if (!resultat.has("url_image")) {
                String message = resultat.getString("message");

                this.modele.getActeOriginal().supprimerNoeud();
                throw new ModeleException(message);
            }

            Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE)
                    .parse(resultat.getString("date"));

            this.modele.setUrlAffichage(resultat.getString("url_image"));
            this.modele.setEtatEnvoi(ModeleDossierEtatEnvoi.STOCKE);
            this.modele.setDateAffichageInternet(date);
            this.modele.setDateAffichageGED(date);

        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            this.annuler();
            throw e;
        }
    }

    /**
     * Copie le fichier d'acte original et y ajoute le tampon.
     * @return Le nœud représentant l'acte signé.
     */
    private NodeRef copierActeOriginal()
            throws IOException, FileNotFoundException, PreRequisException, NoSuchMethodException {

        String nomActeOriginal = this.getProprieteChaine(this.modele.getActeOriginal().getNoeud(), ContentModel.PROP_NAME);

        int dernierePosition = nomActeOriginal.lastIndexOf('.');
        String extension = "";
        if(dernierePosition > -1)
            extension = nomActeOriginal.substring(dernierePosition);

        // Création du nom du nœud.
        String nom = String.format("%s_%s%s", this.modele.identifiant, ModeleDocumentType.ACTE.valeur, extension);

        NodeRef acteSigne = this.copier(this.modele.getNoeud(), this.modele.getActeOriginal().getNoeud(), nom);

        this.setPropriete(acteSigne, ContentModel.PROP_TITLE, nom);
        this.setPropriete(acteSigne, DocinfosAspectModele.TYPEDOCUMENT, ModeleDocumentType.ACTE);
        this.setPropriete(acteSigne, ContentModel.PROP_DESCRIPTION,String.format(
                "Le document d'acte n°%s signé et tamponné.", this.modele.identifiant
        ));

        this.tamponnerNoeud(acteSigne);

        // Retour du résultat.
        return acteSigne;
    }

    /**
     * Récupère les métadonnées pour l'envoi d'acte.
     * @return Les métadonnées au format JSON.
     * @throws JSONException Si une erreur est lancée lors de la création du JSON.
     */
    private JSONObject getMetadonnees() throws JSONException {
        JSONObject metadonnees = new JSONObject();

        StringBuilder objet = new StringBuilder(
                String.format("%s - %s - ", this.modele.typologie.typeMinuscule.valeur, this.modele.getNumero())
        );

        if(this.modele.typologie.typeMinuscule == ModeleDossierTypologieEnumeration.Deliberation)
            objet.append("Réunion du ");

        objet.append(String.format("%s %s %s",
                this.modele.date.jourChaine, this.modele.date.nomMois, this.modele.date.anneeChaine)
        );

        metadonnees.put("objet", objet.toString());
		// On a choisi d'envoyer la métadonnée objet de la GED dans la metadonnee resume de l'affichage coté lenord
        metadonnees.put("resume", UtilitaireChaineDeCaracteres.etreNullOuVide(this.modele.objet) ? "" : this.modele.objet);
        metadonnees.put("date", this.modele.date.dateChaine);
        metadonnees.put("signataire", this.modele.signataire);
        metadonnees.put("numero_acte", this.modele.getNumero());
        metadonnees.put("typologie", this.modele.typologie.typeMinuscule.valeur);
        metadonnees.put("empreinte", this.modele.getActeOriginal().empreinte);

        metadonnees.put("condition_1_rgaa", "1");
        metadonnees.put("condition_2_rgaa", "1");
        metadonnees.put("condition_3_rgaa", "1");
        metadonnees.put("condition_4_rgaa", "1");

        return metadonnees;
    }

    /**
     * Appose un tampon sur un nœud en paramètre.
     * @param nodeRef Le nœud sur lequel apposer un tampon.
     */
    protected void tamponnerNoeud(NodeRef nodeRef) throws IOException {
        ContentReader reader = this.serviceRegistry.getContentService().getReader(nodeRef, ContentModel.PROP_CONTENT);

        File newPdf = TempFileProvider.createTempFile("tmp", "tampon");
        FileOutputStream fos = new FileOutputStream(newPdf);

        PDDocument doc = PDDocument.load(reader.getContentInputStream());
        PDPage page = doc.getDocumentCatalog().getPages().get(0);
        PDFont font = PDType0Font.load(doc, PDDocument.class.getResourceAsStream("/Roboto-Medium.ttf"));

        // Dessin du rectangle de fond
        PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND,
                true, true);
        contentStream.setNonStrokingColor(Color.WHITE);
        contentStream.addRect(3, 3, 255, 15);
        contentStream.fill();
        // Dessin du Texte du tampon
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.setNonStrokingColor(0f, 0.663f, 0.808f);
        contentStream.newLineAtOffset(5, 5);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        String formatDateTime = now.format(formatter);
        contentStream.showText("Publié sur le site lenord.fr le " + formatDateTime);
        contentStream.endText();
        contentStream.close();
        doc.save(fos);
        fos.close();
        doc.close();

        ContentWriter writer = this.serviceRegistry.getContentService().getWriter(nodeRef, ContentModel.PROP_CONTENT,
                true);
        writer.putContent(newPdf);
    }

}

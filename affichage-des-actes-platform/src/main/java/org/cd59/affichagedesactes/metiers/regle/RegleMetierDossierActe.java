package org.cd59.affichagedesactes.metiers.regle;

import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.docinfos.DocinfosAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.conteneuracte.ConteneurActeTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.stockageacteoriginal.StockageActeOriginalTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.stockageannexe.StockageAnnexeTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.stockagefichier.StockageFichierTypeHelperModele;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegleMetierDossierActe {

    public static String obtenirNumeroActe(DossierinfosAspectHelperModele dossier) {
        return String.format("%s_%s_%s_%s",
                dossier.getTypedossier().substring(0,3).replace("é", "e")
                        .replace("É", "E").toUpperCase(),
                dossier.getOrgasigle().toUpperCase(),
                obtenirAnneeActe(dossier), dossier.getNumeroacte());
    }

    /**
     * Méthode permettant d'extraire l'année du dossier d'acte.
     * @param dossier Le dossier d'acte dont on souhaite extraire l'année.
     * @return L'année du dossier d'acte au format {@link String}.
     */
    private static String obtenirAnneeActe(DossierinfosAspectHelperModele dossier) {
        Calendar calendrier = new GregorianCalendar();
        calendrier.setTime(dossier.getDatedossier());
        return Integer.toString(calendrier.get(Calendar.YEAR));
    }

    public static void deplacerDocumentDuSAS(
            FileFolderService fileFolderService, NodeService nodeService, ConteneurActeTypeHelperModele destination,
            NodeRef nodeRef
    ) {
        try{
            FileInfo fileInfo = fileFolderService.move(nodeRef, destination.getNoeud(), genererNomFichier(nodeService, destination, nodeRef));
            majNoeudStocker(fileFolderService, nodeService, fileInfo.getNodeRef());

        }catch (Exception e) { e.printStackTrace(); }
    }

    private static void majNoeudStocker(FileFolderService fileFolderService, NodeService nodeService, NodeRef nodeRef) throws NoSuchAlgorithmException, IOException {
        // Suppression de l'aspect 'docinfo'.
        DocinfosAspectHelperModele fichierSAS = new DocinfosAspectHelperModele(nodeService, nodeRef);
        String type = null;
        if(fichierSAS.hasAspect()) {
            type = fichierSAS.getTypedocument();
            fichierSAS.supprimeAspect();
        }

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String empreinte = Base64.getEncoder().encodeToString(
                digest.digest(fileFolderService.getReader(nodeRef).getContentInputStream().readAllBytes())
        );

        // Mise à jour du type.
        if(type != null) {
            StockageFichierTypeHelperModele fichier = new StockageFichierTypeHelperModele(nodeService, nodeRef);
            fichier.addType();
            fichier.setTypeFichier("INCONNU");
            fichier.setEmpreinteFichier(empreinte);
        }else if( type.equals("ACTE_ORIGINAL") ) {
            StockageActeOriginalTypeHelperModele fichier = new StockageActeOriginalTypeHelperModele(nodeService, nodeRef);
            fichier.addType();
            fichier.setTypeFichier(type);
            fichier.setEmpreinteFichier(empreinte);
        }else if(type.equals("ANNEXE")) {
            StockageAnnexeTypeHelperModele fichier = new StockageAnnexeTypeHelperModele(nodeService, nodeRef);
            fichier.addType();
            fichier.setTypeFichier(type);
            fichier.setEmpreinteFichier(empreinte);
        }
    }

    private static String genererNomFichier(NodeService nodeService, ConteneurActeTypeHelperModele dossierActe, NodeRef nodeRef) {
        DocinfosAspectHelperModele fichierSAS = new DocinfosAspectHelperModele(nodeService, nodeRef);
        if(!fichierSAS.hasAspect())
            return String.format("%s_%d", dossierActe.obtenirNom(), dossierActe.getContenu().size());

        String type = fichierSAS.getTypedocument();
        if(type == null || (!type.equals("ACTE_ORIGINAL") && !type.equals("ANNEXE")))
            return String.format("%s_%d", dossierActe.obtenirNom(), dossierActe.getContenu().size());

        return String.format("%s_%s", dossierActe.obtenirNom(), type.toUpperCase());
    }
}

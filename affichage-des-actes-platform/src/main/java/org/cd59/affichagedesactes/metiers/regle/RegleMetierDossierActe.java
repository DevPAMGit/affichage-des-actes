package org.cd59.affichagedesactes.metiers.regle;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;

import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.NodeRef;

import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.alfresco.modeles.sources.AlfrescoModeleHelper;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.docinfos.DocinfosAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.type.sas.SasTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.type.sas.SasTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.aspect.informationdossier.InformationDossierAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.acteoriginal.ActeOriginalTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.annexe.AnnexeTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossieracte.DossierActeTypeHelperModele;

import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossieractes.DossierActesTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossieractes.DossierActesTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierannee.DossierAnneeTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierannee.DossierAnneeTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierjour.DossierJourTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossierjour.DossierJourTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossiermois.DossierMoisTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossiermois.DossierMoisTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossiertypologie.DossierTypologieTypeHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossiertypologie.DossierTypologieTypeModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.fichier.FichierTypeHelperModele;
import org.cd59.affichagedesactes.metiers.factory.DossierFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Classe pour respecter les règle métier des actes.
 */
public class RegleMetierDossierActe {

    /**
     * Objet permettant l'exclusion mutuelle sur l'arborescence des dossiers d'actes
     */
    private static final Object ARBORESCENCE_MUTEX = new Object();

    /**
     * Objet permettant l'exclusion mutuelle sur le compteur du dossier SAS.
     */
    private static final Object COMPTEUR_SAS_MUTEX = new Object();

    /**
     * Les gestionnaires des règles métiers pour les erreurs.
     */
    private final RegleMetierErreur regleMetierErreur;

    /***
     * Le nom de dossier contenant tous les actes.
     */
    private static final String NOM_DOSSIER_ACTES = "Actes";

    /**
     * Le logger de la classe.
     */
    private static final Logger logger = LoggerFactory.getLogger(RegleMetierDossierActe.class);

    private final ServiceRegistry serviceRegistry;

    /**
     * Initialise une nouvelle instance de la classe RegleMetierDossierActe.
     * @param serviceRegistry Le registre des services.
     */
    public RegleMetierDossierActe(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        this.regleMetierErreur = new RegleMetierErreur(this.serviceRegistry);
    }

    /**
     * Méthode permettant d'obtenir le dossier de destination de dossier SAS en paramètre.
     * @param dossierActeSAS Le dossier SAS en paramètre dont on souhaite récupérer le dossier de destination.
     */
    public void rangerDossierActe(DossierinfosAspectHelperModele dossierActeSAS) {
        DossierActesTypeHelperModele dossierActes;
        DossierAnneeTypeHelperModele dossierAnnee = null;
        DossierMoisTypeHelperModele dossierMois = null;
        DossierJourTypeHelperModele dossierJour = null;
        DossierTypologieTypeHelperModele dossierTypologie = null;
        DossierActeTypeHelperModele dossierActe = null;

        // On touche à l'arborescence des actes : Utilisation du MUTEX.
        synchronized (ARBORESCENCE_MUTEX) {

            try {
                dossierActes = this.obtenirDossierActes(dossierActeSAS.getAncetre(2));
                dossierAnnee = this.obtenirDossierAnnee(dossierActes, dossierActeSAS);
                dossierMois = this.obtenirDossierMois(dossierAnnee, dossierActeSAS);
                dossierJour = this.obtenirDossierJour(dossierMois, dossierActeSAS);
                dossierTypologie = this.obtenirDossierTypologie(dossierJour, dossierActeSAS);

                String nomDossier = obtenirNumeroActe(
                        new SasTypeHelperModele(this.serviceRegistry, dossierActeSAS.getNoeudParent()), dossierActeSAS
                );

                // Vérification que le dossier d'acte n'existe pas dans le dossier de typologie.
                if(dossierTypologie.searchNoeudDossierParNom(/*ContentModel.TYPE_FOLDER,*/ nomDossier) != null) {
                    // Le dossier existe : il y a donc erreur sur le dossier d'acte SAS.
                    this.regleMetierErreur.ajouterErreurDossierActeSAS(
                            dossierActeSAS.getNoeud(),
                            String.format(
                                    "Le dossier '%s' existe déjà sur le chemin %d/%s/%s/%s",
                                    nomDossier, dossierTypologie.getAnnee(),
                                    entierSurDeuxChiffres(dossierTypologie.getMois()),
                                    entierSurDeuxChiffres(dossierTypologie.getJour()),
                                    dossierTypologie.getTypologieDossier()
                            )
                    );
                    // Fin de l'exécution.
                    return;
                }

                // Création du dossier d'acte.
                dossierActe = this.creerDossierActe(dossierTypologie, dossierActeSAS, nomDossier);
                // Déplacement des fichiers dans le dossier nouvellement créé.
                for(NodeRef childNode : dossierActeSAS.getContenu())
                    this.deplacerFichierActeSAS(childNode, dossierActe);

                // Mise à jour des aspects dossier informations.
                this.ajouterDossierActeArborescence(dossierTypologie.getNoeud());

                // Suppression du nœud acte dans le SAS.
                this.serviceRegistry.getFileFolderService().delete(dossierActeSAS.getNoeud());

            }catch (Exception e){
                // On log l'erreur.
                logger.error(e.getMessage(), e);
                e.printStackTrace();

                // Gestion du dossier d'acte nouvellement créer s'il existe.
                if(dossierActe != null) {
                    for (NodeRef childNode : dossierActe.getContenu())
                        this.retournerFichierActeSAS(childNode, dossierActeSAS);

                    // Suppression du dossier d'acte créer.
                    this.serviceRegistry.getFileFolderService().delete(dossierActe.getNoeud());
                    // Roll back sur la création de l'arborescence si besoin.
                    this.supprimerNoeudSiNecessaire(dossierTypologie.getNoeud());

                }
                // Roll back sur l'arborescence.
                else if( dossierTypologie != null ) this.supprimerNoeudSiNecessaire(dossierTypologie.getNoeud());
                else if( dossierJour != null ) this.supprimerNoeudSiNecessaire(dossierJour.getNoeud());
                else if( dossierMois != null ) this.supprimerNoeudSiNecessaire(dossierMois.getNoeud());
                else if( dossierAnnee != null ) this.supprimerNoeudSiNecessaire(dossierAnnee.getNoeud());

                // Mise à jour de l'erreur sur le dossier d'acte.
                this.regleMetierErreur.ajouterErreurDossierActeSAS(
                        dossierActeSAS.getNoeud(),
                        "Une erreur est survenue lors de la création du dossier d'acte."
                );
            }

        }
    }

    /**
     * Supprime un dossier d'acte dans le dossier SAS.
     * @param dossierActeSAS Le dossier d'acte dans le dossier SAS.
     */
    public void supprimerDossierSAS(DossierinfosAspectHelperModele dossierActeSAS) {
        this.regleMetierErreur.retirerErreurDossierActe(dossierActeSAS.getNoeud());
        this.serviceRegistry.getFileFolderService().delete(dossierActeSAS.getNoeud());
    }

    /**
     * Méthode permettant de mettre à jour l'arborescence d'un dossier d'acte au stockage de celui-ci.
     * @param nodeRef Le nœud que l'on souhaite mettre à jour.
     */
    private void ajouterDossierActeArborescence(NodeRef nodeRef) {
        // Pas de nœud : pas de traitement.
        if(nodeRef == null) return;

        // Récupération du type du nœud.
        QName type = this.serviceRegistry.getNodeService().getType(nodeRef);
        boolean fin = type.isMatch(DossierActesTypeModele.NOM);

        // Vérification que le nœud est du type adéquat.
        if(type.isMatch(DossierAnneeTypeModele.NOM) || type.isMatch(DossierMoisTypeModele.NOM) ||
                type.isMatch(DossierJourTypeModele.NOM) || type.isMatch(DossierTypologieTypeModele.NOM)) {

            // Récupération de nombre d'actes en cours.
            int nbActes = (int) this.serviceRegistry.getNodeService().getProperty(nodeRef, InformationDossierAspectModele.NB_ACTES);
            // Incrémentation de son nombre.
            this.serviceRegistry.getNodeService().setProperty(nodeRef, InformationDossierAspectModele.NB_ACTES, (nbActes+1));

            // Execution sur le dossier parent si l'on n'est pas sur le dossier d'acte.
            if(!fin) this.ajouterDossierActeArborescence( this.serviceRegistry.getNodeService().getPrimaryParent(nodeRef).getParentRef());
        }
    }

    /**
     * Méthode supprimant tous les dossiers vides créés durant la création d'un dossier d'acte.
     * @param nodeRef le nœud que l'on souhaite supprimer si possible.
     */
    private void supprimerNoeudSiNecessaire(NodeRef nodeRef) {
        if(nodeRef == null) return;

        // Récupération du type du nœud.
        QName type = this.serviceRegistry.getNodeService().getType(nodeRef);

        // Si l'on est à la racine du dossier (dossier 'Actes') l'algorithme est terminée.
        if(!type.equals(DossierActesTypeModele.NOM) &&
                // Vérification que le nœud est du type adéquat
                (type.isMatch(DossierAnneeTypeModele.NOM) || type.isMatch(DossierMoisTypeModele.NOM) ||
                        type.isMatch(DossierJourTypeModele.NOM) || type.isMatch(DossierTypologieTypeModele.NOM) )
                // Pour le supprimer il faut que le nœud que nombre d'actes stocker soit à zéro.
                && ((int)this.serviceRegistry.getNodeService().getProperty(nodeRef, InformationDossierAspectModele.NB_ACTES)) == 0
        ) {
            // Récupération du parent.
            NodeRef parent = this.serviceRegistry.getNodeService().getPrimaryParent(nodeRef).getParentRef();

            // Suppression du nœud.
            this.serviceRegistry.getFileFolderService().delete(nodeRef);

            // Tentative de suppression du parent.
            this.supprimerNoeudSiNecessaire(parent);
        }
    }

    /**
     * Méthode permettant de retourner dans le dossier de l'acte dans le SAS un fichier.
     * @param childNode Le nœud du fichier.
     * @param dossierActeSAS Le dossier d'acte dans le SAS.
     */
    private void retournerFichierActeSAS(NodeRef childNode, DossierinfosAspectHelperModele dossierActeSAS) {
        try{
            this.serviceRegistry.getFileFolderService().move(childNode, dossierActeSAS.getNoeud(), (String)this.serviceRegistry.getNodeService().getProperty(childNode, ContentModel.PROP_NAME));
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Déplace les fichiers du SAS vers le dossier de destination.
     * @param childNode Le nœud à déplacer.
     * @param dossierActe Le dossier de destination.
     * @throws FileNotFoundException Si le fichier a déplacé n'est pas trouvé.
     * @throws NoSuchAlgorithmException ...
     * @throws IOException S'il y a une exception Input output.
     */
    private void deplacerFichierActeSAS(NodeRef childNode, DossierActeTypeHelperModele dossierActe)
            throws FileNotFoundException, NoSuchAlgorithmException, IOException {
        FileInfo fileInfo = this.serviceRegistry.getFileFolderService().move(childNode, dossierActe.getNoeud(), this.genererNomFichier(dossierActe, childNode));
        this.majNoeudStocker(fileInfo.getNodeRef());
    }

    /**
     * Méthode permettant de créer un dossier d'acte pour stockage.
     * @param dossierTypologie Le dossier de destination de l'acte.
     * @param dossierActeSAS Le dossier de l'acte dans le SAS.
     * @param nomDossier Le nom de l'acte.
     * @return Une instance référençant le dossier de l'acte pour stockage.
     */
    private DossierActeTypeHelperModele creerDossierActe(
            DossierTypologieTypeHelperModele dossierTypologie, DossierinfosAspectHelperModele dossierActeSAS,
            String nomDossier) {
        // Création du dossier.
        DossierActeTypeHelperModele dossier = new DossierActeTypeHelperModele(
                this.serviceRegistry, dossierTypologie.creerDossier(nomDossier)
        );
        // Modification de ses
        dossier.hasType();
        dossier.majProprietes(DossierFactory.obtenirParametresDossierActe(dossierActeSAS, nomDossier));

        // Retour du résultat.
        return dossier;
    }

    /**
     * Méthode permettant d'obtenir le nœud contenant les actes d'une typologie pour un jour du mois sur l'année du
     * dossier d'acte du SAS.
     * @param dossierJour Nœud contenant tous les actes d'une journée dans le mois de l'année de l'acte.
     * @param dossierActeSAS Le dossier de l'acte dans le SAS.
     * @return Le dossier contenant les actes d'une typologie pour un jour du mois sur l'année du dossier d'acte du SAS.
     */
    private DossierTypologieTypeHelperModele obtenirDossierTypologie(
            DossierJourTypeHelperModele dossierJour, DossierinfosAspectHelperModele dossierActeSAS
    ) throws Exception {
        String type = this.formatTypeDossier(dossierActeSAS.getTypedossier());

        // Recherche du dossier jour dans le dossier du mois.
        NodeRef noeudTypologie = dossierJour.searchNoeudDossierParNom(type);
        if(noeudTypologie == null) {
            noeudTypologie = dossierJour.creerDossierAvecDonnees(
                    type, DossierFactory.obtenirParametresDossierType(
                            dossierJour.getJour(), dossierJour.getMois(), dossierJour.getAnnee(), type
                    )
            );
        }

        // Ajout du type si le dossier vient d'être créé.
        DossierTypologieTypeHelperModele dossier = new DossierTypologieTypeHelperModele(this.serviceRegistry,
                noeudTypologie);
        if(!dossier.hasType()) dossier.addType();

        // retour du resultat.
        return dossier;
    }

    /**
     * Méthode permettant de formatter le type de dossier.
     * @param typeDossier Le type du dossier.
     * @return Le type du dossier formatté.
     */
    private String formatTypeDossier(String typeDossier) throws Exception {
        if(typeDossier.equals("Délibération") || typeDossier.equals("DELIBERATION")) return "DELIBERATIONS";
        else if(typeDossier.equals("ARRETE") || typeDossier.equals("Arrêté")) return "ARRETES";
        throw new Exception("Le type du dossier n'est pas valide.");
    }

    /**
     * Méthode permettant d'obtenir le nœud contant les actes d'un jour du mois sur l'année du dossier d'acte du SAS.
     * @param dossierMois Nœud contenant tous les actes de du mois de l'année de l'acte.
     * @param dossierActeSAS Le dossier de l'acte dans le SAS.
     * @return Le dossier contenant tous les actes d'un jour du mois sur l'année du dossier d'acte du SAS.
     */
    private DossierJourTypeHelperModele obtenirDossierJour(
            DossierMoisTypeHelperModele dossierMois, DossierinfosAspectHelperModele dossierActeSAS
    ) {
        // Récupération d'un calendrier pour parser la date.
        Calendar calendrier = new GregorianCalendar();
        calendrier.setTime(dossierActeSAS.getDatedossier());

        // Récupération de l'année du dossier.
        int jour = calendrier.get(Calendar.DAY_OF_MONTH);
        String jourChaine = entierSurDeuxChiffres(jour);

        // Recherche du dossier jour dans le dossier du mois.
        NodeRef noeudJour = dossierMois.searchNoeudDossierParNom(/*ContentModel.TYPE_FOLDER,*/ jourChaine);
        if(noeudJour == null) {
            noeudJour = dossierMois.creerDossierAvecDonnees(
                    jourChaine, DossierFactory.obtenirParametresDossierJour(
                            jour, dossierMois.getMois(), dossierMois.getAnnee()
                    )
            );
        }

        // Mise en place du type si le dossier vient d'être créé.
        DossierJourTypeHelperModele dossier = new DossierJourTypeHelperModele(this.serviceRegistry, noeudJour);
        if(!dossier.hasType()) dossier.addType();

        // Retour du résultat.
        return dossier;
    }

    /**
     * Méthode permettant d'obtenir le nœud contant les actes d'un mois sur l'année du dossier d'acte du SAS.
     * @param dossierAnnee Nœud contenant tous les actes de l'année.
     * @param dossierActeSAS Le dossier de l'acte dans le SAS.
     * @return Le dossier contenant tous les actes d'un mois sur l'année du dossier d'acte du SAS.
     */
    private DossierMoisTypeHelperModele obtenirDossierMois(
            DossierAnneeTypeHelperModele dossierAnnee, DossierinfosAspectHelperModele dossierActeSAS)
    {
        // Récupération d'un calendrier pour parser la date.
        Calendar calendrier = new GregorianCalendar();
        calendrier.setTime(dossierActeSAS.getDatedossier());

        // Récupération de l'année du dossier.
        int mois = calendrier.get(Calendar.MONTH);
        String moisChaine = entierSurDeuxChiffres(mois);

        // Recherche du dossier mois dans le dossier de l'année.
        NodeRef noeudMois = dossierAnnee.searchNoeudDossierParNom(/*ContentModel.TYPE_FOLDER,*/ moisChaine);
        if(noeudMois == null) {
            noeudMois = dossierAnnee.creerDossierAvecDonnees(
                    moisChaine, DossierFactory.obtenirParametresDossierMois(mois, dossierAnnee.getAnnee())
            );
        }

        // Mise en place du type si le dossier vient d'être créé.
        DossierMoisTypeHelperModele dossier = new DossierMoisTypeHelperModele(this.serviceRegistry, noeudMois);
        if(!dossier.hasType()) dossier.addType();

        // Retour du résultat.
        return dossier;
    }

    /**
     * Méthode permettant d'obtenir le nœud contant les actes de l'année du dossier d'acte du SAS.
     * @param dossierActes Nœud contenant tous les actes.
     * @param dossierActeSAS Le dossier de l'acte dans le SAS.
     * @return Le dossier contenant tous les actes de l'année du dossier d'acte du SAS.
     */
    private DossierAnneeTypeHelperModele obtenirDossierAnnee(
            DossierActesTypeHelperModele dossierActes, DossierinfosAspectHelperModele dossierActeSAS
    ) {
        // Récupération d'un calendrier pour parser la date.
        Calendar calendrier = new GregorianCalendar();
        calendrier.setTime(dossierActeSAS.getDatedossier());

        // Récupération de l'année du dossier.
        int annee = calendrier.get(Calendar.YEAR);
        String anneeChaine = Integer.toString(annee);

        // Recherche du dossier année dans le dossier des actes.
        NodeRef noeudAnnee = dossierActes.searchNoeudDossierParNom(anneeChaine);
        if(noeudAnnee == null) {
            noeudAnnee = dossierActes.creerDossierAvecDonnees(
                    anneeChaine, DossierFactory.obtenirParametresDossierAnnees(annee)
            );
        }

        // Mise en place du type si le dossier vient d'être créé.
        DossierAnneeTypeHelperModele dossier = new DossierAnneeTypeHelperModele(this.serviceRegistry, noeudAnnee);
        if(!dossier.hasType()) dossier.addType();

        // Retour du résultat.
        return dossier;
    }

    /**
     * Permet de récupérer le nœud contenant tous les actes.
     * @param nodeRef Le nœud contenant tous les actes.
     * @return Le dossier d'actes.
     */
    private DossierActesTypeHelperModele obtenirDossierActes(NodeRef nodeRef) {
        AlfrescoModeleHelper racine = new AlfrescoModeleHelper(this.serviceRegistry, nodeRef);

        DossierActesTypeHelperModele dossierActes = new DossierActesTypeHelperModele(this.serviceRegistry, racine.creerDossier(NOM_DOSSIER_ACTES));
        if(!dossierActes.hasType()) {
            dossierActes.addType();
            dossierActes.majProprietes(DossierFactory.obtenirParametresDossierActes());
        }

        return dossierActes;
    }

    /**
     * Méthode permettant d'obtenir le numéro d'acte du dossier.
     * @param dossierSAS Le dossier SAS.
     * @param dossierActeSAS Le dossier de l'acte du SAS.
     * @return Le numéro d'acte du dossier d'acte du SAS.
     */
    public static String obtenirNumeroActe(
            SasTypeHelperModele dossierSAS, DossierinfosAspectHelperModele dossierActeSAS
    ) {
        String numeroActe = dossierActeSAS.getNumeroacte();
        if(numeroActe == null || numeroActe.isEmpty() || numeroActe.isBlank())
            synchronized (COMPTEUR_SAS_MUTEX) {
                Serializable nActe = dossierSAS.getPropriete(SasTypeModele.COMPTEUR);
                if(nActe == null) {
                    dossierSAS.setCompteur(1);
                    nActe = dossierSAS.getCompteur();
                }

                dossierSAS.setCompteur( ((int)nActe) +1 );
                numeroActe =  entierSurTroisChiffres( (int)nActe );
            }

        return String.format("%s_%s_%s_%s",
                dossierActeSAS.getTypedossier().substring(0,3).toUpperCase(),
                dossierActeSAS.getOrgasigle().toUpperCase(), obtenirAnneeActe(dossierActeSAS), numeroActe
        );
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

    /**
     * Met à jour le nœud stocké.
     * @param nodeRef Le nœud
     * @throws NoSuchAlgorithmException ...
     * @throws IOException S'il y a une exception Input/Output
     */
    private void majNoeudStocker(NodeRef nodeRef) throws NoSuchAlgorithmException, IOException {
        // Suppression de l'aspect 'docinfo'.
        DocinfosAspectHelperModele fichierSAS = new DocinfosAspectHelperModele(this.serviceRegistry, nodeRef);
        String type = null;

        if(fichierSAS.hasAspect()) {
            type = fichierSAS.getTypedocument();
            fichierSAS.supprimeAspect();
        }

        // Génération de l'emprunte.
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String empreinte = Base64.getEncoder().encodeToString(
                digest.digest(this.serviceRegistry.getFileFolderService().getReader(nodeRef).getContentInputStream().readAllBytes())
        );

        // Mise à jour du type.
        if(type == null) {

            FichierTypeHelperModele fichier = new FichierTypeHelperModele(this.serviceRegistry, nodeRef);
            fichier.addType();
            fichier.setTypologieFichier("INCONNU");
            fichier.setEmpreinte(empreinte);

        }else if( type.equals("ACTE_ORIGINAL") ) {

            ActeOriginalTypeHelperModele fichier = new ActeOriginalTypeHelperModele(this.serviceRegistry, nodeRef);
            fichier.addType();
            fichier.setTypologieFichier(type);
            fichier.setEmpreinte(empreinte);

        }else if(type.equals("ANNEXE")) {

            AnnexeTypeHelperModele fichier = new AnnexeTypeHelperModele(this.serviceRegistry, nodeRef);
            fichier.addType();
            fichier.setTypologieFichier(type);
            fichier.setEmpreinte(empreinte);

        }
    }

    /**
     * Génère le nom de fichier adéquat pour un fichier d'acte.
     * @param dossierActe Le dossier d'acte contenant le fichier.
     * @param nodeRef Le nœud dont on modifie le nom.
     * @return Le nom du dossier généré.
     */
    private String genererNomFichier(DossierActeTypeHelperModele dossierActe, NodeRef nodeRef) {
        DocinfosAspectHelperModele fichierSAS = new DocinfosAspectHelperModele(this.serviceRegistry, nodeRef);
        if(!fichierSAS.hasAspect())
            return String.format("%s_%s",
                    dossierActe.obtenirNom(), entierSurDeuxChiffres(dossierActe.getContenu().size())
            );

        String type = fichierSAS.getTypedocument();
        if(type == null || (!type.equals("ACTE_ORIGINAL") && !type.equals("ANNEXE")))
            return String.format("%s_%s",
                    dossierActe.obtenirNom(), entierSurDeuxChiffres(dossierActe.getContenu().size())
            );

        return String.format("%s_%s", dossierActe.obtenirNom(), type.toUpperCase());
    }

    /**
     * Méthode permettant d'obtenir un entier sur 2 chiffres
     * @param entier Un entier
     * @return L'entier au format {@link String}.
     */
    private String entierSurDeuxChiffres(int entier) {
        if(entier < 10) return String.format("0%d", entier);
        return String.format("%d", entier);

    }

    /**
     * Méthode permettant d'obtenir un entier sur 3 chiffres
     * @param entier Un entier
     * @return L'entier au format {@link String}.
     */
    private static String entierSurTroisChiffres(int entier) {
        if(entier < 10) return String.format("00%d", entier);
        else if(entier < 100) return String.format("0%d", entier);
        return Integer.toString(entier);
    }
}

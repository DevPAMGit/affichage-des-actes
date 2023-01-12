package org.cd59.affichagedesactes.action.custom.stockage;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.DuplicateChildNodeNameException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.model.ContentModel;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.action.custom.source.v1.action.ModeleAction;
import org.cd59.affichagedesactes.modele.alfresco.type.*;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.ModeleDossierEtatEnvoi;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.stockage.ModeleDossierEtatStockage;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.stockage.ModeleDossierStockage;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.stockage.multiple.IModeleDossierStockageMultiple;
import org.cd59.affichagedesactes.modele.donnee.aspect.dossier.stockage.multiple.ModeleDossierStockageMultiple;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Classe d'action permettant le stockage d'un acte.
 */
public class StockerDossierActeAction extends ModeleAction {

    /**
     * Le logger de la classe.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StockerDossierActeAction.class);

    /**
     * Le modèle de données de l'action.
     */
    private ModeleDossierStockage modele;

    private final int position;

    /**
     * Initialise une nouvelle instance de la classe {@link StockerDossierActeAction}.
     // @param serviceRegistry Le registre de services d'Alfresco.
     * @param nodeRef Le nœud du dossier d'acte.
     * @throws ModeleException Si une erreur de modèle survient.
     * @throws PreRequisException Si le nœud en paramètre est null.
     */
    public StockerDossierActeAction(ServiceRegistry serviceRegistry, NodeRef nodeRef, int position) throws Exception {
        super(serviceRegistry);
        this.modele = null;

        LOGGER.info("Stockage d'un acte");
        LOGGER.info("1. Initialisation du Modèle");
        this.position = position;
        this.modele = new ModeleDossierStockage(this, nodeRef);

        LOGGER.info("2. Recherche du fichier d'acte dans le dossier.");
        this.modele.initFichierActe(this.requeterNoeuds(nodeRef,
                String.format(StockerDossierActeRequete.RECHERCHE_ACTE_ORIGINAL, nodeRef.getId())));

        LOGGER.info("3. Recherche des fichiers annexes dans le dossier.");
        this.modele.setFichierAnnexes(this.requeterNoeuds(nodeRef,
                String.format(StockerDossierActeRequete.RECHERCHE_AUTRES_DOCUMENTS, nodeRef.getId())));
    }

    @Override
    public void executer() throws Exception {
        // Synchronisation sur le mutex de l'arborescence.
        synchronized (StockerDossierActeMutex.MUTEX_ARBORESCENCE) {
            // 1. Récupération du chemin de dépôt de l'acte.
            NodeRef destination = this.creerArborescence();

            // Renommage des multiples dossiers.
            this.deplacerDossierActe(destination);
        }

        this.modele.setEtatStockage(ModeleDossierEtatStockage.STOCKE);
        this.modele.setEtatEnvoi(ModeleDossierEtatEnvoi.PRET_A_ETRE_ENVOYE);
    }

    /**
     * Déplace le dossier modèle.
     * @param nodeRef Le nœud de destination du nœud modèle.
     * @throws PreRequisException A remplir.....
     * @throws ModeleException Si la propriété du modèle à modifier est null.
     * @throws FileNotFoundException Si le nœud à déplacer n'est pas trouvé.
     */
    private void deplacerDossierActe(NodeRef nodeRef) throws PreRequisException, ModeleException, FileNotFoundException,
            NoSuchMethodException {
        // Récupération de la liste des dossiers du même identifiant.
        List<NodeRef> nodeRefList = this.requeterNoeuds(nodeRef,
                String.format(StockerDossierActeRequete.RECHERCHE_DOSSIER_ACTE,
                        this.modele.getIdentifiant(), nodeRef.getId())
        );

        // Initialisation du compteur des fichiers.
        int numero = nodeRefList.size() - 1;
        int nouveauNumero = nodeRefList.size() == 0 ? 0 : nodeRefList.size() +1;

        // Renommage de tous les nœuds référencés en nœud multiple.
        for(NodeRef nodeRef1 : nodeRefList) {
            // Renommage des dossiers.
            numero += 1;
            this.renommerDossierActe(new ModeleDossierStockageMultiple(this, nodeRef1), numero);
        }

        // Déplacement du dossier modèle.
        this.deplacer(nodeRef, this.modele.getNoeud());
        // Renommage du dossier modèle.
        this.renommerDossierActe(this.modele,  nouveauNumero);
    }

    /**
     * Renomme un dossier d'acte.
     * @param dossier Le modèle du dossier à renommer.
     * @param numero Le numéro du dossier dans sa multiplicité.
     * @throws ModeleException Si la propriété du modèle à modifier est null.
     */
    private void renommerDossierActe(IModeleDossierStockageMultiple dossier, int numero)
            throws ModeleException, PreRequisException, NoSuchMethodException {
        // Vérification que le dossier n'a pas déjà (été) géré.
        if(dossier.getReferenceMultiple()) return;

        // 1. Indication que la multiple référence passe à vrai.
        if(numero > 0) dossier.switchReferenceMultiple();
        // 2. Formatage du nom.
        dossier.setNom(numero);
    }

    /**
     * Crée l'arborescence pour le stockage du dossier d'acte.
     * @return Le nœud de destination du dossier d'acte.
     * @throws PreRequisException Si le nœud de destination est null.
     */
    private NodeRef creerArborescence() throws PreRequisException, NoSuchMethodException {
        // Création / Récupération du dossier destination de l'acte.
        return this.obtenirDossierTypologie(
            // Récupération du dossier de la journée.
            this.obtenirDossierJournee(
                // Récupération du dossier du mois.
                this.obtenirDossierMensuel(
                    // Récupération du dossier d'année.
                    this.obtenirDossierAnnuel(
                        // Récupération du dossier d'actes.
                        this.obtenirDossierActes()
                    )
                )
            )
        );
    }

    /**
     * Méthode permettant de créer (ou récupérer) le dossier de typologie d'un dossier d'acte.
     * @param nodeRef Le nœud parent du dossier de typologie.
     * @return Le nœud de typologie du dossier.
     * @throws PreRequisException Si le nœud parent est null.
     */
    private NodeRef obtenirDossierTypologie(NodeRef nodeRef) throws PreRequisException, NoSuchMethodException {
        // Vérification que le type n'est pas déjà présent dans le dossier parent.
        List<NodeRef> recherche = this.requeterNoeuds(nodeRef,
                String.format(StockerDossierActeRequete.RECHERCHE_DOSSIER_TYPOLOGIE,
                        this.modele.typologie.typeMajuscule.valeur, nodeRef.getId())
        );

        // Récupération du nœud si la recherche est effective.
        if(recherche.size() > 0) return recherche.get(0);

        // Création du dossier.
        return this.creerDossierType(nodeRef, DossierTypologieTypeModele.NOM,
                this.modele.typologie.typeMajuscule.valeur,
                StockerDossierActeDonneesFactory.obtenirMetadonneesTypologie(this.modele)
        );
    }

    /**
     * Permet de récupérer / créer le dossier du jour du dossier.
     * @param nodeRef Le nœud parent du dossier du jour.
     * @throws PreRequisException Si le nœud est null.
     */
    private NodeRef obtenirDossierJournee(NodeRef nodeRef) throws PreRequisException, NoSuchMethodException {
        LOGGER.info("9. Création / Récupération du journalier de stockage des actes.");

        // Recherche du dossier s'il existe déjà.
        List<NodeRef> recherche = this.requeterNoeuds(nodeRef,
                String.format(StockerDossierActeRequete.RECHERCHE_DOSSIER_JOURNALIER,
                        this.modele.date.jourChaine, nodeRef.getId()
                )
        );

        // Retour du resultat si la recherche n'est pas null.
        if(recherche.size() > 0) return recherche.get(0);

        // Création du dossier, car il n'existe pas.
        return this.creerDossierType(nodeRef, DossierJourTypeModele.NOM, this.modele.date.jourChaine,
                StockerDossierActeDonneesFactory.obtenirMetadonneesJour(this.modele.date)
        );
    }

    /**
     * Méthode permettant de créer / récupérer le dossier de stockage des actes pour un mois.
     * @param nodeRef Le nœud de destination du dossier mensuel.
     * @return Le nœud de stockage des actes pour une année.
     * @throws PreRequisException Si le noeud racine est null.
     */
    private NodeRef obtenirDossierMensuel(NodeRef nodeRef) throws PreRequisException, NoSuchMethodException {
        LOGGER.info("8. Création / Récupération du dossier mensuel de stockage des actes.");

        // Recherche du nœud correspondant au mois.
        List<NodeRef> recherche = this.requeterNoeuds(nodeRef,
                String.format(
                        StockerDossierActeRequete.RECHERCHE_DOSSIER_MENSUEL,
                        this.modele.date.moisChaine, nodeRef.getId()
                )
        );
        // Récupération du nœud mensuel.
        if(recherche.size() > 0) return recherche.get(0);

        // Création du nœud du mois.
        return this.creerDossierType(
                nodeRef,  DossierMoisTypeModele.NOM, this.modele.date.moisChaine,
                StockerDossierActeDonneesFactory.obtenirMetadonneesMois(this.modele.date)
        );
    }

    /**
     * Méthode permettant de créer / récupérer le dossier de stockage des actes pour une année.
     * @param nodeRef Le nœud de destination du dossier annuel.
     * @return Le nœud de stockage des actes pour une année.
     * @throws PreRequisException Si le noeud racine est null.
     */
    private NodeRef obtenirDossierAnnuel(NodeRef nodeRef) throws PreRequisException, NoSuchMethodException {
        LOGGER.info("7. Création / Récupération du dossier annuel de stockage des actes.");

        // Recherche du nœud des actes.
        List<NodeRef> recherche = this.requeterNoeuds(nodeRef,
                String.format(StockerDossierActeRequete.RECHERCHE_DOSSIER_ANNUEL,
                        this.modele.date.annee, nodeRef.getId())
        );
        // Récupération du nœud d'acte.
        if(recherche.size() > 0) return recherche.get(0);

        // Création du nœud d'acte.
        return this.creerDossierType(
                nodeRef, DossierAnneeTypeModele.NOM, this.modele.date.anneeChaine,
                StockerDossierActeDonneesFactory.obtenirMetadonneesAnnee(this.modele.date)
        );
    }

    /**
     * Méthode permettant de créer / récupérer le dossier de stockage des actes.
     * @return Le nœud de stockage des actes.
     * @throws PreRequisException Si le noeud racine est null.
     */
    private NodeRef obtenirDossierActes() throws PreRequisException, NoSuchMethodException {
        LOGGER.info("6. Création / Récupération du dossier de stockage des actes.");

        LOGGER.error(String.format("CREATION - OBTENTION DOSSIER ACTE %d", this.position));
/* 
        // Recherche du noeuds des actes.
        NodeRef noeudRacine = this.getNoeudParent(this.modele.sas.getNoeud());
        List<NodeRef> recherche = this.requeterNoeuds(noeudRacine,
                String.format(StockerDossierActeRequete.RECHERCHE_DOSSIER_ACTES, noeudRacine.getId())
        );

        // Récupération du nœud d'acte.
        if(recherche.size() > 0) {
            LOGGER.error(String.format("OBTENTION DOSSIER ACTE %d TROUVE", this.position));
            return recherche.get(0);
        }else {
            LOGGER.error(String.format("CREATION DOSSIER ACTE %d TROUVE", this.position));
        } */

		NodeRef noeudRacine = this.getNoeudParent(this.modele.sas.getNoeud());
        NodeRef dossier = this.serviceRegistry.getNodeService().getChildByName(noeudRacine, ContentModel.ASSOC_CONTAINS, "Actes");
		LOGGER.info("dossier: ({})", dossier);
		
		if(dossier != null) return dossier;

        // Création du nœud d'acte.
        return this.creerDossierType(
                noeudRacine,  DossierActesTypeModele.NOM, "Actes",
                StockerDossierActeDonneesFactory.obtenirMetadonneesDossierActe()
        );
        // Création du nœud d'acte.		
		
/*         try {

            return this.creerDossierType(
                    noeudRacine,  DossierActesTypeModele.NOM, "Actes",
                    StockerDossierActeDonneesFactory.obtenirMetadonneesDossierActe()
            );

        }catch (DuplicateChildNodeNameException e) {
            LOGGER.error(String.format("DOUBLON ERREUR ACTE %d TROUVE", this.position));
            this.getNoeudParent(this.modele.sas.getNoeud());
            return this.requeterNoeuds(
                    noeudRacine, String.format(StockerDossierActeRequete.RECHERCHE_DOSSIER_ACTES, noeudRacine.getId())
            ).get(0); 
        } */
    }
}

package org.cd59.affichagedesactes.v2.action;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.stockageactes59.type.dossieractes.DossierActesTypeHelperModele;
import org.cd59.affichagedesactes.v2.action.source.ActionMetier;

import java.util.ArrayList;

/**
 * Action métier permettant d'envoyer un dossier au site d'affichage des actes.
 */
public class EnvoyerDossierActe extends ActionMetier {

    /**
     * Indique si le nœud est typé.
     */
    private boolean estType;

    /**
     * Le nœud représentant le dossier d'acte à envoyer.
     */
    private NodeRef dossierActe;

    /**
     * Le nœud représentant un acte original.
     */
    private NodeRef acteOriginal;

    /**
     * La liste de noeuds annexe.
     */
    public ArrayList<NodeRef> annexes;

    /**
     * Initialise une nouvelle instance de la classe {@link ActionMetier}.
     * @param serviceRegistry Le registre de service Alfresco.
     */
    public EnvoyerDossierActe(ServiceRegistry serviceRegistry) {
        super(serviceRegistry);
    }

    @Override
    public void executer() {

    }

    private boolean verifierPrecondition() {
        // 1. Vérification Le nœud est un dossier de type stockageactes59:dossierActe.
        DossierActesTypeHelperModele dossier = new DossierActesTypeHelperModele(this.registryService, this.dossierActe);

        // Erreur le nœud ne contient pas le type : pas de traitement.
        if(!dossier.hasType()) {
            this.estType = false;
            return false;
        }
        // 2. Vérification que le nœud contient un enfant de type Acte original.
        SearchService searchService = this.registryService.getSearchService();
        ResultSet result = searchService.query(this.dossierActe.getStoreRef(),
                SearchService.LANGUAGE_CMIS_STRICT, "select * from stockageactes59:acteOriginal");
        if(result == null ||result.length() == 0) return false;

        this.acteOriginal = result.getNodeRef(0);

        return true;
    }
}

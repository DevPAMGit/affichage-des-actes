package org.cd59.affichagedesactes.metiers.action.stockage;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectHelperModele;
import org.cd59.affichagedesactes.metiers.action.source.IActionMetier;
import org.cd59.affichagedesactes.metiers.regle.RegleMetierDossierActe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SupprimerActeSASActionMetier implements IActionMetier {

    /**
     * Le logger de la classe.
     */
    private static final Logger logger = LoggerFactory.getLogger(SupprimerActeSASActionMetier.class);

    /**
     * Le registre des services
     */
    private final ServiceRegistry serviceRegistry;

    /**
     * Initialise une nouvelle instance de la classe {@link SupprimerActeSASActionMetier}.
     * @param serviceRegistry Le registre de service d'Alfresco.
     */
    public SupprimerActeSASActionMetier(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void executer(NodeRef nodeRef) {
        new RegleMetierDossierActe(this.serviceRegistry).supprimerDossierSAS(
                new DossierinfosAspectHelperModele(this.serviceRegistry, nodeRef)
        );
    }

}

package org.cd59.affichagedesactes.v2.evenement;

import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectModele;

import java.io.Serializable;
import java.util.Map;

/**
 * Gestionnaire d'évènement pour le rangement d'acte.
 */
public class RangementDossierActe implements NodeServicePolicies.OnUpdatePropertiesPolicy {

    /**
     * Le registre de service.
     */
    private ServiceRegistry serviceRegistry;

    /**
     * Le gestionnaire d'évènements.
     */
    private PolicyComponent policyComponent;

    /**
     * Le comportement à ... lorsqu'une propriété est mise à jour.
     */
    private Behaviour quandProprietesMisesAJour;

    /**
     * Initialise le gestionnaire d'évènements.
     */
    public void initialiser() {

        // Création du comportement.
        this.quandProprietesMisesAJour = new JavaBehaviour(
                this, "quandProprietesMisesAJour", Behaviour.NotificationFrequency.TRANSACTION_COMMIT
        );

        // Liaison du comportement à la politique d'un nœud.
        this.policyComponent.bindClassBehaviour(
                NodeServicePolicies.OnUpdatePropertiesPolicy.QNAME, DossierinfosAspectModele.DOSSIERCOMPLET,
                this.quandProprietesMisesAJour
        );
    }

    @Override
    public void onUpdateProperties(NodeRef nodeRef, Map<QName, Serializable> map, Map<QName, Serializable> map1) {

    }
}

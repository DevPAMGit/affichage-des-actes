package org.cd59.affichagedesactes.v2.application;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.affichage59.aspect.affichage.AffichageAspectModele;
import org.cd59.affichagedesactes.v2.action.source.ActionMetier;
import org.cd59.affichagedesactes.v2.application.source.ApplicationSource;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * Classe gérant l'aspect 'affichage59:affichage'.
 */
public class ApplicationEnvoiSAS extends ApplicationSource {
    /**
     * Initialise une nouvelle instance de la classe {@link ApplicationSource}.
     * @param action L'action à laquelle est reliée l'application.
     */
    public ApplicationEnvoiSAS(ActionMetier action) {
        super(action);
    }

    /**
     * Initialise un nœud avec un aspect 'affichage59:affichage'.
     * @param nodeRef Le nœud que l'on souhaite initier.
     */
    public void initierAspect(NodeRef nodeRef) {
        if(nodeRef == null) return;

        // Changement des propriétés s'il existe.
        if(!this.aAspect(nodeRef))
            this.action.registryService.getNodeService().setProperties(nodeRef,
                    this.obtenirProprietesAspect("Prêt à être envoyer", null, null)
            );

        // Ajout de l'aspect si l'aspect n'existe pas.
        else
            this.action.registryService.getNodeService().addAspect(nodeRef, AffichageAspectModele.NOM,
                    this.obtenirProprietesAspect("Prêt à être envoyer", null, null)
            );
    }

    /**
     * Permet de modifier l'aspect 'affichage59:affichage' présent sur un nœud.
     * @param nodeRef Le nœud dont il faut modifier les propriétés.
     * @param etat La valeur de la propriété 'affichage59:etat'.
     * @param dateAffichage La valeur de la propriété 'affichage59:dateAffichage'.
     */
    public void modifierAspect(NodeRef nodeRef, String etat, Date dateAffichage) {
        if(nodeRef == null) return;

        // Changement des propriétés s'il existe.
        if(!this.aAspect(nodeRef))
            this.action.registryService.getNodeService().setProperties(nodeRef,
                    this.obtenirProprietesAspect(etat, new Date(), dateAffichage)
            );

            // Ajout de l'aspect si l'aspect n'existe pas.
        else
            this.action.registryService.getNodeService().addAspect(nodeRef, AffichageAspectModele.NOM,
                    this.obtenirProprietesAspect(etat, new Date(), null)
            );
    }

    /**
     * Récupère le {@link java.util.Map} de propriétés pour l'aspect 'affichage59:affichage'.
     * @param etat La valeur de la propriété 'affichage59:etat'.
     * @param tentativeEnvoi La valeur de la propriété 'affichage59:tentativeEnvoi'.
     * @param dateAffichage La valeur de la propriété 'affichage59:dateAffichage'.
     * @return Le {@link java.util.Map} de propriétés pour l'aspect 'affichage59:affichage'.
     */
    private HashMap<QName, Serializable> obtenirProprietesAspect(String etat, Date tentativeEnvoi, Date dateAffichage) {
        HashMap<QName, Serializable> resultat = new HashMap<>();

        resultat.put(AffichageAspectModele.ETAT, etat);
        if(dateAffichage != null) resultat.put(AffichageAspectModele.DATE_AFFICHAGE, dateAffichage);
        if(tentativeEnvoi != null) resultat.put(AffichageAspectModele.TENTATIVE_ENVOI, tentativeEnvoi);

        return resultat;
    }

    private boolean aAspect(NodeRef nodeRef) {
        return this.action.registryService.getNodeService().hasAspect(nodeRef, AffichageAspectModele.NOM);
    }
}

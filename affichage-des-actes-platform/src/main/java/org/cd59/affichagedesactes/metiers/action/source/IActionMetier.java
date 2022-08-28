package org.cd59.affichagedesactes.metiers.action.source;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Interface contractuelle pour toutes les actions métier à implémenter.
 * */
public interface IActionMetier {

    /**
     * Execute l'action du métier sur un nœud.
     * Le nœud sur lequel executer l'action métier.
    */
    void executer(NodeRef nodeRef);
}

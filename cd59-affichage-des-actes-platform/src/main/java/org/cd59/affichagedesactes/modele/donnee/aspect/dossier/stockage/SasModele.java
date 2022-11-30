package org.cd59.affichagedesactes.modele.donnee.aspect.dossier.stockage;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.action.custom.source.v1.action.IModeleNoeudAction;
import org.cd59.affichagedesactes.action.custom.stockage.StockerDossierActeMutex;
import org.cd59.affichagedesactes.modele.alfresco.type.SasTypeModele;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;
import org.cd59.affichagedesactes.modele.donnee.source.ModeleNoeud;
import org.cd59.affichagedesactes.utilitaire.UtilitaireChaineDeCaracteres;

public class SasModele extends ModeleNoeud {
    public SasModele(/*ServiceRegistry serviceRegistry*/ IModeleNoeudAction modeleNoeudAction, NodeRef nodeRef) throws ModeleException, PreRequisException {
        super(/*serviceRegistry*/ modeleNoeudAction, nodeRef);

        if(!this.avoirType(SasTypeModele.NOM))
            throw new ModeleException("Le dossier n'est pas de type valide ('actes59:sas').");
    }

    /**
     * Génère un numéro d'acte et met à jour le modèle de données (le noeud).
     * @return Un numéro d'acte unique.
     * @throws ModeleException Si la propriété 'acte59:compteur'
     */
    public String genererNumeroActe() throws ModeleException, PreRequisException, NoSuchMethodException {
        // Synchronisation sur un mutex sur le dossier de SAS.
        synchronized (StockerDossierActeMutex.MUTEX_SAS) {
            // Récupération du compteur.
            int numero = this.getProprieteEntier(SasTypeModele.COMPTEUR);
            if (numero == 10000) numero = 1;

            // Transformation du numéro en chaîne de caractères.
            String resultat = UtilitaireChaineDeCaracteres.entierSurNChiffres(numero, 4);
            // Modification de la propriété.
            this.setPropriete(SasTypeModele.COMPTEUR, (numero+1));

            // Retour du résultat.
            return String.format("M%s", resultat);
        }
    }
}

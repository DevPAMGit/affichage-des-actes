package org.cd59.affichagedesactes.metiers.regle;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.erreurbase.ErreurBaseAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.erreurbase.ErreurBaseAspectModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.erreurdossier.ErreurDossierAspectHelperModele;
import org.cd59.affichagedesactes.alfresco.modeles.typescontenus.erreur59.aspect.erreurdossier.ErreurDossierAspectModele;
import org.cd59.affichagedesactes.metiers.factory.ErreurFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe proposant des méthodes qui permettent de manipuler les aspects d'erreurs du projet.
 */
public class RegleMetierErreur {

    /**
     * Le registre des services
     * */
    private final ServiceRegistry serviceRegistry;

    /**
     * Le logger de la classe.
     */
    private static final Logger logger = LoggerFactory.getLogger(RegleMetierErreur.class);

    /**
     * La variable permettant la mutuelle exclusion sur le dossier de SAS.
     */
    public static final Object SAS_MUTEX = new Object();

    /**
     * Initialise une nouvelle instance de la classe {@link RegleMetierErreur}.
     * @param serviceRegistry Le registre des services.
     * */
    public RegleMetierErreur(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    /**
     * Méthode permettant d'ajouter un aspect d'erreur à un fichier dans un dossier d'acte dans le SAS.
     * @param nodeRef Nœud référençant le fichier dans le dossier d'acte dans le SAS.
     * @param message Le message d'erreur.
     */
    public void ajouterErreurFichierActeSAS(NodeRef nodeRef, String message) {
        this.ajouterErreur(nodeRef, message);
        this.ajouterErreurDossierActeSAS(this.obtenirNoeudParent(nodeRef));
    }

    /**
     * Méthode permettant de retirer un aspect d'erreur à un fichier dans un dossier d'acte dans le SAS.
     * @param nodeRef Nœud référençant le fichier dans le dossier d'acte dans le SAS.
     */
    public void retirerErreurFichierActeSAS(NodeRef nodeRef) {
        if( this.retirerErreur(nodeRef) )
            this.retirerErreurDossierActe(this.obtenirNoeudParent(nodeRef));
    }

    /**
     * Méthode permettant d'ajouter un aspect d'erreur à au dossier d'acte dans le SAS.
     * @param nodeRef Nœud référençant le fichier dans le dossier d'acte dans le SAS.
     */
    public void ajouterErreurDossierActeSAS(NodeRef nodeRef, String message) {
        if(!this.serviceRegistry.getNodeService().hasAspect(nodeRef, ErreurBaseAspectModele.NOM)
                && !this.serviceRegistry.getNodeService().hasAspect(nodeRef, ErreurDossierAspectModele.NOM))
            this.ajouterErreurDossierSAS(this.obtenirNoeudParent(nodeRef));

        this.ajouterErreur(nodeRef, message);
    }

    /**
     * Méthode permettant d'ajouter une erreur au nœud d'un acte dans le dossier SAS.
     * @param nodeRef Le nœud référençant le dossier d'acte dans le dossier SAS sur lequel on doit ajouter l'erreur
     *                de dossier.
     */
    private void ajouterErreurDossierActeSAS(NodeRef nodeRef) {
        this.ajouterErreurDossier(nodeRef);
        this.ajouterErreurDossierSAS(this.obtenirNoeudParent(nodeRef));
    }

    /**
     * Méthode permettant retirer une erreur au nœud d'un acte dans le dossier SAS.
     * @param nodeRef Le nœud référençant le dossier d'acte dans le dossier SAS sur lequel on doit soustraire l'erreur
     *                de dossier.
     */
    public void retirerErreurDossierActe(NodeRef nodeRef) {
        ErreurBaseAspectHelperModele erreur1 = new ErreurBaseAspectHelperModele(this.serviceRegistry, nodeRef);
        ErreurDossierAspectHelperModele erreur2 = new ErreurDossierAspectHelperModele(this.serviceRegistry, nodeRef);

        if(!erreur1.hasAspect() && !erreur2.hasAspect()) return;

        if(erreur1.hasAspect()) {
            this.retirerErreur(nodeRef);
            this.soustraireErreurDossierSAS(erreur1.getNoeudParent());
        }

        if(erreur2.hasAspect()) {
            this.soustraireErreurDossier(nodeRef);
            // Si l'aspect n'est plus présent : alors il faut soustraire une erreur du dossier SAS.
            if(!erreur2.hasAspect()) this.soustraireErreurDossierSAS(erreur2.getNoeudParent());
        }
    }

    /**
     * Méthode permettant d'ajouter une erreur au nœud SAS.
     * @param nodeRef Le nœud référençant le dossier SAS sur lequel on doit soustraire l'erreur de dossier.
     */
    private void ajouterErreurDossierSAS(NodeRef nodeRef) {
        // ATTENTION : On touche au dossier SAS ! On utilise la mutex.
        synchronized (SAS_MUTEX) {
            this.ajouterErreurDossier(nodeRef);
        }
    }

    /**
     * Méthode permettant de soustraire une erreur au nœud SAS.
     * @param nodeRef Le nœud référençant le dossier SAS sur lequel on doit soustraire l'erreur de dossier.
     */
    private void soustraireErreurDossierSAS(NodeRef nodeRef) {
        // ATTENTION : On touche au dossier SAS ! On utilise la mutex.
        synchronized (SAS_MUTEX) {
            this.soustraireErreurDossier(nodeRef);
        }
    }

    /**
     * Méthode permettant d'ajouter une erreur simple sur un nœud.
     * @param nodeRef Le nœud sur lequel on doit ajouter l'erreur.
     * @param message Le message lié à l'erreur.
     * */
    private void ajouterErreur(NodeRef nodeRef, String message) {
        ErreurBaseAspectHelperModele erreur = new ErreurBaseAspectHelperModele(this.serviceRegistry, nodeRef);
        logger.info(String.format("Ajout d'une erreur sur l'élément %s", erreur.obtenirNom()));
        if(!erreur.hasAspect()) erreur.addAspect(ErreurFactory.obtenirParametresErreur(message));
        else erreur.majProprietes(ErreurFactory.obtenirParametresErreur(message));
    }

    /**
     * Méthode permettant de retirer une erreur simple d'un nœud.
     * @param nodeRef Le nœud dont on souhaite retirer l'erreur.
     * @return <c>true</c> Si un aspect d'erreur a été supprimé ; sinon <c>false</c>.
     */
    private boolean retirerErreur(NodeRef nodeRef) {
        ErreurBaseAspectHelperModele erreur = new ErreurBaseAspectHelperModele(this.serviceRegistry, nodeRef);

        logger.info(String.format("Retrait d'une erreur sur l'élément %s", erreur.obtenirNom()));

        if(!erreur.hasAspect()) return false;
        erreur.supprimeAspect();

        return true;
    }

    /**
     * Méthode permettant d'ajouter une erreur dossier sur un nœud.
     * @param nodeRef Le nœud sur lequel on doit ajouter l'erreur de dossier.
     */
    private void ajouterErreurDossier(NodeRef nodeRef) {
        ErreurDossierAspectHelperModele erreur = new ErreurDossierAspectHelperModele(this.serviceRegistry, nodeRef);
        logger.info(String.format("Ajouter d'une erreur dossier sur le dossier %s", erreur.obtenirNom()));
        if(!erreur.hasAspect()) erreur.addAspect(ErreurFactory.obtenirParametresErreurDossier(1));
        else erreur.majProprietes(ErreurFactory.obtenirParametresErreurDossier(erreur.getNbFichierEnErreur() + 1 ));
    }

    /**
     * Soustrait une erreur d'un dossier ayant l'aspect d'erreur de dossier.
     * @param nodeRef Le nœud auquel on soustrait une erreur.
     */
    private void soustraireErreurDossier(NodeRef nodeRef) {
        ErreurDossierAspectHelperModele erreur = new ErreurDossierAspectHelperModele(this.serviceRegistry, nodeRef);
        logger.info(String.format("Soustraction d'une erreur sur le dossier %s", erreur.obtenirNom()));

        // Vérification de l'existence de l'aspect sur le nœud.
        if(!erreur.hasAspect()) {
            // Le nœud n'a pas l'aspect : Arrêt des opérations.
            logger.info(String.format(
                    "Le noeud %s n'a pas l'aspect  que l'on souhaite lui supprimer : Fin.", erreur.obtenirNom())
            );
            return;
        }
        // Mise à jour du message et du nom d'éléments contenus en erreurs.
        logger.info(String.format("Mise à jour de l'erreur sur le dossier %s", erreur.obtenirNom()));
        erreur.majProprietes(
                ErreurFactory.obtenirParametresErreurDossier(erreur.getNbFichierEnErreur() - 1)
        );

        // Si le nombre d'erreurs est inférieur au nombre 1 : c'est que le nœud n'a plus vocation à posséder l'aspect.
        if(erreur.getNbFichierEnErreur() > 0) return;

        // Suppression de l'aspect.
        logger.info(String.format("Suppression l'erreur sur le dossier %s", erreur.obtenirNom()));
        erreur.supprimeAspect();
    }

    /**
     * Méthode permettant de récupérer le nœud parent d'un nœud.
     * @param nodeRef Le nœud dont on souhaite récupérer le parent.
     * @return le nœud parent du nœud en référence.
     */
    private NodeRef obtenirNoeudParent(NodeRef nodeRef) {
        return this.serviceRegistry.getNodeService().getPrimaryParent(nodeRef).getParentRef();
    }
}

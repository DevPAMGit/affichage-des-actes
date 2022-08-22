package org.cd59.affichagedesactes.actions;


import org.alfresco.model.ContentModel;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;

import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.modeles.factory.ErreurFactory;
import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;
import org.cd59.affichagedesactes.modeles.typescontenus.actes59.aspect.docinfos.DocinfosAspectHelperModele;
import org.cd59.affichagedesactes.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectHelperModele;
import org.cd59.affichagedesactes.modeles.typescontenus.erreur59.aspect.erreurbase.ErreurBaseAspectHelperModele;
import org.cd59.affichagedesactes.modeles.typescontenus.erreur59.aspect.erreurdossier.ErreurDossierAspectHelperModele;

import java.io.Serializable;
import java.util.*;

/** Classe personnalisée d'action permettant de ranger un dossier dans la GED. */
public class RangerActeAction extends ActionExecuterAbstractBase {

    /**
     * Le registre des services Alfresco.
     */
    private ServiceRegistry serviceRegistry;

    /**
     * Paramètre servant à synchroniser les {@link Thread} lors de la modification
     */
    private final Object lockSASErreur = new Object();

    /** Modifie la valeur du paramètre de classe "serviceRegistry".
     * @param serviceRegistry La nouvelle valeur du paramètre de classe "serviceRegistry".
     * */
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    protected void executeImpl(Action action, NodeRef nodeRef) {
        // On retire l'aspect d'erreur si celui-ci existe.
        DossierinfosAspectHelperModele dossier = this.razErreurDossierArrete(nodeRef);

        // Vérification que le nœud possède l'aspect adéquat.
        if(!dossier.hasAspect()) {
            this.setNoeudErreur(
                    nodeRef,"Le dossier n'as pas l'aspect requis pour pouvoir être traité."
            );
            return;
        }

        // Vérification que la métadonées
        else if(!dossier.getDossiercomplet()) return;

        // Commencement du traitement.

        if(!dossier.estAspectValide()) {
            this.setMessageDossierArreteInvalide(dossier);
            return;
        }

        List<NodeRef> noeuds = dossier.getContenu();
        if(noeuds.size() < 2) {
            this.setNoeudErreur(nodeRef, "Le dossier n'a pas le nombre de fichiers minimum requis.");
            return;
        }

        boolean aErreur = false;
        for(NodeRef noeud : noeuds)
            if(!this.verifierFichier(noeud)){
                this.setMessageFichierArreteErreur(noeud);
                aErreur = true;
            }

        if(aErreur) return;

        NodeRef destination = this.getNoeudDestination(dossier.getAncetre(2), dossier);
    }

    /**
     * Méthode permettant de gérer l'erreur sur un fichier contenu dans le dossier d'arrêté.
     * @param nodeRef Le nœud du fichier du dossier d'arrêté.
     */
    private void setMessageFichierArreteErreur(NodeRef nodeRef) {
        StringBuilder message = new StringBuilder();
        DocinfosAspectHelperModele fichier = new DocinfosAspectHelperModele(this.serviceRegistry.getNodeService(), nodeRef);

        if(!fichier.hasAspect() ) message.append("Le document n'as pas l'aspect requis pour être traité.");
        else if(!fichier.estTypedocumentValide()) message.append("Le type de document n'as pas été saisi.");

        this.setNoeudErreur(nodeRef, message.toString());
        this.setDossierArreteErreur(fichier.getNoeudParent());
    }

    /**
     * Méthode permettant de mettre le dossier d'arrêté en erreur avec un message spécifique.
     * @param dossier Une instance modèle du dossier d'arrêté.
     */
    private void setMessageDossierArreteInvalide(DossierinfosAspectHelperModele dossier) {

        StringBuilder message = new StringBuilder();

        if(!dossier.hasAspect())
            message.append("Le dossier n'as pas l'aspect requis pour pouvoir être traité.\n");
        else {
            if (!dossier.estSignataireValide())
                message.append("Le dossier ne renseigne pas son champs obligatoire 'signataire'.\n");

            if (!dossier.estSourceValide())
                message.append("Le dossier ne renseigne pas son champs obligatoire 'source'.\n");

            if (!dossier.estAnneeValide())
                message.append("Le dossier ne renseigne pas son champs obligatoire 'année'.\n");

            if (!dossier.estObjetValide())
                message.append("Le dossier ne renseigne pas son champs obligatoire 'objet'.\n");

            if (!dossier.estOrgasigleValide())
                message.append("Le dossier ne renseigne pas le sigle de l'organisation émettrice.\n");

            if (!dossier.estNumeroacteValide())
                message.append("Le dossier ne renseigne le numéro d'acte.\n");

            if (!dossier.estTypedossierValide())
                message.append("Le dossier ne renseigne pas la typologie du dossier.\n");

            if (!dossier.estTypedossierValide())
                message.append("Le dossier ne renseigne pas son identifiant.\n");

            if (!dossier.estTypedossierValide())
                message.append("Le dossier ne renseigne pas son identifiant.\n");

            if (!dossier.estDatedossierValide())
                message.append("Le dossier ne renseigne pas de date.\n");
        }

        this.setNoeudErreur(dossier.getNoeud(), message.toString());
    }

    /**
     * Méthode qui ajoute/modifie l'aspect d'erreur pour une erreur sur le dossier d'arrêté.
     * @param nodeRef Le nœud correspondant au dossier d'arrêté.
     */
    private void setDossierArreteErreur(NodeRef nodeRef) {
        ErreurDossierAspectHelperModele node = new ErreurDossierAspectHelperModele(
                this.serviceRegistry.getNodeService(), nodeRef
        );

        if(!node.hasAspect()) {
            node.addAspect(ErreurFactory.getParametresAspectDossierErreur(1));
            setDossierSasErreur(node.getNoeudParent());
            return;
        }
        int nbErreurs = node.getNbFichierEnErreur() + 1;
        String pluriel = nbErreurs > 1 ? "s" : "";

        node.setMessageErreur(
                String.format("Le dossier contient %s élément%s en erreur%s.", nbErreurs, pluriel, pluriel)
        );
        node.setDateErreur(new Date());
    }

    /**
     * Méthode qui ajoute/modifie l'aspect d'erreur pour une erreur sur le dossier d'arrêté.
     * @param nodeRef Le nœud correspondant au dossier d'arrêté.
     * @param message Le message d'erreur devant accompagner l'erreur.
     */
    private void setNoeudErreur(NodeRef nodeRef, String message) {
        System.out.println("=====================================");
        System.out.println( this.serviceRegistry.getNodeService().getProperty(nodeRef, ContentModel.PROP_NAME) );
        ErreurBaseAspectHelperModele node = new ErreurBaseAspectHelperModele(this.serviceRegistry.getNodeService(), nodeRef);
        if(!node.hasAspect()) {
            System.out.println("CREATION ERREUR");
            node.addAspect(ErreurFactory.getParametresAspectErreur(message));
            // setDossierSasErreur(node.getNoeudParent());
            return;
        }
        System.out.println("MODIFICATION ERREUR");
        node.setMessageErreur(message);
        node.setDateErreur(new Date());
    }

    /**
     * Méthode qui ajoute/modifie l'aspect d'erreur sur le dossier SAS.
     * @param nodeRef Le nœud correspondant au dossier SAS.
     */
    private void setDossierSasErreur(NodeRef nodeRef) {
        ErreurDossierAspectHelperModele node = new ErreurDossierAspectHelperModele(
                this.serviceRegistry.getNodeService(), nodeRef
        );

        synchronized (this.lockSASErreur) {
            if(!node.hasAspect())
                node.addAspect(ErreurFactory.getParametresAspectDossierErreur(1));
            else {

                int nbErreurs = node.getNbFichierEnErreur() + 1;
                String pluriel = nbErreurs > 1 ? "s" : "";

                node.setDateErreur(new Date());
                node.setNbFichierEnErreur(nbErreurs);
                node.setMessageErreur(
                        String.format("Le dossier contient %s élément%s en erreur%s.", nbErreurs, pluriel, pluriel)
                );
            }
        }
    }

    private void retraitErreurFichier(NodeRef nodeRef) {
        ErreurBaseAspectHelperModele erreurFichier = new ErreurBaseAspectHelperModele(
                this.serviceRegistry.getNodeService(), nodeRef
        );

        if(!erreurFichier.hasAspect()) return;
        this.retraitErreurDossierArrete(erreurFichier.getNoeudParent());
    }

    private void retraitErreurDossierArrete(NodeRef nodeRef) {
        ErreurDossierAspectHelperModele erreurDossier = new ErreurDossierAspectHelperModele(
                this.serviceRegistry.getNodeService(), nodeRef
        );
        ErreurBaseAspectHelperModele erreurSimple = new ErreurBaseAspectHelperModele(
                this.serviceRegistry.getNodeService(), nodeRef
        );
        boolean aErreurSimple = erreurSimple.hasAspect();
        boolean aErreurDossier = erreurDossier.hasAspect();

        if( aErreurDossier ) erreurSimple.supprimeAspect();
        if( aErreurDossier ) erreurDossier.supprimeAspect();

        if(aErreurDossier || aErreurSimple) this.retraitErreurDossierSAS(erreurDossier.getNoeudParent());
    }

    private void retraitErreurDossierSAS(NodeRef nodeRef) {
        System.out.println("HEEEEEEEEEEEEEEEEEEEEERE");
        System.out.println(this.serviceRegistry.getNodeService().getProperty(nodeRef, ContentModel.PROP_NAME));
        System.out.println("FIN - HEEEEEEEEEEEEEEEEEEEEERE");
        ErreurDossierAspectHelperModele erreurSas = new ErreurDossierAspectHelperModele(
                this.serviceRegistry.getNodeService(), nodeRef
        );

        if( !erreurSas.hasAspect() ) return;

        // Synchronisation du Thread pour modifier le dossier SAS.
        synchronized (this.lockSASErreur) {
            int nb_erreur = erreurSas.getNbFichierEnErreur() - 1;

            // Suppression de l'aspect si plus aucun dossier n'est en erreur.
            if(nb_erreur == 0) {
                erreurSas.supprimeAspect();
                return;
            }

            // Modification de l'aspect sinon.
            String pluriel = nb_erreur > 1 ? "" : "s";
            erreurSas.setDateErreur(new Date());
            erreurSas.setNbFichierEnErreur(nb_erreur);
            erreurSas.setMessageErreur(String.format(
                    "Le SAS contient %s dossier%s en erreur%s", nb_erreur, pluriel, pluriel)
            );
        }
    }

    /**
     * Méthode permettant de retirer l'aspect d'erreur sur un dossier d'arrêté.
     * @param nodeRef Le dossier (nœud) dont il faut retirer l'aspect.
     */
    private DossierinfosAspectHelperModele razErreurDossierArrete(NodeRef nodeRef) {
        DossierinfosAspectHelperModele dossier = new DossierinfosAspectHelperModele(
                this.serviceRegistry.getNodeService(), nodeRef
        );
        for(NodeRef node : dossier.getContenu()) this.retraitErreurFichier(node);
        return dossier;
    }

    /**
     * Permet de récupérer le nœud de destination de l'acte.
     * @param noeudSource Le nœud dans lequel les dossiers sont créés.
     * @param dossier Le nœud du dossier de l'acte.
     * */
    private NodeRef getNoeudDestination(NodeRef noeudSource, DossierinfosAspectHelperModele dossier) {
        NodeService service = this.serviceRegistry.getNodeService();

        Calendar calendrier = new GregorianCalendar();
        calendrier.setTime(dossier.getDatedossier());

        String annee = Integer.toBinaryString(calendrier.get(Calendar.YEAR));
        String mois = Integer.toBinaryString(calendrier.get(Calendar.MONTH));
        String jour = Integer.toBinaryString(calendrier.get(Calendar.DAY_OF_MONTH));
        String type = dossier.getTypedossier();

        return new AlfrescoModeleHelper(service,
            new AlfrescoModeleHelper(service,
                new AlfrescoModeleHelper(service,
                        new AlfrescoModeleHelper(service,
                                new AlfrescoModeleHelper(service, noeudSource).creerDossier("Actes"))
                                .creerDossierAvecDonnees(annee, getMetadonneesAnnee(annee)))
                        .creerDossierAvecDonnees(mois, getMetadonneesMois(annee, mois)))
                    .creerDossierAvecDonnees(jour, getMetadonneesJour(annee, mois, jour)))
                .creerDossierAvecDonnees(type, getMetadonneesType(annee, mois, jour, type));
    }

    /** Méthode permettant de récupérer les métadonnées du dossier du mois.
     * @param annee L'année.
     * @return Map Les données de l'année. */
    private static Map<QName, Serializable> getMetadonneesAnnee(String annee) {
        HashMap<QName, Serializable> resultat = new HashMap<>();
        resultat.put(
                ContentModel.PROP_DESCRIPTION, String.format("Dossier des actes pour l'année ' %s", annee) );
        resultat.put(ContentModel.PROP_TITLE, annee);
        resultat.put(ContentModel.PROP_NAME, annee);
        return resultat;
    }

    /** Méthode permettant de récupérer les métadonnées du dossier du mois.
     * @param annee Le mois de l'année du mois.
     * @param mois Le mois.
     * @return Map Les données du mois. */
    private static Map<QName, Serializable> getMetadonneesMois(String annee, String mois) {
        HashMap<QName, Serializable> resultat = new HashMap<>();
        resultat.put(
                ContentModel.PROP_DESCRIPTION,
                String.format("Dossier des actes pour le mois %s de l'année %s", mois, annee) );
        resultat.put(ContentModel.PROP_TITLE, mois);
        resultat.put(ContentModel.PROP_NAME, mois);
        return resultat;
    }

    /** Méthode permettant de récupérer les métadonnées du dossier du jour.
     * @param annee L'année du jour.
     * @param mois Le mois du jour.
     * @param jour Le jour.
     * @return Map Les données du jour. */
    private static Map<QName, Serializable> getMetadonneesJour(String annee, String mois, String jour) {
        HashMap<QName, Serializable> resultat = new HashMap<>();
        resultat.put(
                ContentModel.PROP_DESCRIPTION,
                String.format("Dossier des actes pour la date du %s/%s/%s", jour, mois, annee) );
        resultat.put(ContentModel.PROP_TITLE, jour);
        resultat.put(ContentModel.PROP_NAME, jour);
        return resultat;
    }

    /** Méthode permettant de récupérer les métadonnées du dossier des types.
     * @param annee L'année du type.
     * @param mois Le mois du type.
     * @param jour Le jour du type.
     * @param type Le type.
     * @return Map Les données du type. */
    private static Map<QName, Serializable> getMetadonneesType(String annee, String mois, String jour, String type) {
        HashMap<QName, Serializable> resultat = new HashMap<>();
        resultat.put(
                ContentModel.PROP_DESCRIPTION,
                String.format("Dossier des %ss pour la journée du %s/%s%s", type, jour, mois, annee) );
        resultat.put(ContentModel.PROP_TITLE, type);
        resultat.put(ContentModel.PROP_NAME, type);
        return resultat;
    }

    /** Méthode permettant de vérifier si les fichiers contenus dans le dossier est actes est valide.
     * @param noeud Le nœud étant le fichier à vérifier. */
    private boolean verifierFichier(NodeRef noeud) {
        return new DocinfosAspectHelperModele(this.serviceRegistry.getNodeService(), noeud).estAspectValide();
    }

    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> list) {

    }
}

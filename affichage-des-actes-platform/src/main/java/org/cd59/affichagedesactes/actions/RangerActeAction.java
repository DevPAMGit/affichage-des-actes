package org.cd59.affichagedesactes.actions;


import org.alfresco.model.ContentModel;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;

import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;
import org.cd59.affichagedesactes.modeles.typescontenus.actes59.aspect.docinfos.DocinfosAspectHelperModele;
import org.cd59.affichagedesactes.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectHelperModele;
import org.cd59.affichagedesactes.modeles.typescontenus.erreur59.aspect.erreurbase.ErreurBaseAspectHelperModele;
import org.cd59.affichagedesactes.modeles.typescontenus.erreur59.aspect.erreurbase.ErreurBaseAspectModele;

import java.io.Serializable;
import java.util.*;

/** Classe personnalisée d'action permettant de ranger un dossier dans la GED. */
public class RangerActeAction extends ActionExecuterAbstractBase {

    /** Le registre des services Alfresco. */
    private ServiceRegistry serviceRegistry;

    /** Modifie la valeur du paramètre de classe "serviceRegistry".
     * @param serviceRegistry La nouvelle valeur du paramètre de classe "serviceRegistry". */
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    protected void executeImpl(Action action, NodeRef nodeRef) {
        System.out.println("ACTION " + action.getActionDefinitionName());
        DossierinfosAspectHelperModele dossier =
                new DossierinfosAspectHelperModele(this.serviceRegistry.getNodeService(), nodeRef);

        // Vérification que le nœud possède l'aspect adéquat.
        if(!dossier.hasAspect()) {
            this.setError(nodeRef, "Le dossier n'a pas l'aspect requis.");
            return;
        }

        // Vérification que la métadonées
        else if(!dossier.getDossiercomplet()) return;

        if(!dossier.estAspectValide()) {
            this.setError(nodeRef, "Le dossier n'a pas rempli tout les aspects requis.");
            return;
        }

        List<NodeRef> noeuds = dossier.getContenu();
        if(noeuds.size() < 2) {
            this.setError(nodeRef, "Le dossier n'a pas le nombre de fichiers minimum requis.");
            return;
        }

        for(NodeRef noeud : noeuds)
            if(!this.verifierFichier(noeud))
                return;

        NodeRef destination = this.getNoeudDestination(dossier.getAncetre(2), dossier);
    }

    private void setError(NodeRef nodeRef, String message) {
        ErreurBaseAspectHelperModele node = new ErreurBaseAspectHelperModele(this.serviceRegistry.getNodeService(), nodeRef);

        HashMap<QName, Serializable> parameters = new HashMap<>();
        parameters.put(ErreurBaseAspectModele.DATE_ERREUR, new Date());
        parameters.put(ErreurBaseAspectModele.MESSAGE_ERREUR, message);

        node.addAspect(parameters);
    }

    /** Permet de récupérer le nœud de destination de l'acte.
     * @param noeudSource Le nœud dans lequel les dossiers sont créés.
     * @param dossier Le nœud du dossier de l'acte. */
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

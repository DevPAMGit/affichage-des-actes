package org.cd59.affichagedesactes.logique.factory;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.modeles.sources.AlfrescoModeleHelper;
import org.cd59.affichagedesactes.modeles.typescontenus.stockageactes59.aspect.dossierinfo.DossierInfoAspectModele;
import org.cd59.affichagedesactes.modeles.typescontenus.stockageactes59.type.actesconteneur.ActesConteneurTypeHelperModele;
import org.cd59.affichagedesactes.modeles.typescontenus.stockageactes59.type.actesconteneur.ActesConteneurTypeModele;
import org.cd59.affichagedesactes.modeles.typescontenus.stockageactes59.type.conteneuracteannee.ConteneurActeAnneeTypeHelperModele;

import java.io.Serializable;
import java.util.HashMap;

public class DossierFactory {

    private static HashMap<QName, Serializable> obtenirInfoDossierParametres(String nom, String description) {
        HashMap<QName, Serializable> resultat = new HashMap<>();
        resultat.put(ContentModel.PROP_NAME, nom);
        resultat.put(ContentModel.PROP_TITLE, nom);
        resultat.put(DossierInfoAspectModele.NB_DOSSIERS_EN_ERREUR, 0);
        resultat.put(DossierInfoAspectModele.NB_DOSSIERS_PRESENTS, 0);
        return resultat;
    }

    public static ActesConteneurTypeHelperModele obtenirDossierActe(NodeService nodeService, NodeRef dossierSource) {
        AlfrescoModeleHelper source = new AlfrescoModeleHelper(nodeService, dossierSource);
        NodeRef conteneur = source.creerDossier("Actes");
        ActesConteneurTypeHelperModele resultat = new ActesConteneurTypeHelperModele(nodeService, conteneur);

        if(!resultat.hasType(ActesConteneurTypeModele.NOM)) {
            resultat.addType();
            resultat.majProprietes(obtenirInfoDossierParametres("Actes", "Dossier contenant tous les " +
                    "actes du département du Nord."));
        }

        return resultat;
    }

    public static ActesConteneurTypeHelperModele obtenirDossierAnnee(NodeService nodeService, AlfrescoModeleHelper dossierActes, String annee) {
        NodeRef conteneur = dossierActes.creerDossier(annee);
        ActesConteneurTypeHelperModele resultat = new ActesConteneurTypeHelperModele(nodeService, conteneur);

        if(!resultat.hasType(ActesConteneurTypeModele.NOM)) {
            resultat.addType();
            resultat.majProprietes(obtenirInfoDossierParametres(annee, String.format("Dossier stockant les actes du " +
                    "département du Nord pour l'année %s.", annee)));
        }

        return resultat;
    }


}

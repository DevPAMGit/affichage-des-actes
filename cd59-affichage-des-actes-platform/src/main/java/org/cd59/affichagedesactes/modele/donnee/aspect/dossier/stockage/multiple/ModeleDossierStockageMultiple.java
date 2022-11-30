package org.cd59.affichagedesactes.modele.donnee.aspect.dossier.stockage.multiple;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.action.custom.source.v1.action.IModeleNoeudAction;
import org.cd59.affichagedesactes.action.custom.stockage.StockerDossierActeDonneesFactory;
import org.cd59.affichagedesactes.modele.alfresco.aspect.DossierinfosAspectModele;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;
import org.cd59.affichagedesactes.modele.donnee.source.ModeleNoeud;


public class ModeleDossierStockageMultiple extends ModeleNoeud implements IModeleDossierStockageMultiple {

    /**
     * L'identifiant du dossier.
     */
    protected String identifiant;

    /**
     * Récupère la valeur de la référence multiple.
     * @return <c>true</c> si la référence multiple est à vrai. Sinon <c>false</c>.
     */
    public boolean getReferenceMultiple() {
        return this.referenceMultiple;
    }

    /**
     * Indique si le dossier est en référence multiple.
     */
    protected boolean referenceMultiple;

    /**
     * Initialise une nouvelle instance de la classe {@link ModeleDossierStockageMultiple}.
     *
     // @param serviceRegistry Le service de registre du nœud.
     * @param nodeRef         Le nœud source.
     * @throws ModeleException Si le registre de services ou le nœud sont null.
     */
    public ModeleDossierStockageMultiple(/*ServiceRegistry serviceRegistry*/ IModeleNoeudAction modeleNoeudAction, NodeRef nodeRef) throws ModeleException, PreRequisException {
        super(/*serviceRegistry*/ modeleNoeudAction, nodeRef);

        this.identifiant = this.getProprieteChaine(DossierinfosAspectModele.IDDOSSIER);
        this.referenceMultiple = this.obtenirProprieteEnBooleen(DossierinfosAspectModele.EST_EN_REF_MULTIPLE);
    }

    @Override
    public void switchReferenceMultiple() throws /*ModeleException,*/ PreRequisException, NoSuchMethodException {
        this.referenceMultiple = !this.referenceMultiple;
        this.setPropriete(DossierinfosAspectModele.EST_EN_REF_MULTIPLE, this.referenceMultiple);
    }

    @Override
    public void setNom(int numero) throws /*ModeleException,*/ PreRequisException, NoSuchMethodException {
        String nom = StockerDossierActeDonneesFactory.getNomDossier(this.identifiant, numero);
        this.setPropriete(ContentModel.PROP_NAME, nom);
        this.setPropriete(ContentModel.PROP_TITLE, nom);
    }

    @Override
    public String getIdentifiant(){
        return this.identifiant;
    }

}

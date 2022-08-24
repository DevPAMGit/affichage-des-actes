package org.cd59.affichagedesactes.logique;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.cd59.affichagedesactes.logique.factory.DossierFactory;
import org.cd59.affichagedesactes.modeles.typescontenus.actes59.aspect.docinfos.DocinfosAspectHelperModele;
import org.cd59.affichagedesactes.modeles.typescontenus.actes59.aspect.dossierinfos.DossierinfosAspectHelperModele;
import org.cd59.affichagedesactes.modeles.typescontenus.stockageactes59.type.actesconteneur.ActesConteneurTypeHelperModele;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class StockerDossierActeLogique extends AffichageDesActesSourceLogique {

    /**
     * Le nombre de fichiers d'acte original trouvés.
     */
    private int nbFichierActeOriginal;

    /**
     * Le nœud de référence vers le dossier de destination de l'acte en traitement.
     */
    private NodeRef dossierDestinationActe;


    /**
     * Initialise une nouvelle instance de la classe {@link StockerDossierActeLogique}.
     * @param nodeService Le service de gestion des nœuds Alfresco.
     */
    public StockerDossierActeLogique(NodeService nodeService) {
        super(nodeService);
        this.dossierDestinationActe = null;
        this.nbFichierActeOriginal = 0;
    }

    public void executer(NodeRef nodeRef) {
        // TODO: 24/08/2022  <br /> dans les message d'erreurs.
        // TODO: 24/08/2022

        System.out.println("EXECUTION");
        this.razErreurDossierActeSas(nodeRef);

        // Vérification des préconditions de l'acte.
        if(!this.verifierDossierActe(nodeRef)) return;
        System.out.println("VERIFICATION DOSSIER OK");
        // Traitement du dossier.
        DossierinfosAspectHelperModele dossier = new DossierinfosAspectHelperModele(this.nodeService, nodeRef);
        this.majDossierDestination(dossier);
        System.out.println("MAJ DOSSIER OK");
    }

    private void majDossierDestination(DossierinfosAspectHelperModele dossier) {
        // Récupération du dossier racine.
        NodeRef racine = dossier.getAncetre(2);

        // Création du dossier d'actes s'il n'existe pas.
        ActesConteneurTypeHelperModele actes = DossierFactory.obtenirDossierActe(this.nodeService, racine);

        /*Calendar calendrier = new GregorianCalendar();
        calendrier.setTime(dossier.getDatedossier());

        String annee = Integer.toBinaryString(calendrier.get(Calendar.YEAR));
        String mois = Integer.toBinaryString(calendrier.get(Calendar.MONTH));
        String jour = Integer.toBinaryString(calendrier.get(Calendar.DAY_OF_MONTH));
        String type = dossier.getTypedossier();*/
    }

    /**
     * Méthode permettant de construire le nom du dossier d'acte.
     * @param dossier Le dossier d'acte.
     * @return Le nom du dossier d'acte.
     */
    private String obtenirNomDossierActe(DossierinfosAspectHelperModele dossier) {
        return String.format("%s_%s_%s_%s",
                dossier.getTypedossier().substring(0,3).toUpperCase(),
                dossier.getOrgasigle().toUpperCase(),
                this.getAnneeDossierActe(dossier), dossier.getNumeroacte());
    }

    /**
     * Méthode permettant de récupérer l'année de la date du dossier d'acte.
     * @param dossier Le dossier d'acte.
     * @return L'année de la date du dossier d'acte en chaîne de caractères.
     */
    private String getAnneeDossierActe(DossierinfosAspectHelperModele dossier) {
        Calendar calendrier = new GregorianCalendar();
        calendrier.setTime(dossier.getDatedossier());
        return Integer.toBinaryString(calendrier.get(Calendar.YEAR));
    }

    /**
     * Méthode permettant de vérifier les préconditions du dossier d'acte avant traitement pour stockage.
     * @param nodeRef Le nœud faisant référence vers le dossier d'acte.
     * @return <c>true</c> si le dossier est valide ; <c>false</c> sinon.
     */
    private boolean verifierDossierActe(NodeRef nodeRef) {
        DossierinfosAspectHelperModele dossier = new DossierinfosAspectHelperModele(this.nodeService, nodeRef);
        // Vérification que le dossier peut être traité.
        if(!dossier.getDossiercomplet()) {
            System.out.println("SORTIE 1");
            return false;
        }
        // Vérification du dossier.
        if(!dossier.estAspectValide() || !this.verifierTypeDossier(dossier)) {
            this.ajouterErreurDossierActeSas(nodeRef, this.obtenirMessageErreurPreconditionDossier(dossier));
            System.out.println("SORTIE 2");
            return false;
        }

        // Vérification du contenu de dossier.
        boolean contenuEnErreur = false;
        for(NodeRef noeudEnfant : dossier.getContenu())
            if(!this.verifierDocumentPrecondition(noeudEnfant)) contenuEnErreur = true;

        // Des aspects d'erreurs ont déjà été ajoutés au dossier 'acte' et 'sas' en cas de contenus en erreur.
        if(contenuEnErreur) {
            System.out.println("SORTIE 3");
            return false;
        }

        // Vérification que le contenu du dossier d'acte est valide.
        if( !this.verifierContenuDossierActe() ) {
            this.ajouterErreurDossierActeSas(nodeRef, this.obtenirMessageErreurDocumentDossier());
            System.out.println("SORTIE 4");
            return false;
        }

        return true;
    }

    /**
     * Méthode permettant de vérifier les précondtions du contenu d'un dossier d'actes avant traitement pour stockage.
     * @return <c>true</c> si les préconditions sont respectées ; sinon <c>false</c>.
     */
    private boolean verifierContenuDossierActe() {
        return (this.nbFichierActeOriginal == 1);
    }

    /**
     * Vérifie les préconditions d'un document
     * @param nodeRef Le nœud qui référencie un fichier d'acte.
     * @return <c>true</c> si les préconditions sont valides ; sinon <c>false</c>.
     */
    private boolean verifierDocumentPrecondition(NodeRef nodeRef) {
        DocinfosAspectHelperModele document = new DocinfosAspectHelperModele(this.nodeService, nodeRef);
        if(document.estAspectValide() && this.verifierTypeDocument(document))
            return true;
        this.ajouterErreurFichierActeSas(nodeRef, this.obtenirMessageErreurFichier(document));
        return false;
    }

    /**
     * Méthode permettant d'obtenir le message d'erreur d'un dossier d'actes sur le contenu de son dossier.
     * @return Le message d'erreur.
     */
    private String obtenirMessageErreurDocumentDossier() {
        StringBuilder message = new StringBuilder();

        if(this.nbFichierActeOriginal > 1)
            message.append("Le dossier d'acte ne doit contenir qu'un seul fichier d'acte original.");
        if(this.nbFichierActeOriginal < 1) message.append("Le dossier d'acte doit un fichier d'acte original.");

        return message.toString();
    }

    /**
     * Méthode permettant d'obtenir le message d'erreur d'un dossier d'actes sur le non-respect des preconditions.
     * @param dossier Le dossier pour lequel on souhaite récupérer l'erreur.
     * @return Le message d'erreur.
     */
    private String obtenirMessageErreurPreconditionDossier(DossierinfosAspectHelperModele dossier) {
        StringBuilder message = new StringBuilder();
        if(!dossier.hasAspect()) message.append("Le dossier n'a pas l'aspect requis.");
        else {
            if(!dossier.estSignataireValide()) message.append("Le signataire de l'acte n'a pas été renseigné.\n");
            if(!dossier.estSourceValide()) message.append("La source du dépôt d'acte n'a pas été renseignée.\n");
            if(!dossier.estAnneeValide()) message.append("L'année de l'acte n'a pas été renseignée.\n");
            if(!dossier.estObjetValide()) message.append("L'objet de l'acte n'a pas été renseigné.\n");
            if(!dossier.estOrgasigleValide())
                message.append("Le signe de l'organisation l'acte n'a pas été renseigné.\n");
            if(!dossier.estNumeroacteValide()) message.append("Le numéro de l'acte n'a pas été renseigné.\n");

            if(!dossier.estTypedossierValide()) message.append("Le type du dossier de l'acte n'a pas été renseigné.\n");
            else if( !this.verifierTypeDossier(dossier) )
                message.append("Le type du dossier d'acte n'est pas valide.\nLes valeurs peuvent être " +
                        "'Délibération' ou 'Délibération'. Ou encore 'Arrêté' ou 'ARRETE'.\n");

            if(!dossier.estIddossierValide())
                message.append("L'identifiant du dossier de l'acte n'a pas été renseigné.\n");

            if(!dossier.estDatedossierValide()) message.append("La date de l'acte n'est pas renseigné.");

        }
        return message.toString();
    }

    /**
     * Méthode permettant de récupérer le message d'erreur d'un fichier.
     * @param document le document dont on souhaite récupérer le message d'erreur.
     * @return Le message d'erreur.
     */
    private String obtenirMessageErreurFichier(DocinfosAspectHelperModele document) {
        StringBuilder message = new StringBuilder();
        if(!document.hasAspect()) message.append("Le document n'a pas l'aspect attendu pour être pris en compte.");
        else if(!document.estTypedocumentValide()) message.append("Le type du document n'a pas été renseigné.");
        else if(!this.verifierTypeDocument(document))
            message.append("Le type du document n'est pas valide. Les valeurs doivent être soit 'ACTE' " +
                    "ou 'ANNEXE' ou bien 'ACTE_ORIGINAL').");
        return message.toString();
    }

    /**
     * Méthode permettant de vérifier si le type de l'acte est valide.
     * @param dossier Le dossier dont on souhaite vérifier le type.
     * @return <c>true</c> si le type est valide ; sinon <c>false</c>.
     */
    private boolean verifierTypeDossier(DossierinfosAspectHelperModele dossier) {
        if(!dossier.estTypedossierValide()) return false;
        String type = dossier.getTypedossier();

        return (type.equals("Délibération") || type.equals("Arrêté") ||  type.equals("ARRETE") ||
                type.equals("DELIBERATION"));
    }

    /**
     * Vérifie si le type de document est valide.
     * @param document le document dont on souhaite vérifier le type.
     * @return <c>true</c> si le type est valide ; sinon <c>false</c>. Le type du dossier de l'acte n'a pas de type valide
     */
    private boolean verifierTypeDocument(DocinfosAspectHelperModele document) {
        if(!document.estTypedocumentValide()) return false;
        String type = document.getTypedocument();

        boolean resultat = true;

        if( type.equals("ACTE_ORIGINAL") || type.equals("ANNEXE") ) {
            if (type.equals("ACTE_ORIGINAL")) this.nbFichierActeOriginal++;
        } else resultat = false;

        return resultat;
    }
}

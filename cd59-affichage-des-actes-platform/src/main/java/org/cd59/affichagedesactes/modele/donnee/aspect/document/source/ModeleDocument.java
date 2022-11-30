package org.cd59.affichagedesactes.modele.donnee.aspect.document.source;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;
import org.cd59.affichagedesactes.action.custom.source.v1.action.IModeleNoeudAction;
import org.cd59.affichagedesactes.modele.alfresco.aspect.DocinfosAspectModele;
import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;
import org.cd59.affichagedesactes.modele.donnee.source.ModeleNoeud;
import org.cd59.affichagedesactes.utilitaire.UtilitaireChaineDeCaracteres;
import org.cd59.affichagedesactes.utilitaire.UtilitaireFichier;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.EnumSet;
import java.util.stream.Collectors;

/**
 * Classe modèle pour un document.
 */
public class ModeleDocument extends ModeleNoeud {
    /**
     * Le type du document.
     */
    protected final ModeleDocumentType type;

    /**
     * Le contenu du fichier.
     */
    public final byte[] contenu;

    /**
     * L'empreinte du fichier.
     */
    public final String empreinte;

    /**
     * Le nom du nœud.
     */
    public final String nom;

    /**
     * Initialise une nouvelle instance de la classe {@link ModeleDocument}.
     // @param serviceRegistry  Le service de registre du nœud.
     * @param nodeRef          Le nœud source.
     * @throws ModeleException Si le registre de services ou le nœud sont null.
     * @throws IOException     Si une erreur entrée/sortie à lieu lors de la lecture du nœud.
     */
    public ModeleDocument(/*ServiceRegistry serviceRegistry*/ IModeleNoeudAction modeleNoeudAction, NodeRef nodeRef)
            throws ModeleException, IOException, NoSuchAlgorithmException, PreRequisException, NoSuchMethodException {

        super(/*serviceRegistry*/ modeleNoeudAction, nodeRef);

        // Vérification que le nœud est de type document.
        if(!this.avoirAspect(DocinfosAspectModele.NOM))
            throw new ModeleException("Le document n'a pas l'aspect 'docinfos'.");

        this.type = this.getType();
        this.contenu = this.lire();
        this.empreinte = this.getEmpreinte();
        this.nom = this.getProprieteChaine(ContentModel.PROP_NAME);
    }

    /**
     * Méthode permettant d'obtenir le type.
     * @return Le type du document.
     */
    private ModeleDocumentType getType() throws ModeleException, PreRequisException {
        String typeNoeud = this.getProprieteChaine(DocinfosAspectModele.TYPEDOCUMENT);

        if (typeNoeud == null || typeNoeud.trim().isEmpty())
            throw new ModeleException("Le type d'un document n'est pas renseigné.");

        ModeleDocumentType[] enumerations = ModeleDocumentType.values();
        int index = 0;

        while (index < enumerations.length && !enumerations[index].valeur.equals(typeNoeud))
            index++;

        // Le type n'a pas été trouvé : Erreur.
        if(index == enumerations.length)
            throw new ModeleException(
                    String.format(
                            "Le type d'un document n'est pas valide (les valeurs doivent être comprises entre (%s)",
                            String.join(EnumSet.allOf(ModeleDocumentType.class).stream()
                                    .map(Enum::toString).collect(Collectors.joining(",")))
                    )
            );

        // Initialisation du type.
        return enumerations[index];
    }

    /**
     * Récupère ou génère le hash du fichier.
     * @return Le hash du fichier en 'SHA-256'.
     // @throws ModeleException Si une propriete est null.
     */
    private String getEmpreinte() throws /*ModeleException,*/ NoSuchAlgorithmException, PreRequisException, NoSuchMethodException {
        // Récupération de la valeur de l'empreinte.
        String hash = this.getProprieteChaine(DocinfosAspectModele.EMPREINTE);

        // S'il n'y a pas d'empreinte : initialisation.
        if(UtilitaireChaineDeCaracteres.etreNullOuVide(hash)) {
            hash = UtilitaireFichier.getEmpreinte("SHA-256", this.contenu);
            this.setPropriete(DocinfosAspectModele.EMPREINTE, hash);
        }

        // Retour de la valeur.
        return hash;
    }
}

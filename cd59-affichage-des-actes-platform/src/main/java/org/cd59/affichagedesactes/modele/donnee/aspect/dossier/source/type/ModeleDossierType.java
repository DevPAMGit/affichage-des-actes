package org.cd59.affichagedesactes.modele.donnee.aspect.dossier.source.type;


import org.cd59.affichagedesactes.modele.donnee.exception.ModeleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.stream.Collectors;

/**
 * Classe modèle pour le type d'un dossier d'acte.
 */
public class ModeleDossierType {

    /**
     * Le logger de la classe.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ModeleDossierType.class);

    /**
     * Le type de dossier (Délibération ou Arrêté) en majuscule.
     */
    public final ModeleDossierTypologieEnumeration typeMajuscule;

    /**
     * Le type de dossier (Délibération ou Arrêté) en minuscule.
     */
    public final ModeleDossierTypologieEnumeration typeMinuscule;

    /**
     * Le diminutif du type.
     */
    public final String diminutif;

    /**
     * Initialise une nouvelle instance de la classe {@link ModeleDossierType}.
     * @param type Le type du dossier d'acte.
     * @throws ModeleException Si le type du dossier n'est pas renseigné ou si le type n'est pas valide.
     */
    public ModeleDossierType(String type) throws ModeleException {
        // Vérification des préconditions.
        LOGGER.info("Vérification des préconditions.");

        if(type == null || type.trim().isEmpty())
            throw new ModeleException("Le type du dossier n'est pas renseigné.");

        // Recherche du type dans la liste des types possibles.
        LOGGER.info("Recherche du type dans la liste des types possibles.");
        ModeleDossierTypologieEnumeration[] enumerations = ModeleDossierTypologieEnumeration.values();
        int index = 0;

        while (index < enumerations.length && !enumerations[index].valeur.equals(type))
            index++;

        // Le type n'a pas été trouvé : Erreur.
        if(index == enumerations.length)
            throw new ModeleException(
                    String.format(
                            "Le type du dossier n'est pas valide (les valeurs doivent être comprises entre (%s)",
                            String.join(EnumSet.allOf(ModeleDossierTypologieEnumeration.class).stream()
                                  .map(Enum::toString).collect(Collectors.joining(",")))

                    )
            );

        // Initialisation du type.
        if(enumerations[index] == ModeleDossierTypologieEnumeration.ARRETE
           || enumerations[index] == ModeleDossierTypologieEnumeration.Arrete) {
            this.typeMajuscule = ModeleDossierTypologieEnumeration.ARRETE;
            this.typeMinuscule = ModeleDossierTypologieEnumeration.Arrete;
        } else {
            this.typeMajuscule = ModeleDossierTypologieEnumeration.DELIBERATION;
            this.typeMinuscule = ModeleDossierTypologieEnumeration.Deliberation;
        }

        // Initialisation du diminutif.
        this.diminutif = this.typeMajuscule.valeur.substring(0, 3);
    }
}

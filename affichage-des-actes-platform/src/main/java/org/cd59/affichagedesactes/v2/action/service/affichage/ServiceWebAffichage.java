package org.cd59.affichagedesactes.v2.action.service.affichage;

/**
 * Met à disposition les services pour l'affichage des actes.
 */
public class ServiceWebAffichage {
    /**
     * L'hôte de connexion.
     */
    private final String hote;

    /**
     * Le jeton de connexion.
     */
    private String jeton;

    /**
     * Initialise une nouvelle instance de la classe {@link ServiceWebAffichage}.
     * @param hote L'hôte de connexion.
     */
    public ServiceWebAffichage(String hote) {
        this.hote = hote;
    }


}

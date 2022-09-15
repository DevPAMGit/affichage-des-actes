package org.cd59.affichagedesactes.v2.action.service.source.methode;

import java.io.IOException;

/**
 * Requete POST MULTIPART avec
 */
public abstract class RequeteWebPostMultipartBearer extends RequeteWebPostMultipart {

    /**
     * Le jeton d'authentification.
     */
    private final String jeton;

    /**
     * Initialise une nouvelle instance de la classe {@link RequeteWebPostMultipartBearer}.
     * @param hote L'hôte sur lequel se connecter.
     * @param jeton Le jeton d'authentification.
     * @throws IOException Si une erreur d'entré/sortie est lancée à l'ouverture du socket.
     */
    public RequeteWebPostMultipartBearer(String hote, String jeton) throws IOException {
        super(hote);
        this.jeton = jeton;
    }

    @Override
    protected void modifierEntetes() {
        super.modifierEntetes();
        this.ajouterEntete("Authorization", String.format("Bearer %s", this.jeton));

    }
}

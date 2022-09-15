package org.cd59.affichagedesactes.v2.action.service.source.methode;

import org.cd59.affichagedesactes.v2.action.service.source.source.MethodeRequeteWeb;
import org.cd59.affichagedesactes.v2.action.service.source.source.RequeteWeb;

import java.io.IOException;

/**
 * Requête web utilisant la méthode GET.
 */
public abstract class RequeteWebGet extends RequeteWeb {
    /**
     * Initialise une nouvelle instance de la classe {@link RequeteWeb}.
     * @param hote  L'hôte sur lequel se connecter.
     * @throws IOException Si une erreur d'entré/sortie est lancée à l'ouverture du socket.
     */
    public RequeteWebGet(String hote) throws IOException {
        super(hote, MethodeRequeteWeb.GET);
    }

    @Override
    protected String obtenirCorps() {
        return null;
    }
}

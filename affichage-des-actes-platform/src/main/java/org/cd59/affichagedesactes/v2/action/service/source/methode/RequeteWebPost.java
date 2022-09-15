package org.cd59.affichagedesactes.v2.action.service.source.methode;

import org.cd59.affichagedesactes.v2.action.service.source.source.MethodeRequeteWeb;
import org.cd59.affichagedesactes.v2.action.service.source.source.RequeteWeb;

import java.io.IOException;

/**
 * Requête web utilisant la méthode POST.
 */
public abstract class RequeteWebPost extends RequeteWeb {
    /**
     * Initialise une nouvelle instance de la classe {@link RequeteWeb}.
     * @param hote    L'hôte sur lequel se connecter.
     * @throws IOException Si une erreur d'entré/sortie est lancée à l'ouverture du socket.
     */
    public RequeteWebPost(String hote) throws IOException {
        super(hote, MethodeRequeteWeb.POST);
    }
}

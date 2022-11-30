package org.cd59.affichagedesactes.action.custom.envoi.service.source.web.method.classic;


import org.cd59.affichagedesactes.action.custom.envoi.service.source.web.core.url.RequeteURL;
import org.cd59.affichagedesactes.action.custom.envoi.service.source.web.core.url.UrlRequestMethod;

import java.io.IOException;

/**
 * Classe personnalisée pour gérer les requêtes utilisant la methode GET.
 */
public abstract class RequeteURLGet extends RequeteURL {

    /**
     * Initialise une nouvelle instance de la classe RequeteURLPost.
     * @param url    L'URL à laquelle se connecté.
     * @throws IOException Si une erreur  d'E/S.
     * */
    public RequeteURLGet(String url) throws IOException {
        super(url, UrlRequestMethod.GET);
    }
    @Override
    protected String getBody() { return null; }
}

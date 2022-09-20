package org.cd59.affichagedesactes.v2.action.service.source.web.method.classic;


import org.cd59.affichagedesactes.v2.action.service.source.web.core.url.RequeteURL;
import org.cd59.affichagedesactes.v2.action.service.source.web.core.url.UrlRequestMethod;

import java.io.IOException;

/**
 * Classe personnalisée pour gérer les requêtes utilisant la methode POST.
 */
public abstract class RequeteURLPost extends RequeteURL {

    /**
     * Initialise a new instance of {@link RequeteURLPost} class.
     * @param host     The host name, or null for the loopback address.
     * @param resource The resource to get into the server.
     * @throws IOException              If an I/O error occurs when creating the socket.
     */
    public RequeteURLPost(String host, String resource) throws IOException {
        super(String.format("%s%s", host, resource), UrlRequestMethod.POST);
    }
}

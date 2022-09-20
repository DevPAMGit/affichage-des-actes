package org.cd59.affichagedesactes.v2.action.service.source.web.core.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Classe personnalisée pour gérer les requêtes MULTIPART utilisant la methode POST.
 */
public abstract class RequeteURL {

    /**
     * Instance de connexion vers l'URL de la requête.
     */
    protected final HttpURLConnection connection;

    /**
     * Initialise une nouvelle instance de la classe RequeteURL.
     * @param url L'URL su laquelle se connecter.
     * @param method La méthode web à utiliser.
     * @throws IOException Si une I/O exception est lancée.
     */
    public RequeteURL(String url, UrlRequestMethod method) throws IOException {
        this.connection = (HttpURLConnection) new URL(url).openConnection();
        this.connection.setRequestMethod(method.value);
    }

    /** Ajoute une valeur à l'en-tête.
     * @param key La clé de la valeur.
     * @param value La valeur de l'en-tête. */
    protected void addHeader(String key, String value) {
        this.connection.setRequestProperty(key, value);
    }

    /** Appel la requête.
     * @return String La réponse de la requête. */
    public String call() throws IOException {
        this.setHeaders();
        this.send();
        String result = this.getResponse();
        if(this.connection != null) this.connection.disconnect();
        return result;
    }

    /**
     * Récupère la réponse de la requête web.
     * @return String The web request response.
     * @throws IOException Si une I/O exception est lancée. */
    private String getResponse() throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
        }catch (IOException e) {
            reader = new BufferedReader(new InputStreamReader(this.connection.getErrorStream()));
        }
        StringBuilder result = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) result.append(line);
        reader.close();
        return result.toString();

    }

    /**
     * Envoie la requête.
     * @throws IOException Si une exception d'E/S est lancée.
     */
    protected void send() throws IOException {
        String body = this.getBody();
        OutputStream output = null;
        if(body != null && !body.isEmpty()) {
            this.connection.setDoOutput(true);
            output = this.connection.getOutputStream();
            output.write(body.getBytes());
        }
        if(output != null) {
            output.flush();
            output.close();
        }
    }

    /**
     * Récupère le corps de la requête.
     * @return String Récupère la valeur du corps de la requête.
     */
    protected abstract String getBody();

    /**
     * Modifie les en-têtes.
     */
    protected abstract void setHeaders();
}

package org.cd59.affichagedesactes.action.custom.envoi.service.source.web.method.classic;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Classe personnalisée pour gérer les requêtes utilisant la methode GET.
 */
public abstract class UrlPostMultipartRequestPost extends RequeteURLPost {

    /**
     * Limite des arguments de la requêtes.
     */
    protected final static String LIMITE = "------LIMITE";

    /**
     * Le retour d'une requête.
     */
    protected final static String RETOUR = "\r\n";

    /**
     * Le flux de sortie.
     */
    protected OutputStream sortie;

    /**
     * Le rédacteur sur la sortie.
     */
    protected PrintWriter redacteur;

    /**
     * Initialise une nouvelle instance de la classe {@link UrlPostMultipartRequestPost}.
     * @param host     Le nom de l'hôte ou null pour une adresse local.
     * @param resource La ressource à récupérer sur le serveur.
     * @throws IOException Si une E/S est lancée lors de la création du socket.
     */
    public UrlPostMultipartRequestPost(String host, String resource) throws IOException {
        super(host, resource);
    }

    /**
     * Envoie un fichier.
     * @param nomParametre Le nom du paramètre fichier.
     * @param nomFichier Le nom du fichier.
     * @param contenu Le contenu du fichier.
     * @throws IOException Si une E/S est lancée lors de l'écriture sur la sortie.
     */
    protected void envoyerFichier(String nomParametre, String nomFichier, byte[] contenu) throws IOException {
        System.out.println("=======================================================================");
        System.out.println("NOM PARAMETRE " + nomParametre +"\nNOM FICHIER " + nomFichier);
        System.out.println("TYPE " + URLConnection.guessContentTypeFromName(nomFichier));
        System.out.println("=======================================================================");

        this.redacteur.append(String.format("--%s%s", LIMITE, RETOUR));
        this.redacteur.append(String.format("Content-Disposition: form-data; name=\"%s\"; filename=\"%s\"%s",
                nomParametre, nomFichier, RETOUR));
        this.redacteur.append(String.format("Content-type: %s%s",  URLConnection.guessContentTypeFromName(nomFichier), RETOUR));
        this.redacteur.append(String.format("Content-Transfer-Encoding: binary%s", RETOUR));
        this.redacteur.append(RETOUR);
        this.redacteur.flush();

        this.sortie.write(contenu);
        this.sortie.flush();

        this.redacteur.append(RETOUR);
        this.redacteur.flush();
    }

    /**
     * Envoie un paramètre au format JSon.
     * @param nomParametre Le nom du parametre dans la requête POST Multipart
     * @param valeur La valeur du paramètre à envoyer.
     */
    protected void envoyerChampJson(String nomParametre, JSONObject valeur) {
        this.redacteur.append(String.format("--%s%s", LIMITE, RETOUR));
        this.redacteur.append((String.format("Content-Disposition: form-data; name=\"%s\"%s", nomParametre, RETOUR)));
        this.redacteur.append((String.format("Content-Type: application/json%s", RETOUR)));
        this.redacteur.append(RETOUR);
        this.redacteur.append(valeur.toString());
        this.redacteur.append(RETOUR);
        this.redacteur.flush();
    }

    /**
     * Modifie les en-têtes de la requête.
     */
    @Override
    protected void setHeaders() {
        this.addHeader("Content-Type", String.format("multipart/form-data;boundary=\"%s\"", LIMITE) );
        this.addHeader("Connection", "Keep-Alive");
        this.addHeader("Cache-Control", "no-cache");
    }

    /**
     * Envoie la requête.
     * @throws IOException Si une exception d'E/S est lancée.
     */
    @Override
    protected void send() throws IOException {

        this.connection.setDoInput(true);
        this.connection.setDoOutput(true);
        this.connection.setUseCaches(false);

        this.sortie = this.connection.getOutputStream();
        this.redacteur = new PrintWriter(new OutputStreamWriter(this.sortie, StandardCharsets.UTF_8), true);

        this.ecrireLeCorps();

        this.redacteur.close();
    }

    /**
     * Récupère le corps de la requête.
     * @return String Récupère la valeur du corps de la requête.
     */
    @Override
    protected String getBody() {
        return null;
    }

    /**
     * Écrit le corps de la requête.
     * @throws IOException Si une erreur E/S est lancée.
     */
    protected abstract void ecrireLeCorps() throws IOException;

}

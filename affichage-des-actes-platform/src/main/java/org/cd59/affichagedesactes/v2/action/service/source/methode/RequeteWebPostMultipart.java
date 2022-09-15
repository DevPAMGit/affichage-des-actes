package org.cd59.affichagedesactes.v2.action.service.source.methode;

import java.io.ByteArrayOutputStream;

import org.cd59.affichagedesactes.v2.action.service.source.source.RequeteWeb;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Requête web utilisant la méthode POST Multipart.
 */
public abstract class RequeteWebPostMultipart extends RequeteWebPost {

    /**
     * Le jeton de frontière.
     */
    private final static String FRONTIERE = "----FrontièreRequete";

    /**
     * Initialise une nouvelle instance de la classe {@link RequeteWeb}.
     * @param hote    L'hôte sur lequel se connecter.
     * @throws IOException Si une erreur d'entré/sortie est lancée à l'ouverture du socket.
     */
    public RequeteWebPostMultipart(String hote) throws IOException {
        super(hote);
    }

    /**
     * @param nom Le nom de la donnée.
     * @param value La valeur de la donnée.
     * @return L'argument en tableau de octets.
     * @throws IOException */
    protected byte[] obtenirArgument(String nom, byte[] value) throws IOException {
        ByteArrayOutputStream flux = new ByteArrayOutputStream();
        flux.write(String.format("%s\r\n", FRONTIERE).getBytes());
        flux.write(("Content-Disposition: form-data; name=\""+nom+"\"\r\n").getBytes());
        flux.write("Content-Type: application/octet-stream\r\n".getBytes());
        flux.write(value);
        flux.write("\r\n".getBytes());
        return flux.toByteArray();
    }

    @Override
    protected void envoyer() throws IOException {
        byte[] corps = this.obtenirCorpsEnOctet();
        OutputStream sortie = null;
        if(corps != null && !(corps.length > 0)) {
            this.connexion.setDoOutput(true);
            sortie = this.connexion.getOutputStream();
            sortie.write(corps);
        }
        sortie.flush();
        sortie.close();
    }

    @Override
    protected void modifierEntetes() {
        this.ajouterEntete("Content-Type:", String.format("multipart/form-data; boundary=%s", FRONTIERE));
    }

    @Override
    protected String obtenirCorps() {
        return null;
    }

    /**
     * Permet de récupérer le corps de la requete au format octet.
     * @return Le corps de la requete au format octet.
     * @throws IOException
     */
    protected abstract byte[] obtenirCorpsEnOctet() throws IOException;
}

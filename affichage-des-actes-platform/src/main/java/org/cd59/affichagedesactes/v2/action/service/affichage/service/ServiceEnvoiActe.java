package org.cd59.affichagedesactes.v2.action.service.affichage.service;

import org.cd59.affichagedesactes.v2.action.service.source.methode.RequeteWebPostMultipartBearer;

import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class ServiceEnvoiActe extends RequeteWebPostMultipartBearer {

    /**
     * Le contenu du fichier.
     */


    /**
     * Initialise une nouvelle instance de la classe {@link RequeteWebPostMultipartBearer}.
     *
     * @param hote  L'hôte sur lequel se connecter.
     * @param jeton Le jeton d'authentification.
     * @throws IOException Si une erreur d'entré/sortie est lancée à l'ouverture du socket.
     */
    public ServiceEnvoiActe(String hote, String jeton) throws IOException {
        super(hote, jeton);
    }



    @Override
    protected byte[] obtenirCorpsEnOctet() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        /*stream.write( this.obtenirArgument("file", this.act) );
        stream.write( this.getArgument("annexe", this.annexe) );
        stream.write( this.getArgument("metadonnees", ) );*/
        return stream.toByteArray();
    }
}

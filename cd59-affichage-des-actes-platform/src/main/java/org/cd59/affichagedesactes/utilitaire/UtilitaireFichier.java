package org.cd59.affichagedesactes.utilitaire;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Méthode permettant de manipuler des fichiers.
 */
public class UtilitaireFichier {
    /**
     * Méthode permettant de récupérer l'empreinte d'un fichier.
     * @param algorithme L'algorithme de l'emprunte.
     * @param contenu Le contenu du fichier.
     * @eturn L'empreinte du fichier.
     */
    public static String getEmpreinte(String algorithme, byte[] contenu) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithme);
        return Base64.getEncoder().encodeToString(digest.digest(contenu));
    }
}

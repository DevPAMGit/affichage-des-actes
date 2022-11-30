package org.cd59.affichagedesactes.utilitaire;

/**
 * Classe utilitaire pour les chaînes de caractères.
 */
public class UtilitaireChaineDeCaracteres {
    /**
     * Convertit un entier en chaîne de caractère et ajoute des zéros avant le nombre si besoin est.
     * @param entier L'entier à convertir.
     * @param taille La taille de la chaîne de caractères demandée.
     * @return Une chaine de caractères de la taille demandée.
     */
    public static String entierSurNChiffres(int entier, int taille) {
        StringBuilder resultat = new StringBuilder();

        // Récupération de l'entier au format chaine.
        String entierEnChaine = Integer.toString(entier);
        // Calcule de nombre de zéros nécessaires et initialisation de l'index.
        int max = taille - entierEnChaine.length(), index = 0;

        // Initialisation du résultat avec les zéros nécessaires.
        while(index < max) {
            resultat.append("0");
            index++;
        }

        // Ajout de l'entier.
        resultat.append(entierEnChaine);
        // Retour du résultat.
        return resultat.toString();
    }

    /**
     * Vérifie si la chaîne de caractères est null ou vide.
     * @param chaine La chaîne à tester.
     * @return <c>true</c> si la chaîne est vide ou null.
     */
    public static boolean etreNullOuVide(String chaine) {
        return (chaine == null || chaine.trim().isEmpty());
    }
}

package org.cd59.affichagedesactes.action.custom.source.v1.action;

import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.action.custom.source.exception.prerequis.PreRequisException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Interface contractuelle pour les modèles
 */
public interface IModeleNoeudAction {

    /**
     * Modifie le type d'un nœud.
     * @param nodeRef                   Le nœud dont il faut modifier le type.
     * @param qName                     Le nouveau type de la propriété.
     * @throws PreRequisException       Si le nœud ou la propriété en paramètre est null ou vide.
     * @throws NoSuchMethodException    Levée lorsqu'une méthode particulière est introuvable.
     */
    void setType(NodeRef nodeRef, QName qName) throws PreRequisException, NoSuchMethodException;

    /**
     * Modifie la valeur d'une propriété d'un nœud.
     * @param nodeRef                Le nœud dont il faut modifier la propriété
     * @param propriete              La propriété a modifié.
     * @param valeur                 La nouvelle valeur de la propriété.
     * @throws PreRequisException    Si le nœud ou la propriété en paramètre est null ou vide.
     * @throws NoSuchMethodException Levée lorsqu'une méthode particulière est introuvable.
     */
    void setPropriete(NodeRef nodeRef, QName propriete, Serializable valeur)
            throws PreRequisException, NoSuchMethodException;

    /**
     * Modifie les valeurs des propriétés d'un nœud.
     * @param nodeRef                   Le nœud dont il faut modifier la propriété
     * @param valeurs                   Les nouvelles valeurs des propriétés
     * @throws PreRequisException       Si le nœud est vide.
     * @throws NoSuchMethodException    Levée lorsqu'une méthode particulière est introuvable.
     */
    void modifierProprietes(NodeRef nodeRef, HashMap<QName, Serializable> valeurs)
            throws PreRequisException, NoSuchMethodException;

    /**
     * Crée un dossier dans le nœud en paramètre.
     * @param nodeRef                Le nœud dans lequel créer le nœud.
     * @param nom                    Le nom du nouveau à créer.
     * @return                       Une nouvelle instance {@link NodeRef} représentant le nouveau nœud créé.
     * @throws PreRequisException    PreRequisException Si le nœud ou le nom du dossier sont null.
     * @throws NoSuchMethodException Levée lorsqu'une méthode particulière est introuvable.
     */
    NodeRef creerDossier(NodeRef nodeRef, String nom) throws PreRequisException, NoSuchMethodException;

    /**
     * Crée un dossier et lui attribue un type.
     * @param nodeRef                Le nœud de destination.
     * @param qName                  Le type du nouveau dossier.
     * @param nom                    Le nom du nouveau dossier.
     * @return                       Le {@link NodeRef} du nouveau dossier.
     * @throws PreRequisException    Si l'un des paramètres est null ou vide.
     * @throws NoSuchMethodException Levée lorsqu'une méthode particulière est introuvable.
     */
    NodeRef creerDossierType(NodeRef nodeRef, QName qName, String nom) throws PreRequisException, NoSuchMethodException;

    /**
     * Crée un dossier, le type et initialise les propriétés.
     * @param nodeRef                Le nœud de destination.
     * @param qName                  Le type du nouveau dossier.
     * @param nom                    Le nom du nouveau dossier.
     * @param metadonnees            Les propriétés du nouveau dossier.
     * @return                       Le {@link NodeRef} du nouveau dossier.
     * @throws PreRequisException    Si l'un des paramètres est null ou vide.
     * @throws NoSuchMethodException Levée lorsqu'une méthode particulière est introuvable.
     */
     NodeRef creerDossierType(NodeRef nodeRef, QName qName, String nom, HashMap<QName, Serializable> metadonnees)
             throws PreRequisException, NoSuchMethodException;

    /**
     * Indique si le nœud à un aspect.
     * @param aspect                L'aspect à vérifier.
     * @throws PreRequisException   Si l'aspect est null.
     */
    boolean avoirAspect(NodeRef nodeRef, QName aspect) throws PreRequisException;

    /**
     * Indique si le nœud à un type donné.
     * @param nodeRef               Le nœud à tester.
     * @param qName                 Le type à vérifier.
     * @return                      <c>true</c> si le nœud est typé du même type que celui en paramètre. Sinon <c>false</c>.
     * @throws PreRequisException   Si la propriété ou le nœud sont null ou vides.
     */
    boolean avoirType(NodeRef nodeRef, QName qName) throws PreRequisException;

    /**
     * Méthode permettant de récupérer la valeur d'une propriété en chaîne de caractères.
     * @param qName                 La propriété dont il faut récupérer la valeur.
     * @return                      La valeur de la propriété.
     * @throws PreRequisException   Si le nœud ou la propriété est null.
     */
    String getProprieteChaine(NodeRef nodeRef, QName qName) throws PreRequisException;

    /**
     * Méthode permettant de récupérer la valeur d'une propriété en entier.
     * @param nodeRef               Le nœud pour dont on souhaite récupérer la valeur.
     * @param qName                 La propriété dont il faut récupérer la valeur.
     * @return                      La valeur de la propriété.
     * @throws PreRequisException   Si le nœud ou la propriété sont null.
     */
    int getProprieteEntier(NodeRef nodeRef, QName qName) throws PreRequisException;

    /**
     * Méthode permettant de récupérer la valeur d'une propriété au format {@link Date}.
     * @param nodeRef Le nœud pour dont on souhaite récupérer la valeur.
     * @param qName La propriété dont il faut récupérer la valeur.
     * @return La valeur de la propriété au format {@link Date}.
     * @throws PreRequisException Si le nœud ou la propriété est null.
     */
    Date getProprieteDate(NodeRef nodeRef, QName qName) throws PreRequisException;

    /**
     * Récupère la valeur d'une propriété d'un nœud en booléen.
     * @param nodeRef Le nœud pour dont on souhaite récupérer la valeur.
     * @param qName La propriété que l'on souhaite récupérer.
     * @return La valeur de la propriété ou null.
     * @throws PreRequisException Si la propriété ou le nœud sont null.
     */
    boolean obtenirProprieteEnBooleen(NodeRef nodeRef, QName qName) throws PreRequisException;

    /**
     * Récupère le nœud parent de celui en paramètre.
     * @param nodeRef Le nœud dont on souhaite récupérer le parent.
     * @throws PreRequisException Si le nœud en paramètre est null.
     */
    NodeRef getNoeudParent(NodeRef nodeRef) throws PreRequisException;

    /**
     * Récupère le contenu d'un fichier.
     * @param nodeRef Le nœud dont on souhaite récupérer le contenu.
     * @return Le contenu du fichier, sinon null.
     * @throws IOException Si une erreur d'entrée/sortie a eu lieu.
     */
    byte[] getContenu(NodeRef nodeRef) throws IOException;

    /**
     * Créer un fichier dans un autre nœud.
     * @param nodeRef Le nœud dans lequel crée le fichier.
     * @param nom Le nom du fichier.
     * @return Le nœud du fichier.
     * @throws PreRequisException Si le nœud en paramètre est null.
     * @throws NoSuchMethodException Levée lorsqu'une méthode particulière est introuvable.
     */
    NodeRef creerFichier(NodeRef nodeRef, String nom) throws PreRequisException, NoSuchMethodException;

    /**
     * Créer un fichier dans un autre nœud avec un type.
     * @param nodeRef Le nœud dans lequel crée le fichier.
     * @param nom Le nom du fichier.
     * @return Le nœud du fichier.
     * @throws PreRequisException Si le nœud en paramètre est null.
     * @throws NoSuchMethodException Levée lorsqu'une méthode particulière est introuvable.
     */
    NodeRef creerFichierType(NodeRef nodeRef, QName qName, String nom) throws PreRequisException, NoSuchMethodException;

    /**
     * Supprime le nœud en référence.
     * @throws PreRequisException       Si le nœud en paramètre est null.
     * @throws NoSuchMethodException    Levée lorsqu'une méthode particulière est introuvable.
     */
    void supprimerNoeud(NodeRef nodeRef) throws NoSuchMethodException, PreRequisException;

    /**
     * Recherche des nœuds à l'aide d'une requête.
     * @param nodeRef Le nœud dans lequel on lance la recherche.
     * @param requete La requête.
     * @return Une liste de nœuds répondant aux critères de la recherche.
     * @throws PreRequisException Si le nœud ou la requête sont null.
     */
    List<NodeRef> requeterNoeuds(NodeRef nodeRef, String requete) throws PreRequisException;

    void deplacer(NodeRef destination, NodeRef nodeRef)
            throws FileNotFoundException, PreRequisException, NoSuchMethodException;
}

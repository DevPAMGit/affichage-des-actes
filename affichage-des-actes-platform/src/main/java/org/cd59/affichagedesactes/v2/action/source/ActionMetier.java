package org.cd59.affichagedesactes.v2.action.source;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.cd59.affichagedesactes.v2.action.source.exception.ActionMetierException;
import org.cd59.affichagedesactes.v2.action.source.exception.NoeudNullException;
import org.cd59.affichagedesactes.v2.action.source.exception.NomNoeudVideException;
import org.cd59.affichagedesactes.v2.action.source.exception.QNameNullException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * Source pour toutes les actions métiers
 */
public abstract class ActionMetier {

    /**
     * Le registre de service d'Alfresco.
     */
    public final ServiceRegistry registryService;

    /**
     * Initialise une nouvelle instance de la classe {@link ActionMetier}.
     * @param serviceRegistry Le registre de service Alfresco.
     */
    public ActionMetier(ServiceRegistry serviceRegistry) {
        this.registryService = serviceRegistry;
    }

    /**
     * Execute l'action métier.
     */
    public abstract void executer();

    /**
     * Permet de récupérer le nom du fichier.
     * @param nodeRef Le nœud dont on souhaite récupérer le nom.
     * @throws NomNoeudVideException Si le nom du nœud est vide ou null.
     */
    protected String obtenirNomNoeud(NodeRef nodeRef) throws NomNoeudVideException {
        NodeService nodeService = this.registryService.getNodeService();
        // Récupération de la propriété.
        Serializable valeur =  nodeService.getProperty(nodeRef, ContentModel.PROP_NAME);
        // Vérification que le nom du nœud est valide.
        if(valeur == null || ((String) valeur).isEmpty())
            throw new NomNoeudVideException("Le nom du noeud est null ou vide.");
        // Retour du résultat.
        return String.format("%s.%s", valeur, ".pdf");
    }

    /**
     * Permet de récupérer le contenu d'un nœud de type fichier.
     * @param nodeRef Le nœud de type fichier.
     * @return Le contenu du fichier.
     * @throws IOException Si une erreur d'E/S est lancée durant la lecture du fichier.
     * @throws NoeudNullException Si le nœud est null ou vide.
     */
    protected byte[] obtenirContenuFichier(NodeRef nodeRef) throws IOException, NoeudNullException {
        // Vérification du nœud.
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null.");

        ByteArrayOutputStream resultat = new ByteArrayOutputStream();
        // Récupération du contenu.
        ContentReader contentReader = this.registryService.getContentService().getReader(nodeRef,
                ContentModel.PROP_CONTENT
        );

        InputStream lecteur = contentReader.getContentInputStream();
        byte[] buffer = new byte[1024];
        int taille;

        while((taille = lecteur.read(buffer) ) != -1)
            resultat.write(buffer, 0, taille);

        return resultat.toByteArray();
    }

    /**
     * Récupère la valeur d'une propriété d'un nœud en chaîne de caractères.
     * @param nodeRef Le nœud dont on souhaite récupérer la valeur de la propriété.
     * @param propriete La propriété que l'on souhaite récupérer.
     * @return La valeur de la propriété ou null.
     */
    protected String obtenirValeurProprieteEnChaine(NodeRef nodeRef, QName propriete) throws ActionMetierException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null ou vide.");
        if(propriete == null) throw new QNameNullException("La propriété en paramètre ne peut être null ou vide.");

        // Récupération de la valeur.
        Serializable serializable = this.registryService.getNodeService().getProperty(nodeRef, propriete);
        if(serializable == null) return null;
        return (String) serializable;
    }

    /**
     * Récupère la valeur d'une propriété d'un nœud en chaîne de caractères.
     * @param nodeRef Le nœud dont on souhaite récupérer la valeur de la propriété.
     * @param propriete La propriété que l'on souhaite récupérer.
     * @return La valeur de la propriété ou null.
     */
    protected Date obtenirValeurProprieteEnDate(NodeRef nodeRef, QName propriete) throws ActionMetierException {
        // Vérification des préconditions.
        if(nodeRef == null) throw new NoeudNullException("Le noeud en paramètre ne peut être null ou vide.");
        if(propriete == null) throw new QNameNullException("La propriété en paramètre ne peut être null ou vide.");

        // Récupération de la valeur.
        Serializable serializable = this.registryService.getNodeService().getProperty(nodeRef, propriete);
        if(serializable == null) return null;
        return (Date) serializable;
    }


}

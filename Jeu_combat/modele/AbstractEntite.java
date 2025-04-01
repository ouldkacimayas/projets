package modele;
import observer.AbstractListenableModel;

/**
 * Classe abstraite représentant une entité dans le modèle.
 * Elle implémente l'interface  Entite et étend la classe AbstractListenableModel,
 * permettant de notifier les observateurs des changements d'état.
 *
 * Cette classe sert de base pour définir des entités ayant un nom et des coordonnées dans un plateau.
 */
public abstract class AbstractEntite extends AbstractListenableModel implements Entite{
    /**
     * Le nom de l'entité.
     */
    protected String nom;

    /**
     * La position X et Y de l'entité sur le plateau.
     */
    protected int x,y;

    /**
     * Constructeur de la classe AbstractEntite.
     *
     * @param nom le nom de l'entité.
     * @param x   la position X initiale de l'entité.
     * @param y   la position Y initiale de l'entité.
     */
    public AbstractEntite(String nom, int x, int y){
        this.nom = nom;
        this.x = x;
        this.y = y;
    }

    /**
     * Retourne le nom de l'entité.
     *
     * @return le nom de l'entité.
     */
    public String getNom(){
        return this.nom;
    }

    /**
     * Retourne la position X actuelle de l'entité.
     *
     * @return la position X de l'entité.
     */
    public int getX(){
        return this.x;
    }

     /**
     * Retourne la position Y actuelle de l'entité.
     *
     * @return la position Y de l'entité.
     */
    public int getY(){
        return this.y;
    }

    /**
     * Définit une nouvelle position X pour l'entité.
     *
     * @param nouvX la nouvelle position X de l'entité.
     */
    public void setX(int nouvX){
        this.x = nouvX;
    }

    /**
     * Définit une nouvelle position Y pour l'entité.
     *
     * @param nouvY la nouvelle position Y de l'entité.
     */
    public void setY(int nouvY){
        this.y = nouvY;
    }

}
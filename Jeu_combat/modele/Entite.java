package modele;

/**
 * Interface Entite représentant une entité dans le jeu.
 * Toutes les entités sur le plateau, comme les combattants, les armes et autre doivent implémenter cette interface afin de fournir des informations communes 
 * telles que le nom, la position sur le plateau, et la possibilité de modifier leur position.
 */
public interface Entite {

    /**
     * Retourne le nom de l'entité.
     * 
     * @return le nom de l'entité.
     */
    public String getNom();

    /**
     * Retourne la position X de l'entité sur le plateau.
     * 
     * @return la coordonnée X de l'entité.
     */
    public int getX();

    /**
     * Retourne la position Y de l'entité sur le plateau.
     * 
     * @return la coordonnée Y de l'entité.
     */
    public int getY();

    /**
     * Définit la nouvelle position X de l'entité sur le plateau.
     * 
     * @param x la nouvelle coordonnée X de l'entité.
     */
    public void setX(int x);

    /**
     * Définit la nouvelle position Y de l'entité sur le plateau.
     * 
     * @param y la nouvelle coordonnée Y de l'entité.
     */
    public void setY(int y);
}

package modele;

/**
 * Représente une case vide dans le jeu.
 * Une case vide est une entité qui ne contient aucun objet ou obstacle.
 * Elle sert à créer des espaces où les combattants peuvent se déplacer ou où d'autres entités peuvent être placées.
 * 
 * La classe étend AbstractEntite, hérite de ses propriétés de position et de nom.
 */
public class Vide extends AbstractEntite{
    /**
     * Constructeur de la classe Vide.
     * Initialise une case vide avec des coordonnées spécifiées.
     * 
     * @param x La coordonnée x de la case vide.
     * @param y La coordonnée y de la case vide.
     */
    public Vide(int x, int y){
        super("Vide",x,y);
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractère de la case vide.
     * Le symbole utilisé pour une case vide est le point ".".
     * 
     * @return Une chaîne de caractère représentant la case vide ("." pour une case vide).
     */
    @Override
    public String toString() {
        return "."; // Représentation d'une case vide par un point
    }
}
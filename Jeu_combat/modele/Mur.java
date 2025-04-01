package modele;

/**
 * Classe Mur représentant un mur sur le plateau du jeu.
 * Les murs sont des entités qui bloquent les déplacements des combattants et peuvent être utilisés pour créer des obstacles stratégiques.
 * Hérite de la classe AbstractEntite.
 */
public class Mur extends AbstractEntite{
    /**
     * Constructeur de la classe Mur.
     * Initialise un mur avec une position donnée sur le plateau.
     * 
     * @param x la position X du mur sur le plateau.
     * @param y la position Y du mur sur le plateau.
     */
    public Mur(int x, int y){
        super("Mur",x,y);
    }

    /**
     * Retourne une représentation sous forme de chaîne du mur.
     * Cette méthode est utilisée pour afficher un symbole pour le mur sur le plateau.
     * 
     * @return une chaîne représentant le mur, ici "#".
     */
    @Override
    public String toString() {
        return "#"; 
    }
}
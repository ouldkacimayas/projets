package modele;
import java.util.Random;

/**
 * Interface qui définit une méthode pour remplir une grille de jeu avec des entités aléatoires.
 * Cette interface permet d'implémenter des stratégies pour générer le contenu du plateau de jeu
 * en fonction des dimensions du plateau et d'un générateur de nombres aléatoires.
 */
public interface RemplirGrille{
    /**
     * Remplit la grille de jeu avec des entités aléatoires en fonction de la largeur,
     * de la hauteur et d'un générateur de nombres aléatoires.
     * 
     * @param largeur La largeur de la grille à remplir.
     * @param hauteur La hauteur de la grille à remplir.
     * @param random Un objet Random utilisé pour générer des placements aléatoires.
     * @return Une matrice 2D d'entités qui représente la grille remplie.
     */
    public Entite[][] remplirGrille(int largeur, int hauteur, Random random);
}
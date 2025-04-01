package modele;
import java.util.Random;

/**
 * Implémentation de l'interface RemplirGrille pour remplir une grille de jeu
 * avec des entités de manière équilibrée et contrôlée.
 * 
 * Contrairement à un remplissage totalement aléatoire, cette classe distribue les entités 
 * de manière à assurer un certain équilibre dans la grille en fonction de probabilités.
 * Les entités placées sont des cases vides, des murs, des énergies, des bombes, des mines, des boucliers et des munitions.
 */
public class RemplissageEquillibre implements RemplirGrille{
    /**
     * Remplit la grille de jeu avec des entités de manière équilibrée en fonction des valeurs aléatoires générées. Cette méthode place des entités comme des énergies, des murs, des bombes, des mines, des boucliers, et des munitions de façon à créer un plateau de jeu intéressant avec une distribution plus contrôlée.
     * 
     * @param largeur La largeur de la grille à remplir.
     * @param hauteur La hauteur de la grille à remplir.
     * @param random Un générateur de nombres aléatoires utilisé pour générer des entités.
     * @return Une matrice 2D d'entités représentant la grille remplie de manière équilibrée.
     */
    public Entite[][] remplirGrille(int largeur, int hauteur, Random random){
        Entite[][] grille = new Entite[largeur][hauteur];
        int nombreAleatoir = 0;
        Entite entite;

        // Remplissage de la grille
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                // Génération d'un nombre aléatoire pour déterminer l'entité à placer
                nombreAleatoir = random.nextInt(60);
                if(nombreAleatoir<45){
                    // Placer une case vide
                    entite = new Vide(i, j);
                }
                else if(nombreAleatoir<50){
                    // Placer des objets comme des bombes, des mines, des boucliers, des munitions, ou des énergies
                    if(nombreAleatoir==45){
                        entite = new Bombe(10,2,2,i,j);
                    }
                    else if(nombreAleatoir==46){
                        entite = new Mine(15,2,i,j);
                    }
                    else if(nombreAleatoir==47){
                        entite = new Bouclier(1,i,j);
                    }else if(nombreAleatoir==48){
                        entite = new Munitions(2,i,j);
                    }else{
                        entite = new Energie(i,j,2);
                    }
                }else{
                    // Placer des murs
                    entite = new Mur(i,j);
                }
                // Affecter l'entité générée à la case de la grill
                grille[i][j] = entite;
            }
        }
        return grille;
    }
}
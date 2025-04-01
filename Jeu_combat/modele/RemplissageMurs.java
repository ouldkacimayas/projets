package modele;
import java.util.Random;

/**
 * Implémentation de l'interface RemplirGrill} pour remplir une grille de jeu 
 * avec des entités tout en plaçant une quantité significative de murs.
 * 
 * Cette classe génère une grille où la majorité des cases sont occupées par des murs,
 * mais elle place également des cases vides, des objets comme des bombes, des mines,
 * des boucliers, des munitions et des énergies.
 */
public class RemplissageMurs implements RemplirGrille{

    /**
     * Remplit la grille de jeu avec des entités tout en garantissant une forte présence de murs. Cette méthode utilise un générateur de nombres aléatoires pour décider du type d'entité à placer à chaque position sur la grille.
     * Les entités placées peuvent être des murs, des cases vides, des bombes, des mines, des boucliers, des munitions, ou des énergies, avec une forte priorité pour les murs.
     * 
     * @param largeur La largeur de la grille à remplir.
     * @param hauteur La hauteur de la grille à remplir.
     * @param random Un générateur de nombres aléatoires utilisé pour générer des entités.
     * @return Une matrice 2D d'entités représentant la grille remplie avec une forte
     *         présence de murs.
     */
    public Entite[][] remplirGrille(int largeur, int hauteur, Random random){
        Entite[][] grille = new Entite[largeur][hauteur];
        int nombreAleatoir = 0;
        Entite entite;

        // Remplissage de la grille
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                // Génération d'un nombre aléatoire pour déterminer l'entité à placer
                nombreAleatoir = random.nextInt(70);
                if(nombreAleatoir<45){
                    // Placer des objets comme des bombes, des mines, des boucliers, des munitions, ou des énergies
                    entite = new Vide(i, j);
                }
                else if(nombreAleatoir<50){
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
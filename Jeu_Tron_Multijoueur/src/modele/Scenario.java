package modele;
import java.util.*; 

/**
 * Classe représentant un scénario de jeu, avec une grille, une liste de joueurs et le nombre de répétitions.
 */
public class Scenario {
    private int tailleGrille; 
    //private int profondeur; 
    private List<Joueur> joueurs; 
    private int repetitions; 
    // Constructeur sans argument (utilisé par Jackson)
    public Scenario() {}

    // Constructeur pour initialiser directement les valeurs
    public Scenario(int tailleGrille,List<Joueur> joueurs,int repetitions) {
        this.tailleGrille = tailleGrille;
        this.joueurs = joueurs; 
        this.repetitions = repetitions; 
    }

    public int getTailleGrille() {
        return tailleGrille; 
    }

    public void setTailleGrille(int tailleGrille) {
        this.tailleGrille = tailleGrille;
    }

    public List<Joueur> getJoueurs(){
        return joueurs; 
    } 

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs; 
    }
    // Getter pour repetitions
    public int getRepetitions() {
        return repetitions;
    }

    // Setter pour repetitions
    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    /**
     * Méthode toString() qui fournit une représentation sous forme de chaîne de caractères du scénario.
     * 
     */
    @Override
    public String toString() {
        return "Scenario{" +
                "tailleGrille=" + tailleGrille +
                ", joueurs=" + joueurs +
                ", repetitions=" + repetitions + // Ajout du champ repetitions dans le toString
                '}';
    }
}

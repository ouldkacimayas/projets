package modele;

import java.util.*;
/**
 * Classe implémentant l'algorithme Max-N pour déterminer le meilleur déplacement dans un jeu multi-joueurs.
 */
public class AlgoMaxN {

    private static Map<Joueur, Integer> joueurToIndex;
    /**
     * Méthode récursive de l'algorithme Max-N pour évaluer les déplacements possibles à chaque profondeur.
     *
     */
    private static int[] maxn(Grille grille, int profondeur, Joueur joueurActuel, int[] alpha, int[] beta) {
        if (profondeur <= 0 || grille.deplacementsPossibles(joueurActuel).isEmpty()) {
            int[] scores = grille.evaluer();
            return scores;
        }

        int nbJoueurs = grille.getJoueurs().size();
        int[] meilleurScore = new int[nbJoueurs];
        Arrays.fill(meilleurScore, Integer.MIN_VALUE);

        List<int[]> deplacementsPossibles = grille.deplacementsPossibles(joueurActuel);

        for (int[] deplacement : deplacementsPossibles) {
            int[] positionPrec = { joueurActuel.getX(), joueurActuel.getY() };
            grille.effectuerDeplacement(joueurActuel, deplacement);

            int indexActuel = joueurToIndex.get(joueurActuel);
            Joueur joueurSuivant = grille.getJoueurs().get((indexActuel + 1) % nbJoueurs);

            int[] scores = maxn(grille, profondeur - 1, joueurSuivant, alpha, beta);

            grille.annulerDeplacement(joueurActuel, positionPrec);

            if (scores[indexActuel] > meilleurScore[indexActuel]) {
                System.arraycopy(scores, 0, meilleurScore, 0, nbJoueurs);
            }

            
            if (scores[indexActuel] > alpha[indexActuel]) {
                alpha[indexActuel] = scores[indexActuel];
            }
            if (estCoupe(alpha, scores)) {
                break; 
            }
        }

        return meilleurScore;
    }

    /**
     * Méthode pour déterminer le meilleur déplacement pour un joueur, en utilisant l'algorithme Max-N.
     */
   public static int[] meilleurDeplacementMaxN(Grille grille, Joueur joueurActuel, int profondeur) {
    joueurToIndex = new HashMap<>();
    List<Joueur> joueurs = grille.getJoueurs();

    for (int i = 0; i < joueurs.size(); i++) {
        joueurToIndex.put(joueurs.get(i), i);
    }

    if (profondeur < 1) {
        profondeur = 1;
    }

    int meilleurScore = Integer.MIN_VALUE;
    int[] meilleurDeplacement = null;
    int nbJoueurs = joueurs.size();
    List<int[]> deplacements = grille.deplacementsPossibles(joueurActuel);

    int[] alpha = new int[nbJoueurs];
    Arrays.fill(alpha, Integer.MIN_VALUE);
    
    int[] beta = new int[nbJoueurs];
    Arrays.fill(beta, Integer.MAX_VALUE); 

    int[] positionPrec = { joueurActuel.getX(), joueurActuel.getY() };

    
    deplacements.sort((a, b) -> {
        grille.effectuerDeplacement(joueurActuel, a);
        int scoreA = grille.heuristiqueAvancee(joueurActuel);
        grille.annulerDeplacement(joueurActuel, a);

        grille.effectuerDeplacement(joueurActuel, b);
        int scoreB = grille.heuristiqueAvancee(joueurActuel);
        grille.annulerDeplacement(joueurActuel, b);

        return Integer.compare(scoreB, scoreA); 
    });

    for (int[] deplacement : deplacements) {
        grille.effectuerDeplacement(joueurActuel, deplacement);

        int indexActuel = joueurToIndex.get(joueurActuel);
        Joueur joueurSuivant = joueurs.get((indexActuel + 1) % nbJoueurs);

        int[] scores = maxn(grille, profondeur - 1, joueurSuivant, alpha, beta);

        grille.annulerDeplacement(joueurActuel, positionPrec);

        if (scores[indexActuel] > meilleurScore) {
            meilleurScore = scores[indexActuel];
            meilleurDeplacement = deplacement;
        }
    }

    if (meilleurDeplacement == null && deplacements.size() > 0) {
        meilleurDeplacement = deplacements.get(0);
    }

    return meilleurDeplacement;
}


    /**
     * Méthode pour vérifier si une coupe alpha-beta est possible.
     * 
     */
   private static boolean estCoupe(int[] alpha, int[] scores) {
    for (int i = 0; i < alpha.length; i++) {
        if (scores[i] > alpha[i]) {
            return false; 
        }
    }
    return true; 
}


}
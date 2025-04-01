package modele;

import java.util.*;
/**
 * Classe implémentant l'algorithme SOS pour déterminer le meilleur déplacement dans un jeu multi-joueurs.
 * Cet algorithme prend en compte les scores des équipes, et chaque joueur essaie de maximiser le score de son équipe.
 */
public class AlgoSOS {

    private static Map<Joueur, Integer> joueurToIndex;
    private static Map<Joueur, Integer> joueurToEquipe;
    /**
     * Méthode récursive de l'algorithme SOS pour évaluer les déplacements possibles à chaque profondeur.
     * 
     */
    private static int[] sos(Grille grille, int profondeur, Joueur joueurActuel) {
        if (profondeur <= 0 || grille.estTerminee() || grille.deplacementsPossibles(joueurActuel).isEmpty()) {
            return grille.evaluer(); 
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

            int[] scores = sos(grille, profondeur - 1, joueurSuivant);

            grille.annulerDeplacement(joueurActuel, positionPrec);

            
            int scoreEquipeActuel = 0;
            int meilleurScoreEquipe = 0;
            for (int i = 0; i < nbJoueurs; i++) {
                if (joueurToEquipe.get(grille.getJoueurs().get(i)).equals(joueurToEquipe.get(joueurActuel))) {
                    scoreEquipeActuel += scores[i]; 
                    meilleurScoreEquipe += meilleurScore[i];
                }
            }

            
            if (scoreEquipeActuel > meilleurScoreEquipe) {
                meilleurScore = scores;
            }
        }

        return meilleurScore;
    }
    
    /**
     * Méthode pour déterminer le meilleur déplacement pour un joueur, en utilisant l'algorithme SOS.
     * 
     */
    public static int[] meilleurDeplacementSOS(Grille grille, Joueur joueurActuel, int profondeur) {
        joueurToIndex = new HashMap<>();
        joueurToEquipe = new HashMap<>();
        List<Joueur> joueurs = grille.getJoueurs();

        for (int i = 0; i < joueurs.size(); i++) {
            joueurToIndex.put(joueurs.get(i), i);
            joueurToEquipe.put(joueurs.get(i), joueurs.get(i).getEquipe()); 
        }

        int meilleurScoreEquipe = Integer.MIN_VALUE;
        int[] meilleurDeplacement = null;
        int nbJoueurs = joueurs.size();
        List<int[]> deplacements = grille.deplacementsPossibles(joueurActuel);

        for (int[] deplacement : deplacements) {
            int[] positionPrec = { joueurActuel.getX(), joueurActuel.getY() };
            grille.effectuerDeplacement(joueurActuel, deplacement);

            int indexActuel = joueurToIndex.get(joueurActuel);
            Joueur joueurSuivant = joueurs.get((indexActuel + 1) % nbJoueurs);

            // Calcul des scores avec SOS
            int[] scores = sos(grille, profondeur - 1, joueurSuivant);

            grille.annulerDeplacement(joueurActuel, positionPrec);

            // Calcul du score de l'équipe après ce déplacement
            int scoreEquipeActuel = 0;
            for (int i = 0; i < nbJoueurs; i++) {
                if (joueurToEquipe.get(joueurs.get(i)).equals(joueurToEquipe.get(joueurActuel))) {
                    scoreEquipeActuel += scores[i];
                }
            }

            // Mise à jour du meilleur déplacement
            if (scoreEquipeActuel > meilleurScoreEquipe) {
                meilleurScoreEquipe = scoreEquipeActuel;
                meilleurDeplacement = deplacement;
            }
        }

        if (meilleurDeplacement == null && deplacements.size() > 0) {
            meilleurDeplacement = deplacements.get(0);
        }

        return meilleurDeplacement;
    }
}

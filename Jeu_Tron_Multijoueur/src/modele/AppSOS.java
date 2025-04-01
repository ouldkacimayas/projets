

package modele;
import java.util.Random; 
import java.io.IOException;
import java.util.*;

public class AppSOS {
    public static void main(String[] args) {
        
        if (args.length != 1) {
            System.out.println("Erreur d'utilisation : <scenario.json> attendu");
            return;
        }
        
        String filePath = args[0]; // Chemin vers le fichier JSON contenant le scénario
        List<Scenario> scenarios = null;
        Map<Integer, Integer> victoiresParProfondeur = new HashMap<>();
        

        try {
            scenarios = ScenarioLoader.loadScenarios(filePath); // Charger les scénarios depuis le fichier JSON
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des scénarios : " + e.getMessage());
            return;
        }

        // Parcourir chaque scenario 
        for (Scenario scenario : scenarios) {
            // Affichage du scénario en cours
            System.out.println("\n\n--- Début du scénario ---");
            System.out.println("Scénario : " + scenario);
            int tailleGrille = scenario.getTailleGrille();
            int repetitions = scenario.getRepetitions();
            List<Joueur> joueurs = new ArrayList<>(scenario.getJoueurs());

            
            
            for (int rep = 0; rep < repetitions; rep++) {
                System.out.println("\nRépetition " + (rep + 1) + "/" + repetitions);

                // Initialisation de la grille
                Grille grille = new Grille(tailleGrille);
                //grille.setHeuristique(2);
        
            for (Joueur joueur : joueurs) {
                
          Random random = new Random();
                      int x = random.nextInt(tailleGrille); 
                int y = random.nextInt(tailleGrille);
                joueur.setPosition(x, y);
                // Initialisation de la grille
                grille.ajouterJoueur(joueur);
                grille.ajouterJoueurDansEquipe(joueur.getEquipe(), joueur);
                
            }

                // Affichage de la grille initiale
                System.out.println("Grille initiale :");
                grille.afficher();

                // Associer chaque équipe à sa profondeur
                Map<Integer, Integer> profondeurParEquipe = new HashMap<>();
                for (Joueur joueur : joueurs) {
                    profondeurParEquipe.put(joueur.getEquipe(), joueur.getProfondeur());
                }

                
                while (!grille.estTerminee()) {
                    for (Joueur joueur : new ArrayList<>(grille.getJoueurs())) {
                        int profondeur = profondeurParEquipe.get(joueur.getEquipe()); 
                        System.out.println("\nJoueur " + joueur.getNum() + " (Équipe " + joueur.getEquipe() + ") joue avec profondeur " + profondeur + "...");

                        int[] meilleurDeplacement = AlgoSOS.meilleurDeplacementSOS(grille, joueur, profondeur);

                        if (meilleurDeplacement != null) {
                            grille.effectuerDeplacement(joueur, meilleurDeplacement);
                            System.out.println("Déplacement vers : (" + meilleurDeplacement[0] + "," + meilleurDeplacement[1] + ")");
                        } else {
                            System.out.println("Aucun déplacement possible ! Joueur " + joueur.getNum() + " est éliminé.");
                            grille.eliminerJoueur(joueur);
                        }

                        

                        if (grille.estTerminee()) {
                            break;
                        }
                    }
                }
                afficherResultat(joueurs, victoiresParProfondeur);

          
            }
           

        }
        ResultExporter2.exporterResultatsCSV(victoiresParProfondeur, "resultats.csv");
        System.out.println(victoiresParProfondeur);
    }


   public static void afficherResultat(List<Joueur> joueurs, Map<Integer, Integer> victoiresParProfondeur) {
    if (joueurs.size() > 1) {
        System.out.println("Game Over : égalité\n");
    } else if (joueurs.size() == 1) {
        int gagnant = joueurs.get(0).getNum();
        int profondeurGagnant = joueurs.get(0).getProfondeur();

        // On s'interesse uniquement aux resultats de Joueur 1 (car cest le sujet de notre etude )
        if (gagnant == 1) { 
            
            victoiresParProfondeur.put(profondeurGagnant, victoiresParProfondeur.getOrDefault(profondeurGagnant, 0) + 1);
            System.out.println("Game Over ! Victoire du joueur " + gagnant + " avec profondeur " + profondeurGagnant + "\n");
        } else {
            System.out.println("Game Over : " + "Joueur 2 a gagné\n");
        }
    } else {
        System.out.println("Game Over : aucun joueur gagnant\n");
    }
}
}

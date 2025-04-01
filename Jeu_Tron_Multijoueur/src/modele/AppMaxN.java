package modele;

import java.io.IOException;
import java.util.*;

public class AppMaxN {
    public static void main(String[] args) {
        // Charger les scénarios depuis le fichier JSON
        if (args.length != 1) {
            System.err.println("Erreur d'utilisation : <scenario.json> attendu");
            System.exit(1);
        }
        String filePath = args[0]; // Chemin vers le fichier JSON
        List<Scenario> scenarios = null;
        Map<Integer, Integer> victoiresParProfondeur = new HashMap<>();

        try {
            scenarios = ScenarioLoader.loadScenarios(filePath);
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des scénarios : " + e.getMessage());
            return;
        }

        // Parcourir chaque scénario et tester avec l'algorithme MaxN
        for (int i = 0; i < scenarios.size(); i++) {
            Scenario scenario = scenarios.get(i);

            // Affichage du scénario en cours
            System.out.println("\n\n--- Début du scénario " + (i + 1) + " ---");
            System.out.println("Scénario " + (i + 1) + " : " + scenario);

            // Récupérer le nombre de répétitions
            int repetitions = scenario.getRepetitions();

            // Répéter le scénario le nombre de fois défini
            for (int rep = 0; rep < repetitions; rep++) {
                System.out.println("\nRépetition " + (rep + 1) + "/" + repetitions);

                // Créer la grille avec les données du scénario
                int tailleGrille = scenario.getTailleGrille();
                List<Joueur> joueurs = new ArrayList<>(scenario.getJoueurs());
                // Initialisation de la grille
                Grille grille = new Grille(tailleGrille);
                grille.setHeuristique(2);
                for (Joueur joueur : joueurs) {
                    grille.ajouterJoueur(joueur);
                }

                int tour = 0;

                // Jouer la partie jusqu'à la fin
                while (!grille.estTerminee() && joueurs.size() > 1) {
                    List<Joueur> joueursElimines = new ArrayList<>();

                    for (int j = 0; j < joueurs.size(); j++) {
                        Joueur joueurActuel = joueurs.get(j);
                         System.out.println("\t tour du joueur " +joueurActuel.getNum()+"\n");

                        // Déterminer le meilleur déplacement avec la profondeur correspondante
                        int[] meilleurDeplacement = AlgoMaxN.meilleurDeplacementMaxN(grille, joueurActuel, joueurActuel.getProfondeur());
                        //int[] meilleurDeplacement = ParanoidAlgorithm.meilleurCoup(grille, joueurActuel, joueurActuel.getProfondeur());

                        // Exécution du meilleur déplacement ou élimination
                        if (meilleurDeplacement != null) {
                            grille.effectuerDeplacement(joueurActuel, meilleurDeplacement);
                        } else {
                            joueursElimines.add(joueurActuel);
                        }

                        // Si un seul joueur reste, arrêter immédiatement
                        if (joueurs.size() - joueursElimines.size() <= 1) break;
                    }

                    // Supprimer les joueurs éliminés après la boucle
                    joueurs.removeAll(joueursElimines);
                    tour++;
                }

                // Afficher le résultat final de chaque répétition et enregistrer les victoires
                afficherResultat(joueurs, victoiresParProfondeur);
            }

            System.out.println("\n\n--- Fin du scénario " + (i + 1) + " ---");
        }

        // Exporter les résultats dans un fichier CSV
        ResultExporter.exporterResultatsCSV(victoiresParProfondeur, "resultats.csv");
    }


   public static void afficherResultat(List<Joueur> joueurs, Map<Integer, Integer> victoiresParProfondeur) {
    if (joueurs.size() > 1) {
        System.out.println("Game Over : égalité\n");
    } else if (joueurs.size() == 1) {
        int gagnant = joueurs.get(0).getNum();
        int profondeurGagnant = joueurs.get(0).getProfondeur();

        // On s'intéresse uniquement aux résultats de Joueur 1
        if (gagnant == 1) { 
            // Ajouter ou incrémenter la victoire pour cette profondeur de Joueur 1
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

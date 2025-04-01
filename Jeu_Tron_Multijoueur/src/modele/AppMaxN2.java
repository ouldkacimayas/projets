package modele;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AppMaxN2 {
    public static void main(String[] args) {
        // Charger les scénarios depuis le fichier JSON
        if (args.length != 1) {
            System.err.println("Erreur d'utilisation : <scenario.json> attendu");
            System.exit(1);
        }
        String filePath = args[0]; // Chemin vers le fichier JSON
        List<Scenario> scenarios = null;
        Map<Integer, Integer> victoiresParTailleGrille = new HashMap<>();

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
                    Random random = new Random();
                    int x = random.nextInt(tailleGrille); 
                    int y = random.nextInt(tailleGrille);
                    joueur.setPosition(x, y);
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
                afficherResultat(joueurs, victoiresParTailleGrille, tailleGrille);
            }

            System.out.println("\n\n--- Fin du scénario " + (i + 1) + " ---");
        }

        // Exporter les résultats dans un fichier CSV directement ici
        exporterResultatsCSV(victoiresParTailleGrille, "resultats.csv");
    }
    // Méthode pour afficher le résultat d'une partie et enregistrer les victoires pour chaque taille de grille
    public static void afficherResultat(List<Joueur> joueurs, Map<Integer, Integer> victoiresParTailleGrille, int tailleGrille) {
        if (joueurs.size() > 1) {
            System.out.println("Game Over : égalité\n");
        } else if (joueurs.size() == 1) {
            int gagnant = joueurs.get(0).getNum();
            
            // On s'intéresse uniquement aux résultats de Joueur 1
            if (gagnant == 1) { 
                // Ajouter ou incrémenter la victoire pour cette taille de grille
                victoiresParTailleGrille.put(tailleGrille, victoiresParTailleGrille.getOrDefault(tailleGrille, 0) + 1);

                System.out.println("Game Over ! Victoire du joueur " + gagnant + " avec la taille de grille " + tailleGrille + "\n");
            } else {
                System.out.println("Game Over : Joueur 2 a gagné\n");
            }
        } else {
            System.out.println("Game Over : aucun joueur gagnant\n");
        }
    }

    // Méthode pour exporter les résultats en CSV
    public static void exporterResultatsCSV(Map<Integer, Integer> victoiresParTailleGrille, String nomFichier) {
        try (FileWriter writer = new FileWriter(nomFichier)) {
            // En-tête du fichier CSV
            writer.append("Taille de grille,Nombre de victoires,Taux de victoire (%)\n");

            // Écriture des données pour Joueur 1
            for (Map.Entry<Integer, Integer> entry : victoiresParTailleGrille.entrySet()) {
                int tailleGrille = entry.getKey();
                int nombreVictoires = entry.getValue();

                // Calcul du taux de victoire
                double tauxVictoires = (double) nombreVictoires / 100 * 100;

                // Écrire dans le fichier CSV
                writer.append(tailleGrille + "," + nombreVictoires + "," + tauxVictoires + "\n");
            }

            System.out.println("Les résultats ont été exportés dans : " + nomFichier);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'exportation des résultats : " + e.getMessage());
        }
    }
}

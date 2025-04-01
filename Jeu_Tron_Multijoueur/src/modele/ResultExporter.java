package modele;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ResultExporter {

    // Méthode pour exporter les résultats en CSV pour Joueur 1 uniquement
    public static void exporterResultatsCSV(Map<Integer, Integer> victoiresParProfondeur, String nomFichier) {
        try (FileWriter writer = new FileWriter(nomFichier)) {
            // En-tête du fichier CSV
            writer.append("Profondeur,Nombre de victoires,Taux de victoire (%)\n");

            // Écriture des données pour Joueur 1
            for (Map.Entry<Integer, Integer> entry : victoiresParProfondeur.entrySet()) {
                int profondeur = entry.getKey();
                int nombreVictoires = entry.getValue();

                // Calcul du taux de victoire
                double tauxVictoires = (double) nombreVictoires / 200*100;

                // Écrire dans le fichier CSV
                writer.append(profondeur + "," + nombreVictoires + "," + tauxVictoires + "\n");
            }

            System.out.println("Les résultats ont été exportés dans : " + nomFichier);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'exportation des résultats : " + e.getMessage());
        }
    }
}

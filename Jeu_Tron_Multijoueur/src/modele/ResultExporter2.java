package modele;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
class ResultExporter2 {
    public static void exporterResultatsCSV(Map<Integer, Integer> victoires, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Entête du fichier CSV
            writer.append("Profondeur-TailleEquipe,Nombre_Victoires\n");

            // Parcours de la Map et exportation des résultats
            for (Map.Entry<Integer, Integer> entry : victoires.entrySet()) {
                int profondeur = entry.getKey();
                int nombreVictoires = entry.getValue();
                writer.append(profondeur + "," + nombreVictoires + "\n");
            }

            System.out.println("Résultats exportés dans le fichier " + fileName);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'exportation des résultats : " + e.getMessage());
        }
    }
}
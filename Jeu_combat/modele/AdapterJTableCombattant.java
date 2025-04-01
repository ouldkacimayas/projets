package modele;

import javax.swing.table.AbstractTableModel;
import vue.*; 

/**
 * Classe d'adaptation permettant de connecter les données des combattants d'une partie graphique
 * à un modèle de table Swing JTable.
 *
 * Cette classe étend AbstractTableModel et fournit les informations nécessaires
 * pour afficher les combattants dans une table, avec des colonnes telles que le nom, l'énergie,
 * les munitions et les armes.
 */
public class AdapterJTableCombattant extends AbstractTableModel {
    /**
     * Référence à l'objet PartieGraphic contenant les données des combattants.
     */
    private PartieGraphic partie;

    /**
     * Constructeur de la classe AdapterJTableCombattant.
     *
     * @param partie la partie graphique.
     */
    public AdapterJTableCombattant(PartieGraphic partie) {
        this.partie = partie;
    }

    /**
     * Retourne le nombre de lignes dans la table, correspondant au nombre de combattants.
     *
     * @return le nombre total de combattants dans la partie.
     */
    @Override
    public int getRowCount() {
        return partie.getNombreCombattants(); // Retourne le nombre de combattants
    }

    /**
     * Retourne le nombre de colonnes dans la table.
     *
     * @return le nombre de colonnes (fixé à 4 : Nom, Énergie, Munitions, Armes).
     */
    @Override
    public int getColumnCount() {
        return 4; // Trois colonnes : Nom, Énergie, Munitions
    }

    /**
     * Retourne le nom de la colonne pour l'indice spécifié.
     *
     * @param columnIndex l'indice de la colonne (0 à 3).
     * @return le nom de la colonne correspondante.
     */
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "Nom de Combattant";
            case 1: return "Énergie Combattant";
            case 2: return "Munitions Combattant";
            case 3: return "Armes Combattant"; 
            default: return "";
        }
    }

    /**
     * Retourne la valeur à afficher dans une cellule donnée.
     *
     * @param rowIndex    l'indice de la ligne (combattant).
     * @param columnIndex l'indice de la colonne (propriété du combattant).
     * @return la valeur correspondante à la cellule demandée.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Combattant combattant = partie.getCombattant(rowIndex);
        switch (columnIndex) {
            case 0: return combattant.getNom();         // Nom du combattant
            case 1: return combattant.getEnergie();     // Énergie du combattant
            case 2: return combattant.getMunitions();   // Munitions du combattant
            case 3: return combattant.getArmes();       // Liste des armes du combattan
            default: return null;
        }
    }
}

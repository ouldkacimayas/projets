package modele;

import javax.swing.*;
import java.awt.*;

public class TronVue extends JPanel {
    private static final int CELL_SIZE = 40;  // Taille d'une cellule en pixels
    private Grille grille;
    private JLabel infoLabel;  // Pour afficher les informations du jeu

    public TronVue(Grille grille) {
        this.grille = grille;
        setPreferredSize(new Dimension(
            grille.getTaille() * CELL_SIZE,
            grille.getTaille() * CELL_SIZE
        ));
        infoLabel = new JLabel("Jeu en cours...");
        add(infoLabel);
    }

    public void setInfoText(String text) {
        infoLabel.setText(text);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Dessiner la grille
        g2d.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x <= grille.getTaille(); x++) {
            g2d.drawLine(x * CELL_SIZE, 0, x * CELL_SIZE, getHeight());
        }
        for (int y = 0; y <= grille.getTaille(); y++) {
            g2d.drawLine(0, y * CELL_SIZE, getWidth(), y * CELL_SIZE);
        }
        
        // Dessiner les entités
        Entite[][] grilleEntites = grille.getGrille();
       
        for (int x = 0; x < grille.getTaille(); x++) {
             for (int y = 0; y < grille.getTaille(); y++) {
                Entite entite = grilleEntites[x][y];
                if (entite != null) {
                    if (entite instanceof Joueur) {
                        Joueur joueur = (Joueur) entite;
                        if (joueur.numero == 1) {
                            g2d.setColor(Color.BLUE);
                        } 
                        if (joueur.numero == 2){
                             g2d.setColor(Color.RED);

                        }
                        if (joueur.numero == 3){
                             g2d.setColor(Color.GREEN);

                        }
                           
                        
                        g2d.fillOval(x * CELL_SIZE + 5, y * CELL_SIZE + 5, 
                                   CELL_SIZE - 10, CELL_SIZE - 10);
                    } else if (entite instanceof Mur) {
                        g2d.setColor(Color.BLACK);
                        g2d.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, 
                                   CELL_SIZE - 2, CELL_SIZE - 2);
                    }
                }
            }
        }
    }
}
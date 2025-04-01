package vue;

import modele.*;
import javax.swing.*;
import java.awt.*;
import observer.ModelListener;
import javax.swing.border.TitledBorder;
/**
 * Classe représentant la vue principale de l'application.
 * <p>
 * Cette classe affiche le plateau de jeu, les informations des combattants
 * et les actions possibles. Elle met également à jour l'affichage lorsque
 * le modèle change, en implémentant l'interface `ModelListener`.
 */
public class Vue extends JFrame implements ModelListener {

    private PlateauInter plateau;
    private JPanel panneauPlateau;
    private JButton[][] boutonsPlateau;
    private PartieGraphic partie;
    private JTable tableCombattants;
    private JPanel panneauActions;
    private JButton boutonDeplacer;
    private JButton boutonBombe;
    private JButton boutonMine;
    private JButton boutonBouclier;
    private JButton boutonPasserTour;
    private JButton boutonTirer;
    private boolean modeDeplacement = false; 
    /**
     * Constructeur de la classe Vue.
     * 
     * @param plateau Le plateau de jeu à afficher.
     * @param partie La partie en cours, pour gérer les informations des combattants.
     * @throws IllegalArgumentException si le plateau est null.
     */
    public Vue(PlateauInter plateau,PartieGraphic partie) {
        this.plateau = plateau; 
        this.partie = partie;
        setTitle("Jeu de Combat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

       
        if (plateau == null) {
            throw new IllegalArgumentException("Le plateau ne peut pas être null.");
        }

        // Initialiser le tableau des boutons avec les dimensions du plateau
        boutonsPlateau = new JButton[plateau.getHauteur()][plateau.getLargeur()];

        // Panneau principal avec une disposition verticale
        JPanel panneauPrincipal = new JPanel();
        panneauPrincipal.setLayout(new BorderLayout(10, 10));
        panneauPrincipal.setBackground(new Color(0, 80, 150)); 

        // Adapter pour afficher les differentes information des joueurs
     
        AdapterJTableCombattant adapterJTableCombattant = new AdapterJTableCombattant(partie);

        // Créer la JTable
        tableCombattants = new JTable(adapterJTableCombattant);
        tableCombattants.setRowHeight(30); 
        tableCombattants.setBackground(new Color(0, 80, 150));  
        tableCombattants.setForeground(Color.WHITE);  
        tableCombattants.setSelectionBackground(new Color(255, 223, 85)); 
        tableCombattants.setSelectionForeground(Color.BLACK); 
        tableCombattants.setBorder(BorderFactory.createLineBorder(new Color(255, 223, 85), 1)); 
        JScrollPane scrollPane = new JScrollPane(tableCombattants);
        scrollPane.setPreferredSize(new Dimension(800, 150));

        // Ajouter la JTable dans la partie supérieure
        panneauPrincipal.add(scrollPane, BorderLayout.NORTH);

        
        panneauPlateau = new JPanel();
        panneauPlateau.setLayout(new GridLayout(plateau.getHauteur(), plateau.getLargeur()));
        panneauPlateau.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); 
        panneauPlateau.setBackground(new Color(0, 80, 150)); 

        afficherPlateau();

        // Ajouter le plateau au panneau principal
        panneauPrincipal.add(panneauPlateau, BorderLayout.CENTER);
        // Panneau pour les actions
        panneauActions = creerPanneauActions();
        panneauPrincipal.add(panneauActions, BorderLayout.EAST);
        // Ajouter le panneau principal à la fenêtre
        add(panneauPrincipal);

        // Ajuster automatiquement la taille de la fenêtre
        pack();
        setResizable(false); 
        setVisible(true);
    }

        // Méthode pour créer le panneau d'actions
    private JPanel creerPanneauActions() {
        JPanel panneau = new JPanel();
        panneau.setLayout(new BoxLayout(panneau, BoxLayout.Y_AXIS));
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Actions");
        titledBorder.setTitleColor(Color.YELLOW); 
        panneau.setBorder(titledBorder);
        panneau.setBackground(new Color(0, 80, 150));

        // Boutons pour les différentes actions
        boutonDeplacer = new JButton("Déplacer");
        boutonBombe = new JButton("Déposer Bombe");
        boutonMine = new JButton("Déposer Mine");
        boutonTirer = new JButton("Tirer");
        boutonBouclier = new JButton("Activer Bouclier");
        boutonPasserTour = new JButton("Passer Tour");

        // Style des boutons
        JButton[] boutons = { boutonDeplacer, boutonBombe, boutonMine,boutonTirer, boutonBouclier, boutonPasserTour };
        for (JButton bouton : boutons) {
            bouton.setAlignmentX(Component.CENTER_ALIGNMENT);
            bouton.setFocusPainted(false);
            bouton.setBackground(new Color(255, 223, 85)); // Doré
            bouton.setForeground(Color.BLACK);
            bouton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            panneau.add(Box.createRigidArea(new Dimension(0, 10))); // Espacement
            panneau.add(bouton);
        }

        return panneau;
    }

    /**
     * Affiche le plateau de jeu en mettant à jour les boutons représentant chaque case.
     */
    public void afficherPlateau() {
        panneauPlateau.removeAll();

        // Obtenir le joueur actif
        Combattant joueurActif = partie.getJoueurActif();
        Entite[][]  grille = plateau.getGrille();
        // Parcours de chaque case du plateau
        for (int y = 0; y < plateau.getHauteur(); y++) {
            for (int x = 0; x < plateau.getLargeur(); x++) {
                Entite entite = grille[x][y];

                // Créer un bouton pour chaque case
                JButton caseButton = new JButton();
                caseButton.setHorizontalAlignment(SwingConstants.CENTER);
                caseButton.setVerticalAlignment(SwingConstants.CENTER);
                caseButton.setFocusPainted(false);  
                caseButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1)); 

                
                caseButton.setRolloverEnabled(true);
                caseButton.setOpaque(true);
                caseButton.setBackground(new Color(200, 200, 200));  

                // Déterminer ce qu'il y a dans la case (combattant, objet, vide, etc.)
                if (entite != null) {
                    if (entite instanceof Combattant) {
                        Combattant combattant = (Combattant) entite;
                        if (combattant.equals(joueurActif)) {
                            ImageIcon icon = new ImageIcon("vue/images/combattant-icon.png");
                            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                            caseButton.setIcon(new ImageIcon(img));
                        } else {
                            ImageIcon icon = new ImageIcon("vue/images/combattants-icon.png");
                            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                            caseButton.setIcon(new ImageIcon(img));
                        }

                        
                    } else if (entite instanceof Bombe) {
                        if(((Arme)entite).getEstDeposee()){
                            ImageIcon icon = new ImageIcon("vue/images/bombe-icon.png");
                            Image img = icon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH);
                            caseButton.setIcon(new ImageIcon(img));
                        }else{
                            ImageIcon icon = new ImageIcon("vue/images/bombe0-icon.png");
                            Image img = icon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH);
                            caseButton.setIcon(new ImageIcon(img));
                        }
                    } else if (entite instanceof Mine) {
                        if(((Arme)entite).getEstDeposee()){
                            ImageIcon icon = new ImageIcon("vue/images/mine-icon.png");
                            Image img = icon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH);
                            caseButton.setIcon(new ImageIcon(img));
                        }else{
                            ImageIcon icon = new ImageIcon("vue/images/mine0-icon.png");
                            Image img = icon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH);
                            caseButton.setIcon(new ImageIcon(img));
                        }
                    } else if (entite instanceof Mur) {
                        ImageIcon icon = new ImageIcon("vue/images/mur1-icon.png");
                        Image img = icon.getImage().getScaledInstance(64, 50, Image.SCALE_SMOOTH);
                        caseButton.setIcon(new ImageIcon(img));
                    } else if (entite instanceof Energie) {
                        ImageIcon icon = new ImageIcon("vue/images/energie-icon.png");
                        Image img = icon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH);
                        caseButton.setIcon(new ImageIcon(img));
                    } else if (entite instanceof Munitions) {
                        ImageIcon icon = new ImageIcon("vue/images/munition-icon.png");
                        Image img = icon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH);
                        caseButton.setIcon(new ImageIcon(img));
                    } else if (entite instanceof Bouclier) {
                        ImageIcon icon = new ImageIcon("vue/images/bouclier-icon.png");
                        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                        caseButton.setIcon(new ImageIcon(img));
                    }
                } else {
                    caseButton.setText("");  
                    caseButton.setBackground(new Color(230, 230, 230));  
                }
                                final int finalX = x;
                final int finalY = y;
                caseButton.addActionListener(e -> {
                    // Stocker les coordonnées dans le contrôleur
                    Controller.setX(finalX);
                    Controller.setY(finalY);
                });
             
                boutonsPlateau[y][x] = caseButton;
                panneauPlateau.add(caseButton);
            }
        }

        // Redessiner le panneau pour afficher les changements
        panneauPlateau.revalidate();
        panneauPlateau.repaint();
    }

    /**
     * Met à jour l'affichage du plateau après un changement.
     */

    public void mettreAJour() {
        afficherPlateau();
    }
    /**
     * Méthode appelée lorsque le modèle subit un changement.
     * 
     * @param source L'objet source du changement.
     */
    @Override
    public void somethingHasChanged(Object source) {
        repaint(); // Redessine la vue lorsque le modèle est mis à jour
        afficherPlateau();
    }
    /**
     * Obtient le bouton permettant de déplacer un combattant.
     * 
     * @return Le bouton "Déplacer".
     */
    public JButton getBoutonDeplacer() {
        return boutonDeplacer;
    }
    /**
     * Obtient le bouton permettant de déposer une bombe.
     * 
     * @return Le bouton "Déposer Bombe".
     */
    public JButton getBoutonBombe() {
        return boutonBombe;
    }
    /**
     * Obtient le bouton permettant de déposer une mine.
     * 
     * @return Le bouton "Déposer Mine".
     */
    public JButton getBoutonMine() {
        return boutonMine;
    }
    /**
     * Obtient le bouton permettant d'activer le bouclier.
     * 
     * @return Le bouton "Activer Bouclier".
     */
    public JButton getBoutonBouclier() {
        return boutonBouclier;
    }
    /**
     * Obtient le bouton permettant de passer le tour.
     * 
     * @return Le bouton "Passer Tour".
     */
    public JButton getBoutonPasserTour() {
        return boutonPasserTour;
    }
    /**
     * Obtient le bouton permettant de tirer.
     * 
     * @return Le bouton "Tirer".
     */
    public JButton getBoutonTirer() {
        return boutonTirer;
    }
}

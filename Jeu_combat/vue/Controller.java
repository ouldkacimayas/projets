package vue;

import modele.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
/**
 * Contrôleur principal qui gère les interactions entre la vue et le modèle.
 * <p>
 * Cette classe coordonne les actions effectuées dans l'interface graphique (vue)
 * et leur application sur le modèle du jeu, en utilisant la logique fournie
 * par les objets de type `PartieGraphic` et `Plateau`.
 */
public class Controller {

    private PartieGraphic partie;   // Référence vers le modèle Jeu
    private Vue vue;   // Référence vers la vue principale (globale)
    private Plateau plateau;
    private static int x = -1; // Coordonnée X
    private static int y = -1; // Coordonnée Y -1 pour eviter de deplacer vers 0 0 
    //lors du premier tous si lutisateur na pas selection la case 
    /**
     * Constructeur du contrôleur.
     * 
     * @param plateau Le plateau de jeu.
     * @param vue La vue principale affichant le plateau et les actions possibles.
     * @param partie Le modèle de gestion de la partie (tour par tour).
     */
    public Controller(Plateau plateau, Vue vue,PartieGraphic partie) {
        
        this.plateau = plateau;
        this.vue = vue;
        this.partie=partie;
       

        // Ajout de la vue globale comme écouteur des changements du modèle
        plateau.addModelListener(vue);  
        List<Combattant> l = partie.getCombattants();

        for (Combattant c : l){
            c.addModelListener(vue);
        }
        
       initActions(); 
    }


    /**
     * Initialise les actions des boutons de l'interface utilisateur.
     * <p>
     * Associe chaque bouton de la vue à une action spécifique à réaliser
     * sur le modèle de jeu.
     */
    private void initActions() {

        // Action pour déplacer un combattant
        vue.getBoutonDeplacer().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Combattant joueurActif = partie.getJoueurActif();
            if (joueurActif != null) {
            
                int newX =Controller.getX();
                int newY =Controller.getY();
                // Tenter de déplacer le combattant
                boolean success = plateau.deplacerCombattant(newX, newY, joueurActif);
                if (success) {
                    
                    System.out.println(joueurActif.getNom() + " s'est déplacé vers (" + newX + ", " + newY + ")");
                } else {
                System.out.println("Déplacement impossible vers (" + newX + ", " + newY + ")");
                    JOptionPane.showMessageDialog(vue,
                    "Déplacement impossible vers (" + newX + ", " + newY + ")",
                    "Erreur de déplacement",
                    JOptionPane.ERROR_MESSAGE); 
                }
            }
            partie.passerTour();
        }
        });


        // Action pour déposer une bombe
        vue.getBoutonBombe().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Combattant joueurActif = partie.getJoueurActif();
                
                    int x = Controller.getX(); // Récupérer la coordonnée X de la case sélectionnée
                    int y = Controller.getY(); 
                    try {
                        Bombe bombeDeposee = joueurActif.deposerBombe(x, y, PartieGraphic.nombreDeTours);
                        if(bombeDeposee!=null){
                            partie.ajouterBombe(bombeDeposee);
                            System.out.println("Bombe déposée avec succès !");
                        }else{
                            JOptionPane.showMessageDialog(
                            vue,
                            "Impossible de déposer une bombe à (" + x + ", " + y + ") :\n" ,
                            "Erreur de dépôt de bombe",
                            JOptionPane.ERROR_MESSAGE
                        );                         
                        }
                       
                        
                    } catch (Exception ex) {
                        System.err.println("Erreur lors de la dépose de la bombe : " + ex.getMessage());
                                        // Affichage de l'erreur dans une boîte de dialogue
                        JOptionPane.showMessageDialog(
                            vue,
                            "Impossible de déposer une bombe à (" + x + ", " + y + ") :\n" ,
                            "Erreur de dépôt de bombe",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                    
                  
                   partie.passerTour();
                
            }
        });

        // Action pour déposer une mine
        vue.getBoutonMine().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Combattant joueurActif = partie.getJoueurActif();
                if (joueurActif != null) {
                    int x = Controller.getX(); // Récupérer la coordonnée X de la case sélectionnée
                    int y = Controller.getY(); 
                try {
                    boolean mineDeposee = joueurActif.deposerMine(x, y);
                    if(mineDeposee){
                        System.out.println("Mine déposée par " + joueurActif.getNom() + " à (" + x + ", " + y + ").");
                    }else{
                        JOptionPane.showMessageDialog(
                        vue,
                        "Impossible de déposer une mine à (" + x + ", " + y + ") :\n",
                        "Erreur de dépôt de mine",
                        JOptionPane.ERROR_MESSAGE
                        );                 
                    }
                    
                }catch (Exception ex) {
                // Affichage du message d'erreur géré par la méthode
                System.err.println("Erreur lors de la dépose de la mine : " + ex.getMessage());
                
                // Optionnel : afficher l'erreur dans une boîte de dialogue
                JOptionPane.showMessageDialog(
                    vue,
                    "Impossible de déposer une mine à (" + x + ", " + y + ") :\n" + ex.getMessage(),
                    "Erreur de dépôt de mine",
                    JOptionPane.ERROR_MESSAGE
                );
                }
                   
                }
                partie.passerTour();
            }
        });
        vue.getBoutonTirer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Combattant joueurActif = partie.getJoueurActif();
                if (joueurActif != null) {
                    
                    Direction[] directions = Direction.values();
                    String[] options = new String[directions.length];
                    for (int i = 0; i < directions.length; i++) {
                        options[i] = directions[i].name(); // Utiliser les noms des directions
                    }

                    // Afficher une boite de dialogue pour choisir la direction
                    String choix = (String) JOptionPane.showInputDialog(
                        vue,
                        "Choisissez une direction pour tirer :",
                        "Direction du tir",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0] // Direction par defaut
                    );

                    // Vérifier si un choix 
                    if (choix != null) {
                        try {
                            //Convertir le choix en instance de Direction
                            Direction directionTire = Direction.valueOf(choix);

                            // Appeler la methode du modele pour tirer
                            joueurActif.tirer(directionTire);
                            System.out.println(joueurActif.getNom() + " a tiré vers " + directionTire.name());
                        } catch (Exception ex) {
                            System.err.println("Erreur lors du tir : " + ex.getMessage());
                            JOptionPane.showMessageDialog(
                                vue,
                                "Erreur lors du tir :\n" + ex.getMessage(),
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE
                            );
                        }
                    } else {
                        // Aucun choix effectue
                        System.out.println("Aucune direction choisie. Tir annulé.");
                    }
                }

                // Passer au tour suivant apres le tir
                partie.passerTour();
            }
        });

        // Action pour activer le bouclier
        vue.getBoutonBouclier().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Combattant joueurActif = partie.getJoueurActif();
                if (joueurActif != null) {
                    joueurActif.activerBouclier();
                    System.out.println(joueurActif.getNom() + " a activé son bouclier.");
                }
                partie.passerTour();
            }
        });

        // Action pour passer le tour
        vue.getBoutonPasserTour().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                partie.passerTour();
               // partie.passerTour();
                System.out.println("Tour passé. Prochain joueur actif : " + partie.getJoueurActif().getNom());
            }
        });
    }   

       
    /**
     * Définit la coordonnée X de la case sélectionnée.
     * 
     * @param newX La coordonnée X.
     */
    public static void setX(int newX) {
        x = newX;
    }
    /**
     * Définit la coordonnée Y de la case sélectionnée.
     * 
     * @param newY La coordonnée Y.
     */
    public static void setY(int newY) {
        y = newY;
    }

    /**
     * Obtient la coordonnée X de la case sélectionnée.
     * 
     * @return La coordonnée X.
     */   
    public static int getX() {
        return x;
    }
    /**
     * Obtient la coordonnée Y de la case sélectionnée.
     * 
     * @return La coordonnée Y.
     */
    public static int getY() {
        return y;
    }
  
}

package vue;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import observer.ModelListener;
import modele.Taquin;
import modele.TaquinBox;


import javax.swing.*;
import java.awt.event.*;
/**
 * Classe qui represente le controleur pour le modele 
 */
public class TaquinController extends JFrame implements MouseListener, KeyListener {

    private TaquinView taquinView;
    private Taquin taquin;

    /**
     * Constructeur du controlleur .
     * 
     * @param taquinView est la vue.
     * @param taquin est le  modèle.
     */
    public TaquinController(TaquinView taquinView, Taquin taquin) {
        this.taquinView = taquinView;
        this.taquin = taquin;
        JFrame frame = new JFrame("Puzzle Taquin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        centreWindow(frame);

        frame.getContentPane().add(taquinView);

        frame.setJMenuBar(createMenu(frame));

        frame.pack();
        frame.setVisible(true);

        // Ajouter cet objet comme écouteur de la souris à la vue
        taquinView.addMouseListener(this);
        taquinView.addKeyListener(this);
      


    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2 - 170);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2 -190);
        frame.setLocation(x, y);
    }
       /**
     * Méthode createMenu.
     * Cette méthode crée la barre de menu.
     * elle permet a lutilsateur de selectionner la taille du taquin.
     * @param frame Le cadre JFrame auquel la barre de menu sera ajoutee.
     * @return La barre de menu créée.
     */
     public  JMenuBar createMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Taille");

        int[] sizes = {3,4, 5, 6, 7, 8, 9, 10}; 
        for (int size : sizes) {
            JMenuItem sizeItem = new JMenuItem(size + "x" + size);
            sizeItem.addActionListener(new SizeMenuItemListener(size));
            optionsMenu.add(sizeItem);
        }

        menuBar.add(optionsMenu);
        return menuBar;
    }

    // Classe interne pour écouter les événements de clic sur les éléments du menu
    private class SizeMenuItemListener implements ActionListener {
    private int size;

    public SizeMenuItemListener(int size) {
        this.size = size * size;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Mettre à jour la taille du taquin et redessiner la vue
        setTaquinSize(size);
    }
    }
    



    public void setTaquinSize(int size) {
    taquin.setSize(size);
    taquinView.setTaquin(taquin); // Mettre à jour le modèle de la vue avec le nouveau taquin
    }



    /**
     * Gère l'evenements clic de la souris.
     * 
     * @param e L'événement MouseEvent.
     */

@Override 
public void mouseClicked(MouseEvent e) {
    // Récupérer les coordonnées du clic de sourisj
    int mouseX = e.getX();
    int mouseY = e.getY();

   
    int row = mouseY / (taquinView.boxSize+taquinView.padding);
    int col = mouseX / (taquinView.boxSize+taquinView.padding);



    taquin.initializeBoxValues();
  
    // teste pour eviter les out of bound index. 
    if ((col<Math.sqrt(taquin.getSize()))&& (row<Math.sqrt(taquin.getSize()))){
    int clickedValue = taquin.boxValues[col][row];

    // Mettre à jour le modèle en fonction de la case cliquée

    List<TaquinBox> res = taquin.getSwitchableBox();
    int[] switchableV = new int[res.size()];
    boolean cond = false;
    cond = false;
    for(int i=0; i<res.size(); i++){
        switchableV[i] = res.get(i).getValue();
    }
    for(int val : switchableV){
        if(clickedValue==val){
            cond = true;
            break;
            }
    }
    if(!cond){
        if(clickedValue>taquin.getSize()-1){
            System.out.println("Erreur le chiffre entre n'existe pas!");
            }
           }else{
            taquin.switchBoxes(clickedValue);
        }
            // Vérifier si le joueur a gagné la partie
    if (taquin.isWin()) {
        JOptionPane.showMessageDialog(this, "Bravo, vous avez gagné la partie !");
      }
    }

}
    /**
     * Gère l'événement de clic sur les touches du clavier .
     * 
     * @param e L'événement KeyEvent.
     */
@Override
    public void keyPressed(KeyEvent e){
         int keyCode = e.getKeyCode();
        TaquinBox emptyBox =taquin.getEmptyBox();
        int X = emptyBox.getX();
        int Y = emptyBox.getY();

        //pour trouver les case qu on peut deplacer
        List<TaquinBox> res = taquin.getSwitchableBox();
        int[] switchableV = new int[res.size()];

        for(int i=0; i<res.size(); i++){
          switchableV[i] = res.get(i).getValue();
        }
        
         switch (keyCode){
            case KeyEvent.VK_Z:
                 // Déplacer la case vers le haut
                int value1 = taquin.getBoxValue(X,Y-1);
                for(int val : switchableV){
                   if(value1==val){
                    taquin.switchBoxes(value1);
                   
                   break;
                 }
                 }
                
            break;
            case KeyEvent.VK_Q:
            // Déplacer la case vers la gauche
                int value2 = taquin.getBoxValue(X-1,Y);
                for(int val : switchableV){
                    if(value2==val){
                        taquin.switchBoxes(value2);

                    }
                }
               
            break;
            case KeyEvent.VK_S:
            // Déplacer la case vers le bas
                int value3 = taquin.getBoxValue(X,Y+1);
                for(int val : switchableV){
                    if(value3==val){
                        taquin.switchBoxes(value3);

                    }
                }

                
            break;
            case KeyEvent.VK_D:
            // Déplacer la case vers la droite
                int value4 = taquin.getBoxValue(X+1,Y);
                for(int val : switchableV){
                    if(value4==val){
                        taquin.switchBoxes(value4);

                    }

                }
                
            break;
            default:
            // Ne rien faire si une autre touche est pressée
          
            break;
            

         }
        if (taquin.isWin()) {
        JOptionPane.showMessageDialog(this, "Bravo, vous avez gagné la partie !");
      }
    }

    // Implémenter les autres méthodes de MouseListener si nécessaire
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void keyReleased(KeyEvent e){}
    @Override
    public void keyTyped (KeyEvent e){}
       
   
}

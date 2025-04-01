package vue;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import observer.ModelListener;
import modele.Taquin;
import modele.TaquinBox;
/**
 * Classe représentant la vue pour notre modele .

 */
public class TaquinView extends JPanel implements ModelListener {

    private Taquin taquin;
    protected int boxSize; // Taille d'une case
    protected int padding; // Espace entre les cases
    //protected int[][] boxValues;
    private JLabel label;
    /**
     * Constructeur de la classe TaquinView.
     * @param taquin est le  modèle a  afficher.
     */
    public TaquinView(Taquin taquin) {
        this.taquin = taquin;
        this.taquin.addListener(this); //La vue s'enregistre comme observateur du modèle
        this.boxSize = 85; // Taille de base pour une case (modifiable)
        this.padding = 5; // Espace entre les cases (modifiable)
        //this.boxValues=initializeBoxValues();
        setFocusable(true);
        setLayout(new BorderLayout());
       

        setPreferredSize(new Dimension(taquin.getSize() * (boxSize + padding)/2, taquin.getSize() * (boxSize + padding)/2));
     
        
        label = new JLabel("Nb coups: "+Integer.toString(taquin.getnbswitch()));
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(Color.BLACK);

        label.setHorizontalAlignment(JLabel.LEFT);
        //add(label);
        add(label, BorderLayout.SOUTH);
    }
   
     
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        List<TaquinBox> taquinBoxes = taquin.getTaquin();
        Color c1 = new Color(51,204,255,150);
        Color c2 = new Color(10,10,10,150);
        Color c3 = new Color(10,120,90,220);
        int fontSize = 24;
        for (TaquinBox box : taquinBoxes) {
            int x = box.getX() * (boxSize + padding);
            int y = box.getY() * (boxSize + padding);
            int value = box.getValue();
            if (value != 0) {
                g.setColor(c1);
                g.fillRect(x, y, boxSize, boxSize);
                g.setColor(c2);
                g.drawRect(x, y, boxSize, boxSize);
                g.setColor(c3);
                g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
                g.drawString(Integer.toString(value), x + boxSize / 2 - fontSize/4, y + boxSize / 2 + fontSize/4);
            } else {
                g.setColor(c2);
                g.fillRect(x, y, boxSize, boxSize);
            }
        }
        


    }

        /**
     * Méthode modelUpdated.
     * Cette méthode est appele lorsque le model est mis a jour .
     * Elle permet de redessiner la vue de nouveau a chaque changement .
     * @param source L'objet qui a déclenché la mise à jour du modèle.
     */
    @Override
    public void modelUpdated(Object source) {
        
        repaint(); // Redessine la vue lorsque le modèle est mis à jour
        label.setText("Nb coups: "+Integer.toString(taquin.getnbswitch()));
    
    }
 
    public void setTaquin(Taquin taquin) {
    this.taquin = taquin;
    repaint(); // Redessinez la vue pour refléter les changements
    }




}

package modele;
import java.util.*;

/**
 * Classe représentant un robot (Bot) dans le jeu.
 * 
 * Un Bot est une spécialisation de la classe Combattant avec un 
 * comportement spécifique pouvant être déclenché automatiquement. Il dispose 
 * également de la capacité de se déplacer aléatoirement sur le plateau.
 */
public class Bot extends Combattant {

    /**
     * Comportement du robot, qui peut être "Attaquant", "Défenseur", etc.
     * Défini par l'interface Comportement.
     */
    private Comportement comportement; // comportement de robot, par exemple "Attaquant", "Défenseur"

    /**
     * Constructeur de la classe Bot.
     *
     * @param nom           le nom du Bot.
     * @param energie       l'énergie initiale du Bot.
     * @param x             la position x initiale du Bot sur le plateau.
     * @param y             la position y initiale du Bot sur le plateau.
     * @param comportement  le comportement attribué au Bot.
     */
    public Bot(String nom, int energie, int x, int y, Comportement comportement) {
        super(nom, energie, x, y);
        this.comportement = comportement;
    }

    /**
     * Retourne le comportement du Bot.
     *
     * @return le comportement actuel du Bot.
     */
    public Comportement getComportement() {
        return comportement;
    }

    /**
     * Définit un nouveau comportement pour le Bot.
     *
     * @param comportement le nouveau comportement à attribuer au Bot.
     */
    public void setComportement(Comportement comportement) {
        this.comportement = comportement;
    }

    /**
     * Déclenche le comportement spécifique du Bot.
     * 
     * Le comportement est défini par l'implémentation de l'interface Comportement.
     */
    public void declencherComportement(){
        this.comportement.agir(this);
    }
    

    /**
     * Effectue un déplacement aléatoire du Bot sur le plateau.
     * 
     * Le Bot peut se déplacer dans une direction choisie aléatoirement parmi les 
     * quatre cardinales (haut, bas, gauche, droite). Si le déplacement est valide, 
     * il est appliqué au Bot.
     */
    public void seDeplacerAleatoirement() {
        int nouvX = this.x;
        int nouvY = this.y;
        Random random = new Random();
        int rand = random.nextInt(4);

        switch(rand){
            case 0://Droite
                nouvX += 1;
                break;
            case 1://Bas
                nouvY += 1;
                break;
            case 2://Gauche
                nouvX -= 1;
                break;
            case 3://Haut
                nouvY -= 1;
                break;
        }

        this.plateau.deplacerCombattant(nouvX,nouvY,this);
    }

    /**
     * Retourne une représentation textuelle simplifiée du Bot.
     * 
     * Le format est "R" suivi de son numéro, permettant d'identifier rapidement les Bots sur le plateau.
     *
     * @return une chaîne représentant le Bot, par exemple "R1" ou "R2".
     */
    @Override
    public String toString() {
        return "R"+this.nom.substring(3);
    }
}

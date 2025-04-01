package modele;

/**
 * Classe Munitions représentant des munitions dans le jeu.
 * Les munitions sont des objets que les combattants peuvent ramasser pour pouvoir tirer.
 * Elles sont placées sur le plateau et peuvent être récupérées par les combattants pour augmenter leur réserve de munitions.
 * Hérite de la classe AbstractEntite.
 */
public class Munitions extends AbstractEntite{
    private int quantite;

    /**
     * Constructeur de la classe Munitions.
     * 
     * @param quantite la quantité de munitions.
     * @param x        la position X de l'objet sur le plateau.
     * @param y        la position Y de l'objet sur le plateau.
     */
    public Munitions(int quantite, int x, int y){
        super("Munition",x,y);
        this.quantite = quantite;
    }

    /**
     * Retourne la quantité de munitions.
     * 
     * @return la quantité de munitions.
     */
    public int getQuantite(){
        return this.quantite;
    }

    /**
     * Retourne une représentation sous forme de chaîne de l'objet munitions.
     * Cette méthode est utilisée pour afficher un symbole pour les munitions sur le plateau.
     * 
     * @return une chaîne représentant les munitions, ici "m".
     */
    @Override
    public String toString() {
        return "m";  
    }
}
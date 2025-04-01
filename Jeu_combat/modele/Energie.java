package modele;

/**
 * La classe Energie représente une entité d'énergie dans le jeu. 
 * Elle est placée sur le plateau à une position donnée et contient une quantité d'énergie.
 */
public class Energie extends AbstractEntite{
    private int quantite;

    /**
     * Constructeur de la classe Energie.
     * 
     * @param x la position X sur le plateau.
     * @param y la position Y sur le plateau.
     * @param quantite la quantité d'énergie contenue dans cette entité.
     */
    public Energie(int x, int y, int quantite){
        super("Energie",x,y);
        this.quantite = quantite;
    }

    /**
     * Retourne la quantité d'énergie contenue dans cette entité.
     * 
     * @return la quantité d'énergie.
     */
    public int getQuantite(){
        return this.quantite;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'entité Energie.
     * Dans ce cas, la chaîne renvoyée est simplement "E" pour indiquer l'entité Energie.
     * 
     * @return la chaîne de caractères "E".
     */
    @Override
    public String toString() {
        return "E";  
    }
}
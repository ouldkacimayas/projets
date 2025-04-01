package modele;
public class TaquinBox{
    //Attribus
    private int x,y;
    private int value;

    /**
     * Constructeur
     * @param x coordonnee x d'une case (colonne)
     * @param y coordonnee y d'une case (ligne)
     * @param value valeur d'une case
     */
    public TaquinBox(int x, int y, int value){
        this.x = x;
        this.y = y;
        this.value = value;
    }

    //Getteurs
    public int getX(){ return this.x;}
    public int getY(){ return this.y;}
    public int getValue(){ return this.value;}

    //Accesseurs
    public void setX(int newX){ this.x = newX;}
    public void setY(int newY){ this.y = newY;}
    private void setValue(int newValue){ this.value = newValue;}

    /**
     * Methode qui retourne la valeur d'une case
     * @return valeur entiere
     */
    public String toString(){ return Integer.toString(this.value);}
}
package modele;
public abstract class EntiteAbstraite implements Entite{
    protected int x;
    protected int y;

    public EntiteAbstraite(int x ,int y){
        
        this.x = x;
        this.y = y;
    }

   
    public int getX(){ return this.x;}
    public int getY(){ return this.y;}
}
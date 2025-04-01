package modele;
class Mur extends EntiteAbstraite {
    public Mur(int x, int y) {
        super(x, y); 
    }
    public void afficher() {
        System.out.print("#");
    }
    
}

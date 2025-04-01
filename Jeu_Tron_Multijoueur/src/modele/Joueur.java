package modele;
public class Joueur extends EntiteAbstraite{
    protected int numero;
    private int score;
    private int profondeur; 
    private int equipe;

    public Joueur() {
        super(0, 0); // Correction : appel du constructeur de la classe parente
    } 
    public Joueur(int numero, int x, int y) {
        super(x,y);
        this.numero = numero;
        this.score = 0;
        this.equipe = -1;
    }
    public Joueur(int numero, int x, int y, int profondeur) {
        super(x,y);
        this.numero = numero;
        this.score = 0;
        this.profondeur = profondeur; 
    }

    public Joueur(int numero, int x, int y, int profondeur, int numEquipe) { 
        super(x, y);
        this.numero = numero;
        this.score = 0;
        this.profondeur = profondeur;
        this.equipe = numEquipe; // Ajout de l'équipe
    }


    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNum(){
        return this.numero;
    }
    public int getNumero() {  // Ajout du getter attendu par Jackson
        return numero;
    }

    public void setNumero(int numero) {  // Ajout du setter
        this.numero = numero;
    }
    public int getProfondeur(){
        return this.profondeur; 
    }
    public void setProfondeur(int profondeur){
        this.profondeur = profondeur; 
    }

    public void setEquipe(int num){
        this.equipe = num;
    }

    public int getEquipe(){
        return this.equipe;
    }

    public void afficher(){
        System.out.print(this.numero);
    }   

      @Override
    public String toString() {
        return "Joueur{" +
                "numero=" + numero +
                ", x=" + x +
                ", y=" + y +
                "prof = " + profondeur+
                "equipe= "+ equipe+
                '}';
    }
}

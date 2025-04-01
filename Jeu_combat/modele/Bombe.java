package modele;

/**
 * Classe représentant une bombe.
 * 
 * Une bombe peut être déposée sur le plateau et explose après un délai déterminé ou * quand un combattant marche sur elle, infligeant des dégâts aux cibles présentes. 
 * Elle est une spécialisation de la classe abstraite Arme.
 */
public class Bombe extends Arme {
    private int delai;
    private int tourExplosion;
    private boolean estDeposee;


    /**
     * Constructeur de la classe Bombe.
     *
     * @param degats      les dégâts infligés par la bombe.
     * @param coutEnergie le coût énergétique pour utiliser la bombe.
     * @param delai       le délai avant l'explosion de la bombe (en tours).
     * @param x           la position x initiale de la bombe (non utilisée tant qu'elle n'est pas déposée).
     * @param y           la position y initiale de la bombe (non utilisée tant qu'elle n'est pas déposée).
     */
    public Bombe(int degats, int coutEnergie, int delai,int x, int y) {
        super("Bombe", degats, coutEnergie,x,y); 
        this.delai = delai;
        this.tourExplosion = -1;
        this.estDeposee = false;
        this.x = -1;// Par défaut, la bombe n'est pas placée sur le plateau.
        this.y = -1;// Par défaut, la bombe n'est pas placée sur le plateau.
    }

    /**
     * Retourne le délai avant l'explosion de la bombe.
     *
     * @return le délai en tours avant l'explosion.
     */
    public int getDelai(){
        return this.delai;
    }

    /**
     * Indique si la bombe a été déposée sur le plateau.
     *
     * @return true si la bombe est déposée, false sinon.
     */
    @Override
    public boolean getEstDeposee(){
        return this.estDeposee;
    }

    /**
     * Retourne le tour exact auquel la bombe explosera.
     *
     * @return le numéro du tour où la bombe explosera, ou -1 si elle n'est pas encore déposée.
     */
    public int getTourExplosion(){
        return this.tourExplosion;
    }

    /**
     * Dépose la bombe sur le plateau à une position donnée, en définissant son délai avant explosion.
     *
     * @param x               la position x où la bombe est déposée.
     * @param y               la position y où la bombe est déposée.
     * @param nombreDeTours   le nombre de tours écoulés depuis le début de la partie.
     * @param proprietaire    le combattant qui dépose la bombe.
     */
    public void bombeDeposee(int x, int y, int nombreDeTours, Combattant proprietaire){
        this.estDeposee = true;
        this.tourExplosion = nombreDeTours + this.delai + 1;
        this.x = x;
        this.y = y; 
        this.proprietaire = proprietaire;
        fireChange();
    }

    /**
     * Utilise la bombe contre un combattant cible, infligeant des dégâts.
     *
     * @param cible le combattant touché par l'explosion de la bombe.
     */
    @Override
    public void utiliser(Combattant cible) {
        System.out.println("aiiieee: "+cible.getNom()+" est touche par une bombe!!");
        fireChange();
        cible.subirDegats(this.degats);
    }

    @Override
    public String toString() {
        return "B";  
    }
}
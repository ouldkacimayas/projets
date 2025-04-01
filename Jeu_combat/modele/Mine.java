package modele;

/**
 * Classe Mine représentant une mine dans le jeu.
 * La mine est une arme qui peut être déposée sur le plateau et qui inflige des dégâts
 * lorsqu'un combattant entre en contact avec elle.
 * Elle hérite de la classe Arme et possède des propriétés spécifiques à la mine.
 */
public class Mine extends Arme {
    private boolean estDeposee;

    /**
     * Constructeur de la classe Mine.
     * 
     * @param degats      les dégâts infligés par la mine lorsqu'elle explose.
     * @param coutEnergie le coût en énergie pour utiliser la mine.
     * @param x           la position X de la mine sur le plateau.
     * @param y           la position Y de la mine sur le plateau.
     */
    public Mine(int degats, int coutEnergie, int x, int y) {
        super("Mine", degats, coutEnergie,x,y); 
        this.estDeposee = false;
    }

    /**
     * Dépose la mine sur le plateau à une position spécifique.
     * 
     * @param x          la position X où la mine sera déposée.
     * @param y          la position Y où la mine sera déposée.
     * @param proprietaire le combattant qui possède la mine.
     */
    public void mineDeposee(int x, int y, Combattant proprietaire){
        this.estDeposee = true;
        this.x = x;
        this.y = y;
        this.proprietaire = proprietaire;
    }

    /**
     * Vérifie si la mine a été déposée sur le plateau.
     * 
     * @return true si la mine a été déposée, false sinon.
     */
    public boolean getEstDeposee(){
        return this.estDeposee;
    }

    /**
     * Utilise la mine, infligeant des dégâts au combattant cible.
     * Cette méthode est appelée lorsque la mine explose et touche un combattant.
     * 
     * @param cible le combattant qui subira les dégâts de la mine.
     */
    @Override
    public void utiliser(Combattant cible) {
        // Logique pour gérer l'explosion de la mine
        // Infliger des dégâts au combattant
         cible.subirDegats(this.degats);

    }

    /**
     * Retourne une représentation sous forme de chaîne de la mine.
     * Cette méthode est utilisée pour afficher un symbole pour la mine sur le plateau.
     * 
     * @return une chaîne représentant la mine, ici "M".
     */
    @Override
    public String toString() {
        return "M";  
    }
}
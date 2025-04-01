package modele;


/**
 * Classe abstraite représentant une arme dans le jeu.
 * 
 * Une arme est une entité possédant des dégâts, un coût énergétique d'utilisation, 
 * et un propriétaire. Chaque arme peut être utilisée contre une cible et peut 
 * potentiellement être déposée sur le plateau.
 */
public abstract class Arme extends AbstractEntite{
    /**
     * Dégâts infligés par l'arme lorsqu'elle est utilisée.
     */
    protected int degats;

    /**
     * Coût énergétique pour utiliser l'arme.
     */
    protected int coutEnergie;

    /**
     * Combattant propriétaire de l'arme.
     */
    protected Combattant proprietaire;

    /**
     * Constructeur de la classe {@code Arme}.
     *
     * @param nom         le nom de l'arme.
     * @param degats      les dégâts infligés par l'arme.
     * @param coutEnergie le coût énergétique pour utiliser l'arme.
     * @param x           la position x initiale de l'arme sur le plateau.
     * @param y           la position y initiale de l'arme sur le plateau.
     */
    public Arme(String nom, int degats, int coutEnergie, int x, int y) {
        super(nom,x,y);
        this.degats = degats;
        this.coutEnergie = coutEnergie;
        this.proprietaire = null;
    }

    /**
     * Méthode abstraite permettant d'utiliser l'arme contre une cible.
     *
     * @param cible le combattant cible de l'arme.
     */
    public abstract void utiliser(Combattant cible);

    /**
     * Retourne les dégâts infligés par l'arme.
     *
     * @return les dégâts infligés par l'arme.
     */
    public int getDegats() {
        return degats;
    }

    /**
     * Retourne le coût énergétique pour utiliser l'arme.
     *
     * @return le coût énergétique de l'arme.
     */
    public int getCoutEnergie() {
        return coutEnergie;
    }  

    /**
     * Retourne le combattant propriétaire de l'arme.
     *
     * @return le combattant qui possède actuellement l'arme.
     */
    public Combattant getProprietaire(){
        return this.proprietaire;
    }

    /**
     * Méthode abstraite indiquant si l'arme est déposée sur le plateau.
     *
     * @return true si l'arme est déposée, false sinon.
     */
    public abstract boolean getEstDeposee();
}

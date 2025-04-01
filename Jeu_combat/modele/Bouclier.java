package modele;

/**
 * Classe représentant un bouclier, une arme défensive dans le jeu.
 * 
 * Un bouclier permet de réduire les dégâts subis en consommant de l'énergie. Il ne 
 * inflige pas de dégâts à d'autres combattants, mais permet de protéger son 
 * propriétaire en utilisant de l'énergie pour activer sa protection.
 */
public class Bouclier extends Arme {

    /**
     * Constructeur de la classe Bouclier.
     *
     * @param coutEnergie le coût en énergie pour utiliser le bouclier.
     * @param x           la position x initiale du bouclier sur le plateau.
     * @param y           la position y initiale du bouclier sur le plateau.
     */
    public Bouclier(int coutEnergie, int x, int y) {
        super("Bouclier", 0, coutEnergie,x,y); // le bouclier c'est pour se defendre donc pas il n'infflige aucun degats 
    }

    /**
     * Utilise le bouclier pour protéger le combattant en consommant de l'énergie.
     * 
     * Cette méthode réduit l'énergie du combattant en fonction du coût d'utilisation
     * du bouclier. Si l'énergie du combattant est suffisante pour couvrir le coût,
     * l'énergie est déduite. Sinon, aucune action n'est effectuée.
     *
     * @param cible le combattant qui utilise le bouclier.
     */
    @Override
    public void utiliser(Combattant cible) {
        // utilser le bouclier 
        if (cible.getEnergie() >= getCoutEnergie()){
            cible.setEnergie(cible.getEnergie() - getCoutEnergie());

        }
        
    }

    /**
     * Indique si le bouclier est déposé ou non.
     * 
     * Contrairement à d'autres armes qui peuvent être déposées, le bouclier n'est 
     * jamais déposé une fois utilisé, le combattant s'en debarasse aprés.
     * 
     * @return toujours false car le bouclier ne peut pas être déposé.
     */
    public boolean getEstDeposee(){
        return false;
    }

    @Override
    public String toString() {
        return "S";  
    }
}
package vue;
import modele.*;

import java.util.*;
/**
 * Classe représentant une partie de jeu en mode interface graphic.
 * Elle gère l'évolution d'une partie avec plusieurs combattants, les actions des joueurs (déplacement, attaque, etc.),
 * le suivi des tours et la vérification de la fin de la partie.
 */
public class PartieGraphic{
    private PlateauInter plateau; //la grille du jeu ou les combattent se deplace et ...
    private List<Combattant> combattants;
    private int indexTour;
    public static int nombreDeTours;
    private Scanner scanner;
    private Set<Bombe> bombsesDeposees;
    private boolean tourTermine;  // Flag pour savoir si le tour est terminé
     private final Object lock = new Object();
    /**
     * Constructeur de la classe PartieGraphic.
     * Initialise les variables de la partie et prépare l'environnement de jeu.
     * 
     * @param plateau La grille du jeu (PlateauInter) où les combattants interagiront.
     */
    public PartieGraphic(PlateauInter plateau) {
        this.plateau = plateau;
        this.combattants = new ArrayList<>();
        this.indexTour = 0;
        this.nombreDeTours = 1;
        this.scanner = new Scanner(System.in);
        this.bombsesDeposees = new LinkedHashSet<Bombe>();
         this.tourTermine = false;  // Initialisation à false
    }
    /**
     * Ajoute un combattant à la partie.
     * Place le combattant sur le plateau et l'ajoute à la liste des combattants.
     * 
     * @param combattant Le combattant à ajouter à la partie.
     */

    public void ajouterCombattant(Combattant combattant) {
        combattants.add(combattant);
        plateau.placerCombattant(combattant);
    }


   /**
     * Démarre la partie et gère les tours des joueurs jusqu'à ce qu'il y ait un gagnant ou que le nombre de tours soit atteint.
     * 
     * 
     */
    public Combattant demarrer()throws Exception{
        Combattant combattantActuel = null;
        

        while (combattants.size() > 1) {
            combattantActuel = combattants.get(indexTour);
            combattantActuel.tourCombattant();
            //Verifier le compteurs des bombes deposees si il y en a
            verifierLesBombesDeposees();
            //verifier si il y a un combattant qui est mort
           verifierEtatCombattant();
           
                       
            synchronized (lock) {
                while (!tourTermine) {
                    lock.wait(); // Attend jusqu'à ce que notify() soit appele
                    
                    
                }
                // Réinitialiser pour le prochain tour
                tourTermine = false;
            }
            // Passer au joueur suivant
            indexTour = (indexTour + 1) % combattants.size();
            nombreDeTours++;

        }
        System.out.println("Le gagnant est : " + combattants.get(0));
        return combattants.get(0);

    }
  
  
   public void passerTour(){
        synchronized (lock) {
            tourTermine = true;
            lock.notify(); // Réveille le thread en attente dans demarrer()
        }
    
   }

     /**
     * Recupere le plateau.
     * 
     * @return plateau globale.
     */            
    public PlateauInter getPlateau() {
        return plateau;
    }
    /**
     * Vérifie les bombes déposées et les fait exploser si nécessaire.
     */
    public void verifierLesBombesDeposees(){
        for(Bombe bombe:bombsesDeposees){
            if(bombe.getTourExplosion() == this.nombreDeTours){
                exploserBombe(bombe);
                int x = bombe.getX();
                int y = bombe.getY();
                this.plateau.setCase(x, y, new Vide(x,y));
            }else{
                return;
            }
        }
    }
    /**
     * Gère l'explosion d'une bombe, en infligeant des dégâts aux combattants approximité.
     * 
     * @param bombe La bombe qui explose.
     */
    public void exploserBombe(Bombe bombe) {
        System.out.println("La bombe explose !");
        // Récupérer les cases voisines
        List<Entite> voisines = this.plateau.getCasesVoisines(bombe.getX(), bombe.getY());
        for (Entite voisine : voisines) {
            if (voisine instanceof Combattant) {
                // Appliquer l'effet de la bombe sur le combattant
                bombe.utiliser((Combattant)voisine);
            }
        }
    }
// methode pour ajouter une bombe a lensemble des bombes deposer 
    public void ajouterBombe(Bombe bombe){
        this.bombsesDeposees.add(bombe);

    }
  
    /**
     * Vérifie si un combattant est mort (énergie <= 0).
     * Si oui, le retire de la liste des combattants.
     */
    public void verifierEtatCombattant(){
        for(Combattant combattant:combattants){
            if(combattant.getEnergie() <= 0){
                System.out.println("Le combattant: "+combattant.getNom());
                combattants.remove(combattant);
            }
            }
    }

          // methodes pour adapter JTable 
    public List<Combattant> getCombattants(){
        return combattants;
    }
    public int getNombreCombattants(){
        return combattants.size(); 
    }

    public Combattant getCombattant(int index) {
        if(index >= 0 && index < combattants.size()){
            return combattants.get(index); 
        } else {
             throw new IndexOutOfBoundsException("Index invalide de Combattant : " + index);
        }
    }

    public Combattant getJoueurActif() {
    if (combattants.isEmpty()) {
        throw new IllegalStateException("Aucun combattant n'est disponible dans le jeu.");
    }
    return combattants.get(indexTour);
    }

}

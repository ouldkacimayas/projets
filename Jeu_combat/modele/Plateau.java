package modele;
import java.util.*;
import observer.AbstractListenableModel;
/**
 * Représente le plateau de jeu contenant les differentes entites (Combattant, Bombe, Energie ....).
 * Gère la logique de placement, de déplacement et d'interaction des entités sur le plateau.
 */
public class Plateau extends AbstractListenableModel implements PlateauInter{
    private int largeur;
    private int hauteur;
    private Entite[][] grille;// est une materice ou chaque elem est une case
    private Random random;
    private RemplirGrille strategieRemplissage;
    private List<Combattant> combattants;
    private List<Bombe> bombesDeposees;
    /**
     * Initialise un plateau avec une largeur, une hauteur et une stratégie de remplissage.
     *
     * @param largeur            la largeur du plateau
     * @param hauteur            la hauteur du plateau
     * @param strategieRemplissage la stratégie pour remplir le plateau
     */
    public Plateau(int largeur, int hauteur, RemplirGrille strategieRemplissage) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        grille = new Entite[largeur][hauteur]; // cree une mat de case
        this.random = new Random();
        this.strategieRemplissage = strategieRemplissage;
        this.combattants = new ArrayList<Combattant>();
        this.bombesDeposees = new ArrayList<Bombe>();
        initialiserPlateau();
    }
    /**
     * Initialise le plateau en le remplissant avec la stratégie de remplissage définie.
     */
    private void initialiserPlateau() {
        this.grille = strategieRemplissage.remplirGrille(this.largeur, this.hauteur, this.random);
    }
    /**
     * Récupère la grille du plateau.
     *
     * @return la matrice représentant la grille du plateau
     */
    public Entite[][] getGrille(){
        return this.grille;
    }
    /**
     * Récupère l'entité située à une position donnée sur le plateau.
     *
     * @param x la coordonnée X
     * @param y la coordonnée Y
     * @return l'entité à la position donnée
     * @throws IndexOutOfBoundsException si les coordonnées sont hors limites
     */
    public Entite getCase(int x, int y) {
        if (x >= 0 && x < largeur && y >= 0 && y < hauteur) {
            return grille[x][y];
        } else {
            throw new IndexOutOfBoundsException("Coordonnées hors limites du plateau");
        }
    }
    /**
     * Modifie une entité à une position donnée sur le plateau.
     *
     * @param x      la coordonnée X
     * @param y      la coordonnée Y
     * @param entite l'entité à placer
     */
    public void setCase(int x, int y, Entite entite){
        this.grille[x][y] = entite;
    }

    /**
     * Récupère les cases voisines (y compris diagonales) d'une case donnée.
     *
     * @param x la coordonnée X de la case
     * @param y la coordonnée Y de la case
     * @return une liste d'entités voisines
     */
  
    public List<Entite> getCasesVoisines(int x, int y) {
        List<Entite> voisines = new ArrayList<>();
        voisines.add(grille[x][y]);
        int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1}, // lignes au-dessus
            {0, -1},           {0, 1},  // lignes à côté
            {1, -1}, {1, 0}, {1, 1}    // lignes en dessous
        };

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            // Vérifiez que la position est valide avant d'ajouter la case voisine
            if (positionValide(newX, newY)) {
                voisines.add(grille[newX][newY]); // Ajoutez la case voisine à la liste
            }
        }

        return voisines;
    }
    
   
    /**
     * Récupère les cases voisines uniquement dans les directions horizontales et verticales.
     *
     * @param x la coordonnée X de la case
     * @param y la coordonnée Y de la case
     * @return une liste d'entités voisines horizontales et verticales
     */
    public List<Entite> get4CasesVoisines(int x, int y) {
        List<Entite> voisines = new ArrayList<>();
        voisines.add(grille[x][y]);
        int[][] directions = {
             {-1, 0},  // lignes au-dessus
            {0, -1},           {0, 1},  // lignes à côté
             {1, 0},     // lignes en dessous
        };

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            // Vérifiez que la position est valide avant d'ajouter la case voisine
            if (positionValide(newX, newY)) {
                voisines.add(grille[newX][newY]); // Ajoutez la case voisine à la liste
            }
        }

        return voisines;
    }
    /**
     * Ajoute un combattant au plateau.
     *
     * @param combattant le combattant à ajouter
     */
    public void ajouterCombattant(Combattant combattant){
        this.combattants.add(combattant);
    }
    /**
     * Récupère la liste des combattants sur le plateau.
     *
     * @return la liste des combattants
     */
    public List<Combattant> getCombattants(){
        return this.combattants;
    }
    /**
     * Ajoute une bombe à la liste des bombes déposées.
     *
     * @param bombe la bombe à ajouter
     */
    public void ajouterBombe(Bombe bombe){
        this.bombesDeposees.add(bombe);
    }
    /**
     * Récupère la liste des bombes déposées.
     *
     * @return la liste des bombes
     */
    public List<Bombe> getBombesDeposees(){
        return this.bombesDeposees;
    }
    /**
     * Vérifie si une position est a l'interieur du plateau.
     *
     * @param x la coordonnée X
     * @param y la coordonnée Y
     * @return true si la position est valide, false sinon
     */
    public boolean positionValide(int x, int y) {
        // Vérifiez que x et y sont dans les limites du plateau
        return x >= 0 && x < largeur && y >= 0 && y < hauteur;
    }

    /**
     * Retourner la largeur.
     * @return la largeur
     */
    public int getLargeur(){ return this.largeur;}
    /**
     * Retourner la hauteur.
     * @return la hauteur
     */  
    public int getHauteur(){ return this.hauteur;}

    /**
     * Place un mur sur une position donnée.
     *
     * @param x      la coordonnée X
     * @param y      la coordonnée Y
     */
    public void placerMur(int x, int y)throws Exception{
        if(grille[x][y] instanceof Vide){
            grille[x][y] = new Mur(x,y);
        }else{
            System.out.println("Impossible de placer un mur a cette position car elle est deja prise.");
        }
    }
    /**
     * Place une énergie sur une position donnée.
     *
     * @param x la coordonnée X
     * @param y la coordonnée Y
     */
    public void placerEnergie(int x, int y)throws Exception{
        if(grille[x][y] instanceof Vide){
            grille[x][y] = new Energie(x,y,2);
        }else{
            System.out.println("Impossible de placer l'energie a cette position car elle est deja prise.");
        }
    }
    /**
     * Placer une bombe.
     * @param bombe la mine à placer
     * @param x coordonées x
     * @param y coordonées y
     */
    public void placerBombe(Bombe bombe, int x, int y){
        this.grille[x][y] = bombe;
        fireChange();
    }
    /**
     * Placer une mine.
     * @param mine la mine à placer
     * @param x coordonées x
     * @param y coordonées y
     */
    public void placerMine(Mine mine, int x, int y){
        this.grille[x][y] = mine;
        fireChange();
    }
    /**
     * Place un combattant sur une position aléatoire libre sur le plateau.
     *
     * @param combattant le combattant à placer
     */
    public void placerCombattant(Combattant combattant){
        int randX = this.random.nextInt(this.largeur);
        int randY = this.random.nextInt(this.hauteur); 
        boolean placementEffectue = false;

        do{
            if(grille[randX][randY] instanceof Vide){
                if(deplacementPossible(randX+1,randY)||deplacementPossible(randX-1,randY)||deplacementPossible(randX,randY+1)||deplacementPossible(randX,randY-1)){
                    grille[randX][randY] = (Entite)combattant;
                    combattant.setX(randX);
                    combattant.setY(randY);
                    placementEffectue = true;
                    ajouterCombattant(combattant);
                }                
            }else{
                randX = this.random.nextInt(this.largeur);
                randY = this.random.nextInt(this.hauteur);
            }
        }while(!placementEffectue);
    }
    /**
     * Verifier si la case n'est pas un mur, il n'y a pas un combattant dans cette case et elle est dans le plateau.
     *
     * @param x coordonée x
     * @param y coordonée y
     * @return true si le combattant peut ce deplcer à cette case et false sinon
     */
    public boolean deplacementPossible(int x, int y){
        if (x < 0 || x >= this.largeur || y < 0 || y >= this.hauteur) {
            return false;
        }

        Entite position = grille[x][y];
        if((position instanceof Combattant) || (position instanceof Mur)){
            return false;
        }
        else{
            return true;
        }
    }
    /**
     * Deplacer un combattant sur la grille.
     *
     * @param x coordonée x
     * @param y coordonée y
     * @param combattant combattant qui se deplace
     * @return true si le deplacement est effectué et false sinon
     */
    public boolean deplacerCombattant(int nouvX,int nouvY, Combattant combattant){
        if(!deplacementPossible(nouvX,nouvY)){
            return false;
        }

        int combX = combattant.getX();
        int combY = combattant.getY();

        if(!get4CasesVoisines(combX,combY).contains(this.grille[nouvX][nouvY])){
            System.out.println("Case non voisisne");
            return false;
        }

        marcherSurMineOuBombe(nouvX,nouvY,combattant);
        ramasserObjets(nouvX,nouvY,combattant);
        int x = combattant.getX();
        int y = combattant.getY();
        grille[x][y] = grille[nouvX][nouvY];
        grille[nouvX][nouvY] = (Entite)combattant;
        grille[x][y].setX(x);
        grille[x][y].setY(y);
        combattant.setX(nouvX);
        combattant.setY(nouvY);
        
        combattant.setEnergie(-2);
        fireChange();
        return true;
    }
    /**
     * Verifier si un combattant a marché sur une un objet ramassable lors de son déplacement puis le ramasser.
     *
     * @param x coordonée x
     * @param y coordonée y
     * @param combattant combattant qui se deplace
     */
    public void ramasserObjets(int x, int y, Combattant combattant){
        Entite entite = this.grille[x][y];
        if(entite instanceof Munitions){
            combattant.setMunitions(((Munitions)entite).getQuantite());
        }
        else if(entite instanceof Energie){
            combattant.setEnergie(((Energie)entite).getQuantite());
        }else if((entite instanceof Arme)){
            if((!((Arme)entite).getEstDeposee())){
                combattant.ajouterArme((Arme)entite);
            }
        }
        grille[x][y] = new Vide(x,y); 
       fireChange();
    }
     /**
     * Verifier si un combattant a marché sur une bombe ou une mine lors de son déplacement.
     *
     * @param x coordonée x
     * @param y coordonée y
     * @param combattant combattant qui se deplace
     */   
    public void marcherSurMineOuBombe(int x, int y, Combattant combattant){
        Entite entite = this.grille[x][y];
        if((entite instanceof Arme)){
            Arme arme = (Arme) entite;
            if(arme.getEstDeposee()){
                System.out.println("Oups le combattant "+combattant.getNom()+" a marche sur une "+entite.getNom()+"\n");
                arme.utiliser(combattant);
                this.grille[x][y] = new Vide(x,y);
                if(arme instanceof Bombe){
                    this.bombesDeposees.remove(arme);
                }
                fireChange();
            }
        }
    }
    /**
     * Récupère un combattant par son nom.
     *
     * @param nom le nom du combattant
     * @return le combattant correspondant ou null s'il n'est pas trouvé
     */
    public Combattant getCombattantParNom(String nom) {
        
        for (Combattant combattant : this.combattants) { // Parcourt la liste de combattants
            if (combattant.getNom().equals(nom)) { // Compare le nom fourni avec le nom du combattant
                return combattant; // Retourne le combattant trouvé
            }
        }
        return null; // Retourne null si aucun combattant ne correspond
    }   
    /**
     * Crée une représentation en chaine de characteres du plateau.
     *
     * @return une chaîne représentant le plateau
     */
    @Override
    public String creerSchemaPlateau(){
        StringBuilder affichage = new StringBuilder(); 

        for (int y = 0; y < getHauteur(); y++) {
            affichage.append("\t");
            for (int x = 0; x < getLargeur(); x++) {
                Entite entite = getCase(x,y);
                String formatted = String.format("%-4s", entite != null ? entite.toString() : ".");
                affichage.append(formatted);  // Aligne chaque cellule avec une largeur de 10 caractères
            }
            affichage.append("\n\n");  // Nouvelle ligne pour chaque rangée
        }
        return affichage.toString();
    }
}

package modele;
import java.util.*;
import observer.AbstractListenableModel;

/**
 * La classe PlateauCombattant représente un plateau de jeu qui contient un combattant.
 * Cette classe est un proxy qui permet de manipuler le plateau concret tout en
 * bloquant l'affichage de bombes et de mines deposées par un autre combattant.
 * Elle implémente l'interface PlateauInte, qui définit les opérations sur le plateau de jeu.
 */
public class PlateauCombattant extends AbstractListenableModel implements PlateauInter{
    PlateauInter plateauConcret;
    Combattant combattant;

    /**
     * Constructeur de la classe PlateauCombattan}.
     * 
     * @param plateauConcret Le plateau de jeu sur lequel les actions sont effectuées.
     * @param combattant Le combattant associé à ce plateau.
     */
    public PlateauCombattant(PlateauInter plateauConcret, Combattant combattant){
        this.plateauConcret = plateauConcret;
        this.combattant = combattant;
    }

    // Implémentation des méthodes de l'interface PlateauInter

    /**
     * Retourne la largeur du plateau.
     * 
     * @return La largeur du plateau.
     */

    @Override
    public int getLargeur(){
        return plateauConcret.getLargeur();
    }

    /**
     * Retourne la hauteur du plateau.
     * 
     * @return La hauteur du plateau.
     */
    @Override
    public int getHauteur(){
        return plateauConcret.getHauteur();
    }

    /**
     * Ajoute un combattant sur le plateau.
     * 
     * @param combattant Le combattant à ajouter.
     */
    @Override
    public void ajouterCombattant(Combattant combattant){
        this.plateauConcret.ajouterCombattant(combattant);
    }

    /**
     * Retourne la liste des combattants présents sur le plateau.
     * 
     * @return Une liste de combattants.
     */
    @Override
    public List<Combattant> getCombattants(){
        return this.plateauConcret.getCombattants();
    }

    /**
     * Ajoute une bombe sur le plateau.
     * 
     * @param bombe La bombe à ajouter.
     */
    @Override
    public void ajouterBombe(Bombe bombe){
        this.plateauConcret.ajouterBombe(bombe);
    }

    /**
     * Retourne la liste des bombes déposées sur le plateau.
     * 
     * @return Une liste de bombes.
     */
    @Override
    public List<Bombe> getBombesDeposees(){
        return this.plateauConcret.getBombesDeposees();
    }

    /**
     * Retourne l'entité située à une position spécifique du plateau.
     * 
     * @param x La coordonnée X de la case.
     * @param y La coordonnée Y de la case.
     * @return L'entité présente à la position (x, y).
     */
    @Override
    public Entite getCase(int x, int y) {
        // Ajouter un contrôle avant l'accès à la case
        return plateauConcret.getCase(x, y);
    }

    /**
     * Modifie l'entité située à une position spécifique du plateau.
     * 
     * @param x La coordonnée X de la case.
     * @param y La coordonnée Y de la case.
     * @param entite L'entité à placer sur la case.
     */
    @Override
    public void setCase(int x, int y, Entite entite){
        plateauConcret.setCase(x, y, entite);
    }

    /**
     * Retourne la liste des cases voisines d'une position donnée.
     * 
     * @param x La coordonnée X de la case.
     * @param y La coordonnée Y de la case.
     * @return La liste des entités voisines.
     */
    @Override
    public List<Entite> getCasesVoisines(int x, int y){
        return plateauConcret.getCasesVoisines(x,y);
    }

    /**
     * Vérifie si une position donnée est valide sur le plateau.
     * 
     * @param x La coordonnée X de la position.
     * @param y La coordonnée Y de la position.
     * @return true si la position est valide, sinon false.
     */
    @Override
    public boolean positionValide(int x, int y){
        return plateauConcret.positionValide(x,y);
    }

    /**
     * Place un mur sur le plateau à une position spécifique.
     * 
     * @param x La coordonnée X du mur.
     * @param y La coordonnée Y du mur.
     * @throws Exception Si l'emplacement n'est pas valide pour placer un mur.
     */
    @Override
    public void placerMur(int x, int y) throws Exception {
        // Contrôler si l'on peut placer un mur
        plateauConcret.placerMur(x, y);
    }

    /**
     * Place de l'énergie sur le plateau à une position spécifique.
     * 
     * @param x La coordonnée X de l'énergie.
     * @param y La coordonnée Y de l'énergie.
     * @throws Exception Si l'emplacement n'est pas valide pour placer de l'énergie.
     */
    @Override
    public void placerEnergie(int x, int y) throws Exception {
        // Contrôler si l'on peut placer de l'énergie
        plateauConcret.placerEnergie(x, y);
    }

    /**
     * Place une bombe sur le plateau à une position spécifique.
     * 
     * @param bombe La bombe à placer.
     * @param x La coordonnée X du placement.
     * @param y La coordonnée Y du placement.
     */
    @Override
    public void placerBombe(Bombe bombe, int x, int y) {
        // Contrôler si l'on peut placer une bombe
        plateauConcret.placerBombe(bombe, x, y);
    }

    /**
     * Place une mine sur le plateau à une position spécifique.
     * 
     * @param mine La mine à placer.
     * @param x La coordonnée X du placement.
     * @param y La coordonnée Y du placement.
     */
    @Override
    public void placerMine(Mine mine, int x, int y) {
        // Contrôler si l'on peut placer une mine
        plateauConcret.placerMine(mine, x, y);
    }

    /**
     * Place un combattant sur le plateau à une position spécifique.
     * 
     * @param combattant Le combattant à placer.
     */
    @Override
    public void placerCombattant(Combattant combattant) {
        // Ajouter une logique de placement
        plateauConcret.placerCombattant(combattant);
    }

    @Override
    public boolean deplacementPossible(int x, int y) {
        // Contrôle si le déplacement est possible
        return plateauConcret.deplacementPossible(x, y);
    }   

    /**
     * Vérifie si le déplacement vers une position donnée est possible.
     * 
     * @param x La coordonnée X de la destination.
     * @param y La coordonnée Y de la destination.
     * @return true si le déplacement est possible, sinon false.
     */
    @Override
    public boolean deplacerCombattant(int nouvX, int nouvY, Combattant combattant) {
        // Contrôler si on peut déplacer le combattant
        fireChange();
        return plateauConcret.deplacerCombattant(nouvX, nouvY, combattant);
    }

    /**
     * Permet à un combattant de ramasser des objets à une position donnée.
     * 
     * @param x La coordonnée X de l'emplacement.
     * @param y La coordonnée Y de l'emplacement.
     * @param combattant Le combattant qui ramasse les objets.
     */
    @Override
    public void ramasserObjets(int x, int y, Combattant combattant) {
        // Contrôler si on peut ramasser des objets
        plateauConcret.ramasserObjets(x, y, combattant);
        fireChange();
    }

    /**
     * Permet à un combattant de marcher sur une mine ou une bombe à une position donnée.
     * 
     * @param x La coordonnée X de la mine ou de la bombe.
     * @param y La coordonnée Y de la mine ou de la bombe.
     * @param combattant Le combattant qui marche sur l'objet.
     */
    @Override
    public void marcherSurMineOuBombe(int x, int y, Combattant combattant) {
        // Contrôler les mines ou bombes
        plateauConcret.marcherSurMineOuBombe(x, y, combattant);
        fireChange();
    }

    /**
     * Retourne un combattant par son nom.
     * 
     * @param nom Le nom du combattant.
     * @return Le combattant correspondant au nom.
     */
    @Override
    public Combattant getCombattantParNom(String nom){
        return this.plateauConcret.getCombattantParNom(nom);
    }

    /**
     * Crée une représentation textuelle du plateau.
     * 
     * @return Une chaîne représentant le plateau sous forme de grille.
     */
    @Override
    public String creerSchemaPlateau(){
        StringBuilder affichage = new StringBuilder(); 

        for (int y = 0; y < getHauteur(); y++) {
            affichage.append("\t");
            for (int x = 0; x < getLargeur(); x++) {
                Entite entite = getCase(x,y);
                String formatted = String.format("%-4s", entite != null ? entiteVisible(entite) : ".");
                affichage.append(formatted);  // Aligne chaque cellule avec une largeur de 10 caractères
            }
            affichage.append("\n\n");  // Nouvelle ligne pour chaque rangée
        }
        return affichage.toString();
    }

    /**
     * Détermine si une entité doit être visible ou non sur le plateau de jeu.
     * Cette méthode est principalement utilisée pour décider si une arme doit être visible ou masquée,
     * en fonction de son propriétaire. Les entités non-armes sont toujours visibles.
     * 
     * @param entite L'entité à vérifier (par exemple, une arme, un mur, etc.).
     * @return Une chaîne représentant l'entité sous forme de son nom ou symbole visible.
     * Si l'entité est une arme et ne lui appartient pas, elle est cachée par un point (".").
     */
    public String entiteVisible(Entite entite){
        if(entite instanceof Arme){
            Arme arme = (Arme) entite;
            //Verifier si c'est le proprietaire de cette arme ou cette arme n'est pas encore deposée
            if(this.combattant == arme.getProprietaire() || arme.getProprietaire() == null){
                return entite.toString();
            }else{//arme deposée qui ne lui apparitent pas
                return ".";
            }
        }
        else{
            return entite.toString();
        }
    }

    /**
     * Crée une nouvelle grille filtrée, représentant le plateau de jeu, en tenant compte de la visibilité
     * des entités. Certaines entités (comme les armes) peuvent être masquées si elles ne sont pas possédées
     * par le combattant actuel. Les autres entités sont affichées normalement.
     * 
     * @return Une matrice 2D représentant le plateau filtré, où chaque case contient une entité visible.
     * Les armes deposées appartenant au combattant sont visibles, tandis que celles des autres combattants sont masquées par des cases vides.
     */
    public Entite[][] getGrille() {
        // Créer une nouvelle grille de mêmes dimensions que l'original
        Entite[][] grilleFiltree = new Entite[getLargeur()][getHauteur()];

        for (int y = 0; y < getHauteur(); y++) {
            for (int x = 0; x < getLargeur(); x++) {
                Entite entite = getCase(x, y);

                if (entite != null) {
                    if (entite instanceof Arme) {
                        Arme arme = (Arme) entite;
                        // Si l'arme appartient au combattant ou n'a pas de propriétaire, on la garde
                        if (this.combattant == arme.getProprietaire() || arme.getProprietaire() == null) {
                            grilleFiltree[x][y] = entite; // Garder l'arme visible
                        } else {
                            grilleFiltree[x][y] = new Vide(x, y); // Masquer avec une case Vide
                        }
                    } else {
                        grilleFiltree[x][y] = entite; // Copier directement les autres entités
                    }
                } else {
                    grilleFiltree[x][y] = new Vide(x, y); // Les cases nulles deviennent Vide
                }
            }
        }
        return grilleFiltree;
    }

}
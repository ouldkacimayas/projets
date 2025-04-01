package modele;

import java.util.*;

/**
 * Interface représentant un plateau de jeu dans un environnement de combat.
 * Cette interface définit les méthodes nécessaires pour manipuler et interagir
 * avec le plateau, les entités et les combattants qui s'y trouvent.
 */
public interface PlateauInter {

    /**
     * Retourne la largeur du plateau.
     * 
     * @return La largeur du plateau.
     */
    int getLargeur();

    /**
     * Retourne la hauteur du plateau.
     * 
     * @return La hauteur du plateau.
     */
    int getHauteur();

    /**
     * Retourne une grille filtrée représentant l'état actuel du plateau,
     * où chaque case peut contenir une entité ou être vide.
     * 
     * @return Une matrice 2D d'entités représentant l'état actuel du plateau.
     */
    Entite[][] getGrille();

    /**
     * Retourne l'entité présente à une position donnée sur le plateau.
     * 
     * @param x La coordonnée X de la case.
     * @param y La coordonnée Y de la case.
     * @return L'entité présente à la position donnée ou null si la case est vide.
     */
    Entite getCase(int x, int y);

    /**
     * Définit l'entité présente à une position donnée sur le plateau.
     * 
     * @param x La coordonnée X de la case.
     * @param y La coordonnée Y de la case.
     * @param entite L'entité à placer sur la case spécifiée.
     */
    void setCase(int x, int y, Entite entite);

    /**
     * Retourne une liste des entités voisines d'une position donnée sur le plateau.
     * 
     * @param x La coordonnée X de la case.
     * @param y La coordonnée Y de la case.
     * @return Une liste d'entités présentes dans les cases voisines de la case donnée.
     */
    List<Entite> getCasesVoisines(int x, int y);

    /**
     * Place un mur sur le plateau à une position spécifiée.
     * 
     * @param x La coordonnée X où placer le mur.
     * @param y La coordonnée Y où placer le mur.
     * @throws Exception Si la position est invalide ou si le mur ne peut pas être placé.
     */
    void placerMur(int x, int y) throws Exception;

    /**
     * Place une entité d'énergie sur le plateau à une position spécifiée.
     * 
     * @param x La coordonnée X où placer l'énergie.
     * @param y La coordonnée Y où placer l'énergie.
     * @throws Exception Si la position est invalide ou si l'énergie ne peut pas être placée.
     */
    void placerEnergie(int x, int y) throws Exception;

    /**
     * Place une bombe sur le plateau à une position spécifiée.
     * 
     * @param bombe L'objet Bombe à placer.
     * @param x La coordonnée X où placer la bombe.
     * @param y La coordonnée Y où placer la bombe.
     */
    void placerBombe(Bombe bombe, int x, int y);

    /**
     * Place une mine sur le plateau à une position spécifiée.
     * 
     * @param mine L'objet Mine à placer.
     * @param x La coordonnée X où placer la mine.
     * @param y La coordonnée Y où placer la mine.
     */
    void placerMine(Mine mine, int x, int y);

    /**
     * Place un combattant sur le plateau à une position spécifiée.
     * 
     * @param combattant L'objet Combattant à placer sur le plateau.
     */
    void placerCombattant(Combattant combattant);

    /**
     * Vérifie si un déplacement vers une position donnée est possible sur le plateau.
     * 
     * @param x La coordonnée X de la position cible.
     * @param y La coordonnée Y de la position cible.
     * @return true si le déplacement est possible, false sinon.
     */
    boolean deplacementPossible(int x, int y);

    /**
     * Vérifie si une position donnée est valide (dans les limites du plateau).
     * 
     * @param x La coordonnée X de la position.
     * @param y La coordonnée Y de la position.
     * @return true si la position est valide, false sinon.
     */
    boolean positionValide(int x, int y);

    /**
     * Déplace un combattant vers une nouvelle position sur le plateau.
     * 
     * @param nouvX La coordonnée X de la nouvelle position.
     * @param nouvY La coordonnée Y de la nouvelle position.
     * @param combattant Le combattant à déplacer.
     * @return true si le déplacement a réussi, false sinon.
     */
    boolean deplacerCombattant(int nouvX, int nouvY, Combattant combattant);

    /**
     * Permet à un combattant de ramasser un objet présent à une position donnée.
     * 
     * @param x La coordonnée X de la case où se trouve l'objet.
     * @param y La coordonnée Y de la case où se trouve l'objet.
     * @param combattant Le combattant qui ramasse l'objet.
     */
    void ramasserObjets(int x, int y, Combattant combattant);

    /**
     * Vérifie si un combattant marche sur une mine ou une bombe et gère les conséquences.
     * 
     * @param x La coordonnée X de la case où le combattant se trouve.
     * @param y La coordonnée Y de la case où le combattant se trouve.
     * @param combattant Le combattant marchant sur la mine ou la bombe.
     */
    void marcherSurMineOuBombe(int x, int y, Combattant combattant);

    /**
     * Retourne un combattant en fonction de son nom.
     * 
     * @param nom Le nom du combattant à rechercher.
     * @return Le combattant correspondant au nom donné, ou null s'il n'existe pas.
     */
    Combattant getCombattantParNom(String nom);

    /**
     * Ajoute un combattant au plateau.
     * 
     * @param combattant Le combattant à ajouter au plateau.
     */
    void ajouterCombattant(Combattant combattant);

    /**
     * Retourne la liste des combattants présents sur le plateau.
     * 
     * @return Une liste des combattants présents sur le plateau.
     */
    List<Combattant> getCombattants();

    /**
     * Ajoute une bombe au plateau.
     * 
     * @param bombe L'objet Bombe à ajouter au plateau.
     */
    void ajouterBombe(Bombe bombe);

    /**
     * Retourne la liste des bombes déposées sur le plateau.
     * 
     * @return Une liste des bombes déposées.
     */
    List<Bombe> getBombesDeposees();

    /**
     * Crée un schéma textuel du plateau, représentant les entités sur une grille.
     * 
     * @return Une chaîne de caractères représentant le schéma du plateau sous forme de texte.
     */
    String creerSchemaPlateau();
}

package modele;

import java.util.ArrayList;
import java.util.List;
import observer.AbstractListenableModel;

/**
 * Représente un combattant sur le plateau. 
 * Les combattants peuvent se déplacer, utiliser des armes, subir des dégâts, et interagir avec le plateau.
 */
public  class Combattant extends AbstractEntite{
    protected int energie;// Énergie du combattant
    protected PlateauInter plateau; // Référence au plateau
    protected List<Arme> armes; // Liste pour stocker les armes
    protected boolean estBouclierActif;// Indique si le bouclier est actif
    protected int compteurBouclier; // Compteur pour savoir combien de tours le bouclier doit rester actif
    protected int munitions;// Nombre de munitions restantes
    protected int degatsTire; // Dégâts infligés par un tir

    /**
     * Constructeur pour créer un combattant avec un nom, une énergie et une position initiale.
     *
     * @param nom     le nom du combattant
     * @param energie l'énergie initiale
     * @param x       la position X initiale
     * @param y       la position Y initiale
     */
    public Combattant(String nom, int energie, int x, int y){
        super(nom,x,y);
        this.energie = energie;
        this.plateau = null;
        this.armes = new ArrayList<>(); 
        this.estBouclierActif = false; // Le bouclier n'est pas actif par défaut
        this.compteurBouclier = 0; // Aucun tour de protection au début
        // Stocker le combattant dans la case correspondante
        this.munitions = 3;
        this.degatsTire = 5;
    }

    /**
     * Associe un plateau au combattant.
     *
     * @param plateau le plateau auquel le combattant appartient
     */
    public void ajouterPlateau(PlateauInter plateau){
        this.plateau = plateau;
    }

    /**
     * Recuperer le nom du combattant.
     *
     * @return nom
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Recuperer la coordonée x du combattant.
     *
     * @return coordonée x
     */
    public int getX(){
        return this.x;
    }
    
    /**
     * Recuperer la coordonée y du combattant.
     *
     * @return coordonée y
     */
    public int getY(){
        return this.y;
    }

    /**
     * Modifier la coordonée x du combattant.
     *
     * @param x nouvelle coordonée x
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Modifier la coordonée y du combattant.
     *
     * @param y nouvelle coordonée y
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * Recuperer l'énergie du combattant.
     *
     * @return l'énergie du combattant
     */
    public int getEnergie() {
        return energie;
    }

    /**
     * Recuperer le plateau du combattant.
     *
     * @return plateau du combattant
     */
    public PlateauInter getPlateau(){
        return this.plateau;
    }

    /**
     * Recuperer le nombre de munitions restantes.
     *
     * @return nombre de munitions
     */
    public int getMunitions(){
        return this.munitions;
    }

    /**
     * Ajouter des munitions.
     *
     * @param quantite nombre de munitions à rajouter
     */
    public void setMunitions(int quantite){
        this.munitions += quantite;
    }

    /**
     * Ajouter de l'énergie.
     *
     * @param energiePlus energie à rajouter
     */
    public void setEnergie(int energiePlus) {
        this.energie = this.energie + energiePlus;
    }

    /**
     * Modifier les degats de tire.
     *
     * @param degats degats infligés par un tire
     */
    public void setDegatsTire(int degats){
        this.degatsTire=degats;
    }

    /**
     * Recuperer les armes sous forme de chaine de characteres.
     *
     * @return chaine de characteres
     */
    public StringBuilder getArmes() {
        StringBuilder sb = new StringBuilder();

        for (Arme arme : armes) {
            sb.append(arme.getNom()).append(" ");
        }
        return sb; 
    }

    /**
     * Ajouter des armes à l'inventaire.
     *
     * @param arme arme à ajouter
     */
    public void ajouterArme(Arme arme) {
        armes.add(arme); // Méthode pour ajouter une arme à la liste
    }

    /**
     * Vérifie si une case appartient aux 8 cases voisines du combattant.
     *
     * @param caseX la coordonnée X de la case
     * @param caseY la coordonnée Y de la case
     * @return true si la case est voisine, false sinon
     */
    private boolean estVoisine(int caseX, int caseY) {
        // Vérifier si la case est dans les 8 cases voisines
        return (Math.abs(caseX - this.x) <= 1 && Math.abs(caseY - this.y) <= 1);
    }
    
    /**
     * Dépose une mine à une position voisine si possible.
     *
     * @param caseX la coordonnée X
     * @param caseY la coordonnée Y
     * @return true si la mine est déposée avec succès, false sinon
     */
    public boolean deposerMine(int caseX, int caseY){
        Mine mine = null;
        
        //Recuperer la mine de l'inventaire si il y en a
        for (Arme arme : this.armes) {
            if (arme.getNom().equals("Mine")) {
                mine = (Mine)arme;
                break;
            }
        }    

        //Si le combattant ne possede aucune mine
        if(mine == null){
            System.out.println("Vous n'avez aucune mine.\n");
            return false;
        }
        
        //Verifier si on peut poser une mine sur cette case
        if (this.plateau.positionValide(caseX, caseY) && estVoisine(caseX, caseY)){
            //Si case vide
            if(this.plateau.getCase(caseX,caseY) instanceof Vide){
                this.plateau.placerMine(mine,caseX,caseY);//placer la mine dans le plateau

                mine.mineDeposee(caseX, caseY,this);//Marquer comme deposée

                this.energie -= mine.getCoutEnergie();//Consommer l'energie

                System.out.println("Mine déposée en (" + caseX + ", " + caseY + "). Énergie restante : " + this.energie);

                fireChange();

                this.armes.remove(mine);//Enlever de l'inventaire
                return true;
            }else{
                System.out.println("Case non vide: imossible de placer la mine.\n");
            }
        }else{
            System.out.println("Case invalide.\n");
        } 

        return false;

    }

    /**
     * Dépose une bombe à une position voisine si possible.
     *
     * @param caseX         la coordonnée X
     * @param caseY         la coordonnée Y
     * @param nombreDeTours le délai avant explosion
     * @return la bombe déposée ou null si l'opération échoue
     */
    public Bombe deposerBombe(int caseX, int caseY, int nombreDeTours){
        Bombe bombe = null;

        //Recuperer une bombe de l'inventaire si il y en a
        for (Arme arme : this.armes){
            if (arme.getNom().equals("Bombe")){
                 bombe = (Bombe)arme;
                 break;
            }  
        }

        //Si le combattant ne possede aucune bombe
        if(bombe == null){
            System.out.println("vous n'avais aucune bombe");
            return null;
        }

        //Verifier si on peut poser une bombe sur cette case
        if(this.plateau.positionValide(caseX, caseY) && estVoisine(caseX, caseY)){
            //Si case vide
            if(this.plateau.getCase(caseX,caseY) instanceof Vide){
                    this.plateau.placerBombe(bombe,caseX,caseY);//placer la bombe dans le plateau

                    bombe.bombeDeposee(caseX, caseY, nombreDeTours,this);//Marquer comme deposée

                    this.energie -= bombe.getCoutEnergie();//Consommer l'energie

                    System.out.println("Bombe déposée en (" + caseX + ", " + caseY + "). Énergie restante : " + this.energie);

                    this.armes.remove(bombe);//Enlever de l'inventaire
                    fireChange();
                }else{
                    System.out.println("Case non vide: imossible de placer la bombe.\n");
                    return null;
                }
        }else{
            System.out.println("Case invalide.\n");
            return null;
        }
            return bombe;
    }

    /**
     * Active le bouclier du combattant s'il en possède dans son inventaire.
     */
    public void activerBouclier(){
        Bouclier bouclier = null;

        //Recuperer un bouclier de l'inventaire si il y en a
        for (Arme arme : this.armes){
            if (arme.getNom().equals("Bouclier")){
                 bouclier = (Bouclier)arme;
                 this.armes.remove(arme);
                 break;
            }  
        }

        //Si le combattant possede un bouclier
        if(bouclier != null){
            bouclier.utiliser(this); // Appel de la méthode utiliser de la classe Bouclier
            this.estBouclierActif = true; // Activation du bouclier
            this.compteurBouclier = 1; // Définir le compteur pour un tour de protection
            this.energie -= bouclier.getCoutEnergie();//Comsommer de l'energie
            System.out.println("Bouclier activé !");
            fireChange();
        }else{
            System.out.println("Vous ne possedez aucun bouclier.");
        }
    }

    /**
     * Méthode appelée à la fin du tour pour gérer l'état du bouclier.
    */
    public void finDeTour() {
        //Bouvlier activer
        if (compteurBouclier > 0) {
            System.out.println("Bouclier actif, le combattant est protégé pour ce tour !");
            compteurBouclier--; // Réduit le compteur de tours restants pour le bouclier
        }
        
        // Désactiver le bouclier si le compteur atteint 0
        if (compteurBouclier == 0) {
            estBouclierActif = false;
        }
    }

    /**
     * Le combattant subit des dégâts s'il n'est pas protégé par un bouclier.
     *
     * @param degats les dégâts subis
     */
    public void subirDegats(int degats) {
        if (!this.estBouclierActif) {
            this.energie -= degats; // Subir des dégâts uniquement si le bouclier n'est pas actif
            fireChange();
        }   
    }

    /**
     * Le combattant n'effectue aucune action économise son énergie.
    */
    public void neRienFaire() {
        System.out.println("Le combattant a choisi de ne rien faire pour économiser son énergie.");
        fireChange();
    }

    /**
     * Tire dans une direction et inflige des dégâts à une cible si elle est touchée.
     *
     * @param direction la direction du tir
     */
    public void tirer(Direction direction){
        //Verifier les munitions
        if(this.munitions == 0){
            System.out.println("Vous avez aucune munition");
        }

        //Chercher si une cible est sur le viseur 
        Combattant cible = chercherCible(direction);
        
        //Aucun combattant ciblable
        if(cible == null){
            System.out.println("Aucune Cible touche.");
        }else{
            System.out.println("Cible touchee: "+cible.getNom());
            ((Combattant)cible).subirDegats(this.degatsTire);//tirer sur un combattant
            fireChange();
        }

        this.munitions -= 1;//consommer une munition
        this.energie -= 2;//consommer de l'énergie
    }

    /**
     * Retourne une description des armes actuellement disponibles dans l'inventaire du combattant.
     *
     * @return une chaîne contenant la liste des armes disponibles, ou "Inventaire vide." si le combattant ne possède aucune arme.
     */
    public String inventaire(){
        StringBuilder str = new StringBuilder();
        if(armes.isEmpty()){
            return "vide";
        }
        for(Arme arme: armes){
            str.append(" - "+arme.getNom()+" - ");
        }
        str.append("\n");
        return str.toString();
    }

    /**
     * Affiche le schéma actuel du plateau auquel le combattant est associé.
     * Si aucun plateau n'est associé, affiche un message d'erreur.
     */
    public void afficherMaGrille(){
        System.out.println(this.plateau.creerSchemaPlateau());
    }

    /**
     * Retourne une description détaillée du combattant, y compris son nom, énergie, position,
     * munitions restantes, dégâts de tir, et la liste de ses armes.
     *
     * @return une chaîne contenant les informations détaillées sur le combattant.
     */
    public String afficherDetail() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("Nom : ").append(this.nom).append("\n");
        sb.append("Énergie : ").append(this.energie).append("\n");
        sb.append("Position : (").append(this.x).append(", ").append(this.y).append(")\n");
        sb.append("Munitions : ").append(this.munitions).append("\n");
        sb.append("Dégâts de tir : ").append(this.degatsTire).append("\n");
        sb.append("Armes : ");

        if (armes.isEmpty()) {
            sb.append("Aucune arme");
        } else {
            for (Arme arme : armes) {
                sb.append(arme.getNom()).append(" ");
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Recherche la première cible dans la direction spécifiée.
     * La recherche continue jusqu'à rencontrer un mur ou sortir de la grille.
     *
     * @param direction la direction dans laquelle chercher (DROITE, GAUCHE, HAUT, BAS).
     * @return le combattant trouvé dans la direction donnée, ou null si aucune cible valide n'est trouvée.
     */
    public Combattant chercherCible(Direction direction){
        int dx = 0;
        int dy = 0;

        switch(direction){
            case DROITE:
                dx=1;
                break;
            case GAUCHE:
                dx=-1;
                break;
            case BAS:
                dy=1;
                break;
            case HAUT:
                dy=-1;
                break;
            default:
                System.out.println("Direction invalide.");
        }

        int positionX = this.x + dx;
        int positionY = this.y + dy; 
        Entite cible = null;
                
        //Verifier si la case est dans le plateau
        if(this.plateau.positionValide(positionX,positionY)){
            cible = this.plateau.getCase(positionX,positionY);
        }else{
            System.out.println("Aucune cible sur le viseur.");
            return null;
        }

        //Tant que on a pas trouvé de mur ou de combattant on continue la recherche jusqu'au bout
        while(!cible.getNom().equals("Mur") && !(cible instanceof Combattant)){
            positionX += dx;
            positionY += dy;
            //Verifier si la case est dans le plateau
            if(this.plateau.positionValide(positionX,positionY)){
                cible = this.plateau.getCase(positionX,positionY);
            }else{
                System.out.println("Aucune cible sur le viseur.");
                return null;
            }     
        }
        //Si on trouve un mur avant de trouver un combattant
        if(cible.getNom().equals("Mur")){
            System.out.println("Presence d'un mur.");
        }else if(cible instanceof Combattant){//Si on trouve un combattant avant de trouver un mur
            return (Combattant)cible;
        }

        return null;
    }

    /**
     * Recherche une cible dans toutes les directions possibles (DROITE, GAUCHE, HAUT, BAS).
     * Retourne la direction dans laquelle une cible a été trouvée, ou null si aucune cible n'est trouvée.
     *
     * @return la direction où une cible a été trouvée, ou null si aucune cible n'est présente.
     */
    public Direction chercherCibleDirection(){
        Combattant cible = null;

        for(Direction direction : Direction.values()){
            cible = chercherCible(direction);
            if(cible!=null){
                return direction;
            }
        }
        return null;
    }

    /**
     * Retourne une représentation textuelle simplifiée du Combattant.
     * 
     * Le format est les 2 premieres lettres du nom du combattant, permettant d'identifier rapidement les Combattants sur le plateau.
     *
     * @return une chaîne représentant le Combattant, par exemple "Da" ou "Se".
     */
    @Override
    public String toString() {
        return this.nom.substring(0,2);  
    }

    /**
     * Actualiser l'interface au debut de chaque tour
     */
    public void tourCombattant(){
        fireChange();
    }
}

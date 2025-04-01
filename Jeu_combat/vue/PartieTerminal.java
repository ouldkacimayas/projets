package vue;
import modele.*;

import java.util.*;

/**
 * Classe représentant une partie de jeu en mode terminal.
 * Elle gère l'évolution d'une partie avec plusieurs combattants, les actions des joueurs (déplacement, attaque, etc.),
 * le suivi des tours et la vérification de la fin de la partie.
 */
public class PartieTerminal {
    private PlateauInter plateau; // Grille du jeu où les combattants se déplacent et interagissent
    private int nbTours; // Nombre total de tours avant la fin de la partie
    private List<Combattant> combattants; // Liste des combattants participants
    private int indexTour; // Index du combattant dont c'est le tour
    public static int nombreDeTours; // Compteur de tours globaux
    private Scanner scanner; // Objet pour lire l'entrée de l'utilisateur
    private Boolean stop; // Flag pour arrêter la partie ou pour contrôler le tour du bot

    /**
     * Constructeur de la classe PartieTerminal.
     * Initialise les variables de la partie et prépare l'environnement de jeu.
     * 
     * @param plateau La grille du jeu (PlateauInter) où les combattants interagiront.
     * @param nbTours Le nombre de tours maximum avant la fin de la partie.
     */
    public PartieTerminal(PlateauInter plateau, int nbTours) {
        this.plateau = plateau;
        this.nbTours = nbTours;
        this.combattants = new ArrayList<>();
        this.indexTour = 0;
        this.nombreDeTours = 1;
        this.scanner = new Scanner(System.in);
        this.stop = false;
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
     * @return Le combattant gagnant de la partie.
     */
    public Combattant demarrer(){
        System.out.println("\tPlateau globale");
        afficherLaGrille();
        Combattant combattantActuel = null;
        Combattant survivant = null;
        while (combattants.size() > 1 && nombreDeTours<nbTours) {
            //Recuperer le combattant qui va jouer ce tour ci
            combattantActuel = combattants.get(indexTour);

           System.out.println("\tTours "+this.nombreDeTours+" : tour du joueur "+combattantActuel.getNom()+"\n");

            //Afficher sa propre grille
            combattantActuel.afficherMaGrille();

            // Si c'est un bot, on exécute son tour automatiquement
            if(combattantActuel instanceof Bot){
                tourBot((Bot) combattantActuel);
            }else{
                // Sinon, le joueur humain joue son tour
                jouerTour(combattantActuel);
            }

            //Verification de l'etat du bouclier
            combattantActuel.finDeTour(); 

            //Verifier le compteurs des bombes deposees si il y en a
            verifierLesBombesDeposees();

            //verifier si il y a un combattant qui est mort
            verifierEtatCombattant();

            System.out.println("\n ------------------------------------------------ \n");

            //Gère la protection et la réinitialisation de l'état du bouclier
            indexTour = (indexTour + 1) % combattants.size(); // la div pour permetre de revenir au debut de la list une fois le dernier combattant a 
            nombreDeTours++; 

            // Si le nombre de tours est dépassé, lancer une mort subite
            if(nombreDeTours >= nbTours){
                survivant = mortSubite();
            }
        }

        if(combattants.size() == 1){
            survivant = combattants.get(0);
        }

        // Afficher le gagnant
        if(survivant != null){
            System.out.println("Le gagnant est : " + survivant.getNom());
        }
        
        return survivant;
    }   

    /**
     * Gère le tour d'un combattant humain.
     * Permet au joueur de choisir une action parmi plusieurs options (déplacement, tir, etc.).
     * 
     * @param combattant Le combattant dont c'est le tour de jouer.
     */
    private void jouerTour(Combattant combattant){
        boolean tourJoue = false;
        
        while(!tourJoue){
            System.out.println("Actions:\n '0' pour passer votre tour.\n '1' pour se deplacer.\n '2' pour tirer.\n '3' pour placer une bombe.\n '4' pour placer une mine.\n '5' pour activer votre bouclier.\n '6' pour voir l'inventair.\n '7' pour voir les infos du combattant.\n");

            int action = -1;
            try{
                action = scanner.nextInt();
            }catch(InputMismatchException e){
                System.out.println("Entre invalide veuillez donnez un chiffre correspodant a une action donne.");
                scanner.nextLine();
                continue;
            }
            
            scanner.nextLine();
            switch(action){
                case 0:
                    combattant.neRienFaire();
                    tourJoue = true;
                    break;
                case 1:
                    System.out.println("Vers quelle direction voulez vous vous deplacer? : w:avancer s:reculer a:gauche d:droite x:annuler) ");
                    int x = 0;
                    int y = 0;
                    String direction = ""; 
                    x = combattant.getX();
                    y = combattant.getY();
                    System.out.println("Deplacement ...\n");
                    direction = scanner.nextLine();
                    tourJoue = true;
                    switch(direction){
                        case "w":
                            if(!plateau.deplacerCombattant(x,y-1,combattant)){
                                System.out.println("Impossible de se deplacer vers cette direction\n");
                            }
                            break;
                        case"s":
                            if(!plateau.deplacerCombattant(x,y+1,combattant)){
                                System.out.println("Impossible de se deplacer vers cette direction\n");
                            }
                            break;
                        case"a":
                            if(!plateau.deplacerCombattant(x-1,y,combattant)){
                                System.out.println("Impossible de se deplacer vers cette direction\n");
                            }
                            break;
                        case"d":
                            if(!plateau.deplacerCombattant(x+1,y,combattant)){
                                System.out.println("Impossible de se deplacer vers cette direction\n");
                            }
                            break;
                        default:
                            System.out.println("Entree invalide veuillez reessayer.\n");
                            tourJoue = false;
                            break;
                    }
                    
                    combattant.afficherMaGrille();
                    break;
                case 2:
                    System.out.println("Vers quelle direction voulez-vous tirer ?");
                    for (Direction dir : Direction.values()) {
                        System.out.println("- " + dir.ordinal() + ": " + dir.name());
                    }

                    // Lire l'entrée de l'utilisateur
                    System.out.print("Choisissez une direction (entrez le numéro) : ");
                    int choixDirection = scanner.nextInt();

                    // Vérifier si l'entrée est valide
                    if (choixDirection >= 0 && choixDirection < Direction.values().length) {
                        Direction directionTire = Direction.values()[choixDirection];
                        combattant.tirer(directionTire); // Appel de la méthode tirer avec une Direction
                        tourJoue = true;
                    } else {
                        System.out.println("Choix invalide !");
                    }
                    break;
                case 3:
                    System.out.println("Sur quelle case vous voulez poser une bombe? :\n\t'q' 'w' 'e'\n\t'a'    'd'\n\t'z' 'x' 'c'\n");
                    String posBombe = scanner.nextLine();
                    int[] posBombeXY = getCoordonnees(combattant.getX(), combattant.getY(),posBombe);
                    Bombe bombeDepose = combattant.deposerBombe(posBombeXY[0],posBombeXY[1],nombreDeTours);
                    if(bombeDepose!=null){
                        this.plateau.ajouterBombe(bombeDepose);
                        System.out.println("Bombe deposee avec succes.\n");
                        tourJoue = true;
                    }else{
                        System.out.println("Bombe non depose.\n");
                    }
                    break;
                case 4:
                    System.out.println("Sur quelle case vous voulez poser une mine? :\n\t'q' 'w' 'e'\n\t'a'    'd'\n\t'z' 'x' 'c'\n");
                    String posMine = scanner.nextLine();
                    int[] posMineXY = getCoordonnees(combattant.getX(), combattant.getY(),posMine);

                    if(combattant.deposerMine(posMineXY[0],posMineXY[1])){
                        System.out.println("Mine deposee avec succes.\n");
                        tourJoue = true;
                    }else{
                        System.out.println("Mine non depose.\n");
                    }
                    break;
                case 5:
                    combattant.activerBouclier();
                    tourJoue = true;
                    break;
                case 6:
                    System.out.println("inventaire :"+combattant.inventaire());
                    break;
                case 7:
                    System.out.println("Etat du combattant :\n"+combattant.afficherDetail());
                    break;
                default:
                    System.out.println("Entree invalide.");
                    break;
            }
        }   
    }

    /**
     * Gère le tour d'un bot, en exécutant automatiquement son comportement.
     * 
     * @param botActuel Le bot dont c'est le tour de jouer.
     */
    public void tourBot(Bot botActuel){
        botActuel.declencherComportement();
        if(stop){
            System.out.println("Appuiyez sur 'Entre' pour passer au tour suivant: \n");
            this.scanner.nextLine(); 
        }
    }
        
    /**
     * Recupere le plateau.
     * 
     * @return plateau globale.
     */    
    public PlateauInter getPlateau() {
        return this.plateau;
    }

    /**
     * Recupere le nombre de tours actuel.
     * 
     * @return le nombre de tours actuel.
     */
    public int getNbTours(){
        return this.nbTours;
    }

    /**
     * Modifier le flag stop pour choisir de controller la simulation d'une partie entre robots ou non.
     * 
     * @param stop true si on veut stoper la simulation entre chaque tour et false sinon.
     */
    public void setStop(boolean stop){
        this.stop = stop;
    }

    /**
     * Vérifie les bombes déposées et les fait exploser si nécessaire.
     */
    public void verifierLesBombesDeposees(){
        List<Bombe> bombesDeposees = this.plateau.getBombesDeposees();
        for(Bombe bombe:bombesDeposees){
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

    /**
     * Affiche la grille du plateau actuel.
     */
    public void afficherLaGrille(){
        System.out.println(this.plateau.creerSchemaPlateau());
    }

    /**
     * Calcule les nouvelles coordonnées d'un combattant en fonction de la direction choisie.
     * 
     * @param x        La coordonnée x initiale.
     * @param y        La coordonnée y initiale.
     * @param direction La direction choisie (w, s, a, d).
     * @return Un tableau contenant les nouvelles coordonnées [x, y].
     */
    public int[] getCoordonnees(int x, int y, String direction){
        switch(direction){
            case "q":
                x -= 1;
                y -= 1;
                break;
            case "w":
                y -= 1;
                break;
            case "e":
                x += 1;
                y -= 1;
                break;
            case "a":
                x -= 1;
                break;
            case "s":
                break;
            case "d":
                x += 1;
                break;
            case "z":
                x -= 1;
                y += 1;
                break;
            case "x":
                y += 1;
                break;
            case "c":
                x += 1;
                y += 1;
                break;
            default:
                System.out.println("Entree invalide.");
                break;
        }
        int[] coordonneesXY = {x,y};
        return coordonneesXY;
    }

    /**
     * Vérifie si un combattant est mort (énergie <= 0).
     * Si oui, le retire de la liste des combattants.
     */
    public void verifierEtatCombattant() {
        Iterator<Combattant> iterator = combattants.iterator();
        while (iterator.hasNext()) {
            Combattant combattant = iterator.next();
            if (combattant.getEnergie() <= 0) {
                System.out.println("Le combattant: " + combattant.getNom() + " est elimine!");
                iterator.remove();  // Utilise l'Iterator pour supprimer l'élément
            }
        }
    }

    /**
     * Gère la fin de la partie en cas de "mort subite", où le combattant avec le plus d'énergie restante est déclaré gagnant.
     * 
     * @return Le combattant gagnant de la "mort subite".
     */
    public Combattant mortSubite(){
        System.out.println("Mort subite.");
        int max = 0;
        Combattant survivant = null;
        for(Combattant combattant:this.combattants){
            int energieRestante = combattant.getEnergie();
            if(energieRestante > max){
                max = energieRestante;
                survivant = combattant;
            }
        }

       return survivant;
    }

}
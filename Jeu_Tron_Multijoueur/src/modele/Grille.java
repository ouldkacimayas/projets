package modele;
import java.util.*;
/**
 * Classe représentant la grille du jeu et la gestion des joueurs, de leurs déplacements et des équipes.
 */

class Grille {
    private int taille;
    private Entite[][] grille;
    private List<Joueur> joueurs;
    private Map<Integer, List<Joueur>> equipes;
    private int numHeurisitque;

    public Grille(int taille) {
        this.taille = taille;
        this.grille = new Entite[taille][taille];
        this.joueurs = new ArrayList<>();
        this.equipes = new HashMap<Integer,List<Joueur>>();
        this.numHeurisitque = 1;
        // Initialiser la grille avec des cases vides
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j] = null;
            }
        }
    }
    public int getTaille() { return this.taille; }
    public Entite[][] getGrille() { return this.grille; }

    // Ajouter un joueur
    public void ajouterJoueur(Joueur joueur) {
        int x = joueur.getX();
        int y = joueur.getY();
        
        while(!estValide(x,y)){
            Random random = new Random();
            x = random.nextInt(this.taille);
            y = random.nextInt(this.taille);
        }

        joueur.setPosition(x,y);

        joueurs.add(joueur);
        grille[joueur.getX()][joueur.getY()] = joueur;
    }

    public void ajouterJoueurDansEquipe(int equipe, Joueur joueur) {
        if (joueur.getEquipe() != -1) {
            System.out.println("Joueur " + joueur.getNum() + " est déjà dans une équipe !");
            return;
        }

        joueur.setEquipe(equipe);
        equipes.putIfAbsent(equipe, new ArrayList<>());
        equipes.get(equipe).add(joueur);
    }

    public Map<Integer, List<Joueur>> getEquipes() {
        return equipes;
    }

    public void setHeuristique(int numHeurisitque){
        this.numHeurisitque = numHeurisitque;
    }

    public int getHeuristique(){
        return this.numHeurisitque;
    }

    /**
     * Vérifie si une position donnée est valide (dans les limites et non occupée).
     * 
     */
    public boolean estValide(int x, int y) {
        return x >= 0 && x < taille && y >= 0 && y < taille && grille[x][y] == null;
    }
    /**
     * Retourne une liste des déplacements possibles pour un joueur.
     * Un déplacement est valide si la case adjacente est vide.
     * 
     */
    public List<int[]> deplacementsPossibles(Joueur joueur) {
       List<int[]> deplacements = new ArrayList<>();
     
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        for (int[] dir : directions) {
            int nx = joueur.x + dir[0];
            int ny = joueur.y + dir[1];

            if(estValide(nx, ny)){
                deplacements.add(new int[]{nx, ny});
            }
        }
     
        return deplacements;

    }
    /**
     * Trouve un joueur par son ID.
     * 
     */
    private Joueur trouverJoueurParId(int idJoueur) {
    for (Joueur joueur : joueurs) {
        if (joueur.getNum() == idJoueur) {
            return joueur; 
        }
    }
    return null; 
    }
    /**
     * Effectue un déplacement pour un joueur.
     * 
     */
    public boolean effectuerDeplacement(Joueur joueur, int[] direction) {
        
        int x = joueur.getX();
        int y = joueur.getY();

    
        int nouvX = direction[0];
        int nouvY = direction[1];

    
        if (estValide(nouvX, nouvY)) {
            
            grille[nouvX][nouvY] = joueur;  
            joueur.setPosition(nouvX,nouvY);
            grille[x][y] = new Mur(x,y);

            return true;  
        }

        return false;  
    }
    /**
     * Annule un déplacement pour un joueur, le ramenant à sa position précédente.
     * 
     */
    public boolean annulerDeplacement(Joueur joueur, int[] positionPrec) {
        
        int x = joueur.getX();
        int y = joueur.getY();
        grille[x][y] = null;
        joueur.setPosition(positionPrec[0], positionPrec[1]);
        grille[positionPrec[0]][positionPrec[1]] = joueur;

        return true;
    }

   

    /**
     * Affiche la grille dans la console.
     * Représente les joueurs par leur identifiant et les murs par un symbole.
     */

    public void afficher() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if(grille[i][j] == null){
                    System.out.print(".");
                }else{
                    grille[i][j].afficher();
                }
            }
            System.out.println();
        }
    }

    /**
     * Vérifie si le jeu est terminé. Le jeu est terminé lorsqu'il ne reste plus qu'un joueur.
     * 
     * @return true si le jeu est terminé, false sinon.
     */
    public boolean estTerminee() {
        return joueurs.size() <= 1;
    }


    // Obtenir les joueurs restants
    public List<Joueur> getJoueurs() {
        return this.joueurs;
    }

    public void ajouterMur(int x, int y) {
        this.grille[x][y] = new Mur(x, y);
    }
    /**
     * Évalue la grille en calculant les scores des joueurs en fonction de l'heuristique choisie.
     * 
     */
    public int[] evaluer() {
    int[] scores = new int[this.joueurs.size()];
    for (int i = 0; i < this.joueurs.size(); i++) {
        Joueur joueur = this.joueurs.get(i);
        if (deplacementsPossibles(joueur).isEmpty()) {
            scores[i] = Integer.MIN_VALUE; 
            System.out.println("Joueur " + joueur.getNum() + " est bloqué, score = " + scores[i]);
        } else {
            if(this.numHeurisitque == 2){
                scores[i] = heuristiqueAvancee(joueur);
                                System.out.println("Joueur " + joueur.getNum() + " heuristique avancée, score = " + scores[i]);

            }else{
                scores[i] = heuristique(joueur);
            }
        }
    }
    return scores;
    }

    /**
     * Calcule le score pour un joueur en utilisant l'heuristique de base.
     * 
     */
    private int heuristique(Joueur joueur) {
            Set<String> visited = new HashSet<>();
            Queue<int[]> toVisit = new LinkedList<>();
            toVisit.add(new int[]{joueur.x, joueur.y});
            int score = 0;

            while (!toVisit.isEmpty()) {
                int[] pos = toVisit.poll();
                String key = pos[0] + "," + pos[1];
                if (!visited.contains(key)) {
                    visited.add(key);
                    score++;
                    for (int[] dir : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                        int nx = pos[0] + dir[0];
                        int ny = pos[1] + dir[1];
                        if (estValide(nx, ny)) {
                            toVisit.add(new int[]{nx, ny});
                            break; //pour suivre un chemin sans retour en arrier
                        }
                    }
                }
            }
            System.out.println("Score final de l'heuristique basique pour le joueur " + joueur.getNum() + ": " + score);
            return score;
        }

    /**
     * Calcule le score pour un joueur en utilisant l'heuristique avancée.
     * 
     */
    public int heuristiqueAvancee(Joueur joueur) {
        int score = 0;
        int x = joueur.getX();
        int y = joueur.getY();
        int taillegrille = this.taille;

        //  Espace de mouvement
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int mouvementsPossibles = 0;
        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (estValide(nx, ny)) {
                mouvementsPossibles++;
            }
        }

        // Penaliser fortement l'absence de mouvements
        if (mouvementsPossibles == 0) {
            return Integer.MIN_VALUE;
        }
        score += mouvementsPossibles * 10;  // Bonus pour les mouvements disponibles

        // . Position par rapport aux autres joueurs : distance minimale pour eviter les collisions
        for (Joueur autreJoueur : joueurs) {
            if (autreJoueur != joueur) {
                int distanceAutreJoueur = Math.abs(x - autreJoueur.getX()) + 
                                        Math.abs(y - autreJoueur.getY());
                // Bonus pour plus de distance avec les autres joueurs
                score += distanceAutreJoueur;
            }
        }

        // . Controle de l'espace : bonus pour etre proche du centre et eviter les bords
        int centreX = taillegrille / 2;
        int centreY = taillegrille / 2;
        int distanceCentre = Math.abs(x - centreX) + Math.abs(y - centreY);
        score += (taillegrille - distanceCentre) * 5;

        // . eviter les zones dangereuses :( bords  murs )
        if (x == 0 || y == 0 || x == taillegrille - 1 || y == taillegrille - 1) {
            score -= 20; // Penalite si le joueur est trop pres du bord
        }
        score += calculerEspaceLibre(joueur) * 2; // Bonus pour un espace libre important
        return score;
    }
    private int calculerEspaceLibre(Joueur joueur) {
        Set<String> visite = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{joueur.getX(), joueur.getY()});
        
        int espace = 0;
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int x = pos[0], y = pos[1];
            String key = x + "," + y;
            
            if (visite.contains(key) || !estValide(x, y)) continue;
            visite.add(key);
            espace++;

            // Ajouter les 4 directions
            queue.add(new int[]{x - 1, y});
            queue.add(new int[]{x + 1, y});
            queue.add(new int[]{x, y - 1});
            queue.add(new int[]{x, y + 1});
        }
        return espace;
    }

    public void eliminerJoueur(Joueur joueur) {
        joueurs.remove(joueur);
        grille[joueur.getX()][joueur.getY()] = null;

        if (equipes != null && equipes.containsKey(joueur.getEquipe())) { 
            equipes.get(joueur.getEquipe()).remove(joueur);
            if (equipes.get(joueur.getEquipe()).isEmpty()) {
                equipes.remove(joueur.getEquipe());
            }
        }
    }

}
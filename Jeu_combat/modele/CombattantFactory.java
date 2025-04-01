package modele;

/**
 * La classe CombattantFactory est une fabrique permettant de créer des objets 
 * de type Combattant ou Bot en fonction du type spécifié.
 * Elle fournit des méthodes statiques pour générer des combattants ou des bots de 
 * différents types avec des caractéristiques spécifiques.
 * 
 * Chaque type de combattant ou de bot a un ensemble d'armes et de caractéristiques qui lui sont associés, permettant ainsi de varier le gameplay en fonction des besoins.
 */
public class CombattantFactory {
    /**
     * Crée un combattant en fonction du type spécifié.
     * 
     * @param type le type du combattant à créer (ex. "guerrier", "sniper", "tank", "ingenieur", "simple").
     * @param nom le nom du combattant.
     * @param x la position x initiale du combattant sur le plateau.
     * @param y la position y initiale du combattant sur le plateau.
     * 
     * @return un nouvel objet Combattant avec des caractéristiques adaptées au type spécifié.
     * @throws IllegalArgumentException si le type de combattant est inconnu.
     */
    public static Combattant creerCombattant(String type, String nom, int x, int y) {
        switch (type.toLowerCase()) {
            case "guerrier":
                // Guerrier : beaucoup d'énergie, mais peu d'armes
                Combattant guerrier = new Combattant(nom, 100, x, y);
                guerrier.ajouterArme(new Mine(20, 5, x, y));
                guerrier.ajouterArme(new Bombe(15, 3, 2, x, y));
                guerrier.setMunitions(5); // Réserve de munitions standard
                return guerrier;

            case "sniper":
                // Sniper : peu d'énergie, mais des armes puissantes
                Combattant sniper = new Combattant(nom, 60, x, y);
                sniper.ajouterArme(new Bombe(25, 4, 3, x, y)); // Bombe puissante
                sniper.setMunitions(10); // Plus de munitions pour tirer à distance
                sniper.setDegatsTire(10); // Dégâts de tir plus élevés
                return sniper;

            case "tank":
                // Tank : très résistant, mais peu mobile
                Combattant tank = new Combattant(nom, 150, x, y);
                tank.ajouterArme(new Mine(30, 6, x, y)); // Mine puissante
                tank.ajouterArme(new Bouclier(5, x, y)); // Bouclier renforcé
                tank.setMunitions(3); // Moins de munitions
                return tank;

            case "ingenieur":
                // Ingénieur : énergie modérée, bon en support
                Combattant ingenieur = new Combattant(nom, 80, x, y);
                ingenieur.ajouterArme(new Bouclier(2, x, y)); // Petit bouclier
                ingenieur.ajouterArme(new Bombe(10, 2, 1, x, y)); // Petite bombe
                ingenieur.setMunitions(8); // Plus de munitions
                return ingenieur;
            case "simple":
                // Combattant simple : uniquement les valeurs par défaut
                return new Combattant(nom, 50, x, y); // 

            default:
                throw new IllegalArgumentException("Type de combattant inconnu : " + type);
        }
    }

    /**
     * Crée un bot en fonction du type spécifié.
     * 
     * @param type le type du bot à créer (ex. "guerrier", "sniper", "tank", "ingenieur", "simple").
     * @param nom le nom du bot.
     * @param x la position x initiale du bot sur le plateau.
     * @param y la position y initiale du bot sur le plateau.
     * @param comportement le comportement du bot (ex. attaquant, défenseur).
     * 
     * @return un nouvel objet {@code Bot} avec des caractéristiques adaptées au type spécifié.
     * @throws IllegalArgumentException si le type de bot est inconnu.
     */
    public static Bot creerBot(String type, String nom, int x, int y,Comportement comportement){
        switch (type.toLowerCase()) {
            case "guerrier":
                // Guerrier : beaucoup d'énergie, mais peu d'armes
                Bot guerrier = new Bot(nom, 100, x, y, comportement);
                guerrier.ajouterArme(new Mine(20, 5, x, y));
                guerrier.ajouterArme(new Bombe(15, 3, 2, x, y));
                guerrier.setMunitions(5); // Réserve de munitions standard
                return guerrier;

            case "sniper":
                // Sniper : peu d'énergie, mais des armes puissantes
                Bot sniper = new Bot(nom, 60, x, y, comportement);
                sniper.ajouterArme(new Bombe(25, 4, 3, x, y)); // Bombe puissante
                sniper.setMunitions(10); // Plus de munitions pour tirer à distance
                sniper.setDegatsTire(10); // Dégâts de tir plus élevés
                return sniper;

            case "tank":
                // Tank : très résistant, mais peu mobile
                Bot tank = new Bot(nom, 150, x, y, comportement);
                tank.ajouterArme(new Mine(30, 6, x, y)); // Mine puissante
                tank.ajouterArme(new Bouclier(5, x, y)); // Bouclier renforcé
                tank.setMunitions(3); // Moins de munitions
                return tank;

            case "ingenieur":
                // Ingénieur : énergie modérée, bon en support
                Bot ingenieur = new Bot(nom, 80, x, y, comportement);
                ingenieur.ajouterArme(new Bouclier(2, x, y)); // Petit bouclier
                ingenieur.ajouterArme(new Bombe(10, 2, 1, x, y)); // Petite bombe
                ingenieur.setMunitions(8); // Plus de munitions
                return ingenieur;
            case "simple":
                // Bot simple : uniquement les valeurs par défaut
                return new Bot(nom, 50, x, y, comportement); // 

            default:
                throw new IllegalArgumentException("Type de bot inconnu : " + type);
        }
    }
}

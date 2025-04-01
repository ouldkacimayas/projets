package modele;

/**
 * La classe ComportementAttaquant implémente le comportement d'un bot qui adopte une stratégie d'attaque.
 * Ce comportement permet au bot de chercher une cible dans une direction donnée et d'attaquer si une cible est trouvée.
 * Si aucune cible n'est trouvée, le bot effectue un déplacement aléatoire.
 * 
 * Ce comportement est utilisé pour les bots qui doivent adopter une stratégie offensive pendant le jeu.
 */
public class ComportementAttaquant implements Comportement {

    /**
     * Exécute l'action d'attaque pour le bot spécifié. Le bot va chercher une cible et, si une cible est trouvée,
     * il effectuera une attaque. Si aucune cible n'est trouvée, le bot se déplace de manière aléatoire.
     * 
     * Si le bot a une énergie inférieure ou égale à 1, il ne fait rien.
     * 
     * @param bot le bot sur lequel l'action d'attaque va être exécutée.
     */
    @Override
    public void agir(Bot bot){
        // Si l'énergie du bot est trop faible, il ne fait rien
        if(bot.getEnergie()<=1){
            bot.neRienFaire();
            return;
        }
        
        System.out.println(bot.getNom() + " adopte un comportement attaquant.");

        // Cherche une cible et tire si elle est trouvée
        Direction direction = bot.chercherCibleDirection();
        if(direction != null){
            bot.tirer(direction);// Tire dans la direction de la cible
        } else {
            // Si aucune cible n'est trouvée, se déplace de manière aléatoire
            System.out.println(bot.getNom() + " ne trouve aucune cible. Il se déplace aleatoirement.");
            bot.seDeplacerAleatoirement(); 
        }
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du comportement d'attaque.
     * 
     * @return une chaîne représentant le comportement d'attaque, ici "Attaque".
     */
    public String toString(){
        return "Attaque";
    }

}

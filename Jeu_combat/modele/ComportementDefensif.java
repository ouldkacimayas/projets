package modele;

/**
 * La classe ComportementDefensif implémente le comportement d'un bot qui adopte une stratégie défensive.
 * Ce comportement permet au bot de se défendre en activant un bouclier si son énergie est faible, ou de rester sur place pour protéger une position stratégique si son énergie est suffisante.
 * 
 * Ce comportement est utilisé pour les bots qui doivent adopter une stratégie de défense pendant le jeu.
 */
public class ComportementDefensif implements Comportement {

    /**
     * Exécute l'action défensive pour le bot spécifié. 
     * Si l'énergie du bot est inférieure à 50, le bot active son bouclier pour se protéger.
     * Sinon, le bot reste sur place pour protéger une position stratégique et ne fait rien.
     * 
     * @param bot le bot sur lequel l'action défensive va être exécutée.
     */
    @Override
    public void agir(Bot bot) {
        System.out.println(bot.getNom() + " adopte un comportement défensif.");

        // Si l'énergie du bot est inférieure à 50, il active son bouclier
        if (bot.getEnergie() < 50) {
            bot.activerBouclier(); // Le bot se défend en activant son bouclier
        } else {// Sinon, le bot reste sur place pour se défendre stratégiquement
            System.out.println(bot.getNom() + " reste sur place pour protéger une position stratégique.");
            bot.neRienFaire();// Ne fait rien
        }
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du comportement défensif.
     * 
     * @return une chaîne représentant le comportement défensif, ici "Deffense".
     */
    public String toString(){
        return "Deffense";
    }

}

package modele;
import vue.PartieTerminal;

/**
 * La classe ComportementSaboteur implémente le comportement d'un bot qui agit de manière sabotante.
 * Ce comportement permet au bot de chercher à poser des mines ou des bombes dans les cases voisines de sa position.
 * Si le bot ne peut pas poser de mine ou de bombe, il se déplace aléatoirement.
 * 
 * Ce comportement est utilisé pour les bots qui tentent de perturber ou d'influencer le jeu en plaçant des pièges
 * sur le terrain sous forme de mines et de bombes.
 */
public class ComportementSaboteur implements Comportement{
    /**
     * Exécute l'action de sabotage pour le bot spécifié.
     * Le bot vérifie les cases voisines (haut, bas, gauche, droite) pour voir s'il peut y poser une mine ou une bombe.
     * Si le bot trouve une case vide valide, il tente de poser une bombe ou une mine.
     * Si aucune action de sabotage n'est possible, le bot se déplace aléatoirement.
     * 
     * @param bot le bot sur lequel l'action de sabotage va être exécutée.
     */
    @Override
    public void agir(Bot bot) {
        PlateauInter plateau = bot.getPlateau();
        Bombe bombeDeposee = null;
        boolean mineDeposee = false;

        // Vérifier les cases voisines pour poser des bombes ou des mines
        for (Direction direction : Direction.values()) {
            int caseX = bot.getX() + direction.getDeltaX();
            int caseY = bot.getY() + direction.getDeltaY();

            // Verifier si on peut deposer une mine
            if (plateau.positionValide(caseX, caseY) && plateau.getCase(caseX, caseY) instanceof Vide) {
                    // Essayer de déposer une bombe
                    bombeDeposee = bot.deposerBombe(caseX, caseY, PartieTerminal.nombreDeTours);

                    // Si aucune bombe n'a été déposée, essayer de poser une mine
                    if(bombeDeposee == null){
                        mineDeposee = bot.deposerMine(caseX,caseY);
                    }                    
            }
        }

        // Si aucune mine ou bombe n'a été déposée, se déplacer aléatoirement
        if(bombeDeposee==null || !mineDeposee){
            bot.seDeplacerAleatoirement();
        }
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du comportement de sabotage.
     * 
     * @return une chaîne représentant le comportement de sabotage, ici "Sabotage".
     */
    public String toString(){
        return "Sabotage";
    }

}

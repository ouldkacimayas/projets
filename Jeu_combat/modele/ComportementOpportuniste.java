package modele;

/**
 * La classe ComportementOpportuniste implémente le comportement d'un bot qui adopte une stratégie opportuniste.
 * Ce comportement permet au bot de chercher et ramasser des objets tels que des armes, de l'énergie ou des munitions
 * dans les cases voisines. Si aucun objet n'est trouvé, le bot se déplace de manière aléatoire.
 * 
 * Ce comportement est utilisé pour les bots qui prennent des décisions en fonction des opportunités qui se présentent autour d'eux, notamment en ramassant des objets pour améliorer leur état.
 */
public class ComportementOpportuniste implements Comportement{

    /**
     * Exécute l'action opportuniste pour le bot spécifié.
     * Le bot vérifie les cases voisines (haut, bas, gauche, droite) pour ramasser des objets comme des armes, de l'énergie ou des munitions. Si un objet est trouvé, le bot se déplace vers cette case et le ramasse.
     * Si aucun objet n'est trouvé, le bot se déplace de manière aléatoire.
     * 
     * @param bot le bot sur lequel l'action opportuniste va être exécutée.
     */
    @Override
    public void agir(Bot bot) {
        PlateauInter plateau = bot.getPlateau();
        Boolean objetRamasse = false;
        // Vérifier les cases voisines pour chercher des armes ou bonus
        for (Direction direction : Direction.values()) {
            int caseX = bot.getX() + direction.getDeltaX();
            int caseY = bot.getY() + direction.getDeltaY();

            // Si la case est valide et contient un objet intéressant, le bot se déplace dessus
            if (plateau.positionValide(caseX, caseY)){
                Entite caseXY =  plateau.getCase(caseX, caseY);
                if(caseXY instanceof Arme || caseXY instanceof Energie || caseXY instanceof Munitions){
                    plateau.deplacerCombattant(caseX, caseY, bot);// Le bot ramasse l'objet
                    objetRamasse = true;
                    break;// Le bot ramasse un objet et s'arrête
                }
            }
        }
        // Si aucun objet n'est trouvé, le bot se déplace aléatoiremen
        if(!objetRamasse){
            bot.seDeplacerAleatoirement();
        }
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du comportement opportuniste.
     * 
     * @return une chaîne représentant le comportement opportuniste, ici "Opportunisme".
     */
    public String toString(){
        return "Opportunisme";
    }
}

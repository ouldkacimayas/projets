import modele.*;
import vue.*;
import java.util.*;

public class AppBots {
    public static final int NB_PATIES = 20;
   public static void main(String[] args){
        PlateauInter plateauGlobale = null;
        String[] bots = {"bot1", "bot2", "bot3", "bot4"};
        Map<String,Integer> scores = new  HashMap<String,Integer>();
        for (String bot : bots) {
            scores.put(bot, 0);
        }

        for(int i=0; i<NB_PATIES; i++){
            // Création d'un plateau de 10x10 avec des entités
            RemplirGrille remplirEquillibre = new RemplissageEquillibre();
            plateauGlobale = new Plateau(10, 10, remplirEquillibre);
            PartieTerminal partie = new PartieTerminal(plateauGlobale, 200);


            Comportement comportementAttaquant = new ComportementAttaquant();
            Bot bot1 = CombattantFactory.creerBot("simple", bots[0], 2, 2,comportementAttaquant);
            PlateauInter plateauPartiel1 = new PlateauCombattant(plateauGlobale,bot1);
            bot1.ajouterPlateau(plateauPartiel1);

            Comportement comportementDefensif = new ComportementDefensif();
            Bot bot2 = CombattantFactory.creerBot("simple", bots[1], 2, 2,comportementDefensif);
            PlateauInter plateauPartiel2 = new PlateauCombattant(plateauGlobale,bot2);
            bot2.ajouterPlateau(plateauPartiel2);

            Comportement comportementOpportuniste = new ComportementOpportuniste();
            Bot bot3 = CombattantFactory.creerBot("simple", bots[2], 2, 2,comportementOpportuniste);
            PlateauInter plateauPartiel3 = new PlateauCombattant(plateauGlobale,bot3);
            bot3.ajouterPlateau(plateauPartiel3);

            Comportement comportementSaboteur = new ComportementSaboteur();
            Bot bot4 = CombattantFactory.creerBot("simple", bots[3], 2, 2,comportementSaboteur);
            PlateauInter plateauPartiel4 = new PlateauCombattant(plateauGlobale,bot4);
            bot4.ajouterPlateau(plateauPartiel4);

            partie.ajouterCombattant(bot1);
            partie.ajouterCombattant(bot2);
            partie.ajouterCombattant(bot3);
            partie.ajouterCombattant(bot4);
            

            try{
                Combattant gagnant = partie.demarrer();
                scores.put(gagnant.getNom(), scores.get(gagnant.getNom()) + 1);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("\n*********************************************\n");
        String meilleurRobot = null;
        int maxVictoires = 0;

        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            if (entry.getValue() > maxVictoires) {
                maxVictoires = entry.getValue();
                meilleurRobot = entry.getKey();
            }
        }

        System.out.println("\nScores finaux :");
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " victoires");
        }

        System.out.println("\nLe meilleur robot est : " + meilleurRobot + " avec " + maxVictoires + " victoires !");

        String meilleurStrategie = null;
        if(plateauGlobale != null){
            Bot bot = (Bot)plateauGlobale.getCombattantParNom(meilleurRobot);
            Comportement meilleurComportement = bot.getComportement();
            meilleurStrategie = meilleurComportement.toString();
        }   

        System.out.println("\nLa meilleur strategie est donc: "+meilleurStrategie);
    }

}

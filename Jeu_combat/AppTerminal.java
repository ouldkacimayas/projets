import modele.*;
import vue.*;

public class AppTerminal {
   public static void main(String[] args){
        // Création d'un plateau de 10x10 avec des entités
        RemplirGrille remplirEquillibre = new RemplissageEquillibre();
        PlateauInter plateauGlobale = new Plateau(10, 10, remplirEquillibre);
        PartieTerminal partie = new PartieTerminal(plateauGlobale,100);

        Combattant combattant1 = CombattantFactory.creerCombattant("guerrier", "Sett", 0, 0);
        PlateauInter plateauPartiel1 = new PlateauCombattant(plateauGlobale,combattant1);
        combattant1.ajouterPlateau(plateauPartiel1);

        Combattant combattant2 = CombattantFactory.creerCombattant("sniper", "Darius", 1, 1);
        PlateauInter plateauPartiel2 = new PlateauCombattant(plateauGlobale,combattant2);
        combattant2.ajouterPlateau(plateauPartiel2);

        Combattant combattant3 = CombattantFactory.creerCombattant("tank", "Garen", 2, 2);
        PlateauInter plateauPartiel3 = new PlateauCombattant(plateauGlobale,combattant3);
        combattant3.ajouterPlateau(plateauPartiel3);

        Comportement comportementAttaquant = new ComportementAttaquant();
        Bot bot1 = CombattantFactory.creerBot("sniper", "bot1", 2, 2,comportementAttaquant);
        PlateauInter plateauPartiel4 = new PlateauCombattant(plateauGlobale,bot1);
        bot1.ajouterPlateau(plateauPartiel4);

        Comportement comportementDefensif = new ComportementDefensif();
        Bot bot2 = CombattantFactory.creerBot("ingenieur", "bot2", 2, 2,comportementDefensif);
        PlateauInter plateauPartiel5 = new PlateauCombattant(plateauGlobale,bot2);
        bot2.ajouterPlateau(plateauPartiel5);

        partie.ajouterCombattant(combattant1);
        partie.ajouterCombattant(combattant2);
        partie.ajouterCombattant(combattant3);
        partie.ajouterCombattant(bot1);
        partie.ajouterCombattant(bot2);

        try{
            partie.demarrer();
        }catch(Exception e) {
            e.printStackTrace();
        }
    
    }

}

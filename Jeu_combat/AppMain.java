import modele.*;
import vue.*;
import java.util.Scanner;
import java.util.*;
import javax.swing.SwingUtilities; 

public class AppMain {
 public static final int NB_PATIES = 20;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
       
        System.out.println("Bienvenue dans le jeu de combat !");
        System.out.println("Veuillez sélectionner une option :");
        System.out.println("1 - Partie avec Interface Graphique");
        System.out.println("2 - Partie Terminale");
        System.out.println("3 - Partie Bots");
        System.out.print("Votre choix : ");

        int choix = scanner.nextInt();

        switch (choix) {
            case 1:
                lancerPartieGraphique();
                break;
            case 2:
                lancerPartieTerminal();
                break;
            case 3:
                lancerPartieBots();
                break;
            default:
                System.out.println("Option invalide. Veuillez relancer le programme.");
        }

        scanner.close();
    }

    /**
     * Lance une partie avec une interface graphique.
     */
    private static void lancerPartieGraphique() {
        // Création d'un plateau de 10x10 avec des entités
        RemplirGrille remplirEquillibre = new RemplissageEquillibre();
        Plateau plateauGlobale = new Plateau(10, 10, remplirEquillibre);
        PartieGraphic partie = new PartieGraphic(plateauGlobale);

        Combattant combattant1 = CombattantFactory.creerCombattant("guerrier", "Sett", 0, 0);
              
        PlateauInter plateauPartiel1 = new PlateauCombattant(plateauGlobale,combattant1);
        combattant1.ajouterPlateau(plateauPartiel1);

        Combattant combattant2 = CombattantFactory.creerCombattant("sniper", "Darius", 1, 1);
        PlateauInter plateauPartiel2 = new PlateauCombattant(plateauGlobale,combattant2);
        combattant2.ajouterPlateau(plateauPartiel2);



        partie.ajouterCombattant(combattant1);
        
        partie.ajouterCombattant(combattant2);
     

      Vue v = new Vue(plateauGlobale,partie);
      Controller controlleur = new Controller(plateauGlobale,v,partie); 
      try {
       Combattant gagnant = partie.demarrer();
        SwingUtilities.invokeLater(new Runnable() {
        @Override
         public void run() {
            v.dispose();  // Ferme la fenêtre de la vue
            }
        });
      }catch(Exception e) {
        e.printStackTrace(); 
      }
    }

    /**
     * Lance une partie en terminal.
     */
    private static void lancerPartieTerminal() {
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

    /**
     * Lance une partie Bots.
     */
    private static void lancerPartieBots() {
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


import modele.*;
import vue.*;
import javax.swing.SwingUtilities;
public class App {
   public static void main(String[] args){
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

}

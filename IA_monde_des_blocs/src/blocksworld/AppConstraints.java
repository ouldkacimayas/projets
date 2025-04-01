package blocksworld;

import modelling.*;
import java.util.*;
import javax.swing.JFrame;
import bwmodel.*;
import bwui.*;

public class AppConstraints{
    public static void main(String[] args){

        int nbBlocks = 5;
        int nbPiles = 2;
        RegularBlocksWorld bw = new RegularBlocksWorld(nbBlocks, nbPiles);
        

        // conf1 : configuration qui satisfait toutes les contraintes reguliere       
        Map<Variable,Object> instVar = new LinkedHashMap<Variable,Object>();
        List<Variable> list = new ArrayList<Variable>(bw.getOnB()); 
     
        instVar.put(list.get(0),1);
        instVar.put(list.get(1),-1);
        instVar.put(list.get(2),3);
        instVar.put(list.get(3),4);
        instVar.put(list.get(4),-2);
    
    

        List<BooleanVariable> list2 = new ArrayList<BooleanVariable>(bw.getFixedB());

        System.out.println("\n\n\n");
        instVar.put(list2.get(0),false);
        instVar.put(list2.get(1),true);
        instVar.put(list2.get(2),false);
        instVar.put(list2.get(3),true);
        instVar.put(list2.get(4),true);
      
   
        
        List<BooleanVariable> list3 = new ArrayList<BooleanVariable>(bw.getFreeP()); 

        
        instVar.put(list3.get(0),false);//pile1
        instVar.put(list3.get(1),false);//pile2

        System.out.println("conf1 : \n");
        if (bw.allSatisfied(instVar)){
            System.out.println("toutes les contraintes reguliere sont satisfaite");
        }else{
             System.out.println(" les contraintes reguliere  qui ne sont pas  satisfaite : \n");
            for (Constraint constraint : bw.getConstraints()) {
              if (!constraint.isSatisfiedBy(instVar)) {
                
                 System.out.println(constraint);
            }
            }

        }

        /// conf2: configuration  qui ne satisfait pas les contrainte reguliere 
        Map<Variable,Object> instVar2 = new LinkedHashMap<Variable,Object>();
         
     
        instVar2.put(list.get(0),1);
        instVar2.put(list.get(1),-1);
        instVar2.put(list.get(2),0);
        instVar2.put(list.get(3),2);
        instVar2.put(list.get(4),-2);
    
    

       

        System.out.println("\n\n\n");
        instVar2.put(list2.get(0),true);
        instVar2.put(list2.get(1),true);
        instVar2.put(list2.get(2),true);
        instVar2.put(list2.get(3),false);
        instVar2.put(list2.get(4),false);
      
   
        
        

        
        instVar2.put(list3.get(0),false);//pile1
        instVar2.put(list3.get(1),false);//pile2


        System.out.println("conf2 : \n");
        if (bw.allSatisfied(instVar2)){
            System.out.println("toutes les contraintes reguliere sont satisfaite");
        }else{
             System.out.println(" les contraintes reguliere  qui ne sont pas  satisfaite : \n");
            for (Constraint constraint : bw.getConstraints()) {
              if (!constraint.isSatisfiedBy(instVar2)) {
                
                 System.out.println(constraint);
            }
            }

        }  


        //// pour les configuraton croissante 
        // conf3: configuration qui ne satisfait pas les contrainte d une configuration croissante
        IncreasingBlocksWorld bwInc = new IncreasingBlocksWorld(nbBlocks, nbPiles);
        // configuration qui ne satisfait pas les contrainte d une configuration croissante
        Map<Variable,Object> instVar3 = new LinkedHashMap<Variable,Object>();
        List<Variable> listInc = new ArrayList<Variable>(bwInc.getOnB()); 
     
        instVar3.put(listInc.get(0),1);
        instVar3.put(listInc.get(1),-1);
        instVar3.put(listInc.get(2),3);
        instVar3.put(listInc.get(3),4);
        instVar3.put(listInc.get(4),-2);
    
    

        List<BooleanVariable> listInc2 = new ArrayList<BooleanVariable>(bwInc.getFixedB());

        System.out.println("\n\n\n");
        instVar3.put(listInc2.get(0),false);
        instVar3.put(listInc2.get(1),true);
        instVar3.put(listInc2.get(2),false);
        instVar3.put(listInc2.get(3),true);
        instVar3.put(listInc2.get(4),true);
      
   
        
        List<BooleanVariable> listInc3 = new ArrayList<BooleanVariable>(bwInc.getFreeP()); 

        
        instVar3.put(listInc3.get(0),false);//pile1
        instVar3.put(listInc3.get(1),false);//pile2 

        System.out.println("conf3 : \n");
        if (bwInc.allSatisfied(instVar3)){
            System.out.println("toutes les contraintes croissante sont satisfaite");
        }else{
             System.out.println(" les contraintes croissante  qui ne sont pas  satisfaite : \n");
            for (Constraint constraint : bwInc.getConstraints()) {
              if (!constraint.isSatisfiedBy(instVar3)) {       
                 System.out.println(constraint);
            }
            }
        } 

        /// conf 4 : configuration qui satisfait toutes les contrainte d une configuration croissantes 

        Map<Variable,Object> instVar4 = new LinkedHashMap<Variable,Object>();
 
     
        instVar4.put(listInc.get(0),-1);
        instVar4.put(listInc.get(1),0);
        instVar4.put(listInc.get(2),-2);
        instVar4.put(listInc.get(3),2);
        instVar4.put(listInc.get(4),3);
    
    

        

        System.out.println("\n\n\n");
        instVar4.put(listInc2.get(0),true);
        instVar4.put(listInc2.get(1),false);
        instVar4.put(listInc2.get(2),true);
        instVar4.put(listInc2.get(3),true);
        instVar4.put(listInc2.get(4),false);
      
   
        


        
        instVar4.put(listInc3.get(0),false);//pile1
        instVar4.put(listInc3.get(1),false);//pile2 

        System.out.println("conf4 : \n");
        if (bwInc.allSatisfied(instVar4)){
            System.out.println("toutes les contraintes croissante sont satisfaite \n");
        }else{
             System.out.println(" les contraintes croissante  qui ne sont pas  satisfaite : \n");
            for (Constraint constraint : bwInc.getConstraints()) {
              if (!constraint.isSatisfiedBy(instVar4)) {       
                 System.out.println(constraint);
            }
            }
        }


        // configuration 5 :  configuration qui satisfait toutes les contrainte regulier et croissante
        // pour cela il suufit juste dajouter les contrainte regulier genrer par bw a lensembe des contrinte croissante generer par bwInc
        // on utilse la configuration instVar4 car elle satisfait toutes les contraintes
        
        Set<Constraint> allConstraint =  bwInc.getConstraints();
        Set<Constraint> contraintesRegulier = bw.getContReguliere();// recuperer les contraintes regulier genenrer par le monde bw
        allConstraint.addAll(contraintesRegulier); // ajouter les contrainte regulier a lensemble des contrainte
        bwInc.setConstraints(allConstraint);// modifer lensemble des contrainte pour bwInc
        System.out.println("conf5 : \n");
        if (bwInc.allSatisfied(instVar4)){
            System.out.println("toutes les contraintes  reguliere et croissante sont satisfaite \n");
        }else{
             System.out.println(" les contraintes  reguliere et croissante  qui ne sont pas  satisfaite : \n");
            for (Constraint constraint : bwInc.getConstraints()) {
              if (!constraint.isSatisfiedBy(instVar4)) {       
                 System.out.println(constraint);
            }
            }
        }   


        // visualisation des configuration
                // Choix du plan a visualiser plan visualiser
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEntrez un numÃ©ro pour visualiser une configuration  :");
        System.out.println("1 - Visualiser la configuration 1");
        System.out.println("2 - Visualiser la configuration 2");
        System.out.println("3 - Visualiser la configuration 3");
        System.out.println("4 - Visualiser la configuration 4");
        System.out.println("5 - Visualiser la configuration 5");
        int choice = scanner.nextInt();    
    
        Map<Variable,Object> selected = null;
         switch (choice) {
            case 1:
                selected = instVar;
                break;
            case 2:
                selected = instVar2;
                break;
            case 3:
                selected = instVar3;
                break;
            case 4:
                selected = instVar4;
                break;
            case 5:
                selected = instVar4;
                break;
            default:
                System.out.println("Choix invalide, aucune configuration ne sera visualisÃ©.");
                return;
        }


        // visialisation de la configuration
        BWIntegerGUI gui = new BWIntegerGUI(nbBlocks);

        // CrÃ©ation d'une fenÃªtre pour afficher le plan 
        JFrame frame = new JFrame("Visualisation de la configuration");

        // CrÃ©ation d'un Ã©tat initial pour la visualisation 
        BWState<Integer> state = Visualisation.makeBWState(nbBlocks, bw, selected);

        // Creation du composant 
        BWComponent<Integer> component = gui.getComponent(state);

        // Ajout du composant a la fenetre
        frame.add(component);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(frame); // positioner la fenetre au centre de l'Ã©cran
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// operation par defaut lors de la fermeture de la fenetre
        frame.setVisible(true); 



       

 

      
    }
}

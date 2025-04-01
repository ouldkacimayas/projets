package blocksworld;
import java.util.*;
import planning.*;
import modelling.*;
import javax.swing.JFrame;
import bwmodel.*;
import bwui.*;

public class AppAction{
  public static void main(String[] args) {

        int nbBlocks = 5;
        int nbPiles = 3;
        BlocksWorldAction bw = new BlocksWorldAction(5,3);
        Set<Action> actions = new HashSet<>();
        actions = bw.getActions();
        
        
        Map<Variable, Object> goal , instVar = new HashMap<>();
        goal = new HashMap<>();
          // creation de letat initial    
        List<Variable> list = new ArrayList<Variable>(bw.getOnB());
         // pour les Variables OnB
        instVar.put(list.get(0),3); 
        instVar.put(list.get(1),2); 
        instVar.put(list.get(2),-1);
        instVar.put(list.get(3),4); 
        instVar.put(list.get(4),-3);


        List<BooleanVariable> list2 = new ArrayList<BooleanVariable>(bw.getFixedB());
        // fixedb
        instVar.put(list2.get(0),false);
        instVar.put(list2.get(1),false);
        instVar.put(list2.get(2),true);
        instVar.put(list2.get(3),true);
        instVar.put(list2.get(4),true);
         
        List<BooleanVariable> list3 = new ArrayList<BooleanVariable>(bw.getFreeP()); 
        
         //freep
        instVar.put(list3.get(0),false);
        instVar.put(list3.get(1),true);
        instVar.put(list3.get(2),false);


      // creation de l'etat but
      // onb
        goal.put(list.get(0),1); 
        goal.put(list.get(1),2); 
        goal.put(list.get(2),3);
        goal.put(list.get(3),4);
        goal.put(list.get(4),-3);

        //// fixedb
        goal.put(list2.get(0),false); 
        goal.put(list2.get(1),true); 
        goal.put(list2.get(2),true);
        goal.put(list2.get(3),true);
        goal.put(list2.get(4),true);
        
 
        //// freep
        goal.put(list3.get(0),true); 
        goal.put(list3.get(1),true); 
        goal.put(list3.get(2),false);
        
        Goal but = new BasicGoal(goal);

        //Resoulution avec DFS
        long startTimeDFS = System.currentTimeMillis();
        DFSPlanner dfsPlanner = new DFSPlanner(instVar,actions, but);
        dfsPlanner.activateNodeCount(true);
        List<Action> dfsPlannerPlan = dfsPlanner.plan();
        long endTimeDFS = System.currentTimeMillis();
        long executionTimeDFS = endTimeDFS - startTimeDFS;



        //Resoulution avec BFS
        
        BFSPlanner bfsPlanner = new BFSPlanner(instVar,actions, but);
        bfsPlanner.activateNodeCount(true);
        long startTimeBFS = System.currentTimeMillis();
        List<Action> bfsPlannerPlan = bfsPlanner.plan();
        long endTimeBFS = System.currentTimeMillis();
        long executionTimeBFS = endTimeBFS - startTimeBFS;
        

        
        //Resoulution avec Dijikstra
        
        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(instVar,actions, but);
        dijkstraPlanner.activateNodeCount(true);
        long startTimeDijkstra = System.currentTimeMillis();
        List<Action> dijkstraPlan = dijkstraPlanner.plan();
        long endTimeDijkstra = System.currentTimeMillis();
        long executionTimeDijkstra = endTimeDijkstra - startTimeDijkstra;


       
       //Resoulution avec Astar 
       // heuristique selon le nombre des bloc mal  place
          
       MisplacedBlocksHeuristic heurist  = new MisplacedBlocksHeuristic(goal);
       DistanceHeuristic  heurist2 = new  DistanceHeuristic(goal);
       AStarPlanner aStarPlanner = new AStarPlanner(instVar, actions, but,heurist);
       AStarPlanner aStarPlanner2 = new AStarPlanner(instVar, actions, but,heurist2);
       aStarPlanner.activateNodeCount(true);
       long startTimeAStar = System.currentTimeMillis();
       List<Action> aStarPlannerPlan = aStarPlanner.plan();
       long endTimeAStar = System.currentTimeMillis();
       long executionTimeAStar = endTimeAStar - startTimeAStar;
       // les diifernt traitement avec la 2 eme heuristique
       aStarPlanner2.activateNodeCount(true);
       long startTimeAStar2 = System.currentTimeMillis();
       List<Action> aStarPlannerPlan2 = aStarPlanner2.plan();
       long endTimeAStar2 = System.currentTimeMillis();
       long executionTimeAStar2 = endTimeAStar2 - startTimeAStar2;



       /////// afficher les resultat pour chaque algorithme 
       // DFS
        System.out.println("---------------DFS-------------------");
        System.out.println("\n");
        if(dfsPlannerPlan != null){
            //System.out.println("le plan trouve  avec DFS : \n" +dfsPlannerPlan+"\n");
            System.out.println("\t le nombre d'action dans le plan trouve  avec DFS : " +dfsPlannerPlan.size()+"\n");
        }else{
              System.out.println("\t Pas de plan trouve  avec DFS \n");
        }
        
        System.out.println("\t le nombre de neuds explorés par  DFS : "+dfsPlanner.getExploredNodesCount()+"\n");
        System.out.println("\t Temps d'exécution de DFS : " + executionTimeDFS + " ms\n");

        /// BFS
        System.out.println("----------------BFS---------------------");
        System.out.println("\n");
        if(bfsPlannerPlan != null){
            //System.out.println("le plan trouve  avec BFS : \n" +bfsPlannerPlan+"\n");
            System.out.println("\t le nombre d'action dans le plan trouve  avec BFS : " +bfsPlannerPlan.size()+"\n");
        }else{
            System.out.println("\t Pas de  plan trouve  avec BFS \n");
        }
        
        System.out.println("\t le nombre de neuds explorés par  BFS : "+bfsPlanner.getExploredNodesCount()+"\n");
        System.out.println("\t Temps d'exécution de BFS : " + executionTimeBFS + " ms\n");

        /// Dijikstra
        System.out.println("------------Dijikstra--------------------");
        System.out.println("\n");
        if(dijkstraPlan != null ) {
            //System.out.println("le plan trouve avec dijikstra est: \n" +dijkstraPlan+"\n");
            System.out.println("\t le nombre d action dans le plan trouve avec dijikstra est: " +dijkstraPlan.size()+"\n");
        } else{
            System.out.println("\t Pas de  plan trouve avec dijikstra \n");
        }
        
        System.out.println("\t le nombre de neuds explorés par Dijikstra est : " +dijkstraPlanner.getNodeCount()+"\n");
        System.out.println("\t Temps d'exécution de Dijkstra : " + executionTimeDijkstra + " ms\n");

        /// Astar avec heuristique 1 

       System.out.println("------------Astar avec MisplacedBlocksHeuristic : -----------------\n");
       if (aStarPlannerPlan != null){
            //System.out.println("le plan trouve  avec A* :\n "+ aStarPlannerPlan+"\n");
            System.out.println("\t nombre  d'action dans le plan trouve  avec A* : "+ aStarPlannerPlan.size()+"\n");
       }else{
          System.out.println("\t Pas de  plan trouve  avec A* \n");
       }
       System.out.println("\t nombre de noeud explorer par Astar :   "+aStarPlanner.getNodeCount()+"\n");
       System.out.println("\t Temps d'exécution de A* : " + executionTimeAStar + " ms\n");
       ///Astar heuristique 2
       System.out.println("-----------------Astar avec DistanceHeuristic : --------------------------\n");
        if (aStarPlannerPlan2 != null){
            //System.out.println("le plan trouve  avec A* : \n"+ aStarPlannerPlan2+"\n");
            System.out.println("\t nombre  d'action dans le plan trouve  avec A* : "+ aStarPlannerPlan2.size()+"\n");
       }else{
          System.out.println("\t Pas de  plan trouve  avec A* \n");
       }
       System.out.println("\t nombre de noeud explorer par Astar :  "+aStarPlanner2.getNodeCount()+"\n");
       System.out.println("\t Temps d'exécution de A* : " + executionTimeAStar2 + " ms\n");
       System.out.println("\n\n");


        // Choix du plan a visualiser plan visualiser
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEntrez un numéro pour visualiser un plan :");
        System.out.println("1 - Visualiser le plan avec DFS");
        System.out.println("2 - Visualiser le plan avec BFS");
        System.out.println("3 - Visualiser le plan avec Dijkstra");
        System.out.println("4 - Visualiser le plan avec A*");
        int choice = scanner.nextInt();    
    
        List<Action> selectedPlan = null;
         switch (choice) {
            case 1:
                selectedPlan = dfsPlannerPlan;
                break;
            case 2:
                selectedPlan = bfsPlannerPlan;
                break;
            case 3:
                selectedPlan = dijkstraPlan;
                break;
            case 4:
                //selectedPlan = aStarPlannerPlan;
                System.out.println("\nChoisissez l'heuristique pour A* :");
                System.out.println("1 - MisplacedBlocksHeuristic");
                System.out.println("2 - DistanceHeuristic");
                int heuristicChoice = scanner.nextInt();
                if (heuristicChoice == 1){
                    selectedPlan = aStarPlannerPlan;
                }else if (heuristicChoice == 2){
                    selectedPlan = aStarPlannerPlan2;
                }else{
                    System.out.println("Choix invalide pour l'heuristique, aucun plan ne sera visualisé.");
                    return;                  
                }

                break;
            default:
                System.out.println("Choix invalide, aucun plan ne sera visualisé.");
                return;
        }       
         

         // visialisation du plan trouve
        BWIntegerGUI gui = new BWIntegerGUI(nbBlocks);

        // Création d'une fenêtre pour afficher le plan 
        JFrame frame = new JFrame("Visualisation de la configuration");

        // Création d'un état initial pour la visualisation 
        BWState<Integer> state = Visualisation.makeBWState(nbBlocks, bw, instVar);

        // Creation du composant 
        BWComponent<Integer> component = gui.getComponent(state);

        // Ajout du composant a la fenetre
        frame.add(component);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(frame); // positioner la fenetre au centre de l'écran
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// operation par defaut lors de la fermeture de la fenetre
        frame.setVisible(true);

       
        // visiualiser le plan trouver selon un algorithme
        if (selectedPlan != null) {
            for (Action a : selectedPlan ) {
                try { 
                    if (choice == 1){ // si on affiche le plan trouver avec DFS (car ya beucoup d'action donc ca prend du temps)
                        Thread.sleep(20);// 20ms  de pause entre chaque action 
                    }else{ // plan trouver avec les autre algorithmes
                        
                        Thread.sleep(1_000);// 1s de pause entre chaque action 
                    }
                    
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                instVar = a.successor(instVar);
                // metre a jour le composant avec le nouvel état
                component.setState(Visualisation.makeBWState(nbBlocks, bw, instVar));
            }
        }

    }

}





   

      

    



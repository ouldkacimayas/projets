package blocksworld;
import modelling.*;
import cp.*;
import java.util.*;
import javax.swing.JFrame;
import bwmodel.*;
import bwui.*;

public class AppSolver{

    public static void main(String[] args){
        //Lancements des solveur avec monde des blocks regulier
        System.out.println("*Monde des blocks regulier: \n");
        BlocksWorldConstraints regularBlocksWorld = new RegularBlocksWorld(5,3);
        Set<Variable> variables = regularBlocksWorld.getVariables();
        Set<Constraint> constraints = regularBlocksWorld.getConstraints();

        //Chercher une solution Avec BacktrackSolver (s’il en existe une)
        Solver backtrackSolver = new BacktrackSolver(variables,constraints);
        
        long startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionBacktrack1 = backtrackSolver.solve();
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        System.out.println("Le temps d'execution d backtrack solver : "+ duration+"ms\n");
        System.out.println("Resultat backtrack solver: \n");
        displaySolution(solutionBacktrack1);


        System.out.println("\n-----------------------\n");

        //Chercher une solution Avec MACSolver (s’il en existe une)
        Solver macSolver = new MACSolver(variables,constraints);
        
        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionMAC1 = macSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;

        System.out.println("Le temps d'execution d mac solver : "+ duration+"ms\n");
        System.out.println("Resultat mac solver: \n");
        displaySolution(solutionMAC1);
        
        System.out.println("\n-----------------------\n");

        //Chercher une solution Avec HeuristicMACSolver (s’il en existe une)
        //D'abord selon la taille des domaines (plus petit)
        VariableHeuristic  variableHeuristic = new DomainSizeVariableHeuristic(false);
        Random rand = new Random();
        ValueHeuristic valueHeuristic = new RandomValueHeuristic(rand);
        Solver heuristicMacSolver = new HeuristicMACSolver(variables,constraints,variableHeuristic,valueHeuristic);

        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionHeuristicDomainSizeF1 = heuristicMacSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;
        System.out.println("Selon la taille des domaines(le plus petit)\n");
        System.out.println("Le temps d'execution de heuristic mac solver: "+ duration+"ms\n");
        System.out.println("Resultat heuristic mac solver: \n");
        displaySolution(solutionHeuristicDomainSizeF1);

        System.out.println("\n---------\n");

        //Le plus grand
        variableHeuristic = new DomainSizeVariableHeuristic(true);
        heuristicMacSolver = new HeuristicMACSolver(variables,constraints,variableHeuristic,valueHeuristic);

        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionHeuristicDomainSizeT1 = heuristicMacSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;
        System.out.println("Selon la taille des domaines(le plus grand)\n");
        System.out.println("Le temps d'execution de heuristic mac solver: "+ duration+"ms\n");
        System.out.println("Resultat heuristic mac solver:  \n");
        displaySolution(solutionHeuristicDomainSizeT1);

        System.out.println("\n---------\n");

        //Ensuite selon le nombre d'apparitions d'une variable dans les contraintes (la variable avec le moin d'apparitions)
        variableHeuristic = new NbConstraintsVariableHeuristic(constraints,false);
        heuristicMacSolver = new HeuristicMACSolver(variables,constraints,variableHeuristic,valueHeuristic);

        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionHeuristicNbConstraintF1 = heuristicMacSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;

        System.out.println("Selon le nombre d'apparitions d'une variable dans les contraintes (le moin)\n");
        System.out.println("Le temps d'execution de heuristic mac solver: "+ duration+"ms\n");
        System.out.println("Resultat heuristic mac solver: \n");
        displaySolution(solutionHeuristicNbConstraintF1);

        System.out.println("\n---------\n");

        //La variable avec le plus d'apparitions dans des contraintes
        variableHeuristic = new NbConstraintsVariableHeuristic(constraints,true);
        heuristicMacSolver = new HeuristicMACSolver(variables,constraints,variableHeuristic,valueHeuristic);

        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionHeuristicNbConstraintT1 = heuristicMacSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;
        System.out.println("Selon le nombre d'apparitions d'une variable dans les contraintes (le plus)\n");
        System.out.println("Le temps d'execution de heuristic mac solver: "+ duration+"ms\n");
        System.out.println("Resultat heuristic mac solver: \n");
        displaySolution(solutionHeuristicNbConstraintT1);


        System.out.println("\n\n\n*************************************************************\n\n\n");



        //Lancements des solveur avec monde des blocks croissant
        System.out.println("*Monde des blocks croissant: \n");
        BlocksWorldConstraints increasingBlocksWorld = new IncreasingBlocksWorld(5,3);
        variables = increasingBlocksWorld.getVariables();
        constraints = increasingBlocksWorld.getConstraints();

        //Chercher une solution Avec BacktrackSolver (s’il en existe une)
        backtrackSolver = new BacktrackSolver(variables,constraints);
        
        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionBacktrack2 = backtrackSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;

        System.out.println("Le temps d'execution d backtrack solver : "+ duration+"ms\n");
        System.out.println("Resultat backtrack solver: \n");
        displaySolution(solutionBacktrack2);


        System.out.println("\n-----------------------\n");

        //Chercher une solution Avec MACSolver (s’il en existe une)
        macSolver = new MACSolver(variables,constraints);
        
        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionMAC2 = macSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;

        System.out.println("Le temps d'execution d mac solver : "+ duration+"ms\n");
        System.out.println("Resultat mac solver: \n");
        displaySolution(solutionMAC2);
        
        System.out.println("\n-----------------------\n");

        //Chercher une solution Avec HeuristicMACSolver (s’il en existe une)
        //D'abord selon la taille des domaines (plus petit)
        variableHeuristic = new DomainSizeVariableHeuristic(false);
        heuristicMacSolver = new HeuristicMACSolver(variables,constraints,variableHeuristic,valueHeuristic);

        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionHeuristicDomainSizeF2 = heuristicMacSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;
        System.out.println("Selon la taille des domaines(le plus petit)\n");
        System.out.println("Le temps d'execution de heuristic mac solver: "+ duration+"ms\n");
        System.out.println("Resultat heuristic mac solver: \n");
        displaySolution(solutionHeuristicDomainSizeF2);

        System.out.println("\n---------\n");

        //Le plus grand
        variableHeuristic = new DomainSizeVariableHeuristic(true);
        heuristicMacSolver = new HeuristicMACSolver(variables,constraints,variableHeuristic,valueHeuristic);

        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionHeuristicDomainSizeT2 = heuristicMacSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;
        System.out.println("Selon la taille des domaines(le plus grand)\n");
        System.out.println("Le temps d'execution de heuristic mac solver: "+ duration+"ms\n");
        System.out.println("Resultat heuristic mac solver:  \n");
        displaySolution(solutionHeuristicDomainSizeT2);

        System.out.println("\n---------\n");

        //Ensuite selon le nombre d'apparitions d'une variable dans les contraintes (la variable avec le moin d'apparitions)
        variableHeuristic = new NbConstraintsVariableHeuristic(constraints,false);
        heuristicMacSolver = new HeuristicMACSolver(variables,constraints,variableHeuristic,valueHeuristic);

        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionHeuristicNbConstraintF2 = heuristicMacSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;

        System.out.println("Selon le nombre d'apparitions d'une variable dans les contraintes (le moin)\n");
        System.out.println("Le temps d'execution de heuristic mac solver: "+ duration+"ms\n");
        System.out.println("Resultat heuristic mac solver: \n");
        displaySolution(solutionHeuristicNbConstraintF2);

        System.out.println("\n---------\n");

        //La variable avec le plus d'apparitions dans des contraintes
        variableHeuristic = new NbConstraintsVariableHeuristic(constraints,true);
        heuristicMacSolver = new HeuristicMACSolver(variables,constraints,variableHeuristic,valueHeuristic);

        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionHeuristicNbConstraintT2 = heuristicMacSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;
        System.out.println("Selon le nombre d'apparitions d'une variable dans les contraintes (le plus)\n");
        System.out.println("Le temps d'execution de heuristic mac solver: "+ duration+"ms\n");
        System.out.println("Resultat heuristic mac solver: \n");
        displaySolution(solutionHeuristicNbConstraintT2);



        System.out.println("\n\n\n*************************************************************\n\n\n");



        //Lancements des solveur avec monde des blocks regulier et croissant
        System.out.println("*Monde des blocks regulier et croissant: \n");
        regularBlocksWorld = new RegularBlocksWorld(5,3);
        IncreasingBlocksWorld inCBlocksWorld = new IncreasingBlocksWorld(5,3);
        variables = regularBlocksWorld.getVariables();
        constraints = regularBlocksWorld.getConstraints();
        constraints.addAll(inCBlocksWorld.getIncConstraints());

        //Chercher une solution Avec BacktrackSolver (s'il en existe une)
        backtrackSolver = new BacktrackSolver(variables,constraints);
        
        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionBacktrack3 = backtrackSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;

        System.out.println("Le temps d'execution d backtrack solver : "+ duration+"ms\n");
        System.out.println("Resultat backtrack solver: \n");
        displaySolution(solutionBacktrack3);


        System.out.println("\n-----------------------\n");

        //Chercher une solution Avec MACSolver (s’il en existe une)
        macSolver = new MACSolver(variables,constraints);
        
        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionMAC3 = macSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;

        System.out.println("Le temps d'execution d mac solver : "+ duration+"ms\n");
        System.out.println("Resultat mac solver: \n");
        displaySolution(solutionMAC3);
        
        System.out.println("\n-----------------------\n");

        //Chercher une solution Avec HeuristicMACSolver (s’il en existe une)
        //D'abord selon la taille des domaines (plus petit)
        variableHeuristic = new DomainSizeVariableHeuristic(false);
        heuristicMacSolver = new HeuristicMACSolver(variables,constraints,variableHeuristic,valueHeuristic);

        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionHeuristicDomainSizeF3 = heuristicMacSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;
        System.out.println("Selon la taille des domaines(le plus petit)\n");
        System.out.println("Le temps d'execution de heuristic mac solver: "+ duration+"ms\n");
        System.out.println("Resultat heuristic mac solver: \n");
        displaySolution(solutionHeuristicDomainSizeF3);

        System.out.println("\n---------\n");

        //Le plus grand
        variableHeuristic = new DomainSizeVariableHeuristic(true);
        heuristicMacSolver = new HeuristicMACSolver(variables,constraints,variableHeuristic,valueHeuristic);

        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionHeuristicDomainSizeT3 = heuristicMacSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;
        System.out.println("Selon la taille des domaines(le plus grand)\n");
        System.out.println("Le temps d'execution de heuristic mac solver: "+ duration+"ms\n");
        System.out.println("Resultat heuristic mac solver:  \n");
        displaySolution(solutionHeuristicDomainSizeT3);

        System.out.println("\n---------\n");

        //Ensuite selon le nombre d'apparitions d'une variable dans les contraintes (la variable avec le moin d'apparitions)
        variableHeuristic = new NbConstraintsVariableHeuristic(constraints,false);
        heuristicMacSolver = new HeuristicMACSolver(variables,constraints,variableHeuristic,valueHeuristic);

        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionHeuristicNbConstraintF3 = heuristicMacSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;

        System.out.println("Selon le nombre d'apparitions d'une variable dans les contraintes (le moin)\n");
        System.out.println("Le temps d'execution de heuristic mac solver: "+ duration+"ms\n");
        System.out.println("Resultat heuristic mac solver: \n");
        displaySolution(solutionHeuristicNbConstraintF3);

        System.out.println("\n---------\n");

        //La variable avec le plus d'apparitions dans des contraintes
        variableHeuristic = new NbConstraintsVariableHeuristic(constraints,true);
        heuristicMacSolver = new HeuristicMACSolver(variables,constraints,variableHeuristic,valueHeuristic);

        startTime = System.currentTimeMillis();
        Map<Variable, Object> solutionHeuristicNbConstraintT3 = heuristicMacSolver.solve();
        endTime = System.currentTimeMillis();

        duration = endTime - startTime;
        System.out.println("Selon le nombre d'apparitions d'une variable dans les contraintes (le plus)\n");
        System.out.println("Le temps d'execution de heuristic mac solver: "+ duration+"ms\n");
        System.out.println("Resultat heuristic mac solver: \n");
        displaySolution(solutionHeuristicNbConstraintT3);


        //En utilisant la librairie fournie 

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEntrez un numéro pour visualiser une configutation :");
        System.out.println(" 1 - Pour un monde regulier");
        System.out.println(" 2 - Pour un monde croissant");
        System.out.println(" 3 - Pour un monde regulier et croissant");
        int choice1 = scanner.nextInt();    

        System.out.println("    En utilisant :");
        System.out.println("      1 - Backtrack solver");
        System.out.println("      2 - MAC solver");
        System.out.println("      3 - Heuristic MAC solver");
        int choice2 = scanner.nextInt();
    
        Map<Variable, Object> selectedConfiguration = null;
         switch (choice1) {
            case 1:
                switch(choice2){
                    case 1:
                        selectedConfiguration = solutionBacktrack1;
                        break;
                    case 2:
                        selectedConfiguration = solutionMAC1;
                        break;
                    case 3:
                        System.out.println("\t 1 - Selon la taille du domain(le plus petit)");
                        System.out.println("\t 2 - Selon la taille du domain(le plus grand)");
                        System.out.println("\t 3 - Selon le nombre d'apparitions d'une variable dans les contraintes(le moins)");
                        System.out.println("\t 4 - Selon le nombre d'apparitions d'une variable dans les contraintes(le plus)");
                        int choice3 = scanner.nextInt();
                        switch (choice3){
                            case 1:
                                selectedConfiguration = solutionHeuristicDomainSizeF1;
                                break;
                            case 2:
                                selectedConfiguration = solutionHeuristicDomainSizeT1;
                                break;
                            case 3:
                                selectedConfiguration = solutionHeuristicNbConstraintF1;
                                break;
                            case 4:
                                selectedConfiguration = solutionHeuristicNbConstraintT1;
                                break;
                            default:
                                System.out.println("Choix invalide, aucune configuration ne sera visualisé.");
                            break;
                        }
                        break;
                    default:
                        System.out.println("Choix invalide, aucune configuration ne sera visualisé.");
                        break;
                }
                break;
            case 2:
                switch(choice2){
                    case 1:
                        selectedConfiguration = solutionBacktrack2;
                        break;
                    case 2:
                        selectedConfiguration = solutionMAC2;
                        break;
                    case 3:
                        System.out.println("\t  1 - Selon la taille du domain(le plus petit)");
                        System.out.println("\t  2 - Selon la taille du domain(le plus grand)");
                        System.out.println("\t  3 - Selon le nombre d'apparitions d'une variable dans les contraintes(le moins)");
                        System.out.println("\t  4 - Selon le nombre d'apparitions d'une variable dans les contraintes(le plus)");
                        int choice3 = scanner.nextInt();
                        switch (choice3){
                            case 1:
                                selectedConfiguration = solutionHeuristicDomainSizeF2;
                                break;
                            case 2:
                                selectedConfiguration = solutionHeuristicDomainSizeT2;
                                break;
                            case 3:
                                selectedConfiguration = solutionHeuristicNbConstraintF2;
                                break;
                            case 4:
                                selectedConfiguration = solutionHeuristicNbConstraintT2;
                                break;
                            default:
                                System.out.println("Choix invalide, aucune configuration ne sera visualisé.");
                            break;
                        }
                        break;
                    default:
                        System.out.println("Choix invalide, aucune configuration ne sera visualisé.");
                        break;
                }
                break;
            case 3:
                switch(choice2){
                    case 1:
                        selectedConfiguration = solutionBacktrack3;
                        break;
                    case 2:
                        selectedConfiguration = solutionMAC3;
                        break;
                    case 3:
                        System.out.println("\t  1 - Selon la taille du domain(le plus petit)");
                        System.out.println("\t  2 - Selon la taille du domain(le plus grand)");
                        System.out.println("\t  3 - Selon le nombre d'apparitions d'une variable dans les contraintes(le moins)");
                        System.out.println("\t  4 - Selon le nombre d'apparitions d'une variable dans les contraintes(le plus)");
                        int choice3 = scanner.nextInt();
                        switch (choice3){
                            case 1:
                                selectedConfiguration = solutionHeuristicDomainSizeF3;
                                break;
                            case 2:
                                selectedConfiguration = solutionHeuristicDomainSizeT3;
                                break;
                            case 3:
                                selectedConfiguration = solutionHeuristicNbConstraintF3;
                                break;
                            case 4:
                                selectedConfiguration = solutionHeuristicNbConstraintT3;
                                break;
                            default:
                                System.out.println("Choix invalide, aucune configuration ne sera visualisé.");
                            break;
                            }
                        break;
                    default:
                        System.out.println("Choix invalide, aucune configuration ne sera visualisé.");
                        break;
                    }
                break;
            default:
                System.out.println("Choix invalide, aucune configuration ne sera visualisé.");
                break;
        }       
         
        int nbBlocks = 5;
        int nbPiles = 3;
        // configuration trouve
        BWIntegerGUI gui = new BWIntegerGUI(nbBlocks);

        //Création d'une fenetre pour afficher le plan 
        JFrame frame = new JFrame("Configuration trouve");
        
        BlocksWorldConstraints bw = new BlocksWorldConstraints(nbBlocks,nbPiles);
        // Creation d'un etat initial pour la visualisation 
        BWState<Integer> state = Visualisation.makeBWState(nbBlocks, bw, selectedConfiguration);

        // Creation du composant 
        BWComponent<Integer> component = gui.getComponent(state);

        // Ajout du composant a la fenetre
        frame.add(component);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(frame); // positioner la fenetre au centre de l'écran
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// operation par defaut lors de la fermeture de la fenetre
        frame.setVisible(true);

    }

    public static void displaySolution(Map<Variable, Object> solution) {
        Map<Variable, Object> onVariables = new HashMap<>();
        Map<Variable, Object> fixedVariables = new HashMap<>();
        Map<Variable, Object> freeVariables = new HashMap<>();

        Variable variable = null;
        Object value = null;
        //Parcours de la map et separation des entrees en fonction des noms des variables
        for (Map.Entry<Variable, Object> entry : solution.entrySet()) {
            variable = entry.getKey();
            value = entry.getValue();

            //Tri en fonction du nom de la variable
            if (variable.getName().startsWith("on")) {
                onVariables.put(variable,value);
            }else if (variable.getName().startsWith("fixed")) {
                fixedVariables.put(variable,value);
            }else if (variable.getName().startsWith("free")) {
                freeVariables.put(variable,value);
            }
        }

        //Affichage des variables triees
        printVariables("onb", onVariables);
        printVariables("fixedb", fixedVariables);
        printVariables("freep", freeVariables);
    }

    private static void printVariables(String type, Map<Variable, Object> variables){
        System.out.println(type + " variables: \n");
        for (Map.Entry<Variable, Object> instanciation : variables.entrySet()){
            System.out.println(instanciation.getKey().getName() + " = " + instanciation.getValue());
        }
        System.out.println("\n");
    }
}

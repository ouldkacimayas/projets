package planning;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import modelling.Variable;

public class App {
    public static void main(String[] args){

        // Domaine pour les variables A, B, et C (valeurs possibles : 0 ou 1)
        Set<Object> domaine = new HashSet<>();
        domaine.add(0);
        domaine.add(1);
        
        // Déclaration des variables
        Variable varA = new Variable("A", domaine);
        Variable varB = new Variable("B", domaine);
        Variable varC = new Variable("C", domaine);

        // État initial: A=0, B=0, C=0
        Map<Variable, Object> initialState = new HashMap<>();
        initialState.put(varA, 0);
        initialState.put(varB, 0);
        initialState.put(varC, 0);

        // Objectif: A=1, B=1, C=1
        Map<Variable, Object> goalState = new HashMap<>();
        goalState.put(varA, 1);
        goalState.put(varB, 1);
        goalState.put(varC, 1);
        Goal goal = new BasicGoal(goalState);

        // Ensemble d'actions
        Set<Action> actions = new HashSet<>();

        // Act 1: A=0 -> A=1 (coût 1)
        Map<Variable, Object> preconditions1 = new HashMap<>();
        preconditions1.put(varA, 0);
        Map<Variable, Object> effects1 = new HashMap<>();
        effects1.put(varA, 1);
        actions.add(new BasicAction(preconditions1, effects1, 1));

        // Act 2: B=0 -> B=1 (coût 1)
        Map<Variable, Object> preconditions2 = new HashMap<>();
        preconditions2.put(varB, 0);
        Map<Variable, Object> effects2 = new HashMap<>();
        effects2.put(varB, 1);
        actions.add(new BasicAction(preconditions2, effects2, 1));

        // Act 3: C=0 -> C=1 (coût 2)
        Map<Variable, Object> preconditions3 = new HashMap<>();
        preconditions3.put(varC, 0);
        Map<Variable, Object> effects3 = new HashMap<>();
        effects3.put(varC, 1);
        actions.add(new BasicAction(preconditions3, effects3, 2));

        // Act 4: A=0, B=0 -> A=1, B=1 (coût 4)
        Map<Variable, Object> preconditions4 = new HashMap<>();
        preconditions4.put(varA, 0);
        preconditions4.put(varB, 0);
        Map<Variable, Object> effects4 = new HashMap<>();
        effects4.put(varA, 1);
        effects4.put(varB, 1);
        actions.add(new BasicAction(preconditions4, effects4, 4));

        // Act 5: B=0, C=0 -> B=1, C=1 (coût 5)
        Map<Variable, Object> preconditions5 = new HashMap<>();
        preconditions5.put(varB, 0);
        preconditions5.put(varC, 0);
        Map<Variable, Object> effects5 = new HashMap<>();
        effects5.put(varB, 1);
        effects5.put(varC, 1);
        actions.add(new BasicAction(preconditions5, effects5, 5));

        System.out.println("/////// pour DFSPlanner");
        
        // Test DFSPlanner
        DFSPlanner dfsPlanner = new DFSPlanner(initialState, actions, goal);
        dfsPlanner.activateNodeCount(true);
        List<Action> dfsPlan = dfsPlanner.plan();

        if (dfsPlan != null) {
            System.out.println("Plan trouvé (DFS):");
            for (Action action : dfsPlan) {
                System.out.println(action);
            }
        } else {
            System.out.println("Aucun plan trouvé (DFS).");
        }
        System.out.println("Nombre de noeuds explorés (DFS): " + dfsPlanner.getExploredNodesCount());
        System.out.println("\n\n\n");

        System.out.println("/////// pour BFSPlanner");
        
        // Test BFSPlanner
        BFSPlanner bfsPlanner = new BFSPlanner(initialState, actions, goal);
        bfsPlanner.activateNodeCount(true);
        List<Action> bfsPlan = bfsPlanner.plan();

        if (bfsPlan != null) {
            System.out.println("Plan trouvé (BFS):");
            for (Action action : bfsPlan) {
                System.out.println(action);
            }
        } else {
            System.out.println("Aucun plan trouvé (BFS).");
        }
        System.out.println("Nombre de noeuds explorés (BFS): " + bfsPlanner.getExploredNodesCount());
        System.out.println("\n\n\n");

        System.out.println("/////// pour DijkstraPlanner");
        
        // Test DijkstraPlanner
        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(initialState, actions, goal);
        dijkstraPlanner.activateNodeCount(true);
        List<Action> dijkstraPlan = dijkstraPlanner.plan();

        if (dijkstraPlan != null) {
            System.out.println("Plan trouvé (Dijkstra):");
            for (Action action : dijkstraPlan) {
                System.out.println(action);
            }
        } else {
            System.out.println("Aucun plan trouvé (Dijkstra).");
        }
        System.out.println("Nombre de noeuds explorés (Dijkstra): " + dijkstraPlanner.getNodeCount());
        System.out.println("\n\n\n");
        System.out.println("/////// pour AstarPlanner");
        Heuristic simpleHeuristic = new SimpleHeuristic();
        AStarPlanner aStarPlanner = new AStarPlanner(initialState, actions, goal, simpleHeuristic);
        aStarPlanner.activateNodeCount(true);
        List<Action> aStarPlan = aStarPlanner.plan();
        if (aStarPlan != null){
            System.out.println("Plan trouvé (A*):");
            for (Action action : aStarPlan) {
                System.out.println(action);

            }
        } else {
            System.out.println("Aucun plan trouvé (A*).");
        }
        System.out.println("Nombre de noeuds explorés (A*): " + aStarPlanner.getNodeCount());
    }
        // Classe interne pour l'heuristique simple
    private static class SimpleHeuristic implements Heuristic {
        @Override
        public float estimate(Map<Variable, Object> state) {
            // Retourne une estimation fixe, par exemple 0.0f
            return 0.0f;
        }
    }
}


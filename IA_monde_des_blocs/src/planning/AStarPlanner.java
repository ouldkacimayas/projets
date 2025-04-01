package planning;

import java.util.*;
import modelling.Variable;

public class AStarPlanner implements Planner {
    private  Map<Variable, Object> initialState;
    private Set<Action> actions;
    private  Goal goal;
    private  Heuristic heuristic;
    private boolean nodeCountActive = false; // Sonde activre ou non
    private int nodeCount = 0; // Nbr de noeuds explores

    // Constructeur
    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    @Override
    public Set<Action> getActions() {
        return this.actions;
    }

    @Override
    public Goal getGoal() {
        return this.goal;
    }
    // Methode pour activer ou desactiver le compteur de noeuds
    public void activateNodeCount(boolean activate) {
        this.nodeCountActive = activate;
    }

   // Methode pour recuperer le nbr de noeud explore
    public int getNodeCount() {
        return this.nodeCount;
    }

    @Override
    public List<Action> plan() {
                
        if (nodeCountActive) {
            nodeCount = 0;
        }

        
        PriorityQueue<StateCostPair> queue = new PriorityQueue<>(Comparator.comparingDouble(pair -> pair.cost));
        queue.add(new StateCostPair(initialState, 0));

        
        Map<Map<Variable, Object>, Map<Variable, Object>> parentMap = new HashMap<>();
        Map<Map<Variable, Object>, Action> actionMap = new HashMap<>();
        Map<Map<Variable, Object>, Float> gCostMap = new HashMap<>();
        gCostMap.put(initialState, 0f);

        
        Set<Map<Variable, Object>> visited = new HashSet<>();

        
        while (!queue.isEmpty()) {
            StateCostPair currentPair = queue.poll();
            Map<Variable, Object> currentState = currentPair.state;
            float gCost = currentPair.cost - heuristic.estimate(currentState); 
            // Si la sonde est activee, on incremente le compteur de noeuds explores
            if (nodeCountActive) {
                nodeCount++;
            }
            
            if (visited.contains(currentState)) {
                continue;
            }
            visited.add(currentState);

            
            if (goal.isSatisfiedBy(currentState)) {
                
                return constructPlan(currentState, parentMap, actionMap);
            }

            
            for (Action action : actions) {
                if (action.isApplicable(currentState)) {
                    Map<Variable, Object> successorState = action.successor(currentState);
                    float newGCost = gCost + action.getCost();
                    float fCost = newGCost + heuristic.estimate(successorState);

                    
                    if (!visited.contains(successorState) && (!gCostMap.containsKey(successorState) || newGCost < gCostMap.get(successorState))) {
                        gCostMap.put(successorState, newGCost);
                        queue.add(new StateCostPair(successorState, fCost));
                        parentMap.put(successorState, currentState); 
                        actionMap.put(successorState, action); 
                    }
                }
            }
        }

        
        return null;
    }

    // Methode auxiliaire pour construire le plan une fois l'objectif atteint
    private List<Action> constructPlan(Map<Variable, Object> state,  Map<Map<Variable, Object>, Map<Variable, Object>> parentMap, Map<Map<Variable, Object>, Action> actionMap) {
        List<Action> plan = new ArrayList<>();
        Map<Variable, Object> currentState = state;

        
        while (parentMap.containsKey(currentState)) {
            Action action = actionMap.get(currentState);
            plan.add(action);
            currentState = parentMap.get(currentState); 
        }

        // Inverser le plan car on a remonte de l'etat final a l'etat initial
        Collections.reverse(plan);
        return plan;
    }

    // Classe interne pour representer un etat et son cout 
    private static class StateCostPair {
        Map<Variable, Object> state;
        float cost;

        StateCostPair(Map<Variable, Object> state, float cost) {
            this.state = state;
            this.cost = cost;
        }
    }
}

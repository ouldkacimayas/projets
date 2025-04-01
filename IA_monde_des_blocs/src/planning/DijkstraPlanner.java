package planning;

import java.util.*;
import modelling.Variable;

public class DijkstraPlanner implements Planner {
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;

   
    private boolean nodeCountActive = false;  // activer/dsactiver la sonde
    private int exploredNodesCount = 0;  


    // Constructeur
    public DijkstraPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
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
        
    public void activateNodeCount(boolean activate) {
        this.nodeCountActive = activate;
    }


    public int getNodeCount() {
        return this.exploredNodesCount;
    }
    

    @Override
    public List<Action> plan() {
      
      
        
        PriorityQueue<StateCostPair> queue = new PriorityQueue<>(Comparator.comparingInt(pair -> pair.cost));
        queue.add(new StateCostPair(initialState, 0));

        
        Map<Map<Variable, Object>, Map<Variable, Object>> parentMap = new HashMap<>();
        Map<Map<Variable, Object>, Action> actionMap = new HashMap<>();
        Map<Map<Variable, Object>, Integer> costMap = new HashMap<>();
        costMap.put(initialState, 0);

        
        Set<Map<Variable, Object>> visited = new HashSet<>();

        
        while (!queue.isEmpty()) {
            StateCostPair currentPair = queue.poll();
            Map<Variable, Object> currentState = currentPair.state;
            int currentCost = currentPair.cost;
                       
            if (nodeCountActive) {
                this.exploredNodesCount++;  
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
                    int newCost = currentCost + action.getCost();

                    
                    if (!visited.contains(successorState) && (!costMap.containsKey(successorState) || newCost < costMap.get(successorState))) {
                        costMap.put(successorState, newCost);
                        queue.add(new StateCostPair(successorState, newCost));
                        parentMap.put(successorState, currentState); 
                        actionMap.put(successorState, action); 
                    }
                }
            }
        }

       
        return null;
    }

    // Methode auxiliaire pour construire le plan une fois l'objectif atteint
    private List<Action> constructPlan(Map<Variable, Object> state, Map<Map<Variable, Object>, Map<Variable, Object>> parentMap, Map<Map<Variable, Object>, Action> actionMap) {
        List<Action> plan = new ArrayList<>();
        Map<Variable, Object> currentState = state;

        while (parentMap.containsKey(currentState)) {
            Action action = actionMap.get(currentState);
            plan.add(action);
            currentState = parentMap.get(currentState); 
        }

        
        Collections.reverse(plan);
        return plan;
    }

    // Classe interne pour representer un etat et son cout
    private static class StateCostPair {
        Map<Variable, Object> state;
        int cost;

        StateCostPair(Map<Variable, Object> state, int cost) {
            this.state = state;
            this.cost = cost;
        }
    }
}

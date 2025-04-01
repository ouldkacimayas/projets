package planning;

import modelling.Variable;
import java.util.*;

public class BFSPlanner implements Planner {
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;

    private boolean nodeCountActive = false;  // activer/désactiver la sonde
    private int exploredNodesCount = 0;  // compteur de nœuds explorés

    public BFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    public int getExploredNodesCount() {
        return this.exploredNodesCount;
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

    @Override
    public List<Action> plan() {
        Queue<Map<Variable, Object>> queue = new LinkedList<>();
        queue.add(initialState);

        Map<Map<Variable, Object>, Map<Variable, Object>> parentMap = new HashMap<>();
        Map<Map<Variable, Object>, Action> actionMap = new HashMap<>(); //plan
        Set<Map<Variable, Object>> visited = new HashSet<>();
        visited.add(initialState);
        //verifie si l'etat initial satisfait deja le but , si c'est le cas retourner un plan vide
        if (goal.isSatisfiedBy(initialState)){
            return new ArrayList<Action>();
        }else{

        while (!queue.isEmpty()) {
            Map<Variable, Object> currentState = queue.poll();
            visited.add(currentState);

            if (nodeCountActive) {
                this.exploredNodesCount++;
            }

            if (goal.isSatisfiedBy(currentState)) {
                return constructPlan(currentState, parentMap, actionMap);
            }

            for (Action action : actions) {
                if (action.isApplicable(currentState)) {
                    Map<Variable, Object> successorState = action.successor(currentState);

                    // Vérification si le successeur a déjà été exploré ou est déjà dans la queue
                    if (!visited.contains(successorState) && !queue.contains(successorState)) {

                        parentMap.put(successorState, currentState);
                        actionMap.put(successorState, action);

            if (goal.isSatisfiedBy(successorState)) {
                return constructPlan(successorState, parentMap, actionMap);
            }else{
                 queue.add(successorState);
            }
                    }
                }
            }
        }

        return null;  // Si aucun plan n'est trouvé
    }
    }

    private List<Action> constructPlan(Map<Variable, Object> state,
                                       Map<Map<Variable, Object>, Map<Variable, Object>> parentMap,
                                       Map<Map<Variable, Object>, Action> actionMap) {
        List<Action> plan = new ArrayList<>();
        Map<Variable, Object> currentState = state;

        // Reconstituer le plan en remontant les nœuds parents
        while (parentMap.containsKey(currentState)) {
            Action action = actionMap.get(currentState);
            plan.add(action);
            currentState = parentMap.get(currentState);
        }

        Collections.reverse(plan);  // Inverser l'ordre pour obtenir le plan dans l'ordre correct
        return plan;
    }
}

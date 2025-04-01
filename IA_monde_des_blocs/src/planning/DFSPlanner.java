package planning;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import modelling.Variable;

public class DFSPlanner implements Planner{
    private Map<Variable,Object> initialState;
    private Set<Action> actions;
    private Goal goal;
        
    private  boolean nodeCountActive = false; // Pour activer desactiver la sonde
    private int exploredNodesCount = 0; 

    public DFSPlanner(Map<Variable,Object> initialState, Set<Action> actions, Goal goal){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    public void activateNodeCount(boolean activate) {
        this.nodeCountActive = activate;
    }

 

    @Override
    public List<Action> plan(){
               
        exploredNodesCount = 0;

        return dfs(initialState, new ArrayList<Action>(), new HashSet<Map<Variable, Object>>());
  
    }
     

    public int getExploredNodesCount() {
        return this.exploredNodesCount;
    }


    @Override
    public Map<Variable,Object> getInitialState(){return this.initialState;}
    
    public Set<Action> getActions(){return this.actions;}

    @Override
     public Goal getGoal(){return this.goal;} 

        
    private List<Action> dfs(Map<Variable, Object> currentState, List<Action> plan, Set<Map<Variable, Object>> visitedStates) {
               // Si la sonde est active, on incremente le compteur
        if (nodeCountActive) {
            this.exploredNodesCount++;
        }
        // Si letat courant satisfait le but, on retourne le plan actuel
        if (goal.isSatisfiedBy(currentState)) {
            return plan;
        }

        // Si cet etat a deja ete visite, on evite les cycles
        if (visitedStates.contains(currentState)) {
            return null;
        }

        // Marque lett courant comme visité
        visitedStates.add(currentState);

        
        for (Action action : actions) {
            
            if (action.isApplicable(currentState)) {
                // generre le  successeur en appliquant l'action
                Map<Variable, Object> successorState = action.successor(currentState);

                // Crere un nouveau plan avec cette action ajoute
                List<Action> newPlan = new ArrayList<>(plan);
                newPlan.add(action);

                // Appelle recursif DFS avec le nouvel etat et le nouveau plan
                List<Action> result = dfs(successorState, newPlan, visitedStates);

                
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }
}
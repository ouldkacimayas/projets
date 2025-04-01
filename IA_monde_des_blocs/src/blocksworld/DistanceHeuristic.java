package blocksworld;

import modelling.*;
import java.util.Map;
import planning.*;

// Heuristique qui calcule la distance  entre la position actuelle des blocs et leur position dans l'objectif
public class DistanceHeuristic implements Heuristic {
    
    private  Map<Variable, Object> goalState;
    
    public DistanceHeuristic(Map<Variable, Object> goalState) {
        this.goalState = goalState;
    }
    
    @Override
    public float estimate(Map<Variable, Object> currentState) {
        float totalDistance = 0;
        
        // Parcourt chaque variable de l'etat but
        for (Variable variable : goalState.keySet()) {
            Object goalValue = goalState.get(variable);
            Object currentValue = currentState.get(variable);
            
            // Si la position du bloc est differnte de celle dans le but  on clacul la distance entre eux
            if (!goalValue.equals(currentValue)) {
                if(goalValue instanceof Integer && currentValue instanceof Integer){
                    int currentPos = (Integer) currentValue;
                    int goalPos = (Integer) goalValue;
                    
                    // Calculer la distance
                    totalDistance += Math.abs(currentPos - goalPos);
                }

            }
        }
        
        return totalDistance;
    }
}

package blocksworld;

import modelling.*;
import java.util.Map;
import planning.*;
// heuristique qui calcule a chaque etape le nombre de block mal placer (par rapport a leur place dans le but)
public class MisplacedBlocksHeuristic implements Heuristic {
    
    private  Map<Variable, Object> goalState;
    
    public MisplacedBlocksHeuristic(Map<Variable, Object> goalState) {
        this.goalState = goalState;
    }
    
    @Override
    public float estimate(Map<Variable, Object> currentState) {
    int misplacedBlocks = 0;
    
        // Parcourt chaque variable de l'etat final
        for (Variable variable : goalState.keySet()) {
            Object goalValue = goalState.get(variable);
            Object currentValue = currentState.get(variable);
            
            // Si le bloc n'est pas dans la bonne position
             // c a d  quela valeur courante est differnete celle  dans le but
            if (!goalValue.equals(currentValue)) {
                misplacedBlocks++;
            }
        }
        
        return misplacedBlocks;
    }


}

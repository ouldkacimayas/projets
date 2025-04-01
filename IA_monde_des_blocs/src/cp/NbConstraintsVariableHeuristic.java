package cp;
import modelling.Variable;
import modelling.Constraint;
import java.util.*;


public class NbConstraintsVariableHeuristic implements VariableHeuristic{
    private Set<Constraint> constraints;
    private boolean cond;

    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean cond){
        this.constraints = constraints;
        this.cond = cond;
    }
    //redefinition de la methode pour trouver la meilleure variable
    @Override
    public Variable best(Set<Variable> variables ,Map<Variable, Set<Object>> domains){
        Map<Variable, Integer> variableConstraintCount = new HashMap<>();
         // compter le nbr de contrainte pour chaque variable
        for (Variable variable : variables) {
            int count = 0;
            for (Constraint constraint : constraints){
                if (constraint.getScope().contains(variable)) { // Vérifie si la contrainte contient  la variable
                    count++;
                }

            } 
            variableConstraintCount.put(variable, count);        
        }
        // Trouver la variable avec le plus ou le moins de contraintes selon la condition

        Variable bestVariable = null;
        int bestCount;
        if (cond) {
            bestCount = Integer.MIN_VALUE; // on cherche le plus  le plus
        } else {
            bestCount = Integer.MAX_VALUE; // on cherche  le moins
        }

        for (Map.Entry<Variable, Integer> entry : variableConstraintCount.entrySet()) {
            Variable variable = entry.getKey();
            int count = entry.getValue();

            if (cond && count > bestCount) { 
                bestCount = count;
                bestVariable = variable;
            } else if (!cond && count < bestCount) { 
                bestCount = count;
                bestVariable = variable;
            }
        }
        return bestVariable;


    }
}

package cp;

import modelling.Variable;
import java.util.*;

public class DomainSizeVariableHeuristic implements VariableHeuristic {
    private boolean cond; 

    public DomainSizeVariableHeuristic(boolean cond) {
        this.cond = cond;
    }
 //methode pour trouver la meilleur variable en fonction de la taille
 // de son domaine
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        Variable bestVariable = null;
        int bestSize;

        
        if (cond) {
            bestSize = Integer.MIN_VALUE; // On cherche la var le plus grand domaine
        } else {
            bestSize = Integer.MAX_VALUE; // On cherche la var le plus petit domaine
        }

        // Trouver la variable avec le plus ou le moins de taille de domaine 
        for (Variable variable : variables) {
            int size = domains.get(variable).size(); // Obtenir la size du domaine de la var

            if (cond && size > bestSize) { //  le plus grand domaine
                bestSize = size;
                bestVariable = variable;
            } else if (!cond && size < bestSize) { //  le plus petit domaine
                bestSize = size;
                bestVariable = variable;
            }
        }

        return bestVariable;
    }
}

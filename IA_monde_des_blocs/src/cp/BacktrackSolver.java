package cp;
import modelling.Variable;
import modelling.Constraint;
import java.util.*;

public class BacktrackSolver extends AbstractSolver{
    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints){
        super(variables,constraints);
    }


    @Override
    public Map<Variable, Object> solve() {
        // Appel initial avec une solution vide
        return backtrack(new HashMap<>(), new ArrayList<>(super.variables));
    }

    // methode auxilaire qui implémente l'algorithme de backtracking
     private Map<Variable, Object> backtrack(Map<Variable, Object> affectationPartielle, List<Variable> variableNonInstanciees) {
        
        if (variableNonInstanciees.isEmpty()) {
            return affectationPartielle; 
        }

        // Choisir une variable non encore instancie
        Variable vni = variableNonInstanciees.remove(0);
      
        for (Object valeur : vni.getDomain()) {
            Map<Variable, Object> nouvelleAffectation = new HashMap<>(affectationPartielle);
            nouvelleAffectation.put(vni, valeur);
				
            if (isConsistent(nouvelleAffectation)) {
                // Continuer la recherche recursive avec la nouvelle affectation
                Map<Variable, Object> resultat = backtrack(nouvelleAffectation, new ArrayList<>(variableNonInstanciees));
                if (resultat != null) {
                    return resultat; 
                }
            }
        }
        
        return null;
    }   

}

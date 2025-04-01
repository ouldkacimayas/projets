package cp;
import modelling.Variable;
import modelling.Constraint;
import java.util.Map;
import java.util.Set;

public abstract class AbstractSolver implements Solver{
    protected Set<Variable> variables;
    protected Set<Constraint> constraints;

    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints){
        this.variables = variables;
        this.constraints = constraints;
    }

    public Set<Variable> getVariables(){return this.variables;}
    public Set<Constraint> getConstraint(){return this.constraints;}

 
    // methode qui vérifie si une affectation partielle des variables respecte toutes les contraintes 
    public boolean isConsistent(Map<Variable, Object> affectationPartielle) {
       
        for (Constraint c : this.constraints) {
            boolean tslesVarInstancier = true;
            
            // pour verifier si ts les variables de la contrainte sont instancier
            for (Variable v : c.getScope()) {
                if (!affectationPartielle.containsKey(v)) {
                    tslesVarInstancier = false;
                    break;
                }
            }
            
          //// si la contrainte est satisfaite
            if (tslesVarInstancier) {
                if (!c.isSatisfiedBy(affectationPartielle)) {
                    return false; 
                }
            }
        	}
        return true; 
    
    }

}

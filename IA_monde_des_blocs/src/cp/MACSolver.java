package cp;

import modelling.Constraint;
import modelling.Variable;

import java.util.*;

public class MACSolver extends AbstractSolver {

    private ArcConsistency arcConsistency;

public MACSolver(Set<Variable> variables, Set<Constraint> constraints) {
    super(variables, constraints); 
    this.arcConsistency = new ArcConsistency(constraints);
}


    @Override
    public Map<Variable, Object> solve() {
        // Creer un mapping des domaines des variables
        Map<Variable, Set<Object>> domaines = createDomainsMap();
        
        return MAC(new HashMap<>(), new HashSet<>(getVariables()), domaines);
    }
    // methode auxilliare implemantant l'algorithme MAC
    private Map<Variable, Object> MAC(Map<Variable, Object> I, Set<Variable> V, Map<Variable, Set<Object>> ED) {
        
        if (V.isEmpty()) {
            return I; 
        }
        else{

            
            
            if (!arcConsistency.ac1(ED)) {
                return null; 
            }

            // prendre une variable non encore instanciee
            Variable xi = V.iterator().next(); 
            V.remove(xi);
            // Pour chaque valeur vi dans le domaine de xi
            for (Object vi : ED.get(xi)) {
                Map<Variable, Object> N = new HashMap<>(I);
                N.put(xi, vi); 

                // Vérifier la cohérence
                if (isConsistent(N)) {
                    
                    Map<Variable, Object> R = MAC(N,V, ED);
                    if (R != null) {
                        return R; // Retourner si une solution valide est trouvee
                    }
                }
            }

            // remettre xi dans V
            V.add(xi); 
            return null; // Retourner null si aucune solution n'a ete trouve
        }
    }

 

    
   
   // Methode pour creer une carte des domaines a partir des variables
    private Map<Variable, Set<Object>> createDomainsMap() {
        Map<Variable, Set<Object>> domaines = new HashMap<>();
        for (Variable variable : getVariables()) {
        domaines.put(variable, new HashSet<>(variable.getDomain())); 
        }
        return domaines;
    }

  



}

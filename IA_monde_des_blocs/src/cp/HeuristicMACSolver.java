package cp;

import modelling.Variable;
import modelling.Constraint;

import java.util.*;

public class HeuristicMACSolver extends AbstractSolver {
    private VariableHeuristic variableHeuristic; 
    private ValueHeuristic valueHeuristic;       
    private ArcConsistency arcConsistency;       

    // Constructeur prenant en arguments : variables, contraintes, heuristique sur les variables, heuristique sur les valeurs
    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints,
                              VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic) {
        super(variables, constraints); // Appelle le constructeur de la classe parente
        this.variableHeuristic = variableHeuristic; 
        this.valueHeuristic = valueHeuristic;       
        this.arcConsistency = new ArcConsistency(constraints); 
    }
// methode auxilliare implemantant l'algorithme MAC
    @Override
    public Map<Variable, Object> solve() {
        // Creer une carte des domaines des variables
        Map<Variable, Set<Object>> domaines = createDomainsMap();
        // Appeler la fonction MAC avec l'affectation initiale vide et les domaines
        return MAC(new HashMap<>(), new HashSet<>(getVariables()), domaines);
    }
    
		private Map<Variable, Object> MAC(Map<Variable, Object> I, Set<Variable> V, Map<Variable, Set<Object>> ED) {
    if (V.isEmpty()) {
        return I;
    } else{

        // Appliquer l'arc-consistance
        if (!arcConsistency.ac1(ED)) {
            return null;
        }

        // Choisir une variable non instanciee en utilisant l'heuristique
        Variable xi = variableHeuristic.best(V, ED);
        V.remove(xi);

        // Obtenir les valeurs dans un ordre specifique en utilisant l'heuristique
        List<Object> values = valueHeuristic.ordering(xi, ED.get(xi));

        for (Object vi : values) {
            Map<Variable, Object> N = new HashMap<>(I);
            N.put(xi, vi);

            if (isConsistent(N)) {
                Map<Variable, Object> R = MAC(N, V, ED);
                if (R != null) {
                    return R;
                }
            }
        }

        V.add(xi); // Remettre la variable dans V
        return null;
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

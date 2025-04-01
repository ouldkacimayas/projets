package cp;

import modelling.Variable;
import java.util.*;

public class RandomValueHeuristic implements ValueHeuristic {
    private Random random;

   
    public RandomValueHeuristic(Random random) {
        this.random = random;
    }
    //methode pour ordonner les valeurs de façon aléatoire
    @Override
    public List<Object> ordering(Variable variable, Set<Object> domain) {
        // conversion vers une liste pour qu on puiis melanger
        List<Object> values = new ArrayList<>(domain);
        
        // Mlanger la liste des valeur aleatoirement 
        Collections.shuffle(values, random);
        
        return values; 
    }
}

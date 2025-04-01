package datamining;

import java.util.HashSet;
import java.util.Set;
import modelling.*;
import java.util.*;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    
    public BruteForceAssociationRuleMiner(BooleanDatabase database) {
        super(database); 
    }

 



// Methode statique pour generer tous les sous-ensembles possible d'un ensemble donne
public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
    Set<Set<BooleanVariable>> subsets = new HashSet<>(); 
    List<List<BooleanVariable>> ps = new ArrayList<>();
    ps.add(new ArrayList<>()); 

    // generer le power set
    for (BooleanVariable item : items) {
        List<List<BooleanVariable>> newPs = new ArrayList<>();

        for (List<BooleanVariable> subset : ps) {
            newPs.add(subset);

            List<BooleanVariable> newSubset = new ArrayList<>(subset);
            newSubset.add(item);
            newPs.add(newSubset);
        }

        ps = newPs;
    }

    // exclure l'ensemble vide et l'ensemble complet
    for (List<BooleanVariable> subset : ps) {
        if (!subset.isEmpty() && subset.size() < items.size()) {
            subsets.add(new HashSet<>(subset));
        }
    }
    
    return subsets;
}

// Méthode pour extraire les règles d'association 
      @Override
    public Set<AssociationRule> extract(float minFrequency, float minConfidence) {
        Set<AssociationRule> rules = new HashSet<>();

        Set<Itemset> frequentItemsets;
        try {
            AbstractItemsetMiner itemsetMiner = new Apriori(this.database); 
            frequentItemsets = itemsetMiner.extract(minFrequency);
        } catch (Exception e) {
            e.printStackTrace();
            return rules; 
        }

        for (Itemset itemset : frequentItemsets) {
            Set<BooleanVariable> items = itemset.getItems();

            Set<Set<BooleanVariable>> candidatePremises = allCandidatePremises(items);

            for (Set<BooleanVariable> premise : candidatePremises) {
                Set<BooleanVariable> conclusion = new HashSet<>(items);
                conclusion.removeAll(premise);

                float ruleConfidence = AbstractAssociationRuleMiner.confidence(premise, conclusion, frequentItemsets);

                if (ruleConfidence >= minConfidence) {
                    rules.add(new AssociationRule(premise, conclusion, itemset.getFrequency(), ruleConfidence));
                }
            }
        }

        return rules;
    }





}

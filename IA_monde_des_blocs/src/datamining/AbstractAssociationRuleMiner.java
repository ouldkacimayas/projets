package datamining;
import  java.util.*;
import modelling.*;


public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {
    protected BooleanDatabase database;

    // Constructeur qui prend une base de données en argument
    public AbstractAssociationRuleMiner(BooleanDatabase database) {
        this.database = database;
    }

    @Override
    public BooleanDatabase getDatabase() {
        return this.database;
    }
    
    @Override
    public abstract Set<AssociationRule> extract(float minFrequency, float minConfidence);

        public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets) {
        for (Itemset itemset : itemsets) {
            if (itemset.getItems().equals(items)) {
                return itemset.getFrequency();
            }
        }
        
        
        throw new IllegalArgumentException("Ensemble d'items non trouvé dans l'ensemble d'itemsets.");
    }
  //// Méthode statique pour calculer la confiance d'une règle d'association  
    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> itemsets) {
        // Union de la prem et de la conclus
        Set<BooleanVariable> union = new HashSet<>(premise);
        union.addAll(conclusion);

        // Calcul de la fréq de la prem
        float premiseFrequency = frequency(premise, itemsets);

        // Calcul de la frq de la prem ∪ conclusi
        float unionFrequency = frequency(union, itemsets);

        // Si la freq de la prémisse est 0 (pour éviter la division par 0)
        if (premiseFrequency == 0) {
            throw new IllegalArgumentException("La fréquence de la prémisse est nulle, la confiance ne peut être calculée.");
        }

        // Calcul de la confiance
        return unionFrequency / premiseFrequency;
    }
}

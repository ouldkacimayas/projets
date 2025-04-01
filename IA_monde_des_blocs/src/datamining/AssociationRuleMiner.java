package datamining;


import java.util.Set;

public interface AssociationRuleMiner {

    // Méthode pour obtenir une instance de BooleanDatabase
    BooleanDatabase getDatabase();

    // Méthode pour extraire les regles d'association
    Set<AssociationRule> extract(float minFrequency, float minConfidence);
}

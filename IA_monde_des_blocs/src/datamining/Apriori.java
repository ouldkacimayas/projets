package datamining;
import modelling.*;
import java.util.*;

public class Apriori extends AbstractItemsetMiner{

    public Apriori(BooleanDatabase database){
        super(database);
    }



    public Set<Itemset> frequentSingletons(float minFrequency)throws Exception{
        Set<Itemset> singletons = new HashSet<Itemset>();

        for(BooleanVariable item: database.getItems()){
            Set<BooleanVariable> items = Collections.singleton(item);
            float frequency = frequency(items);

            if(frequency >= minFrequency){
                singletons.add(new Itemset(items,frequency));
            }
            
        }

        return singletons;
    }
    // methode statique pour combiner deux ensembles de BooleanVariable
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> itemset1, SortedSet<BooleanVariable> itemset2){
        if(itemset1.isEmpty()||itemset2.isEmpty()){
            return null;
        }

        int size1 = itemset1.size();
        int size2 = itemset2.size();
        //Condition 1
        if(size1 != size2){
            return null;
        }

        Iterator iter1 = itemset1.iterator();
        Iterator iter2 = itemset2.iterator();

        BooleanVariable current1 = null;
        BooleanVariable current2 = null;

        for(int i=0; i < size1; i++){
            //On recupere les positions actuelles des itemsets 1 et 2
            current1 = (BooleanVariable)iter1.next();
            current2 = (BooleanVariable)iter2.next();
            
            if(i==(size1-1)){
                //Condition 3
                if(current1.equals(current2)){
                    return null;
                }
            }//Condition 2
            else if(!current1.equals(current2)){
                return null;
            }
        }

        

        SortedSet<BooleanVariable> combined = new TreeSet<BooleanVariable>(COMPARATOR);

        combined.addAll(itemset1);
        combined.addAll(itemset2);

        return combined;
    }


   public static boolean allSubsetsFrequent(Set<BooleanVariable> itemset, Collection<SortedSet<BooleanVariable>> collection) {
    for (BooleanVariable item : itemset) {
        // Crée un sous-ensemble trié en supprimant un élément
        SortedSet<BooleanVariable> subItemset = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        subItemset.addAll(itemset);
        subItemset.remove(item);

        // Verifie si le sous-ensemble trie est present dans la collection
        if (!collection.contains(subItemset)) {
            return false;
        }
    }
    return true;
}



   @Override
public Set<Itemset> extract(float minFrequency) throws Exception {
    Set<Itemset> frequentItemsets = new HashSet<>();
    List<SortedSet<BooleanVariable>> currentLevel = new ArrayList<>();

    // Initialiser avec les singletons fréquents
    for (Itemset singleton : frequentSingletons(minFrequency)) {
        SortedSet<BooleanVariable> itemsetWithComparator = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        itemsetWithComparator.addAll(singleton.getItems());
        currentLevel.add(itemsetWithComparator);
        frequentItemsets.add(singleton);
    }
	
    // Boucle pour extraire les itemsets fréquents de tailles croissantes
    while (!currentLevel.isEmpty()) {
        List<SortedSet<BooleanVariable>> nextLevel = new ArrayList<>();
        for (int i = 0; i < currentLevel.size(); i++) {
            for (int j = i + 1; j < currentLevel.size(); j++) {
                // genrrer un candidat en combinant deux itemsets de taille k
                SortedSet<BooleanVariable> candidate = combine(currentLevel.get(i), currentLevel.get(j));

                // Verifier la validite du candidat et sa frequence
                if (candidate != null && allSubsetsFrequent(candidate, currentLevel)) {
                    float freq = frequency(candidate);
                    if (freq >= minFrequency) {
                        nextLevel.add(candidate);
                        frequentItemsets.add(new Itemset(candidate, freq));
                    }
                }
            }
        }

        // Passer au niveau suivant
        currentLevel = nextLevel;
    }

    return frequentItemsets;
}









}



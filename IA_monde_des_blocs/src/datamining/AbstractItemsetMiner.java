package datamining;
import modelling.BooleanVariable;
import java.util.*;

public abstract class AbstractItemsetMiner implements ItemsetMiner{
    // attribut statique  permettant de comparer les items
    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());
    protected BooleanDatabase database;

    public AbstractItemsetMiner(BooleanDatabase database){
        this.database = database;
    }

    public BooleanDatabase getDatabase(){return this.database;}
    // methode pour calculer la frequence d'un ensemble de BooleanVariables
    public float frequency(Set<BooleanVariable> itemset) {
        int count = 0;

        for (Set<BooleanVariable> transaction : this.database.getTransactions()) {
            if (transaction.containsAll(itemset)) {
                count++;
            }
        }

        return (float) count / this.database.getTransactions().size();
    }
}
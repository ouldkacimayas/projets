package datamining;
import modelling.BooleanVariable;
import java.util.*;
public interface ItemsetMiner{
    BooleanDatabase getDatabase();
    Set<Itemset> extract(float frequency)throws Exception;
}
package datamining;
import modelling.BooleanVariable;
import java.util.*;

public class Itemset{
    private Set<BooleanVariable> items;
    private float frequency;

    public Itemset(Set<BooleanVariable> items, float frequency)throws Exception{
        this.items = items;
        this.frequency = frequency;

        if(this.frequency>1 || this.frequency<0){
            throw new Exception("la frequence doit etre entre 0 et 1!!!\n");
        }
    }

    public Set<BooleanVariable> getItems(){return this.items;}
    public float getFrequency(){return this.frequency;}

    @Override
    public String toString() {
    	return "\n\nItemset{\n" + "items=" + items + ",\nfrequency=" + frequency + "}\n";
   }



}

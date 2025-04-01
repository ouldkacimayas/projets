package blocksworld;
import modelling.*;
import datamining.*;
import java.util.*;
import bwgeneratordemo.Demo;

public class AppDataMining{

    public static void main(String[] args){
        int n=10000;
        float minFrequency = (float) 2 / 3; // Resultat : 0.6666667
        float confiance = (float) 95 / 100; // Resultat : 0.95

        Random random = new Random();
        //Monde des blocks (5 blocks 5 piles)
        BooleanVariablesFactory bvFactory = new BooleanVariablesFactory(5,5);
        Set<BooleanVariable> booleanVariables = bvFactory.getBooleanVariables();
        BooleanDatabase dataBase = new BooleanDatabase(booleanVariables);

        for (int i = 0; i < n; i++) {
            List<List<Integer>> state = Demo.getState(random);
            Set<BooleanVariable> instance = bvFactory.getBooleanInstanciations(state);
            System.out.println("\nAfficher les etats : \n"+Demo.stateToString(state));
            dataBase.add(instance);
        }


        //Recuperer les motifs si il y en a 
        ItemsetMiner itemsetMiner  =  new Apriori(dataBase);
        try{
            Set<Itemset> itemsets = itemsetMiner.extract(minFrequency);
            System.out.println("Afficher les motifs: \n");
            for(Itemset itemset:itemsets){
                System.out.println(itemsets);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


        System.out.println("\n-------------------------------------\n");

        //Recuperer les regles d'associations si il y en a 
        AssociationRuleMiner associationRuleMiner  =  new BruteForceAssociationRuleMiner(dataBase);
        Set<AssociationRule> associationRules = associationRuleMiner.extract(minFrequency,confiance);
        System.out.println("Afficher les regles d'associations: \n"+associationRules);
        for(AssociationRule associationRule:associationRules){
            System.out.println(associationRule);
        }

    }
}

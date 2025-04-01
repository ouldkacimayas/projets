package modelling;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class App{
    public static void main(String[] args){
        //Creation des domaines
        Set<Object> domaine1 = new HashSet(Arrays.asList(2015, 2017, 2020, 2024));
        Set<Object> domaine2 = new HashSet(Arrays.asList(1, 2, 3, 4));
        Set<Object> domaine3 = new HashSet(Arrays.asList("Janvier", "Avril", "Aout", "Septembre", "Juin"));

        //Creation des variables
        Variable var1 = new Variable("annee",domaine1);
        Variable var2 = new Variable("nb",domaine2);
        
        Variable var3 = new Variable("mois", domaine3); 
        //Creation des sous domaines
        Set<Object> s1 = new HashSet(Arrays.asList(2015, 2020, 2024));
        Set<Object> s2 = new HashSet(Arrays.asList(2, 4));
        Set<Object> s3 = new HashSet(Arrays.asList("Janvier", "Avril", "Aout"));
        //Creation des instances
        System.out.println("\nCas : tous les contraintes sont satisfaites :\n");
        Map<Variable,Object> instances = new HashMap();
        instances.put(var1,2015);
        instances.put(var2,2);
        //Creation des contrainte
        instances.put(var3,"Janvier");

        DifferenceConstraint dc = new DifferenceConstraint(var1,var2);
        Implication i = new Implication(var1,s1,var2,s2);
        UnaryConstraint uc = new UnaryConstraint(var3,s3);

        
        //Tester les contraintes 

        if(dc.isSatisfiedBy(instances)){
            System.out.println("\t\t difference ok\n");
        }else{
            System.out.println("\t\t difference ko\n");
        }

        if(i.isSatisfiedBy(instances)){
            System.out.println("\t\t implication ok\n");
        }else{
            System.out.println("\t\t implication ko\n");
        }

        if(uc.isSatisfiedBy(instances)){
            System.out.println("\t\t unitaire ok\n");
        }else{
            System.out.println("\t\t unitaire ko\n");
        }

       ///////////////////////////////////////
         // Cas 2 : Contrainte de différence qui échoue
        System.out.println("\nCas 2 : Contrainte de différence échoue");
        Map<Variable, Object> instances2 = new HashMap<>();
        instances2.put(var1, 2015);   // var1 = 2015
        instances2.put(var2, 2015);    // var2 = 2015 (même valeur que var1)
        instances2.put(var3, "Janvier"); 

        DifferenceConstraint dc2 = new DifferenceConstraint(var1, var2);
        Implication i2 = new Implication(var1, s1, var2, s2); // devrait passer
        UnaryConstraint uc2 = new UnaryConstraint(var3, s3); // devrait passer
        System.out.println("Test des contraintes (Cas 2) : ");
             
        if (dc2.isSatisfiedBy(instances2)) {
            System.out.println("\t\t Contrainte de différence : ok");
        } else {
            System.out.println("\t\t Contrainte de différence : ko");
        }



        ///////////////////////////////////:
        System.out.println("\nCas 3 : Contrainte d'implication échoue");
        Map<Variable, Object> instances3 = new HashMap<>();
        instances3.put(var1, 2015);   // var1 = 2015 (dans s1)
        instances3.put(var2, 1);       // var2 = 1 (pas dans s2)
        instances3.put(var3, "Janvier"); // var3 = "Janvier"

        Implication i3 = new Implication(var1, s1, var2, s2); // devrait échouer
       
        if (i3.isSatisfiedBy(instances3)) {
            System.out.println("\t\t Contrainte d'implication : ok");
        } else {
            System.out.println("\t\t Contrainte d'implication : ko");
        }

      

        //////////////////////////////////////////////
        // Cas 4 : Contrainte unitaire qui échoue
        System.out.println("\nCas 4 : Contrainte unitaire échoue");
        Map<Variable, Object> instances4 = new HashMap<>();
        instances4.put(var1, 2015);  
        instances4.put(var2, 2);      
        instances4.put(var3, "Septembre"); // var3 n'est pas dans s3, donc échouera
        UnaryConstraint uc4 = new UnaryConstraint(var3, s3); // devrait échouer
        if (uc4.isSatisfiedBy(instances4)) {
            System.out.println("\t\t Contrainte unitaire : ok");
        } else {
            System.out.println("\t\t Contrainte unitaire : ko");
        }
        ////////////////////////////////////////////////
        // Cas 5 : instanciation vide une exception doit etre lance 
         System.out.println("\nCas 5 : Instanciation vide ");
        Map<Variable, Object> instances5 = new HashMap<>(); // Aucune instance
        DifferenceConstraint dc5 = new DifferenceConstraint(var1, var2);
        Implication i5 = new Implication(var1, s1, var2, s2);
        UnaryConstraint uc5 = new UnaryConstraint(var3, s3);

        try {
            System.out.println("Contrainte de différence (instances vides) : " + dc5.isSatisfiedBy(instances5));
        } catch (Exception e) {
            System.out.println("\t\t Erreur dans la contrainte de différence : " + e.getMessage());
        }

        try {
            System.out.println("Contrainte d'implication (instances vides) : " + i5.isSatisfiedBy(instances5));
        } catch (Exception e) {
            System.out.println("\t\t Erreur dans la contrainte d'implication : " + e.getMessage());
        }

        try {
            System.out.println("Contrainte unitaire (instances vides) : " + uc5.isSatisfiedBy(instances5));
        } catch (Exception e) {
            System.out.println("\t\t Erreur dans la contrainte unitaire : " + e.getMessage());
        }










          






    }
}
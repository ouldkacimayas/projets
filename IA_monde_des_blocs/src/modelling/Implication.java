package modelling;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
public class Implication implements Constraint{
        
    private Variable var1;
    private Variable var2;
    private Set<Object> s1;
    private Set<Object> s2;

    
    public Implication(Variable var1, Set<Object> s1, Variable var2, Set<Object> s2){
        this.var1 = var1;
        this.var2 = var2;
        this.s1 = s1;
        this.s2 = s2;
    }
    //Getteurs
    public Variable getVariable1(){return this.var1;}
    public Variable getVariable2(){return this.var2;}
    public Set<Object> getS1(){return this.s1;}
    public Set<Object> getS2(){return this.s2;}


    //Methode qui retourne l'ensemble des variables concernees par la contrainte
    public Set<Variable> getScope(){
        Set<Variable> variables = new HashSet<Variable>();
        variables.add(this.var1);
        variables.add(this.var2);
        return variables;
    }

    //Methode qui verifie si la contriante est respectee
    public boolean isSatisfiedBy(Map<Variable,Object> v){

        if (!v.containsKey(var1) || !v.containsKey(var2)) {
            throw new IllegalArgumentException("Pas de valeurs pour toutes les variables ");
        }
        
        Object valeur1 = v.get(this.var1);
        Object valeur2 = v.get(this.var2);

    
        if (s1.contains(valeur1)) {
            return s2.contains(valeur2);
        }

        return true;
    }
    
    @Override
    public String toString() {
        return "Implication \n Variable 1 : " + this.var1.getName()
         + " domaine : " + this.s1 + "\n" + " Variable 2 : "
          + this.var2.getName() + " domaine : " + this.s2+ "\n";
    }
    
}

package modelling;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
public class DifferenceConstraint implements Constraint{
    private Variable var1;
    private Variable var2;

   
    public DifferenceConstraint(Variable var1, Variable var2){
        this.var1 = var1;
        this.var2 = var2;
    }

    //Getteurs
    public Variable getVariable1(){return this.var1;}
    public Variable getVariable2(){return this.var2;}

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
        
            throw new IllegalArgumentException("Pas de  valeurs pour toutes les variable ");
        }

        Object val1 = v.get(this.var1);
        Object val2 = v.get(this.var2);
        return !val1.equals(val2);      
    }
    
    public String toString(){
    	return "Difference -> var1 : "+var1.getName()+" / var2 : "+var2.getName()+"\n";
    }
    
    
}

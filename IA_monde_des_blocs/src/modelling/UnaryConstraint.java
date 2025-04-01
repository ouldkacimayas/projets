package modelling;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;

public class UnaryConstraint implements Constraint{

    private Variable var;
    private Set<Object> s;

    
    public UnaryConstraint(Variable var, Set<Object> s){
        this.var = var;
        this.s = s;
    }

    //Getteurs
    public Variable getVariable(){return this.var;}
    public Set<Object> getS(){return this.s;}
    
    //Methode qui retourne l'ensemble des variables concernees par la contrainte
    public Set<Variable> getScope(){

        Set<Variable> variables = new HashSet<Variable>();
        variables.add(this.var);

        return variables;
    }

    
    //Methode qui verifie si la contriante est respectee
    public boolean isSatisfiedBy(Map<Variable,Object> v){

        if (!v.containsKey(var)) {
            throw new IllegalArgumentException("Pas de valeur pour la variable dans l'affectation");
        }
        Object val = v.get(this.var);
        return s.contains(val);

    }
    

    public String toString(){
        return "Unary : "+this.var.getName()+this.s;
    }
}                                                                                                                                   

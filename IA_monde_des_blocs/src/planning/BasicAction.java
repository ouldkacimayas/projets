package planning;
import modelling.Variable;
import java.util.Map;
import java.util.HashMap;

public class BasicAction implements Action{
    private Map<Variable,Object> pre;
    private Map<Variable,Object> eff;
    private int cost;

    public BasicAction(Map<Variable,Object> pre, Map<Variable,Object> eff, int cost){
        this.pre = pre;
        this.eff = eff;
        this.cost = cost;
    }

    public Map<Variable,Object> getPre(){return this.pre;}
    public Map<Variable,Object> getEff(){return this.eff;}

    @Override
    public int getCost(){return this.cost;}

    @Override
    public boolean isApplicable(Map<Variable,Object> state){
        for(Variable var : this.pre.keySet()){
            if(this.pre.get(var) != state.get(var)){
                return false;
            }
        }

        return true;
    } 



@Override
public Map<Variable, Object> successor(Map<Variable, Object> state) {
    
    // faire une copie de letat
    Map<Variable, Object> sp = new HashMap<>(state);

    // Mettre a jour les variabe
    for (Variable var : this.eff.keySet()) {
        sp.put(var, this.eff.get(var));
    }

    return sp;
}

@Override
public String toString() {
    return "Action [Preconditions: " + mapToString(pre) 
           + ", Effects: " + mapToString(eff) 
           + ", Cost: " + cost + "]";
}
// methode pour afficher un map
private String mapToString(Map<Variable, Object> map) {
    String result = "{";
    for (Variable var : map.keySet()) {
        result += var.getName() + "=" + map.get(var) + ", ";
    }

    return result + "}";
}



}
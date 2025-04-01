package planning;
import modelling.Variable;
import java.util.Map;
import modelling.Variable;
public class BasicGoal implements Goal{
    private Map<Variable,Object> instanciationPartielle;

    public BasicGoal(Map<Variable,Object> instanciationPartielle){
        this.instanciationPartielle = instanciationPartielle;
    }

    public Map<Variable,Object> getInstanciationPartielle(){return this.instanciationPartielle;}
    
    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> state){
        if(state.size()<this.instanciationPartielle.size()){
            return false;
        }
        
        for(Variable var : this.instanciationPartielle.keySet()){
            if(this.instanciationPartielle.get(var) != state.get(var)){
                return false;
            }
        }

        return true;
    }
}
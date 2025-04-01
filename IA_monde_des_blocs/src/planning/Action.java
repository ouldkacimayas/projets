package planning;
import modelling.Variable;
import java.util.Map;
public interface Action{
    boolean isApplicable(Map<Variable,Object> s);
    Map<Variable,Object> successor(Map<Variable,Object> s);
    int getCost();
}
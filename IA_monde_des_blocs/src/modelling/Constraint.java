package modelling;

import java.util.Map;
import java.util.Set;

public interface Constraint{
     Set<Variable> getScope();
     boolean isSatisfiedBy(Map<Variable,Object> v);
}
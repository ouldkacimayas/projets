package datamining;
import java.util.Set;
import modelling.*;

public class AssociationRule {
    private Set<BooleanVariable> premise;
    private Set<BooleanVariable> conclusion;
    private float frequency;
    private float confidence;

    
    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, 
    float frequency, float confidence) {
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequency = frequency;
        this.confidence = confidence;
    }

    public Set<BooleanVariable> getPremise() {
        return premise;
    }

    public Set<BooleanVariable> getConclusion() {
        return conclusion;
    }

    public float getFrequency() {
        return frequency;
    }

    public float getConfidence() {
        return confidence;
    }

    @Override
public String toString() {
    return "\npremise: " + premise.toString() + " , \nconclusion" + conclusion.toString() + " , \nfrequnce: " + this.frequency + " , \nconfiance: " + this.confidence + "\n";
}

}

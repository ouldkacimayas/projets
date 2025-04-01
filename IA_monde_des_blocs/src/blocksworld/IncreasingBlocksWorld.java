package blocksworld;
import java.util.*;
import modelling.*;

public class IncreasingBlocksWorld extends BlocksWorldConstraints {
    private Set<Constraint> incContraints;
    public IncreasingBlocksWorld(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);
        this.incContraints = new LinkedHashSet<>();
        createIncreasingConstraints();
    }

    //Methode qui genere des contraintes de croissance
    public void createIncreasingConstraints() {
        List<Variable> listVariableOnB = new ArrayList<>(super.getOnB());

        // Pour chaque variable onb(i)
        for (Variable variableOnB : listVariableOnB) {
            Set<Object> domainConstraint = new HashSet<>();
            int currentBlock = getNum(variableOnB.getName()); // pour recuperer le numero du bloc
            
            
            //parcourir le domaine de la variable onb
            for (Object value : variableOnB.getDomain()) {
                // Si la valeur est < a celle du  bloc actuel, elle est ajoute au domaine de croissance
                if (value instanceof Integer && (Integer) value < currentBlock) {
                    domainConstraint.add(value);
                }
            }

            // cree la contrainte de croissante avec le domaine generer
            Constraint increasingConstraint = new UnaryConstraint(variableOnB, domainConstraint);
            this.incContraints.add(increasingConstraint);
            super.constraints.add(increasingConstraint);
        }
    }

    //Recuperer que les contraintes de croissance
    public Set<Constraint> getIncConstraints(){
        return this.incContraints;
    }
    
}

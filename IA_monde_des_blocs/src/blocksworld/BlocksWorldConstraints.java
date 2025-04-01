package blocksworld;
import java.util.*;
import modelling.*;

public class BlocksWorldConstraints extends BlocksWorld {
    protected Set<Constraint> constraints;

    public BlocksWorldConstraints(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);
        this.constraints = new LinkedHashSet<Constraint>();
        createConstraints();
    }

 
    // methode pour genrer les contrainte basique que notre monde doit respecter
    public void createConstraints() {
        // Convertir les ensemble des variable en listes
        List<Variable> listVariableOnB = new ArrayList<>(super.getOnB());
        List<BooleanVariable> listVariableFixedB = new ArrayList<>(super.getFixedB());
        List<BooleanVariable> listVariableFreeP = new ArrayList<>(super.getFreeP());

        // generer les contraintes de difference (constraint 1)
        for (int i = 0; i < nbBlocks - 1; i++) {
            for (int j = i + 1; j < nbBlocks; j++) {
                
                DifferenceConstraint c1 = new DifferenceConstraint(listVariableOnB.get(i), listVariableOnB.get(j));
                this.constraints.add(c1);
            }
        }

        // generer les contraintes d'implication (constraint 2)
        for (int i = 0; i < nbBlocks; i++) {
            for (int j = 0; j < nbBlocks; j++) {
                if (i != j) {
                    
                    Set<Object> domaineOnB = new HashSet<>();
                    domaineOnB.add(j);  // Ajouter uniquement `j` comme valeur dans le domaine
                    Set<Object> domaineFixed = new HashSet<>();
                    domaineFixed.add(true);

                    
                    Variable varOnB = listVariableOnB.get(i);
                    BooleanVariable varFixedB = listVariableFixedB.get(j);

                    
                    Constraint implicationConstraint = new Implication(varOnB, domaineOnB, varFixedB, domaineFixed);
                    this.constraints.add(implicationConstraint);
                }
            }
        }


        // generer des contraintes d'implication (constraint 3) : onB -> freeP 
        for (int i = 0; i < nbBlocks; i++) {
            for (int j = 0; j <nbPiles; j++) {
                Variable varOnB = listVariableOnB.get(i);
                BooleanVariable varFreeP = listVariableFreeP.get(j);
                // Domaine de la variable onb
                Set<Object> domaineOnB = new HashSet<>();
                domaineOnB.add(-(j+1 ));
                
                // Domaine de variable FreeP  qui doit etre a false  
                Set<Object> domaineFreeP = new HashSet<>();
                domaineFreeP.add(false);

                Implication implicationConstraint = new Implication(varOnB, domaineOnB, varFreeP, domaineFreeP);
                this.constraints.add(implicationConstraint);
            }
        }
    }

    public Set<Constraint> getConstraints() {
        return this.constraints;
    } 
    public void setConstraints(Set<Constraint> s){
        this.constraints = s;
    }
    // Methode pour tester si toutes les contraintes sont satisfaites
    public boolean allSatisfied(Map<Variable, Object> instanciations) {
        for (Constraint constraint : this.constraints) {
            if (!constraint.isSatisfiedBy(instanciations)) {
                return false;
            }
        }
        return true;
    }

    //Methode qui recupere le numero d'une variable donnee depuis son nom
    public int getNum(String varName){
        for(int i=0; i<varName.length();i++ ){
            if(Character.isDigit(varName.charAt(i))){
                return Integer.parseInt(varName.substring(i));
            }
        }
        //Exception variable sans numero
        throw new IllegalArgumentException("Nom de variable incorrect: elle ne contient pas de chiffre."); 
    }
}

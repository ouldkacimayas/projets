package blocksworld;
import java.util.*;
import modelling.*;

public class RegularBlocksWorld extends BlocksWorldConstraints {
    public  Set<Constraint> contrainteReguliere;
    public RegularBlocksWorld(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);
        this.contrainteReguliere = new HashSet<>();
        addRegularConstraint();
    }

    // Methode pour generer les contraintes de regularite 
    public void addRegularConstraint() {
        // pour chaque couple different de variable on va generer une contrainte d'implication
        for (Variable variable1 : this.getOnB()){
            for (Variable variable2 : this.getOnB()){
                if (!variable1.equals(variable2)){
                    Set<Object> domain= new HashSet<>();
                    for (Variable var : this.getFreeP()){
                        domain.add(-getNum(var.getName()));
                    } 
                    // calculer l'ecart entre la premiere et la 2eme variable
                   int ecart = getNum(variable1.getName()) - getNum(variable2.getName());
    
                    for (Variable variable3 : this.getOnB()){
                        // verifier si la 3eme variable est differnte de la premier et la 2eme
                        if (!variable1.equals(variable3) && !variable2.equals(variable3)){
                            // Calcul de l'ecart entre les deux prochains blocs 

                           int ecart2 = getNum(variable2.getName()) - getNum(variable3.getName());
                            if (ecart == ecart2){
                                //ajouter la valeur de la 3eme variable au domaine 
                                domain.add(getNum(variable3.getName()));
                            }
                        }
                    }

                    Implication imp = new Implication(variable1, Set.of(getNum(variable2.getName())), variable2, domain);
                    contrainteReguliere.add(imp);
                    super.constraints.add(imp);
                    }
            }
        }

    }

    public Set<Constraint> getContReguliere() {
        return this.contrainteReguliere;
    }


}

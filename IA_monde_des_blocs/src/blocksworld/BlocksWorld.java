package blocksworld;
import modelling.*;
import java.util.*;
public class BlocksWorld{
    //Attributs
    protected int nbBlocks;
    protected int nbPiles;
    private Set<Variable> onB;
    private Set<BooleanVariable> fixedB;
    private Set<BooleanVariable> freeP;
    protected Set<Variable> variables;
        //Constructeur
    public BlocksWorld(int nbBlocks,int nbPiles){
        this.nbBlocks=nbBlocks;
        this.nbPiles=nbPiles;
      
        this.onB = new LinkedHashSet<Variable>(); // pour maintenir lordre dinsertion 
        this.fixedB = new LinkedHashSet<BooleanVariable>();
        this.freeP = new LinkedHashSet<BooleanVariable>();
        this.variables = new LinkedHashSet<>();
        this.createVariables();

    }
        //Getteurs
    public int getNbBlocks(){return this.nbBlocks;}
    public int getNbPiles(){return this.nbPiles;}
    public Set<Variable>  getOnB(){return this.onB;}
    public Set<BooleanVariable>  getFixedB(){return this.fixedB;}
    public Set<BooleanVariable>  getFreeP(){return this.freeP;}
        public Set<Variable> getVariables() {
        return this.variables;
    }
    
    // methode pour cree les variables necessaires pour representer le monde des blocs
    public void createVariables(){
        //  variables onb pour chaque bloc
       for (int i = 0; i < nbBlocks; i++) {
       
           Set<Object> domaineOnb = new TreeSet<>();//TreeSet pour qu ca soit ordonne
            
            //  les entiers negatifs pour representer les piles
            for (int j = -nbPiles; j < 0; j++) {
                domaineOnb.add(j);
            }
            
            //  les entiers positifs ou nuls pour repredsenter les blocs
            for (int k = 0; k < nbBlocks; k++) {
                if (k != i) {
                    domaineOnb.add(k);
                }
            }
            
            //Creation et ajout de la variable dans l'ensemble de variables onb
            Variable onb = new Variable("on" + i, domaineOnb);
            this.onB.add(onb);
            this.variables.add(onb);
        }

        //  variables fixedb pour chaque bloc
        for (int i = 0; i < nbBlocks; i++) {
            BooleanVariable fixedb = new BooleanVariable("fixed" + i);
            this.fixedB.add(fixedb);
            this.variables.add(fixedb);
        }

        //  variables freep pour chaque pile
       for (int i = 1; i <= nbPiles; i++) {
         BooleanVariable freep = new BooleanVariable("free" + i);
            this.freeP.add(freep);
           this.variables.add(freep);
        }
    }


}

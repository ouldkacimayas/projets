package blocksworld;
import modelling.*;
import java.util.*;

public class BooleanVariablesFactory {
    //Atributs
    private int nbBlocks;
    private int nbPiles;
    private Set<BooleanVariable> booleanVariables;
    private Set<BooleanVariable> onBB;
    private Set<BooleanVariable> onTableBP;

    //Constructeur
    public BooleanVariablesFactory(int nbBlocks, int nbPiles){
        this.nbBlocks = nbBlocks;
        this.nbPiles = nbPiles;
        this.booleanVariables = new LinkedHashSet<BooleanVariable>();
        this.onBB = new LinkedHashSet<BooleanVariable>();
        this.onTableBP = new LinkedHashSet<BooleanVariable>();
        createBooleanVariables();
    }

    //Creation des variables booleennes onbb, onTablebp, fixedb et freep
    public void createBooleanVariables(){
        //Creation de la variable onbb
        for(int i = 0; i<this.nbBlocks; i++){
            for(int j=0; j<this.nbBlocks; j++){
                if(i!=j){
                    BooleanVariable onbb = new BooleanVariable("on" + i +","+ j);
                    this.onBB.add(onbb);
                    this.booleanVariables.add(onbb);
                }
            }
        }

        //Creation de la variable onTablebp 
        for(int i = 0; i<this.nbBlocks ; i++){
            for(int j=0; j<this.nbPiles; j++){
                BooleanVariable onTablebp = new BooleanVariable("onTable" + i +","+ j);
                this.onTableBP.add(onTablebp);
                this.booleanVariables.add(onTablebp);
            }
        }

        //Creation des variable fixedb et freep
        BlocksWorld blocksWorld = new BlocksWorldConstraints(nbBlocks, nbPiles);
        this.booleanVariables.addAll(blocksWorld.getFixedB());
        this.booleanVariables.addAll(blocksWorld.getFreeP());
    }

    //Accesseurs
    public Set<BooleanVariable> getBooleanVariables(){
        return this.booleanVariables;
    }

    public int getNbBlocks(){
        return this.nbBlocks;
    }
    public int getNbPiles(){
        return this.nbPiles;
    }

    //Recuperer une instance d'un monde de blocks
    public Set<BooleanVariable> getBooleanInstanciations(List<List<Integer>> state){
        Set<BooleanVariable> booleanVars = new HashSet<BooleanVariable>();
        List<Integer> pile = null;//Nombre de piles
        int nbStacks = state.size();//Taille des piles 
        int pileSize = 0;
        int blockNum = 0;
        int currentBlockNum = 0;//Numero du block actuel
        int nextBlockNum = 0;//Numero du block suivant 

        for (int i=0; i<nbStacks; i++){
            pile = state.get(i);
            pileSize = pile.size();
            //Recuperer les variable onTablebp 
            for(int j=0; j<pileSize; j++){
                blockNum = pile.get(j);
                booleanVars.add(new BooleanVariable("onTable" + blockNum + "," + i));
            }

            for(int k=0; k<pileSize-1; k++){
                currentBlockNum = pile.get(k);
                nextBlockNum = pile.get(k+1);
                booleanVars.add(new BooleanVariable("on" + nextBlockNum + "," + currentBlockNum));
            }

            if (pile.isEmpty()){
                booleanVars.add(new BooleanVariable("free" + i));
            }

            for (int z = 0; z < pileSize-1; z++){
                blockNum = pile.get(z);
                booleanVars.add(new BooleanVariable("fixed" + blockNum));
            }
        }

        return booleanVars;
    }
}
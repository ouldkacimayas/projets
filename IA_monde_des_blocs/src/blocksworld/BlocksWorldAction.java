package blocksworld;
import java.util.*;
import modelling.*;
import planning.*;

public class BlocksWorldAction extends BlocksWorld  {
    private int nbBlocks;
    private int nbPiles;
    private Set<Action> actions;
        public BlocksWorldAction(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);  // Appeler le constructeur de la classe parente
        this.actions =  new LinkedHashSet<Action>();
        generateActions();
    }

     // methode pour genrer toutes les action pour tous les types deplacement 
    public void generateActions() {
     List<Variable> listVarOnB = new ArrayList<>(super.getOnB());
     List<Variable> listVarFixedB = new ArrayList<>(super.getFixedB());
     List<Variable> listVarFreep = new ArrayList<>(super.getFreeP());
     
        
        for (Variable b1 : getOnB()) {
            for (Variable b2 : getOnB()) {
                 if (!b1.equals(b2)){
                    // Action 1: Deplacer un bloc b1 du dessus de b2 vers b3
                    for (Variable b3 : getOnB()){
                        if (!b3.equals(b1) && !b3.equals(b2)){
                            generateAction1(b1, b2, b3);
                        }
                    }
                    ////////
                    // Action 2: Deplacer un bloc b1 du dessus de b2 vers une pile vide p
                    for (BooleanVariable p : getFreeP()){
                        generateAction2(b1, b2, p);
                    }

                     // Action 3: Deplacer un bloc b1 du dessous de pile p vers b2
                    for (BooleanVariable p : getFreeP()) {
                        generateAction3(b1, p, b2); 
                     }
                }
 
            }

             // Action 4: Deplacer un bloc b1 du dessous de pile p vers une pile vide p'
             for (BooleanVariable p1 : getFreeP()){
                for (BooleanVariable p2 : getFreeP()){
                    if (!p1.equals(p2)){
                        generateAction4(b1, p1, p2);
                    }
                }
             }
        }
      }

      // methodes pour  specifier les precondition et les effet de chaque type d'action
      // Action 1: Deplacer un bloc b1 du dessus de b2 vers b3 (ces precondition et effets)
    private void generateAction1(Variable b1, Variable b2, Variable b3){
        List<Variable> listVarFixedB = new ArrayList<>(super.getFixedB());
        Map<Variable, Object> pre = new HashMap<>();
        Map<Variable, Object> eff = new HashMap<>();

        pre.put(b1,Integer.parseInt(b2.getName().substring(2)));//b1 est sur b2
  
        eff.put(b1,Integer.parseInt(b3.getName().substring(2))); // apres le deplacemet b1 sera sur b3
        Variable varfixedb1 = listVarFixedB.get(Integer.parseInt(b1.getName().substring(2)));// recuperer la variable fixed de  b1 
       Variable varfixedb3  = listVarFixedB.get(Integer.parseInt(b3.getName().substring(2)));
       Variable varfixedb2  = listVarFixedB.get(Integer.parseInt(b2.getName().substring(2)));
       pre.put(varfixedb1,false); // b1 nest pas fixe
       eff.put(varfixedb2, false);// pour dire que b2 devient libre aucun bloc nest desssus
       pre.put(varfixedb3,false);// cad ya pas de bloc sur b3 (b3 nest pas fixe)
       eff.put(varfixedb3,true); // pour dire b3 devient fixe ya le bloc b1 dessus

       // creation des action avec les precondition et les effet cout 1
       Action action = new BasicAction(pre, eff, 1);
        actions.add(action);

    }

    //// action2 
     // Action 2: Deplacer un bloc b1 du dessus de b2 vers une pile vide p (effets et preconditions)
    private void generateAction2(Variable b1, Variable b2, BooleanVariable p){
        List<Variable> listVarFixedB = new ArrayList<>(super.getFixedB());
        Map<Variable, Object> pre = new HashMap<>();
        Map<Variable, Object> eff = new HashMap<>();  
        pre.put(b1,Integer.parseInt(b2.getName().substring(2)));//b1 est sur b2
        // p doit etre vide freep = true
        pre.put(p,true); // p est vide 
        Variable varfixedb1 = listVarFixedB.get(Integer.parseInt(b1.getName().substring(2)));
        pre.put(varfixedb1,false); // b1 nest pas fixe

        eff.put(b1,-Integer.parseInt(p.getName().substring(4)));// b1 sera sur la pile p
        eff.put(p,false ); // la pile devient occupeer

        Variable varfixedb2  = listVarFixedB.get(Integer.parseInt(b2.getName().substring(2)));
        eff.put(varfixedb2, false);// pour dire que b2 devient libre aucun bloc nest desssus
      
       // creation des action avec les precondition et les effet
       Action action2 = new BasicAction(pre, eff, 1);
       actions.add(action2);



    }


     // Action 3: Deplacer un bloc b1 du dessous de pile p vers b2 (effets et preconditions)
     private void generateAction3(Variable b1, BooleanVariable p, Variable b2){
        List<Variable> listVarFixedB = new ArrayList<>(super.getFixedB());
        Map<Variable, Object> pre = new HashMap<>();
        Map<Variable, Object> eff = new HashMap<>(); 

        pre.put(b1,-Integer.parseInt(p.getName().substring(4))); // b1 est dans le dessous de p
        
        Variable varfixedb1 = listVarFixedB.get(Integer.parseInt(b1.getName().substring(2)));
        pre.put(varfixedb1,false); // b1 nest pas fixe
        Variable varfixedb2  = listVarFixedB.get(Integer.parseInt(b2.getName().substring(2)));
        pre.put(varfixedb2,false);// b2 est libre
        eff.put(varfixedb2,true); // b2 devient fixe apres le deplacemnt 
        eff.put(p,true ); // la pile devient libre
        eff.put(b1,Integer.parseInt(b2.getName().substring(2))); // b1 devient sur b2
        

        // creation des action 
        Action action3 = new BasicAction(pre, eff, 1);
        actions.add(action3);   
     }
    
     // Action 4: Deplacer un bloc b1 du dessous de pile p vers une pile vide p' (effets et preconditions)
     private void generateAction4(Variable b1, BooleanVariable p1, BooleanVariable p2){
        List<Variable> listVarFixedB = new ArrayList<>(super.getFixedB());
        Map<Variable, Object> pre = new HashMap<>();
        Map<Variable, Object> eff = new HashMap<>();

        pre.put(b1,-Integer.parseInt(p1.getName().substring(4))); // b1 est dans le dessous de p1
       Variable varfixedb1 = listVarFixedB.get(Integer.parseInt(b1.getName().substring(2)));
       
       pre.put(varfixedb1,false); // b1 nest pas fixe
       pre.put(p2,true);//p2 est libre

       /// effet
       eff.put(b1,-Integer.parseInt(p2.getName().substring(4)));// b1 sera dans la pile p2
       eff.put(p1,true); //p1 devient libre
       eff.put(p2,false); // p2 ,est plus libre

       // creation des actions 
       Action action4 = new BasicAction(pre, eff, 1); 
       actions.add(action4);




     }
    // methode pour obtenir l'ensemble  des actions
    public Set<Action> getActions() {
        return this.actions;
    }

}
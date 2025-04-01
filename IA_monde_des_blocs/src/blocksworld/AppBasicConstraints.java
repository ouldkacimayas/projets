package blocksworld;

import modelling.*;
import java.util.*;
public class AppBasicConstraints {
    public static void main(String[] args){
        BlocksWorldConstraints bw = new BlocksWorldConstraints(8,5);

       
        Map<Variable,Object> instVar = new LinkedHashMap<Variable,Object>();
        List<Variable> list = new ArrayList<Variable>(bw.getOnB()); 
     
        instVar.put(list.get(0),1);
        instVar.put(list.get(1),2);
        instVar.put(list.get(2),-1);
        instVar.put(list.get(3),7);
        instVar.put(list.get(4),-5);
        instVar.put(list.get(5),-4);
        instVar.put(list.get(6),4);
        instVar.put(list.get(7),-3);
    

        List<BooleanVariable> list2 = new ArrayList<BooleanVariable>(bw.getFixedB());

        System.out.println("\n\n\n");
        instVar.put(list2.get(0),false);
        instVar.put(list2.get(1),true);
        instVar.put(list2.get(2),true);
        instVar.put(list2.get(3),false);
        instVar.put(list2.get(4),true);
        instVar.put(list2.get(5),false);
        instVar.put(list2.get(6),false);
        instVar.put(list2.get(7),true);
   
        
        List<BooleanVariable> list3 = new ArrayList<BooleanVariable>(bw.getFreeP()); 

        
        instVar.put(list3.get(0),false);//pile1
        instVar.put(list3.get(1),true);//pile2
        instVar.put(list3.get(2),false);
        instVar.put(list3.get(3),false);//pile4
        instVar.put(list3.get(4),false);
        
        
        

        System.out.println("conf1 : \n");
        if (bw.allSatisfied(instVar)){
            System.out.println("toutes les contraintes basique sont satisfaite");
        }else{
             System.out.println(" les contraintes basique  qui ne sont pas  satisfaite : \n");
            for (Constraint constraint : bw.getConstraints()) {
              if (!constraint.isSatisfiedBy(instVar)) {
                
                 System.out.println(constraint);
            }
            }

        }


        /// configuration qui ne satisfait pas les contraintes basique 
        Map<Variable,Object> instVar2 = new LinkedHashMap<Variable,Object>();
         
        // onb
        instVar2.put(list.get(0),-2);
        instVar2.put(list.get(1),2);
        instVar2.put(list.get(2),-1);
        instVar2.put(list.get(3),-2);
        instVar2.put(list.get(4),-5);
        instVar2.put(list.get(5),-4);
        instVar2.put(list.get(6),4);
        instVar2.put(list.get(7),-3);
    

        //fixedb

        System.out.println("\n\n\n");
        instVar2.put(list2.get(0),false);
        instVar2.put(list2.get(1),false);
        instVar2.put(list2.get(2),false);
        instVar2.put(list2.get(3),false);
        instVar2.put(list2.get(4),false);
        instVar2.put(list2.get(5),false);
        instVar2.put(list2.get(6),false);
        instVar2.put(list2.get(7),true);
   
        
        // freep 

        
        instVar2.put(list3.get(0),false);//pile1
        instVar2.put(list3.get(1),true);//pile2
        instVar2.put(list3.get(2),false);//pile3
        instVar2.put(list3.get(3),false);//pile4
        instVar2.put(list3.get(4),false);//pile4


      //affichage des resultat
      System.out.println("conf2 : \n");
        if (bw.allSatisfied(instVar2)){
            System.out.println("toutes les contraintes basique sont satisfaite");
        }else{
             System.out.println(" les contraintes basique  qui ne sont pas  satisfaite : \n");
            for (Constraint constraint : bw.getConstraints()) {
              if (!constraint.isSatisfiedBy(instVar2)) {
                
                 System.out.println(constraint);
            }
            }

        }
    }
    }

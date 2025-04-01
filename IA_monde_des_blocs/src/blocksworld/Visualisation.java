package blocksworld;
import java.util.*;
import planning.*;
import modelling.*;
import javax.swing.JFrame;
import bwmodel.*;
import bwui.*;
// class qui permet de visualiser notre configuration 
public class Visualisation{
        // methode statique pour visiualiser un etat de notre monde des bloc
    public static BWState<Integer> makeBWState(int n ,BlocksWorld bw ,Map<Variable , Object> instVar){
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(n);
        // Parcours de chaque bloc dans le modnde des blocks
        for (int b = 0; b < n; b++) {
            Variable onb = null;
            List<Variable> listOnb = new ArrayList<Variable>(bw.getOnB()); 
            onb = listOnb.get(b);
            if (onb != null) {
                int under = (int) instVar.get(onb);
                if (under >= 0) {
                    builder.setOn(b, under);
                }
            }
        }
        
        BWState<Integer> state = builder.getState();
        //retourner l'etat
        return state;
    } 
}
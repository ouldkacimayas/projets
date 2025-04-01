package modelling;
import java.util.Set;
import java.util.HashSet;
public class BooleanVariable extends Variable {
      //Constructeur de la class
     public BooleanVariable(String nom) {
        super(nom, new HashSet<>());
        super.getDomain().add(true);
        super.getDomain().add(false);

    }

     




   


}
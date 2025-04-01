package modelling;
import java.util.Set;
import java.util.HashSet;
public class Variable{
        
    private String nom;
    private Set<Object> domaine;
         
    public Variable(String nom,Set<Object> domaine){
        this.nom=nom;
        this.domaine=domaine;

    }
        
    public String getName(){
        return this.nom;

    }
    public Set<Object> getDomain(){
        return this.domaine;
    }

   
    //Methode qui retourne le hashCode du nom d'une variable
    public int hashCode(){
        return this.nom.hashCode();
    }
    //Methode qui verifie si deux variables ont le meme nom
    public boolean equals(Object obj){
 
     if (this == obj) {
        return true;
     }

     if (obj == null || !(obj instanceof Variable)) {
        return false;
     }
 
     Variable var = (Variable) obj;

     return this.nom.equals(var.nom);

 }
    @Override
    public String toString() {
        return "\n"+"Variable{" +
                "nom='" + nom + '\'' +
                ", domaine=" + domaine +
                '}';
    }
        
    


}

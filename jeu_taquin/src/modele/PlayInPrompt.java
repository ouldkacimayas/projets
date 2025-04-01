package modele;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner; 

public class PlayInPrompt{
    //Attributs
    private Taquin taquin;

    /**
     * Constructeur
     * @param taquin notre table de jeu
     */
    public PlayInPrompt(Taquin taquin){
        this.taquin = taquin;
    }

    /**
     * Methode qui lance le jeu sur terminale
     */
    public void launcheGame(){
        System.out.println(taquin.toString());

        Scanner sc = new Scanner(System.in);
        int ent = 1;
        boolean cond = false;
        
        while(!taquin.isWin()){
            System.out.println("-----------------------------------");
            try{
                ent = sc.nextInt();
            }catch(Exception e){
                System.out.println("Erreur: la valuer entree n'est pas un nombre!.");
                System.exit(0);
            }

            //pour quitter la partie    
            if(ent==0){
                System.out.println("Fin de la partie.");
                System.exit(0);
            }

            List<TaquinBox> res = taquin.getSwitchableBox();
            int[] switchableV = new int[res.size()];
            cond = false;

            //recuperer les valeurs de notre table de jeu dans une matrice
            for(int i=0; i<res.size(); i++){
                switchableV[i] = res.get(i).getValue();
            }
            
            //verifier si l'entrée correspond à une case deplaçable
            for(int val : switchableV){
                if(ent==val){
                    cond = true;
                    break;
                }
            }

            //Verification du bon deplacement de cases 
            if(!cond){
                if(ent>taquin.getSize()-1){
                    System.out.println("Erreur le chiffre entre n'existe pas!");
                }else{
                    System.out.println("Erreur le chiffre entre ne peut pas etre deplace!");
                }
                
                System.out.println("Veuillez entrez un nombre a nouveau: ");
            }
            else{
                taquin.switchBoxes(ent);
            }

            System.out.println(taquin.toString());
        }
        System.out.println("Bravo vous avez gagne la partie!");
    }

    /**
     * Methode qui retourne le Taquin
     * @return Taquin notre jeu
     */
    public Taquin getTaquin(){ return this.taquin;}
}
package modele;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import observer.AbstractListenableModel;
import observer.ModelListener;


//uml
/**
 * 
 
 * Classe qui represente le modele du taquin.

 */
public class Taquin extends AbstractListenableModel {
    private List<TaquinBox> taquin;
    private int size;
    private TaquinBox emptyBox = null;
    private TaquinBox taquinBox = null;
    protected List<ModelListener> listeners = new ArrayList<>();
    public int[][] boxValues;
    protected static int nbswitch = 0;
    /**
     * Constructeur de la classe Taquin.
     * @param size est la taille du taquin.
     */
    public Taquin(int size){
        this.taquin = new ArrayList<TaquinBox>();
        this.size = size*size;
        creatTaquin();
    }
    /**
     * Autre constructeur de la classe Taquin.
     * lattribut size est initialiser par defaut a 9.
     */
    public Taquin(){
        this.taquin = new ArrayList<TaquinBox>();
        this.size = 9;
        creatTaquin();
    }

    /**
     * methode pour cree un taquin ordonné de base.
     */
    private void creatTaquin(){
        int value = 1;
        
        for(int i=0; i<Math.sqrt(this.size);i++){
            for(int j=0; j<Math.sqrt(this.size); j++){
                this.taquinBox = new TaquinBox(j,i,value%(this.size));
                this.taquin.add(this.taquinBox);
                value++;
            }
        }
        setEmptyBox();
        shuffleTquin();
      

    } 
    
    /**
     * methode qui melange le taquin.
     */
    private void shuffleTquin(){
        List<TaquinBox> list = null;
        int val = 0;
        TaquinBox tmpTB = null;
        Random rand = new Random();

        for(int i=0; i<this.size*4; i++){
            list = getSwitchableBox();
            //Choisir au hasard une des cases deplaçables pour echanger sa position avec la case vide 
            val = rand.nextInt(list.size());
            tmpTB = list.get(val);
            switchBoxes(tmpTB);
        }
    }

    /**
     * methode qui retourne la liste des cases du taquin.
     * @return la liste de toutes les cases de notre jeu
     */
    public List<TaquinBox> getTaquin(){ return this.taquin;}

    /**
     * methode pour obtenir la taille du  taquin.
     */
    public int getSize(){ return this.size;}

    /**
     * methode pour obtenir le nombre de deplacement de la case vide du  taquin.
     * @return nombre de deplacements
     */
    public int getnbswitch(){ return this.nbswitch;}

    /**
     * methode pour modifier la taille du  taquin.
     * @param newSize nouvelle taille
     */
    public void setSize(int newSize){  
        this.size = newSize;
        this.taquin.clear();
        creatTaquin();
    }

    /**
     * methode qui retourne l'etat actuel du taquin.
     * @return une chaine de caractere qui represente l'etat de notre table de jeu
     */
    public String toString(){
        String str = "";
        String tmp = "";
        int k = 0;

        for(int i=0; i<Math.sqrt(this.size);i++){
            for(int j=0; j<Math.sqrt(this.size); j++){
                tmp = this.taquin.get(k).toString();
                if(tmp.equals("0")){
                    if(this.size>9){
                        tmp = "  ";
                    }
                    else{
                        tmp = " ";
                    }
                    
                }
                
                str += "|";
                if((tmp.length() == 1)&&(this.size>9)){
                    str += "0";
                }
                str += tmp;
                str += "|";

                k++;
            }
            str+="\n";
        }

        return str;
    }

    /**
     * Methode qui tri les composants taquin box selon leurs coordonnees x et y 
     */

    public void sortTaquin(){
        List<TaquinBox> tmpT = new ArrayList<TaquinBox>();
        TaquinBox tmpTB;
        int x = 0;
        int y = 0;
        while(true){
            int i = 0;
            while(i<this.size){
                tmpTB = this.taquin.get(i);
                if((tmpTB.getX() == x)&&(tmpTB.getY() == y)){
                    tmpT.add(tmpTB);
                    break;
                }
                i++;
            }

            if(x<Math.sqrt(this.size)){
                x++;
            }
            else{
                y++;
                x=0;
            }

            if(x+y==this.size){ break;}

        }
                
        this.taquin = tmpT;
        setEmptyBox();
    }

     /**
     * methode pour modifier la case vide.
     */
    private void setEmptyBox(){
        int k = 0;
        TaquinBox empty = null;
        //Rechercher la case qui porte la valeur 0
        while(k<this.size){
            empty = this.taquin.get(k);
            if(empty.getValue() == 0){
                this.emptyBox = empty;
                break;
            }
            k++;
        }

        this.emptyBox = empty;
    }

    /**
     * methode qui retourne la case vide .
     */
    public TaquinBox getEmptyBox(){ return this.emptyBox;}
     
    /**
     * methode pour obtenir les cases deplaçable dans notre taquin.
     * @return List qui contient toute les classes qu'on peut deplacer
     */
    public List<TaquinBox> getSwitchableBox(){
        int k = 0;
        int x = this.emptyBox.getX();
        int y = this.emptyBox.getY();
        TaquinBox tmpTB = null; 
        List<TaquinBox> tmpT = new ArrayList<TaquinBox>();
        //Rechercher les cases voisines de la case vide 
        while(k<this.size){
            tmpTB = this.taquin.get(k);
            if(tmpTB.getX()-x == 0){
                if(Math.abs(tmpTB.getY()-y) == 1){
                    tmpT.add(tmpTB);
                }
            }
            else if(Math.abs(tmpTB.getX()-x) == 1){
                if(tmpTB.getY()-y == 0){
                    tmpT.add(tmpTB);
                }
            }
            k++;
        }

        return tmpT;
    }

    /**
     * methode pour deplaceer une case .
     *@param value est la valeur de la case qu on veut deplacer.
     */
    public void switchBoxes(int value){
        nbswitch++;
        TaquinBox tmpTB = getBox(value);
        switchBoxes(tmpTB);       
    }

    private void switchBoxes(TaquinBox tmpTB){
        //echanger les positions de la case vide et la case qu'on veut deplacer 
        int tmpX = this.emptyBox.getX();
        int tmpY = this.emptyBox.getY();

        this.emptyBox.setX(tmpTB.getX());
        this.emptyBox.setY(tmpTB.getY());

        tmpTB.setX(tmpX);
        tmpTB.setY(tmpY);
        
        sortTaquin();
        notifyChanges();
    }


     /**
     * methode pour obtenir une case 
     *@param value est la valeur de la case.
     *@return un TaquinBox
     */
    public TaquinBox getBox(int value){
        TaquinBox tmpTB = null;
        int k = 0;

        while(k<this.size){
            tmpTB = this.taquin.get(k);
            if(tmpTB.getValue() == value){
                return tmpTB;
            }
            k++;
        }
        return tmpTB;
    }

    /**
     * methode pour determiner si la partie est terminer .
     * @return true si les valeurs des cases sont triées et false sinon
     */
    public boolean isWin(){
        int valeur = 0;
        TaquinBox tmpTB = null;

        for(int i=0; i<this.size-1; i++){
            tmpTB = this.taquin.get(i);
            valeur = tmpTB.getValue();
            if((i+1) != valeur){
                return false;
            }
        }

        return true;
    }

      /**
     * methode pour obtenir la valeur d'une case.
     *@param x est le numero de ligne de la case dans notre grille.
     *@param y est le numero de colonne de la case dans notre grille.
     *@return un int qui represente la valeur de la case qui a pour coordonnes x et y et -1 si une telle case n'existe pas
     */
        public int getBoxValue(int x, int y) {
        for (TaquinBox box : taquin) {
            if (box.getX() == x && box.getY() == y) {
                return box.getValue();
            }
        }
        return -1; // Retourne -1 si aucune case trouvée avec les coordonnées spécifiées
    }
    
    
      /**
     * methode qui transforme le taquin d'une ArrayList en une matrice.
     * @return int[][] une matrice taquin
     */
    public int[][] initializeBoxValues() {
        List<TaquinBox> taquinBoxes = this.getTaquin();
        boxValues = new int[this.getSize()][this.getSize()];
        for (TaquinBox box : taquinBoxes) {
            boxValues[box.getX()][box.getY()] = box.getValue();
        }
         return boxValues;
    }







    /**
     * Ajoute un écouteur à ce modèle.
     *
     * @param e Le ModelListener à ajouter en tant qu'écouteur.
     */

    @Override
    public void addListener(ModelListener e) {
        listeners.add(e);
    }
        /**
     * Supprime un écouteur de ce modèle.
     *
     * @param e Le ModelListener à supprimer en tant qu'écouteur.
     */

    @Override
    public void removeListener(ModelListener e) {
        listeners.remove(e);
    }
    /**
     * Notifie tous les écouteurs enregistrés que le modèle a été mis à jour.
     */
    @Override
    public void notifyChanges() {
        for (ModelListener e : listeners) {
            e.modelUpdated(this);
        }
    }

}

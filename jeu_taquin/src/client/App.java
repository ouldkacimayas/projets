package client;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner; 
import modele.Taquin;
import modele.*;
import observer.ModelListener;
import javax.swing.*;
import java.awt.event.*;
import vue.TaquinView;
import vue.TaquinController;

public class App{
    public static void main(String[] args){

        Taquin taquin = null;

        if(args.length>0){
            int size = Integer.parseInt(args[0]);
            taquin = new Taquin(size);
        }
        else{
            taquin = new Taquin();
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Choisissez le mode de jeu: Terminale->1 ,Interface graphique->2: ");
        int playingMode = sc.nextInt();

        if(playingMode == 1){
            PlayInPrompt pip = new PlayInPrompt(taquin);
            pip.launcheGame();
        } else if (playingMode == 2){
        // Création d'un objet TaquinView et ajout à une fenêtre JFrame
        TaquinView taquinView = new TaquinView(taquin);


        // Création d'un objet TaquinController et association de la vue
        TaquinController controller = new TaquinController(taquinView, taquin);

                }else{System.out.println("saisir une valeur soit 1 soit 2");}


    }


        



}

les membre du groupe:
SELMANE Madjid
OULD KACI Mayas 
AIT ALI BELKACEM Sonia
ANNOU Rayane


pour compiler le projet :
ouvrir le terminal dans le repertoire src et lancee la commanr "ant"

si vous souhaitez executer les classe executable une par une :voic les commande 
javac -d ./build modele/*.java
javac -d .build vue/*.java
javac -d ./build App.java //classe executable pour la partie graphique
java -cp ./build App
javac -d ./build AppTerminal.java //classe executable pour la partie terminal
java -cp ./build AppTerminal
javac -d ./build AppBots.java //classe executable pour la partie terminal avec la presence des robots
java -cp ./build AppBots
javac -d ./build AppMain.java ////classe executable qui regroupe toutes les type d'execution (classe executer avec la cmd "ant"
java -cp ./build AppMain



/// remarque : 
 pour la partie graphic le joueur actuel est distinguer avec sa couleur (vert olive ) pour le jouer actuel et bleu pour les autre
 aussi pour les action de deplacemnt et depot de bombe ou mine il faut selectionner la case avant de cliquer sur l'action 
 
 // lien vers notre depot git:
  https://redmine-etu.unicaen.fr/git/annou-selmane-ould-kaci-ait-ali-belkacem 

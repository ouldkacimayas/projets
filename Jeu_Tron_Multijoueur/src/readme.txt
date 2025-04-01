javac -d ../build -cp "../lib/*" modele/*.java
java -cp "../build:../lib/*" modele.AppMaxN

liste des classe executable :
AppMaxN prend scenarios2.json comme argument        // java -cp "../build:../lib/*" modele.AppMaxN modele/scenarios2.json //
AppMaxN2 prend scenariotaille.json  comme argument  //java -cp "../build:../lib/*" modele.AppMaxN2 modele/scenariotaille.json //
AlgoSOS  prend scenariotaille.json  comme argument  //java -cp "../build:../lib/*" modele.AppSOS modele/scenarioequipe.json //


pour visualiez les resultat 
python3 dessinerGraphe.py  pour voir l'impact de la profendeur 
python3 dessinertaille.py  pour voir l'impact de la taille de la grille


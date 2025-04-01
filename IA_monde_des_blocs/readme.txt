membres:
1:{
	Nom:SELMANE
	PRENOM:Madjid
}
2:{
	Nom: Ould Kaci
	Prenom:Mayas
}

//// Les commandes sont lancées à partir du répertoire src
// NB: il faut mettre les librairies dans un répertoire lib qui sera au meme niveau que src

// commande pour compiler tous les package dont le package blocksworld depend:
	javac -d ../build modelling/*.java planning/*.java cp/*.java datamining/*.java

	
 // commande pour compiler le package blocksworld avec les bibliotheque necessaire: 
   javac -d ../build -cp .:../lib/bwgenerator.jar:../lib/blocksworld.jar ./blocksworld/*.java
   
   
  // pour executer les classe executable 
  java -cp .:../lib/bwgenerator.jar:../lib/blocksworld.jar:../build blocksworld.NomClasseExecutable
  
  
  !!! remplacer NomClasseExecutable par le nom de la classe a executer 
   
   
   // les differnete classe executable  pour chaque partie:
    	modelisation:
    		AppBasicConstraints : pour tester si les contrainte basic sont satisfaite
    		AppConstraints : pour tester les contrainte reguliere et croissante
    		
        planification:
        	AppAction :Cette classe permet de créer un état initial et un état but,puis de lancer les différents solveurs sur l’exemple donné.
        	en ce qui concerne l'affichage des plans trouvé les lignes qui le permettent sont commentées en raison de surchage d'affichage, pour les afficher il suffit de les decommenter ou bien les visualier à travers l'interface en chosissant un plannificateur
			
			   	
       
       Satisfaction de contraintes:
       		AppSolver : Cette classe exécutable permet de trouver une configuration qui satisfait les contraintes définies, en utilisant différents solveurs.
       		
       		
       Extraction des connaissances:
       		AppDataMining : Cette classe exécutable permet de réaliser des tâches d’extraction de connaissances, telles que la découverte de motifs fréquents et de règles d’association.
       		
       		
 !!! pour plus de detail voir le rapport inclut	
			

Projet Calculette - ANTLR4 en Java

Contexte

Ce projet a été proposé dans le cadre du cours "Théorie des langages et compilation", en 3ème année de Licence Informatique à l'Université de Caen Normandie. L'objectif est d'utiliser les outils étudiés en classe, notamment les automates et les grammaires, pour créer un analyseur syntaxique et un compilateur simple pour un langage d'expressions mathématiques. Ce projet permet d'acquérir une compréhension pratique du fonctionnement des compilateurs et des outils d'analyse pour d'autres langages.

Membres du groupe 

    Nom : SELMANE
    Prénom : Madjid

    Nom : Ould Kaci
    Prénom : Mayas

Description

Ce projet implémente une calculette simple en utilisant ANTLR 4 pour l'analyse et l'interprétation des expressions mathématiques. Le programme prend un fichier d'entrée, analyse les expressions et effectue les calculs correspondants.

Prérequis

Avant d'exécuter ce projet, vous devez avoir installé les éléments suivants :

    Java 11 ou version supérieure

    ANTLR 4 (outil de génération de parseurs)

    JDK (Java Development Kit)


Vérifiez que ANTLR est correctement installé en exécutant :

$ antlr4 -version

Cela doit renvoyer la version d'ANTLR installée.

Installation du projet

    Clonez le projet depuis le dépôt Git :

    $ git clone https://github.com/ouldkacimayas/projets/MVaP_calculette
    $ cd MVaP_calculette

    
    Il faudra au préalable s'assurer d'avoir le jar antlr version 4 dans son CLASSPATH .
    
    $ export CLASSPATH=".:/usr/share/java/*:$CLASSPATH"


Exécution

Pour exécuter le projet, vous devez suivre ces étapes :


1. Génération du parser avec ANTLR

Avant de compiler votre programme Java, vous devez générer les fichiers nécessaires pour le parser avec ANTLR 4. Exécutez la commande suivante pour générer les classes Java à partir de votre grammaire ANTLR (assurez-vous que le fichier grammaire ANTLR existe) :

$ antlr4 Calculette.g4

Cela générera plusieurs fichiers Java nécessaires pour l'analyse syntaxique, comme CalculetteLexer.java, CalculetteParser.java, etc.
2. Compiler les fichiers Java

Ensuite, compilez tous les fichiers Java générés par ANTLR ainsi que le fichier principal MainCalculette.java. Vous pouvez utiliser la commande suivante :

$ javac *.java

Cela compilera tous les fichiers .java dans le répertoire actuel.
3. Exécuter le programme

Une fois les fichiers compilés, vous pouvez exécuter le programme avec la commande suivante :

$ java MainCalculette <fichier_de_test>

Remplacez <fichier_de_test> par le chemin vers le fichier contenant les expressions mathématiques à analyser.
Exemple

Si votre fichier de test s'appelle test.txt et contient une expression comme 3 + 4 * (2 - 1), vous pouvez l'exécuter ainsi :

$ java MainCalculette test.txt

Cela exécutera le programme et affichera le résultat de l'expression.
Structure des fichiers


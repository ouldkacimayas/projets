import csv
import matplotlib.pyplot as plt

class GraphiqueTailleGrille:
    def __init__(self, fichier_csv):
        # Charger les données depuis le fichier CSV sans utiliser Pandas
        self.tailles_grille = []
        self.victoires = []
        
        # Lecture des données du fichier CSV
        with open(fichier_csv, newline='') as csvfile:
            lecteur = csv.DictReader(csvfile)  # Utiliser DictReader pour lire les données
            for ligne in lecteur:
                self.tailles_grille.append(int(ligne['Taille de grille']))  # Extraire la colonne "Taille de grille"
                self.victoires.append(float(ligne['Taux de victoire (%)']))  # Extraire la colonne "Taux de victoire"
        
        # Trier les données par taille de grille (axe X)
        sorted_data = sorted(zip(self.tailles_grille, self.victoires))  # Trier par taille de grille
        self.tailles_grille, self.victoires = zip(*sorted_data)  # Séparer les données triées

    def afficher_graphe(self):
        # Tracer la courbe reliant les points
        plt.figure(figsize=(8,5))
        
        # Tracer la courbe
        plt.plot(self.tailles_grille, self.victoires, marker='o', color='b', linestyle='-', linewidth=2, markersize=8, markeredgewidth=2)

        # Ajouter des labels et un titre
        plt.xlabel("Taille de la grille")
        plt.ylabel("Taux de victoire (%)")
        plt.title("Impact de la taille de la grille sur les victoires")
        
        # Ajouter une grille
        plt.grid(True, linestyle="--", alpha=0.7)

        # Afficher le graphe
        plt.show()

# Utilisation de la classe
fichier_csv = "resultats3.csv"  # Le fichier CSV contenant les données
graphique = GraphiqueTailleGrille(fichier_csv)  # Création d'une instance de la classe
graphique.afficher_graphe()  # Appeler la méthode pour afficher le graphe


import csv
import matplotlib.pyplot as plt

class GraphiqueProfondeur:
    def __init__(self, fichier_csv):
        # Charger les données depuis le fichier CSV sans utiliser Pandas
        self.profondeurs = []
        self.victoires = []
        
        with open(fichier_csv, newline='') as csvfile:
            lecteur = csv.DictReader(csvfile)  # Utiliser DictReader pour lire les données
            for ligne in lecteur:
                self.profondeurs.append(int(ligne['Profondeur']))  # Extraire la colonne "Profondeur"
                self.victoires.append(int(ligne['Nombre de victoires']))  # Extraire la colonne "Nombre de victoires"
    
    def afficher_graphe(self):
        # Tracer le graphe avec une courbe (line plot)
        plt.figure(figsize=(8,5))
        plt.plot(self.profondeurs, self.victoires, marker='o', color='skyblue', linestyle='-', linewidth=2, markersize=8, markeredgewidth=2)

        # Ajouter des labels et un titre
        plt.xlabel("Profondeur de recherche")
        plt.ylabel("Nombre de victoires")
        plt.title("Impact de la profondeur sur les victoires")
        plt.xticks(self.profondeurs)  # Afficher toutes les profondeurs sur l'axe X
        plt.grid(True, linestyle="--", alpha=0.7)

        # Afficher le graphe
        plt.show()

# Utilisation de la classe
fichier_csv = "resultats.csv"  # Le fichier CSV contenant les données
graphique = GraphiqueProfondeur(fichier_csv)  # Création d'une instance de la classe
graphique.afficher_graphe()  # Appeler la méthode pour afficher le graphe


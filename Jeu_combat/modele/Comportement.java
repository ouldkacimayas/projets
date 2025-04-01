package modele;

/**
     * L'interface Comportement définit le comportement d'un bot dans le jeu.
     * Un comportement est une action que le bot peut effectuer, comme attaquer, se défendre, ou toute autre action définie par un comportement spécifique.
     * 
     * Les classes qui implémentent cette interface doivent fournir une logique pour la méthode 
     * agir qui décrit l'action à exécuter par un bot lorsqu'il adopte ce comportement.
     * 
     * Cette interface permet de créer des bots avec différents comportements sans modifier la classe Bot elle-même, favorisant ainsi la flexibilité et l'extensibilité du code.
     */
public interface Comportement{
    

    /**
     * Exécute l'action associée au comportement sur le bot spécifié.
     * 
     * @param bot le bot sur lequel l'action va être exécutée.
     */
    public void agir(Bot bot);

    /**
     * Retourne la strategie employée.
     * 
     * @return une chaîne de caractères représentant le comportement du bot.
     */
    public String toString();
}
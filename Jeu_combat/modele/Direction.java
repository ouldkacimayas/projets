package modele;

/**
 * L'énumération Direction représente les quatre directions possibles sur un plateau de jeu : 
 * haut, bas, gauche et droite.
 * Chaque direction est associée à des valeurs de déplacement en termes de coordonnées x et y.
 */
public enum Direction {
    HAUT(0, -1),
    BAS(0, 1),
    GAUCHE(-1, 0),
    DROITE(1, 0);

    private final int deltaX;
    private final int deltaY;

    /**
     * Constructeur de l'énumération Direction.
     * 
     * @param deltaX le déplacement sur l'axe X pour cette direction.
     * @param deltaY le déplacement sur l'axe Y pour cette direction.
     */
    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Retourne la valeur du déplacement sur l'axe X associé à cette direction.
     * 
     * @return la valeur du déplacement sur l'axe X.
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * Retourne la valeur du déplacement sur l'axe Y associé à cette direction.
     * 
     * @return la valeur du déplacement sur l'axe Y.
     */
    public int getDeltaY() {
        return deltaY;
    }
}

package observer;
public interface ModelListener {

    /**
     * Notifie tous les écouteurs enregistrés que le modèle a été mis à jour.
     */
    void modelUpdated(Object source);
}

package observer;
import java.util.ArrayList;
import java.util.List;



public  abstract class AbstractListenableModel  implements ListenableModel {
    /**
     * Ajoute un écouteur à ce modèle.
     *
     * @param listener Le ModelListener à ajouter en tant qu'écouteur.
     */
    public abstract void   addListener(ModelListener listener);
    /**
     * Supprime un écouteur de ce modèle.
     *
     * @param listener Le ModelListener à supprimer en tant qu'écouteur.
     */
    public abstract void   removeListener(ModelListener listener);
    /**
     * Notifie tous les écouteurs enregistrés que le modèle a été mis à jour.
     */
    public abstract void  notifyChanges();
}

package observer;
import java.util.ArrayList;
import java.util.List;

public interface ListenableModel {
    public void addListener(ModelListener listener);

    public void removeListener(ModelListener listener);
}
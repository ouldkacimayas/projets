package modele; 
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
/**
 * Classe responsable de charger des scénarios à partir d'un fichier JSON.
 */
public class ScenarioLoader {
    public static List<Scenario> loadScenarios(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), objectMapper.getTypeFactory().constructCollectionType(List.class, Scenario.class));
    }
}

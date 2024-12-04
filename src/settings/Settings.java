package settings;

import entity.animal.AnimalType;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Settings {

    public final Map<String, Object> SETTINGS;

    private Settings() {

        SETTINGS = getSettings();

    }

    private static class SettingsHolder {
        public static final Settings HOLDER_INSTANCE = new Settings();
    }

    public static Settings getInstance() {
        return SettingsHolder.HOLDER_INSTANCE;
    }

    public Map<String, Object> getSettings() {

        Map<String, Object> settings = null;
        try (InputStream inputStream = new FileInputStream("src/settings/settings.yaml");) {

            Yaml yaml = new Yaml();

            settings = yaml.load(inputStream);

        } catch (FileNotFoundException e) {
            System.err.println("Файл YAML не найден: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке файла YAML: " + e.getMessage());
            e.printStackTrace();
        }

        return settings;
    }

    public Map<String, Object> probabilityOfEating(String type){
        Map<String, Object> probabilityOfEating = null;
        Map<String, Object> probabilityOfType = null;
        if (SETTINGS.containsKey("probabilityOfEating")) {
            probabilityOfEating = (HashMap<String, Object>) SETTINGS.get("probabilityOfEating");
        }
        if(probabilityOfEating.containsKey(type)){
            probabilityOfType = (HashMap<String, Object>) probabilityOfEating.get(type);
        }

        return probabilityOfType;
    }

    public Map<String, Object> getEntitiesProperty() {
        Map<String, Object> entityProperty = null;
        if (SETTINGS.containsKey("entityProperty")) {
            entityProperty = (HashMap<String, Object>) SETTINGS.get("entityProperty");
        }

        return entityProperty;
    }

    public Map<String, Object> getAnimalProperty(String type) {
        Map<String, Object> animalData = null;
        Map<String, Object> entityProperty = getEntitiesProperty();
        String typeStr = type.toString().toLowerCase();
        if (entityProperty.containsKey(typeStr)) {
            animalData = (HashMap<String, Object>) entityProperty.get(typeStr);
        }

        return animalData;
    }

    public Map<String, Object> getPlantProperty(String type) {
        Map<String, Object> plantData = null;
        Map<String, Object> entityProperty = getEntitiesProperty();
        String typeStr = type.toLowerCase();
        if (entityProperty.containsKey(typeStr)) {
            plantData = (HashMap<String, Object>) entityProperty.get(typeStr);
        }

        return plantData;
    }


}

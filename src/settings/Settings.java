package settings;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class Settings {

    private Settings(){

    }

    private static class SettingsHolder{
        public static final Settings HOLDER_INSTANCE = new Settings();
    }

    public static Settings getInstance(){
        return SettingsHolder.HOLDER_INSTANCE;
    }

    public Map<String, Object> getSettings() {


        Map<String, Object> settings = null;
        try (InputStream inputStream = new FileInputStream("src/settings/settings.yaml");) {

            Yaml yaml = new Yaml();

            settings = yaml.load(inputStream);

            if (settings.containsKey("user")) {
                Map<String, Object> userData = (Map<String, Object>) settings.get("user");
                String userName = (String) userData.get("name");
                System.out.println("Username: " + userName);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Файл YAML не найден: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке файла YAML: " + e.getMessage());
            e.printStackTrace();
        }


        return settings;
    }
}

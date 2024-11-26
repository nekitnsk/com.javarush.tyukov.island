package settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.FileReader;
import java.util.ArrayList;

public class Settings {

    private Settings(){

    }

    private static class SettingsHolder{
        public static final Settings HOLDER_INSTANCE = new Settings();
    }

    public static Settings getInstance(){
        return SettingsHolder.HOLDER_INSTANCE;
    }

    public Settings getSettings() {

        Settings settings = null;
        try (FileReader settingsFile = new FileReader("settings.xml");) {

            ObjectMapper mapper = new XmlMapper();



            settings = mapper.readValue(settingsFile, this.getClass());

        } catch (Exception FileNotFoundException) {
            System.out.println("Не найден файл с настройками");
        }


        return settings;
    }
}

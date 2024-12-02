package island;

import entity.Plant;
import entity.animal.Animal;
import entity.animal.AnimalFactory;
import entity.animal.AnimalType;
import settings.Settings;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cell {

        private final int PERCENT_FILL = 33;

        private List<Plant> plants = new CopyOnWriteArrayList<>();
        private List<Animal> animals = new CopyOnWriteArrayList<>();

        public Cell() {
            Settings settings = Settings.getInstance();

            AnimalType[] values = AnimalType.values();
            AnimalFactory animalFactory = new AnimalFactory();

            for (AnimalType type : values) {
                Map<String, Object> animalData = settings.getAnimalProperty(type);
                int startFill = (int) animalData.get("maxPopulation") * PERCENT_FILL / 100;

                for (int i = 0; i < startFill; i++) {
                    animals.add(animalFactory.getAnimal(type));
                }
            }

            Map<String, Object> plantData = settings.getPlantProperty("plant");
            int startFill = (int) plantData.get("maxPopulation") * PERCENT_FILL / 100;
            for (int i = 0; i < startFill; i++) {
                plants.add(new Plant());
            }
            
        }

        public List<Animal> getAnimals() {
            return animals;
        }

        public List<Plant> getPlants() {
            return plants;
        }
    }

package island;

import entity.Plant;
import entity.animal.Animal;
import entity.animal.AnimalFactory;
import entity.animal.AnimalType;
import settings.Settings;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Cell {

    private final int PERCENT_FILL = 30;

    private List<Plant> plants = new CopyOnWriteArrayList<>();
    public List<Animal> animals = new CopyOnWriteArrayList<>();
    private int myColumn;
    private int myRow;


    private final Lock lock = new ReentrantLock(true);

    public Cell(int row, int column) {
        this.myRow = row;
        this.myColumn = column;
        Settings settings = Settings.getInstance();

        AnimalType[] values = AnimalType.values();
        AnimalFactory animalFactory = new AnimalFactory();

        for (AnimalType type : values) {
            Map<String, Object> animalData = settings.getAnimalProperty(type.toString());
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

    public Lock getLock() {
        return lock;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Animal> getAnimals(AnimalType animalType) {

        List<Animal> animalListByType = getAnimals()
                .stream()
                .filter(animal -> animal.animalType.equals(animalType))
                .collect(Collectors.toList());

        return animalListByType;
    }

    public void addNewAnimal(AnimalType type){
        AnimalFactory animalFactory = new AnimalFactory();
        animals.add(animalFactory.getAnimal(type));
    }


    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    public int getMyColumn() {
        return myColumn;
    }

    public int getMyRow() {
        return myRow;
    }
}

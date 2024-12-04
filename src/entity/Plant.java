package entity;

import island.Cell;
import settings.Settings;

import java.util.List;
import java.util.Map;

public class Plant extends Entity {

    private Integer weight;
    Settings settings = Settings.getInstance();

    public Plant(){
        this.weight = Integer.valueOf(settings.getPlantProperty("plant").get("weight").toString());
    }

    public Integer getWeight() {
        return weight;
    }

    public static void spawn(Cell currentCell){
        currentCell.getLock().lock();
        Settings settings = Settings.getInstance();

        Map<String, Object> plantData = settings.getPlantProperty("plant");
        int maxFill = (int) plantData.get("maxPopulation");

        if(currentCell.getPlants().size() < maxFill){
            List<Plant> plants = currentCell.getPlants();
            plants.add(new Plant());
            currentCell.setPlants(plants);
        }

        currentCell.getLock().unlock();

    }
}

package view;

import entity.animal.AnimalType;
import island.Cell;
import island.Island;
import util.Util;

import java.util.HashMap;

public class View {
    private Island island;

    public View(Island island){
        this.island = island;
    }

    public void showStatistics(){
        Cell[][] cells = island.getCells();

        HashMap<String, Integer> sizeByType = new HashMap<>();
        for (AnimalType animalType : AnimalType.values()) {
            String type = Util.returnTypeAsString(animalType);
            sizeByType.put(type, 0);
        }
        sizeByType.put("Plants", 0);



        for (Cell[] row : cells) {
            for (Cell cell : row) {
                int size = 0;
                for (AnimalType animalType : AnimalType.values()) {
                    String type = Util.returnTypeAsString(animalType);
                    sizeByType.computeIfPresent(type, (k,v) -> v + cell.getAnimals(animalType).size());
                }
                sizeByType.computeIfPresent("Plants", (k,v) -> v + cell.getPlants().size());

            }
        }

        System.out.println(sizeByType);

    }
}

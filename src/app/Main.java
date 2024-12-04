package app;

import entity.animal.Animal;
import entity.animal.AnimalFactory;
import entity.animal.AnimalType;
import entity.animal.predator.Wolf;
import island.Cell;
import island.Island;
import settings.Settings;
import worker.IslandWorker;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Settings settings = Settings.getInstance();

        Island island = new Island((Integer) settings.SETTINGS.get("sizeRow"), (Integer) settings.SETTINGS.get("sizeColumn"));


        Cell[][] cells = island.getCells();

        IslandWorker islandWorker = new IslandWorker(island);
        islandWorker.start();


        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {

            }
        }
    }
}
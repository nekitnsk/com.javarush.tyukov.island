package app;

import entity.animal.Animal;
import entity.animal.AnimalFactory;
import entity.animal.AnimalType;
import island.Island;
import settings.Settings;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Settings settings = Settings.getInstance();

        Island island = new Island((Integer) settings.SETTINGS.get("sizeRow"), (Integer) settings.SETTINGS.get("sizeColumn"));




        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
//                System.out.println(cells[i][j].getAnimals().toString());
            }
        }
    }
}
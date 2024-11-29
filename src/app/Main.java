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

        AnimalFactory animalFactory = new AnimalFactory();

//        System.out.println(Arrays.toString(island.getCells()));

    }
}
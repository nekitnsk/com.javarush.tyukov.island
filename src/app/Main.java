package app;

import island.Island;
import settings.Settings;
import worker.IslandWorker;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Settings settings = Settings.getInstance();

        Island island = new Island((Integer) settings.SETTINGS.get("sizeRow"), (Integer) settings.SETTINGS.get("sizeColumn"));

        IslandWorker islandWorker = new IslandWorker(island);
        islandWorker.start();

    }
}
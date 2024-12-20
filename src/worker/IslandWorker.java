package worker;

import entity.animal.AnimalType;
import island.Island;
import view.View;

import javax.xml.validation.Schema;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IslandWorker extends Thread{

    public static final int CORE_SIZE = 4;
    private static final int PERIOD = 1000;
    private Island island;

    public IslandWorker(Island island){
        this.island = island;
    }

    @Override
    public void run() {

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(CORE_SIZE);
        ScheduledExecutorService showStatistics = Executors.newScheduledThreadPool(CORE_SIZE);

        View view = new View(island);
        showStatistics.scheduleAtFixedRate(view, 1000, 1000, TimeUnit.MILLISECONDS);

        List<EntityWorker> entityByTypesWorkers = new ArrayList<>();

        List<AnimalType> animalTypeList = List.of(AnimalType.values());

        for (AnimalType animalType : animalTypeList) {
            entityByTypesWorkers.add(new EntityWorker(island, animalType));
        }

        pool.scheduleWithFixedDelay(() -> worker(island, entityByTypesWorkers), PERIOD, PERIOD, TimeUnit.MILLISECONDS);

    }

    public void worker(Island island, List<EntityWorker> entityByTypesWorkers){
        ExecutorService executorService = Executors.newFixedThreadPool(CORE_SIZE);

        entityByTypesWorkers.forEach(executorService::submit);

        executorService.shutdown();
    }

}

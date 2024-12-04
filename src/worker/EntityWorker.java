package worker;

import entity.Entity;
import entity.animal.Animal;
import entity.animal.AnimalType;
import island.Cell;
import island.Island;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EntityWorker implements Runnable{

    private Island island;
    private AnimalType type;

    public EntityWorker(Island island, AnimalType type){
        this.island = island;
        this.type = type;
    }

    private final Queue<Task> tasks = new ConcurrentLinkedQueue<>();

    @Override
    public void run() {
        Cell[][] cells = island.getCells();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                try{
                    processInOneCell(cell);
                }catch (Exception e){
                    e.printStackTrace();
                    System.err.println("Entity worker problem with cell");
                    System.exit(0);
                }
            }
        }
    }

    private void processInOneCell(Cell cell){
        List<? extends Entity> animals = cell.getAnimals(type);

        cell.getLock().lock();
        try{
            animals.forEach(animal -> {
                tasks.add(new Task((Animal) animal, cell));
            });
        }finally {
            cell.getLock().unlock();
        }

        tasks.forEach(Task::doTask);
        tasks.clear();

    }

}

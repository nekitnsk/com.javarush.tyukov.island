package worker;

import entity.Plant;
import entity.animal.Animal;
import island.Cell;
import island.Island;

public class TaskPlant {

    private Cell cell;

    public TaskPlant(Cell cell) {
        this.cell = cell;
    }

    public void doTask() {

        Plant.spawn(cell);

    }

}

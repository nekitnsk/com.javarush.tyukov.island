package worker;

import entity.Plant;
import entity.animal.Animal;
import island.Cell;
import island.Island;

public class Task {

    private Island island;
    private Animal animal;
    private Cell cell;

    public Task (Island island, Animal animal, Cell cell){
        this.island = island;
        this.animal = animal;
        this.cell = cell;
    }

    public void doTask(){

        if(animal.eat(cell)){
            animal.reproduce(cell);
        }
        animal.move(island, cell);

    }
}

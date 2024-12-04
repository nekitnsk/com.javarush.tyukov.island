package worker;

import entity.Plant;
import entity.animal.Animal;
import island.Cell;

public class Task {

    private Animal animal;
    private Cell cell;

    public Task (Animal animal, Cell cell){
        this.animal = animal;
        this.cell = cell;
    }

    public void doTask(){
        if(animal.eat(cell)){
            animal.reproduce(cell);
        }
        animal.move(cell);

        Plant.spawn(cell);


    }
}

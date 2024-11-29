package island;


import entity.animal.Animal;
import entity.Plant;
import entity.animal.AnimalType;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Island {

    private Cell[][] cells;

    public Island(){
        this.cells = new Cell[100][20];
        fillCells();
    }

    public Island(int row, int column){
        this.cells = new Cell[row][column];
        fillCells();
    }

    public void fillCells(){

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell();
            }
        }


    }

    public Cell[][] getCells() {
        return cells;
    }


    private static class Cell{

        private List<Plant> plants = new CopyOnWriteArrayList<>();
        private List<List<Animal>> animalGroup = new CopyOnWriteArrayList<>();

        public Cell(){
            AnimalType[] values = AnimalType.values();

            for (AnimalType type : values) {


            }
        }

    }






}

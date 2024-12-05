package island;


import entity.animal.Animal;
import entity.Plant;
import entity.animal.AnimalFactory;
import entity.animal.AnimalType;
import settings.Settings;
import view.View;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Island {

    private Cell[][] cells;

    public Island(int row, int column) {
        this.cells = new Cell[row][column];
        fillCells();
        View view = new View(this);

        view.showStatistics();
    }

    public void fillCells() {

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }


    }

    public Cell[][] getCells() {
        return cells;
    }

}



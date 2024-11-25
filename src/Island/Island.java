package Island;


public class Island {

    private Cell[][] cells;

    public Island(){
        this.cells = new Cell[100][20];
    }

    public Island(int row, int column){
        this.cells = new Cell[100][20];
    }


    public static class Cell{

    }

    public Cell[][] getCells() {
        return cells;
    }


}

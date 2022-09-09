package com.example.catsweeper;

public class CatSweeperGame {
    private CatGrid catGrid;
    public CatSweeperGame(int numRows, int numCols){
        catGrid = new CatGrid(numRows, numCols);
    }
    public CatGrid getCatGrid() {
        return catGrid;
    }
}

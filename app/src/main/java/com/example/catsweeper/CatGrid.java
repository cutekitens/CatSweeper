package com.example.catsweeper;

import java.util.ArrayList;
import java.util.List;

public class CatGrid {
    private List<Tile> tiles;
    private int numRows;
    private int numCols;
    public CatGrid(int numRows, int numCols){
        this.numRows = numRows;
        this.numCols = numCols;
        this.tiles = new ArrayList<>();
        for (int i = 0; i < numRows*numCols; i++){
            tiles.add(new Tile(Tile.BLANK));
        }
    }
    public List<Tile> getTiles() {
        return tiles;
    }
}

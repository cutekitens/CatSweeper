package com.example.catsweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CatGrid {
    private List<Tile> tiles;
    private int numRows;
    private int numCols;
    public CatGrid(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.tiles = new ArrayList<>();
        for (int i = 0; i < numRows * numCols; i++) {
            tiles.add(new Tile(Tile.BLANK));
        }
    }
    public void generateGrid(int numCats){
        int catsPlaced = 0;
        while (catsPlaced < numCats){
            int x = new Random().nextInt(numRows);
            int y = new Random().nextInt(numCols);
            Tile t = tileAt(x, y);
            if (t.getValue() == Tile.BLANK){
                t.setValue(Tile.SLEEPING_CAT);
                catsPlaced++;
            }
        }
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                Tile tile = tileAt(i, j);
                if (tile.getValue() != Tile.SLEEPING_CAT) {
                    List<Tile> adjacentTiles = adjacentTiles(i, j);
                    int num = 0;
                    for (Tile t : adjacentTiles){
                        if (t.getValue() == Tile.SLEEPING_CAT){
                            num++;
                        }
                    }
                        tile.setValue(num);
                }
            }
        }
    }

    public Tile tileAt(int x, int y){
        if (x >= numRows || x < 0 || y >= numCols || y < 0){
            return null;
        }
        return tiles.get(x*numCols+y);
    }

    public int[] getPos(int index){
        int x = index/numCols;
        int y = index-(x*numCols);
        return new int[]{x, y};
    }

    public List<Tile> adjacentTiles(int x, int y){
        List<Tile> adjacentTiles = new ArrayList<>();
        List<Tile> validTiles = new ArrayList<>();
        adjacentTiles.add(tileAt(x+1, y));
        adjacentTiles.add(tileAt(x-1, y));
        adjacentTiles.add(tileAt(x, y-1));
        adjacentTiles.add(tileAt(x, y+1));
        adjacentTiles.add(tileAt(x+1, y+1));
        adjacentTiles.add(tileAt(x+1, y-1));
        adjacentTiles.add(tileAt(x-1, y-1));
        adjacentTiles.add(tileAt(x-1, y+1));
        for (Tile t: adjacentTiles){
            if (t != null){
                validTiles.add(t);
            }
        }
        return validTiles;
    }

    public void revealAllCats(){
        for (Tile t: tiles){
            if (t.getValue() == Tile.SLEEPING_CAT){
                t.setRevealed(true);
            }
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}

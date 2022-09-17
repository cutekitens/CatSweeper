package com.example.catsweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CatGrid {
    private final List<Tile> tiles;
    private final int numRows;
    private final int numCols;
    private boolean catsRevealed = false;
    public CatGrid(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.tiles = new ArrayList<>();
        for (int i = 0; i < numRows * numCols; i++) {
            tiles.add(new Tile(Tile.BLANK));
        }
    }

    public void moveCat(Tile tile){ // move cat to adjacent tile
        System.out.println("Cat moved");
        int[] pos = getPos(getTiles().indexOf(tile)); // get x,y of tile
        List<Tile> adj_tiles = adjacentTiles(pos[0], pos[1]);
        Tile new_tile = adj_tiles.get(0);
        adj_tiles.remove(0);
        new_tile.setValue(Tile.SLEEPING_CAT);
        int[] pos2 = getPos(getTiles().indexOf(new_tile));
        List<Tile> adj_new_tiles = adjacentTiles(pos2[0], pos2[1]);
        for (Tile t: adj_new_tiles){
            if (t != tile && !adj_tiles.contains(t) && t.getValue() != Tile.SLEEPING_CAT){ // if in adj_tiles then cat already accounted for
                t.setValue(t.getValue()+1);
            }
        }
        int num_cats = 1; // already know one cat is adjacent
        for (Tile t: adj_tiles){
            if (t.getValue() == Tile.SLEEPING_CAT){
                num_cats++;
            }
            else if (!adj_new_tiles.contains(t)){ // if not in adj_tiles then need to remove cat
                t.setValue(t.getValue()-1);
            }
        }
        tile.setValue(num_cats);
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
                if (t.isRevealed()){ // cat that player clicked on
                    t.setValue(Tile.UPSET_CAT);
                }
                else {
                    t.setRevealed(true);
                }
            }
            else if (t.isStarred()){
                t.setValue(Tile.NOT_CAT);
                t.setRevealed(true);
            }
        }
        catsRevealed = true;
    }

    public boolean checkGameWon(){
        List<Tile> cats = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Tile tile = tileAt(i, j);
                if (tile.getValue() != Tile.SLEEPING_CAT && !tile.isRevealed()){
                    return false;
                }
                else if (tile.getValue() == Tile.SLEEPING_CAT){
                    cats.add(tile);
                }
            }
        }
        for (Tile cat: cats){
            cat.setStarred(true); // star all cats
        }
        return true;
    }

    public boolean areCatsRevealed() {
        return catsRevealed;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}

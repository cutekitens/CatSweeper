package com.example.catsweeper;

import java.util.ArrayList;
import java.util.List;

public class CatSweeperGame {
    private CatGrid catGrid;
    private int numCats;
    private boolean gameOver = false;
    public CatSweeperGame(int numRows, int numCols, int numCats){
        catGrid = new CatGrid(numRows, numCols);
        this.numCats = numCats;
        catGrid.generateGrid(numCats);
    }
    public void tileClicked(Tile tile){
        if (!gameOver && !tile.isRevealed()){
            tile.setRevealed(true);
            if (tile.getValue() == Tile.SLEEPING_CAT){
                gameOver = true;
            }
            else if (tile.getValue() == Tile.BLANK){
                List<Tile> blankTiles = new ArrayList<>();
                List<Tile> toReveal = new ArrayList<>();
                blankTiles.add(tile);
                while (!blankTiles.isEmpty()){
                    Tile t = blankTiles.get(0);
                    int index = getCatGrid().getTiles().indexOf(t);
                    System.out.print(index);
                    int[] tilePos = getCatGrid().getPos(index);
                    for (Tile adj: getCatGrid().adjacentTiles(tilePos[0], tilePos[1])){
                        if (!toReveal.contains(adj) && !blankTiles.contains(adj) && adj.getValue() == Tile.BLANK){
                            blankTiles.add(adj);
                            adj.setRevealed(true);
                        }
                        else{
                            if (!toReveal.contains(adj)){
                                toReveal.add(adj);
                            }
                        }
                    }
                    toReveal.add(t);
                    blankTiles.remove(t);
                }
                for (Tile t: toReveal){
                    t.setRevealed(true);
                }
            }
        }
    }
    public CatGrid getCatGrid() {
        return catGrid;
    }

    public int getNumCats(){
        return numCats;
    }
    public boolean isGameOver(){
        return gameOver;
    }
}

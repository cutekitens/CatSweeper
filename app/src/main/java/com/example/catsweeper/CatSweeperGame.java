package com.example.catsweeper;

import java.util.ArrayList;
import java.util.List;

public class CatSweeperGame {
    private final CatGrid catGrid;
    private final int numCats;
    private int numStars = 0;
    private boolean gameOver = false;
    private boolean gameWon = false;
    private boolean firstClick = true;
    private boolean starMode = false;

    public CatSweeperGame(int numRows, int numCols, int numCats){
        catGrid = new CatGrid(numRows, numCols);
        this.numCats = numCats;
        catGrid.generateGrid(numCats);
    }


    public void tileClicked(Tile tile) {
        if (gameOver || gameWon) { // do nothing when Game is over or won
            return;
        }
        if (!starMode){
            normalMode(tile);
        }
        else if (!tile.isRevealed()){
            if (!tile.isStarred() && numStars < numCats) {
                tile.setStarred(true);
                numStars++;
            }
            else if (tile.isStarred()){
                tile.setStarred(false);
                numStars--;
            }
        }
    }

    public void normalMode(Tile tile){
        if (tile.isStarred()){
            tile.setStarred(false);
            numStars--;
            return;
        }
        if (!tile.isStarred() && firstClick){ // first click may not be counting cats correctly
            if(tile.getValue() == Tile.SLEEPING_CAT) {
                catGrid.moveCat(tile);
            }
            firstClick = false;
        }
        if (!tile.isRevealed()){
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
                    if (t.isStarred()){
                        t.setStarred(false);
                        numStars--;
                    }
                    t.setRevealed(true);
                }
            }
        }
        // game is won if all tiles except for cat ones are revealed
        if(catGrid.checkGameWon()){
            gameWon = true;
        }
    }

    public CatGrid getCatGrid() {
        return catGrid;
    }

    public boolean isStarMode() {
        return starMode;
    }

    public boolean isGameWon(){
        return gameWon;
    }

    public void setStarMode(boolean starMode) {
        this.starMode = starMode;
    }

    public int getNumStars(){
        return numStars;
    }
    public int getNumCats(){
        return numCats;
    }
    public boolean isGameOver(){
        return gameOver;
    }
}

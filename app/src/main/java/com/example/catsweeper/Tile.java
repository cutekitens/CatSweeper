package com.example.catsweeper;

public class Tile {
    public static final int SLEEPING_CAT = -1;
    public static final int NOT_CAT = -2; // starred tile that isn't a cat
    public static final int UPSET_CAT = -3; // cat that player clicked on
    public static final int BLANK = 0;

    private int value;
    private boolean isRevealed;
    private boolean isStarred;

    public Tile(int value){
        this.value = value;
        this.isRevealed = false;
        this.isStarred = false;
    }
    public int getValue(){
        return value;
    }
    public void setValue(int value){
        this.value = value;
    }
    public boolean isRevealed(){
        return isRevealed;
    }
    public void setRevealed(boolean revealed){
        isRevealed = revealed;
    }

    public boolean isStarred(){
        return isStarred;
    }
    public void setStarred(boolean starred){
        isStarred = starred;
    }
}

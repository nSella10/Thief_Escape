package com.example.game1.characters;

public class Police {
    private int row;
    private  final int col;



    public Police(int row, int col ) {
        this.row = row;
        this.col = col;
    }
    public int getRow() {
        return row;
    }


    public int getCol() {
        return col;
    }

    public void moveForward() {
        this.row++;
    }
}

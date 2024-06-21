
package com.example.game1.Logic;

import android.util.Log;

import com.example.game1.characters.Police;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {

    private final int lifeCount;
    private int hits = 0;
    private int thiefCol;
    Random random = new Random();

    private final List<Police> policeList;

    private static int lastCol1 = -1;
    private static int lastCol2 = -1;

    public int getThiefCol() {
        return thiefCol;
    }

    public GameManager(int lifeCount) {
        this.lifeCount = lifeCount;
        this.thiefCol = 1;
        this.policeList = new ArrayList<>();
    }

    public List<Police> getPoliceList() {
        return policeList;
    }

    public int getLife() {
        return lifeCount;
    }

    public int getHits() {
        return hits;
    }

    public GameManager setHits(int hits) {
        this.hits = hits;
        return this;
    }

    public boolean isGameLost() {
        return getLife() == getHits();
    }

    public void moveTheThief(int direction) {
        // Move left
        if (direction == 0 && getThiefCol() > 0) {
            thiefCol--;
            Log.d("game status", "you press on me");
        } else if (direction == 1 && getThiefCol() < 2) { // Move right
            thiefCol++;
        }
    }

    public void createRandomPolice() {
        int col;
        boolean invalidColumn;

        do {
            col = random.nextInt(4); // Pick a random column

            // Check if the new column creates a diagonal
            // Avoid the same column as the last police
            invalidColumn = (col == 0 && ((lastCol1 == 1 && lastCol2 == 2) || (lastCol1 == 2 && lastCol2 == 1))) || // Avoid diagonal from column 0
                    (col == 1 && ((lastCol1 == 0 && lastCol2 == 2) || (lastCol1 == 2 && lastCol2 == 0))) || // Avoid diagonal from column 1
                    (col == 2 && ((lastCol1 == 0 && lastCol2 == 1) || (lastCol1 == 1 && lastCol2 == 0))) || // Avoid diagonal from column 2
                    (col == lastCol1);
        } while (invalidColumn);

        policeList.add(new Police(0, col));
        lastCol2 = lastCol1;
        lastCol1 = col;
    }
}

//
//package com.example.game1.Logic;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.media.MediaPlayer;
//import android.util.Log;
//
//import com.example.game1.R;
//import com.example.game1.characters.Coin;
//import com.example.game1.characters.Police;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//public class GameManager {
//
//    private final int LIFECOUNT;
//    private int hits = 0;
//    private int thiefCol;
//    private int distance = 0;
//    private int coins = 0;
//    Random random = new Random();
//
//    private final List<Police> POLICELIST;
//    private final List<Coin> COINLIST;
//
//    private static int lastCol1 = -1;
//    private static int lastCol2 = -1;
//
//    private Context context;
//    private Executor executor;
//    private MediaPlayer runMediaPlayer;
//    private MediaPlayer catchMediaPlayer;
//    private MediaPlayer coinMediaPlayer;
//
//    public GameManager(Context context,int lifeCount) {
//        this.context = context;
//        this.LIFECOUNT=lifeCount;
//        this.thiefCol = 2; // Default middle column for 5 columns (0, 1, 2, 3, 4)
//        this.POLICELIST = new ArrayList<>();
//        this.COINLIST = new ArrayList<>();
//        this.executor = Executors.newSingleThreadExecutor();
//        initializeSounds();
//    }
//
//    private void initializeSounds() {
//        runMediaPlayer = MediaPlayer.create(context, R.raw.run_mode);
//        catchMediaPlayer = MediaPlayer.create(context, R.raw.catching);
//        coinMediaPlayer = MediaPlayer.create(context, R.raw.coin);
//    }
//
//    public int getThiefCol() {
//        return thiefCol;
//    }
//
//    public List<Police> getPoliceList() {
//        return POLICELIST;
//    }
//    public List<Coin> getCoinList() {
//        return COINLIST;
//    }
//    public int getLife() {
//        return LIFECOUNT;
//    }
//
//    public int getHits() {
//        return hits;
//    }
//    public int getDistance() {
//        return distance;
//    }
//
//    public int getCoins() {
//        return coins;
//    }
//
//    public GameManager setCoins(int coins) {
//        this.coins = coins;
//        return this;
//    }
//
//    public GameManager setHits(int hits) {
//        this.hits = hits;
//        return this;
//    }
//
//    public boolean isGameLost() {
//        return getLife() == getHits();
//    }
//
//    public void moveTheThief(int direction) {
//        // Move left
//        if (direction == 0 && getThiefCol() > 0) {
//            thiefCol--;
//            Log.d("game status", "you press on me");
//        } else if (direction == 1 && getThiefCol() < 4) { // Move right (max column is now 4)
//            thiefCol++;
//        }
//    }
//
//    public void createRandomCoin() {
//        int col;
//        boolean invalidColumn;
//
//        do {
//            col = random.nextInt(5); // Pick a random column
//            invalidColumn = (col == lastCol1);
//        } while (invalidColumn);
//
//        COINLIST.add(new Coin(0, col));
//    }
//
//    public void createRandomPolice() {
//        int col;
//        boolean invalidColumn;
//
//        do {
//            col = random.nextInt(5); // Pick a random column
//
//            // Check if the new column creates a diagonal
//            // Avoid the same column as the last police
//            invalidColumn = (col == 0 && ((lastCol1 == 1 && lastCol2 == 2) || (lastCol1 == 2 && lastCol2 == 1))) || // Avoid diagonal from column 0
//                    (col == 1 && ((lastCol1 == 0 && lastCol2 == 2) || (lastCol1 == 2 && lastCol2 == 0))) || // Avoid diagonal from column 1
//                    (col == 2 && ((lastCol1 == 0 && lastCol2 == 1) || (lastCol1 == 1 && lastCol2 == 0))) || // Avoid diagonal from column 2
//                    (col == lastCol1);
//        } while (invalidColumn);
//
//        POLICELIST.add(new Police(0, col));
//        lastCol2 = lastCol1;
//        lastCol1 = col;
//    }
//
//    public void playRunSound() {
//        if (runMediaPlayer != null && !runMediaPlayer.isPlaying())
//            executor.execute(() -> {
//                runMediaPlayer.setLooping(true);
//                runMediaPlayer.setVolume(1.0f, 1.0f);
//                runMediaPlayer.start();
//            });
//    }
//
//    public void stopRunSound() {
//        if (runMediaPlayer != null) {
//            executor.execute(() -> {
//                runMediaPlayer.stop();
//                runMediaPlayer.reset();
//                initializeRunMediaPlayer();
//            });
//        }
//    }
//
//    private void initializeRunMediaPlayer() {
//        runMediaPlayer = MediaPlayer.create(context, R.raw.run_mode);
//    }
//
//    public void playColiisonSound() {
//        if (catchMediaPlayer != null) {
//            executor.execute(() -> catchMediaPlayer.start());
//        }
//    }
//
//    public void playAddCoinSound() {
//        if (coinMediaPlayer != null) {
//            executor.execute(() -> coinMediaPlayer.start());
//        }
//        coins++;
//    }
//
//    public void increaseDistance() {
//        this.distance += 5;
//    }
//
//    public void releaseSounds() {
//        if (runMediaPlayer != null) {
//            runMediaPlayer.release();
//            runMediaPlayer = null;
//        }
//        if (catchMediaPlayer != null) {
//            catchMediaPlayer.release();
//            catchMediaPlayer = null;
//        }
//        if (coinMediaPlayer != null) {
//            coinMediaPlayer.release();
//            coinMediaPlayer = null;
//        }
//    }
//
//    public void saveHighScore(int distance, int coins, double lat, double lon){
//        //???
//        SharedPreferences sharedPreferences =context.getSharedPreferences("high_score",Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor =sharedPreferences.edit();
//
//        Gson gson = new Gson();
//        String json =sharedPreferences.getString("scores",null);
//        Type type= new TypeToken<ArrayList<Score>>(){}.getType();
//        List<Score>highScores = gson.fromJson(json,type);
//
//        if(highScores ==null){
//            highScores =new ArrayList<>();
//        }
//        Score newHighScore = new Score(distance,coins,lat,lon);
//        highScores.add(newHighScore);
//
//        String updateJson = gson.toJson(highScores);
//        editor.putString("scores",updateJson);
//        editor.apply();
//
//    }
//}


package com.example.game1.Logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.game1.R;
import com.example.game1.characters.Coin;
import com.example.game1.characters.Police;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GameManager {

    private final int LIFECOUNT;
    private int hits = 0;
    private int thiefCol;
    private int distance = 0;
    private int coins = 0;
    Random random = new Random();

    private final List<Police> POLICELIST;
    private final List<Coin> COINLIST;

    private static int lastCol1 = -1;
    private static int lastCol2 = -1;

    private Context context;
    private Executor executor;
    private MediaPlayer runMediaPlayer;
    private MediaPlayer catchMediaPlayer;
    private MediaPlayer coinMediaPlayer;

    public GameManager(Context context,int lifeCount) {
        this.context = context;
        this.LIFECOUNT=lifeCount;
        this.thiefCol = 2; // Default middle column for 5 columns (0, 1, 2, 3, 4)
        this.POLICELIST = new ArrayList<>();
        this.COINLIST = new ArrayList<>();
        this.executor = Executors.newSingleThreadExecutor();
        initializeSounds();
    }

    private void initializeSounds() {
        runMediaPlayer = MediaPlayer.create(context, R.raw.run_mode);
        catchMediaPlayer = MediaPlayer.create(context, R.raw.catching);
        coinMediaPlayer = MediaPlayer.create(context, R.raw.coin);
    }

    public int getThiefCol() {
        return thiefCol;
    }

    public List<Police> getPoliceList() {
        return POLICELIST;
    }
    public List<Coin> getCoinList() {
        return COINLIST;
    }
    public int getLife() {
        return LIFECOUNT;
    }

    public int getHits() {
        return hits;
    }
    public int getDistance() {
        return distance;
    }

    public int getCoins() {
        return coins;
    }

    public GameManager setCoins(int coins) {
        this.coins = coins;
        return this;
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
        } else if (direction == 1 && getThiefCol() < 4) { // Move right (max column is now 4)
            thiefCol++;
        }
    }

    public void createRandomCoin() {
        int col;
        boolean invalidColumn;

        do {
            col = random.nextInt(5); // Pick a random column
            invalidColumn = (col == lastCol1);
        } while (invalidColumn);

        COINLIST.add(new Coin(0, col));
    }

    public void createRandomPolice() {
        int col;
        boolean invalidColumn;

        do {
            col = random.nextInt(5); // Pick a random column

            // Check if the new column creates a diagonal
            // Avoid the same column as the last police
            invalidColumn = (col == 0 && ((lastCol1 == 1 && lastCol2 == 2) || (lastCol1 == 2 && lastCol2 == 1))) || // Avoid diagonal from column 0
                    (col == 1 && ((lastCol1 == 0 && lastCol2 == 2) || (lastCol1 == 2 && lastCol2 == 0))) || // Avoid diagonal from column 1
                    (col == 2 && ((lastCol1 == 0 && lastCol2 == 1) || (lastCol1 == 1 && lastCol2 == 0))) || // Avoid diagonal from column 2
                    (col == lastCol1);
        } while (invalidColumn);

        POLICELIST.add(new Police(0, col));
        lastCol2 = lastCol1;
        lastCol1 = col;
    }

    public void playRunSound() {
        if (runMediaPlayer != null && !runMediaPlayer.isPlaying())
            executor.execute(() -> {
                runMediaPlayer.setLooping(true);
                runMediaPlayer.setVolume(1.0f, 1.0f);
                runMediaPlayer.start();
            });
    }

    public void stopRunSound() {
        if (runMediaPlayer != null) {
            executor.execute(() -> {
                runMediaPlayer.stop();
                runMediaPlayer.reset();
                initializeRunMediaPlayer();
            });
        }
    }

    private void initializeRunMediaPlayer() {
        runMediaPlayer = MediaPlayer.create(context, R.raw.run_mode);
    }

    public void playCollisionSound() {  // Corrected method name
        if (catchMediaPlayer != null) {
            executor.execute(() -> catchMediaPlayer.start());
        }
    }

    public void playAddCoinSound() {
        if (coinMediaPlayer != null) {
            executor.execute(() -> coinMediaPlayer.start());
        }
        coins++;
    }

    public void increaseDistance() {
        this.distance += 5;
    }

    public void releaseSounds() {
        if (runMediaPlayer != null) {
            runMediaPlayer.release();
            runMediaPlayer = null;
        }
        if (catchMediaPlayer != null) {
            catchMediaPlayer.release();
            catchMediaPlayer = null;
        }
        if (coinMediaPlayer != null) {
            coinMediaPlayer.release();
            coinMediaPlayer = null;
        }
    }

    public void saveHighScore(int distance, int coins, double lat, double lon){
        SharedPreferences sharedPreferences = context.getSharedPreferences("high_score", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = sharedPreferences.getString("scores", null);
        Type type = new TypeToken<ArrayList<Score>>(){}.getType();
        List<Score> highScores = gson.fromJson(json, type);

        if (highScores == null) {
            highScores = new ArrayList<>();
        }
        Score newHighScore = new Score(distance, coins, lat, lon);
        highScores.add(newHighScore);

        String updateJson = gson.toJson(highScores);
        editor.putString("scores", updateJson);
        editor.apply();
    }
}

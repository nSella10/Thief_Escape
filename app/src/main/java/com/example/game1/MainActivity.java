

package com.example.game1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.game1.Logic.GameManager;
import com.example.game1.characters.Police;
import com.google.android.material.button.MaterialButton;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private static final long DELAY = 500;
    private MaterialButton main_IMG_leftArrow;
    private MaterialButton main_IMG_rightArrow;

    private AppCompatImageView[] main_IMG_hearts;
    private ImageView[][] gridCells;
    private GameManager gameManager;
    private final int gridColumnCount = 3;
    private final int gridRowCount = 8;
    private final Handler handler = new Handler();
    private Runnable policeRunner;
    private boolean gameOver = false;
    private boolean isGameRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        gameManager = new GameManager(main_IMG_hearts.length);
        initView();
        startGame();
    }

    private void startGame() {
        policeRunner = new Runnable() {
            @Override
            public void run() {
                if (!gameOver) {
                    movePoliceForward();
                    gameManager.createRandomPolice();
                    refreshUI();
                    handler.postDelayed(this, DELAY);
                }
            }
        };
        handler.postDelayed(policeRunner, DELAY);
        isGameRunning = true;
    }

    private void stopGame() {
        handler.removeCallbacks(policeRunner);
        isGameRunning = false;
    }

    private void findViews() {
        // Find all grid cells and store them in the gridCells array
        gridCells = new ImageView[gridRowCount][gridColumnCount];
        for (int row = 0; row < gridRowCount; row++) {
            for (int col = 0; col < gridColumnCount; col++) {
                String viewId = "image_" + row + "_" + col;
                @SuppressLint("DiscouragedApi") int resId = getResources().getIdentifier(viewId, "id", getPackageName());
                gridCells[row][col] = findViewById(resId);
            }
        }
        // Find other views
        main_IMG_leftArrow = findViewById(R.id.main_IMG_leftArrow);
        main_IMG_rightArrow = findViewById(R.id.main_IMG_rightArrow);
        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3),
        };
    }

    private void initView() {
        main_IMG_leftArrow.setOnClickListener(v -> moveThief(0));
        main_IMG_rightArrow.setOnClickListener(v -> moveThief(1));
        refreshUI();
    }

    private void refreshUI() {
        if (gameManager.isGameLost()) {
            Log.d("Game Status:", "GAME OVER!");
            handler.removeCallbacks(policeRunner);
            if (!gameOver) {
                gameOver = true;
                changeActivity("GAME OVER!");
            }
        } else {
            updateGrid();
        }
    }

    private void changeActivity(String status) {
        Intent finishIntent = new Intent(this, EndGameActivity.class);
        finishIntent.putExtra(EndGameActivity.KEY_STATUS, status);
        startActivity(finishIntent);
        finish();
    }

    private void updateGrid() {
        for (int row = 0; row < gridRowCount; row++) {
            for (int col = 0; col < gridColumnCount; col++) {
                gridCells[row][col].setImageDrawable(null);
            }
        }
        // Update the thief
        int thiefRow = gridRowCount - 1;
        int thiefCol = gameManager.getThiefCol();
        if (thiefCol >= 0 && thiefCol < gridColumnCount)
            gridCells[thiefRow][thiefCol].setImageResource(R.drawable.thief);

        // Update the police
        for (Police police : gameManager.getPoliceList()) {
            int policeRow = police.getRow();
            int policeCol = police.getCol();
            if (policeRow >= 0 && policeRow < gridRowCount && policeCol >= 0 && policeCol < gridColumnCount) {
                gridCells[policeRow][policeCol].setImageResource(R.drawable.police);
            }
        }
    }

    private void moveThief(int direction) {
        gameManager.moveTheThief(direction);
        checkForCollision();
        refreshUI();
    }

    private void checkForCollision() {
        Iterator<Police> iterator = gameManager.getPoliceList().iterator();
        while (iterator.hasNext()) {
            Police police = iterator.next();
            if (police.getRow() == gridRowCount - 1 && police.getCol() == gameManager.getThiefCol()) {
                gameManager.setHits(gameManager.getHits() + 1);
                main_IMG_hearts[main_IMG_hearts.length - gameManager.getHits()].setVisibility(View.INVISIBLE);
                Log.d("Game Status", "Police caught the thief!");
                toastAndVibrate("oops");
                iterator.remove();
                if (gameManager.isGameLost()) {
                    handler.removeCallbacks(policeRunner); // Stop the game loop
                    changeActivity("GAME OVER!");
                }
                return; // Stop checking further collisions after a hit
            }
        }
    }

    private void movePoliceForward() {
        Iterator<Police> iterator = gameManager.getPoliceList().iterator();
        while (iterator.hasNext()) {
            Police police = iterator.next();
            police.moveForWard();
            if (police.getRow() >= gridRowCount) {
                iterator.remove();
                Log.d("game status", "Police removed");
            } else if (police.getRow() == gridRowCount - 1 && police.getCol() == gameManager.getThiefCol()) {
                gameManager.setHits(gameManager.getHits() + 1);
                main_IMG_hearts[main_IMG_hearts.length - gameManager.getHits()].setVisibility(View.INVISIBLE);
                iterator.remove();
                Log.d("game status", "Police caught the thief!");
                toastAndVibrate("oops");
                if (gameManager.isGameLost()) {
                    handler.removeCallbacks(policeRunner); // Stop the game loop
                    changeActivity("GAME OVER!");
                }
            }
        }
        checkForCollision();
        refreshUI();
    }

    private void toastAndVibrate(String text) {
        vibrator();
        toast(text);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void vibrator() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isGameRunning) {
            stopGame();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!gameOver && !isGameRunning) {
            startGame();
        }
    }
}

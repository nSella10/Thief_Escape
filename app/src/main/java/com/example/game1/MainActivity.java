
package com.example.game1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.game1.Logic.GameManager;
import com.example.game1.Utillities.MoveDetector;
import com.example.game1.Utillities.MoveListener;
import com.example.game1.characters.Coin;
import com.example.game1.characters.Police;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements MoveListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MaterialButton main_IMG_leftArrow;
    private MaterialButton main_IMG_rightArrow;
    private AppCompatImageView[] main_IMG_hearts;
    private ImageView[][] gridCells;
    private GameManager gameManager;
    private final int GRIDCOLUMNCOUNT = 5;
    private final int GRIDROWCOUNT = 8;
    private final Handler HANDLER = new Handler();
    private Runnable policeRunner;
    private boolean gameOver = false;
    private boolean isGameRunning = false;
    private String gameMode;
    private MaterialTextView main_LBL_distance;
    private MaterialTextView main_LBL_coin;
    private MoveDetector moveDetector;
    private long delay;
    private double lat;
    private double lon;
    Random random =new Random();
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        findViews();
        gameManager = new GameManager(this, main_IMG_hearts.length);
        initViews();

        gameMode = getIntent().getStringExtra("GAME_MODE");
        Log.d("MainActivity", "Game mode " + gameMode);

        if ("fast".equals(gameMode)) {
            delay = 400L;
        } else if ("sensor".equals(gameMode)) {
            delay = 600L;
            main_IMG_leftArrow.setVisibility(View.INVISIBLE);
            main_IMG_rightArrow.setVisibility(View.INVISIBLE);
            initMoveDetector();
            moveDetector.start();
        } else {
            delay = 600L;
        }
        requestLocationPermission();
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This game requires location permission to function correctly.")
                        .setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                LOCATION_PERMISSION_REQUEST_CODE))
                        .setNegativeButton("Cancel", (dialog, which) -> {
                            Toast.makeText(MainActivity.this, "Permission denied. Exiting game.", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            getLastLocation();
            startGame();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
                startGame();
            } else {
                Toast.makeText(this, "Permission denied. Exiting game.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                lat = location.getLatitude();
                lon = location.getLongitude();
            }
        });
    }

    private void initMoveDetector() {
        moveDetector = new MoveDetector(this, this);
    }

    private void startGame() {
        gameManager.playRunSound();

        policeRunner = new Runnable() {
            @Override
            public void run() {
                if (!gameOver) {
                    movePoliceForward();
                    gameManager.createRandomPolice();

                    // Generate a random number between 0 and 1
                    float randomNumber = random.nextFloat();

                    // Create a coin if the random number is greater than 0.5
                    if(randomNumber > 0.6) {
                        gameManager.createRandomCoin();
                    }

                    refreshUI();
                    HANDLER.postDelayed(this, delay);
                }
            }
        };
        HANDLER.postDelayed(policeRunner, delay);
        isGameRunning = true;
    }

    private void stopGame() {
        HANDLER.removeCallbacks(policeRunner);
        isGameRunning = false;
        gameManager.stopRunSound();
        if (moveDetector != null) {
            moveDetector.stop();
        }
    }

    private void findViews() {
        gridCells = new ImageView[GRIDROWCOUNT][GRIDCOLUMNCOUNT];
        for (int row = 0; row < GRIDROWCOUNT; row++) {
            for (int col = 0; col < GRIDCOLUMNCOUNT; col++) {
                String viewId = "image_" + row + "_" + col;
                @SuppressLint("DiscouragedApi") int resId = getResources().getIdentifier(viewId, "id", getPackageName());
                gridCells[row][col] = findViewById(resId);
            }
        }
        main_IMG_leftArrow = findViewById(R.id.main_IMG_leftArrow);
        main_IMG_rightArrow = findViewById(R.id.main_IMG_rightArrow);
        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3),
        };
        main_LBL_distance = findViewById(R.id.main_LBL_distance);
        main_LBL_coin = findViewById(R.id.main_LBL_coin);
    }

    private void initViews() {
        main_IMG_leftArrow.setOnClickListener(v -> moveThief(0));
        main_IMG_rightArrow.setOnClickListener(v -> moveThief(1));
        refreshUI();
    }

    private void refreshUI() {
        if (gameManager.isGameLost()) {
            Log.d("Game Status:", "GAME OVER!");
            HANDLER.removeCallbacks(policeRunner);
            if (!gameOver) {
                gameOver = true;
                gameManager.saveHighScore(gameManager.getDistance(), gameManager.getCoins(), lat, lon);
                changeActivity("GAME OVER!");
            }
        } else {
            updateGrid();
            updateDistanceAndCoins();
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateDistanceAndCoins() {
        main_LBL_distance.setText("Distance: " + gameManager.getDistance());
        main_LBL_coin.setText(": " + gameManager.getCoins());
    }

    private void changeActivity(String status) {
        Intent finishIntent = new Intent(this, EndGameActivity.class);
        finishIntent.putExtra(EndGameActivity.KEY_STATUS, status);
        startActivity(finishIntent);
        finish();
    }

    private void updateGrid() {
        for (int row = 0; row < GRIDROWCOUNT; row++) {
            for (int col = 0; col < GRIDCOLUMNCOUNT; col++) {
                gridCells[row][col].setImageDrawable(null);
            }
        }
        int thiefRow = GRIDROWCOUNT - 1;
        int thiefCol = gameManager.getThiefCol();
        if (thiefCol >= 0 && thiefCol < GRIDCOLUMNCOUNT)
            gridCells[thiefRow][thiefCol].setImageResource(R.drawable.thief);

        for (Police police : gameManager.getPoliceList()) {
            int policeRow = police.getRow();
            int policeCol = police.getCol();
            if (policeRow >= 0 && policeRow < GRIDROWCOUNT && policeCol >= 0 && policeCol < GRIDCOLUMNCOUNT) {
                gridCells[policeRow][policeCol].setImageResource(R.drawable.police);
            }
        }

        for (Coin coin : gameManager.getCoinList()) {
            int coinRow = coin.getRow();
            int coinCol = coin.getCol();
            if (coinRow >= 0 && coinRow < GRIDROWCOUNT && coinCol >= 0 && coinCol < GRIDCOLUMNCOUNT) {
                gridCells[coinRow][coinCol].setImageResource(R.drawable.coin);
            }
        }
    }

    private void moveThief(int direction) {
        gameManager.moveTheThief(direction);
        checkForCollision();
        checkForCoinCollection();
        refreshUI();
    }

    private void checkForCollision() {
        Iterator<Police> iterator = gameManager.getPoliceList().iterator();
        while (iterator.hasNext()) {
            Police police = iterator.next();
            if (police.getRow() == GRIDROWCOUNT - 1 && police.getCol() == gameManager.getThiefCol()) {
                gameManager.playCollisionSound();
                gameManager.setHits(gameManager.getHits() + 1);
                main_IMG_hearts[main_IMG_hearts.length - gameManager.getHits()].setVisibility(View.INVISIBLE);
                Log.d("Game Status", "Police caught the thief!");
                toastAndVibrate("oops");
                iterator.remove();
                if (gameManager.isGameLost()) {
                    HANDLER.removeCallbacks(policeRunner);
                    changeActivity("GAME OVER!");
                    return;
                }
            }
        }
    }

    private void movePoliceForward() {
        Iterator<Police> iterator = gameManager.getPoliceList().iterator();
        while (iterator.hasNext()) {
            Police police = iterator.next();
            police.moveForward();
            if (police.getRow() >= GRIDROWCOUNT) {
                iterator.remove();
                Log.d("game status", "Police removed");
            } else if (police.getRow() == GRIDROWCOUNT - 1 && police.getCol() == gameManager.getThiefCol()) {
                gameManager.playCollisionSound();
                gameManager.setHits(gameManager.getHits() + 1);
                main_IMG_hearts[main_IMG_hearts.length - gameManager.getHits()].setVisibility(View.INVISIBLE);
                iterator.remove();
                Log.d("game status", "Police caught the thief!");
                toastAndVibrate("oops");
                if (gameManager.isGameLost()) {
                    HANDLER.removeCallbacks(policeRunner);
                    changeActivity("GAME OVER!");
                    return;
                }
            }
        }

        Iterator<Coin> coinIterator = gameManager.getCoinList().iterator();
        while (coinIterator.hasNext()) {
            Coin coin = coinIterator.next();
            coin.moveForward();
            if (coin.getRow() >= GRIDROWCOUNT) {
                coinIterator.remove();
                Log.d("game status", "Coin removed");
            }
        }

        checkForCollision();
        checkForCoinCollection();
        gameManager.increaseDistance();
        refreshUI();
    }

    private void checkForCoinCollection() {
        Iterator<Coin> iterator = gameManager.getCoinList().iterator();
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            if (coin.getRow() == GRIDROWCOUNT - 1 && coin.getCol() == gameManager.getThiefCol()) {
                gameManager.playAddCoinSound();
                gameManager.setCoins(gameManager.getCoins());
                Log.d("Game Status", "Coin collected!");
                iterator.remove();
            }
        }
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
        gameManager.stopRunSound();
        if (isGameRunning) {
            stopGame();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!gameOver && !isGameRunning) {
            startGame();
        } else {
            gameManager.playRunSound();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameManager.releaseSounds();
    }

    @Override
    public void onMoveLeft() {
        moveThief(0);
    }

    @Override
    public void onMoveRight() {
        moveThief(1);
    }
}

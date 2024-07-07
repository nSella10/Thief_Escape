////package com.example.game1;
////
////import android.content.Intent;
////import android.os.Bundle;
////
////import androidx.appcompat.app.AppCompatActivity;
////
////
////import com.example.game1.Logic.GameManager;
////import com.google.android.material.button.MaterialButton;
////import com.google.android.material.textview.MaterialTextView;
////
////public class EndGameActivity extends AppCompatActivity {
////
////    private MaterialTextView end_LBL_game;
////    private MaterialButton newGame_IMG_Button;
////    public static final String KEY_STATUS ="KEY_STATUS";
////    private GameManager gameManager;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_end_game);
////
////        findViews();
////        initViews();
////
////    }
////
////    private void initViews() {
////        Intent previousActivity =getIntent();
////        String status = previousActivity.getStringExtra(KEY_STATUS);
////        end_LBL_game.setText(status);
////
////        gameManager = new GameManager(this, 3);
////        gameManager.playPoliceSound();
////        newGame_IMG_Button.setOnClickListener(v -> newGame());
////
////
////    }
////
////    @Override
////    protected void onPause() {
////        super.onPause();
////        if (gameManager != null) {
////            gameManager.stopPoliceSound();
////        }
////    }
////
////    @Override
////    protected void onStop() {
////        super.onStop();
////        if (gameManager != null) {
////            gameManager.releasePoliceSound();
////        }
////    }
////
////    @Override
////    protected void onDestroy() {
////        super.onDestroy();
////        if (gameManager != null) {
////            gameManager.releaseSounds();
////        }
////    }
////
////    private void newGame() {
////        Intent newGameIntent =new Intent(this,MainActivity.class);
////        startActivity(newGameIntent);
////        finish();
////
////    }
////
////    private void findViews() {
////
////        end_LBL_game = findViewById(R.id.end_LBL_game);
////        newGame_IMG_Button = findViewById(R.id.newGame_IMG_Button);
////    }
////}
//
package com.example.game1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game1.Logic.GameManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class EndGameActivity extends AppCompatActivity {

    private MaterialTextView end_LBL_game;
    private MaterialButton newGame_IMG_Button;
    public static final String KEY_STATUS = "KEY_STATUS";
    private MediaPlayer policeMediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        findViews();
        initViews();
    }

    private void initViews() {
        Intent previousActivity = getIntent();
        String status = previousActivity.getStringExtra(KEY_STATUS);
        end_LBL_game.setText(status);

        initializePoliceSound();
        policeMediaPlayer.start();
        newGame_IMG_Button.setOnClickListener(v -> newGame());
    }

    private void initializePoliceSound() {
        policeMediaPlayer = MediaPlayer.create(this,R.raw.police);
        policeMediaPlayer.setLooping(true);
        policeMediaPlayer.setVolume(1.0f,1.0f);
        policeMediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (policeMediaPlayer != null && policeMediaPlayer.isPlaying()) {
            policeMediaPlayer.pause();

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (policeMediaPlayer != null) {
            policeMediaPlayer.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (policeMediaPlayer != null) {
            policeMediaPlayer.stop();
            policeMediaPlayer.release();
            policeMediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(policeMediaPlayer != null){
            policeMediaPlayer.release();
            policeMediaPlayer=null;
        }
    }

    private void newGame() {
        Intent newGameIntent = new Intent(this, OpenActivity.class);
        startActivity(newGameIntent);
        finish();
    }


    private void findViews() {
        end_LBL_game = findViewById(R.id.end_LBL_game);
        newGame_IMG_Button = findViewById(R.id.newGame_IMG_Button);
    }
}



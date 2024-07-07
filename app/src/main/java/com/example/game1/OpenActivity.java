package com.example.game1;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.button.MaterialButton;

public class OpenActivity extends AppCompatActivity {

    private MaterialButton main_BTN_slow_mode;
    private MaterialButton main_BTN_fast_mode;
    private MaterialButton main_BTN_sensor_mode;
    private MaterialButton main_BTN_high_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.activity_open);

        findViews();

        initView();


    }

    private void initView() {
        main_BTN_slow_mode.setOnClickListener(v->startMainActivity("slow"));
        main_BTN_fast_mode.setOnClickListener(v->startMainActivity("fast"));
        main_BTN_sensor_mode.setOnClickListener(v->startMainActivity("sensor"));
        main_BTN_high_score.setOnClickListener(v->showHighScore());


    }

    private void showHighScore() {
        Intent intent = new Intent(this, HighScoreActivity.class);
        intent.putExtra("GAME_MODE", "highScore");
        startActivity(intent);
    }

    private void startMainActivity(String mode) {
        Intent intent= new Intent(this,MainActivity.class);
        intent.putExtra("GAME_MODE",mode);
        startActivity(intent);
    }

    private void findViews() {
        main_BTN_slow_mode = findViewById(R.id.main_BTN_slow_mode);
        main_BTN_fast_mode = findViewById(R.id.main_BTN_fast_mode);
        main_BTN_sensor_mode = findViewById(R.id.main_BTN_sensor_mode);
        main_BTN_high_score = findViewById(R.id.main_BTN_high_score);

    }
}
package com.example.game1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class EndGameActivity extends AppCompatActivity {

    private MaterialTextView end_LBL_game;
    private MaterialButton newGame_IMG_Button;
    public static final String KEY_STATUS ="KEY_STATUS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        findViews();
        initViews();

    }

    private void initViews() {
        Intent previousActivity =getIntent();
        String status = previousActivity.getStringExtra(KEY_STATUS);
        end_LBL_game.setText(status);

        newGame_IMG_Button.setOnClickListener(v -> newGame());
    }

    private void newGame() {
        Intent newGameIntent =new Intent(this,MainActivity.class);
        startActivity(newGameIntent);
        finish();

    }

    private void findViews() {

        end_LBL_game = findViewById(R.id.end_LBL_game);
        newGame_IMG_Button = findViewById(R.id.newGame_IMG_Button);
    }
}
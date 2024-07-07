//
//package com.example.game1.Fragments;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.game1.Logic.Score;
//import com.example.game1.R;
//import com.google.android.material.button.MaterialButton;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//public class ListFragment extends Fragment {
//
//    private MaterialButton[] buttons;
//    private OnItemClickedListener onItemClickedListener;
//    private MaterialButton main_BTN_clear;
//    private MaterialButton main_BTN_back;
//
//    public ListFragment() {
//        // Required empty public constructor
//    }
//
//    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
//        this.onItemClickedListener = onItemClickedListener;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_list, container, false);
//        findViews(v);
//        initViews();
//        loadHighScores();
//        return v;
//    }
//
//    private void initViews() {
//        main_BTN_clear.setOnClickListener(v -> clearHighScores());
//        main_BTN_back.setOnClickListener(v -> getActivity().onBackPressed());
//    }
//
//    @SuppressLint("SetTextI18n")
//    private void loadHighScores() {
//        List<Score> highScores = getHighScores();
//        Collections.sort(highScores, (Comparator<Score>) (o1, o2) -> {
//            int distanceCompare = Integer.compare(o2.getdistance(), o1.getdistance());
//            if (distanceCompare == 0) {
//                return Integer.compare(o2.getCoins(), o1.getCoins());
//            }
//            return distanceCompare;
//        });
//
//        for (int i = 0; i < buttons.length; i++) {
//            if (i < highScores.size()) {
//                Score highScore = highScores.get(i);
//                buttons[i].setText(i+1+". Score " + highScore.getdistance() + " - coins: " + highScore.getCoins());
//                buttons[i].setOnClickListener(v -> {
//                    if (onItemClickedListener != null) {
//                        onItemClickedListener.onItemClicked(highScore.getLat(), highScore.getLon());
//                    }
//                });
//            } else {
//                buttons[i].setVisibility(View.GONE);
//            }
//        }
//    }
//
//    private List<Score> getHighScores() {
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("high_score", Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        Type type = new TypeToken<ArrayList<Score>>() {}.getType();
//        String json = sharedPreferences.getString("scores", null);
//        List<Score> highScores = gson.fromJson(json, type);
//        return highScores != null ? highScores : new ArrayList<>();
//    }
//
//    private void clearHighScores() {
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("high_score", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
//        loadHighScores();
//    }
//
//    private void findViews(View v) {
//        buttons = new MaterialButton[]{
//                v.findViewById(R.id.button),
//                v.findViewById(R.id.button2),
//                v.findViewById(R.id.button3),
//                v.findViewById(R.id.button4),
//                v.findViewById(R.id.button5),
//                v.findViewById(R.id.button6),
//                v.findViewById(R.id.button7),
//                v.findViewById(R.id.button8),
//                v.findViewById(R.id.button9),
//                v.findViewById(R.id.button10)
//        };
//        main_BTN_clear = v.findViewById(R.id.main_BTN_clear);
//        main_BTN_back = v.findViewById(R.id.main_BTN_back);
//    }
//}


package com.example.game1.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.game1.Logic.Score;
import com.example.game1.MainActivity;
import com.example.game1.OpenActivity;
import com.example.game1.R;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ListFragment extends Fragment {

    private MaterialButton[] buttons;
    private OnItemClickedListener onItemClickedListener;
    private MaterialButton main_BTN_clear;
    private MaterialButton main_BTN_back;

    public ListFragment() {
        // Required empty public constructor
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(v);
        initViews();
        loadHighScores();
        return v;
    }

    private void initViews() {
        main_BTN_clear.setOnClickListener(v1 -> clearHighScores());
        main_BTN_back.setOnClickListener(v -> startMainActivity());
    }

    @SuppressLint("SetTextI18n")
    private void loadHighScores() {
        List<Score> highScores = getHighScores();
        Collections.sort(highScores, (Comparator<Score>) (o1, o2) -> {
            int distanceCompare = Integer.compare(o2.getdistance(), o1.getdistance());
            if (distanceCompare == 0) {
                return Integer.compare(o2.getCoins(), o1.getCoins());
            }
            return distanceCompare;
        });

        for (int i = 0; i < buttons.length; i++) {
            if (i < highScores.size()) {
                Score highScore = highScores.get(i);
                buttons[i].setText((i + 1) + ". Score " + highScore.getdistance() + " - coins: " + highScore.getCoins());
                buttons[i].setOnClickListener(v -> {
                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClicked(highScore.getLat(), highScore.getLon());
                    }
                });
            } else {
                buttons[i].setVisibility(View.GONE);
            }
        }
    }

    private List<Score> getHighScores() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("high_score", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Score>>() {}.getType();
        String json = sharedPreferences.getString("scores", null);
        List<Score> highScores = gson.fromJson(json, type);
        return highScores != null ? highScores : new ArrayList<>();
    }

    private void clearHighScores() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("high_score", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        loadHighScores();
    }

    private void findViews(View v) {
        buttons = new MaterialButton[]{
                v.findViewById(R.id.button),
                v.findViewById(R.id.button2),
                v.findViewById(R.id.button3),
                v.findViewById(R.id.button4),
                v.findViewById(R.id.button5),
                v.findViewById(R.id.button6),
                v.findViewById(R.id.button7),
                v.findViewById(R.id.button8),
                v.findViewById(R.id.button9),
                v.findViewById(R.id.button10)
        };
        main_BTN_clear = v.findViewById(R.id.main_BTN_clear);
        main_BTN_back = v.findViewById(R.id.main_BTN_back);
    }

    private void startMainActivity() {
        Intent intent = new Intent(getActivity(), OpenActivity.class);
        startActivity(intent);
    }
}

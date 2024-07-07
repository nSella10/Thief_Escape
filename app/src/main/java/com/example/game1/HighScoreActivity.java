package com.example.game1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.game1.Fragments.ListFragment;
import com.example.game1.Fragments.MapFragment;
import com.example.game1.Fragments.OnItemClickedListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HighScoreActivity extends AppCompatActivity implements OnItemClickedListener    {

    private MapFragment mapFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();

        ListFragment listFragment = new ListFragment();
        listFragment.setOnItemClickedListener((OnItemClickedListener) this);

        fragmentTransaction.replace(R.id.main_FRAME_list,listFragment);

        mapFragment = new MapFragment();
        fragmentTransaction.replace(R.id.main_FRAME_map, mapFragment);

        fragmentTransaction.commit();

    }


    public void onItemClicked(double lat, double lon) {
        if (mapFragment != null) {
            mapFragment.zoomToLocation(lat, lon);
        }
    }
}
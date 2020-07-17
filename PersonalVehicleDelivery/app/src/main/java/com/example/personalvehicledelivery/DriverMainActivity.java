package com.example.personalvehicledelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;

public class DriverMainActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private FirebaseAuth mAuth;
    GoogleMap map;
    Button buttonGetDirection;

    MarkerOptions place1, place2;
    Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        mAuth = FirebaseAuth.getInstance();

        buttonGetDirection = findViewById(R.id.buttonGetDirection);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

        //(33.784704, -84.407949)
        place1 = new MarkerOptions().position(new LatLng(33.784704, -84.407949)).title("Origin");
        //(33.952890, -84.365042)
        place2 = new MarkerOptions().position(new LatLng(33.952890, -84.365042)).title("Destination");

        buttonGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = getUrl(place1.getPosition(), place2.getPosition(), "driving");
                new FetchURL(DriverMainActivity.this).execute(url, "driving");
            }
        });

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(DriverMainActivity.this, MainActivity.class));
            }
        });
    }

    private String getUrl(LatLng origin, LatLng dest, String dirMode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + dirMode;
        String params = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" +
                params + "&key=" + getString(R.string.api_key);
        return url;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(place1);
        map.addMarker(place2);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(33.784704, -84.407949), 12));
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        }
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }
}
package com.example.parkingpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button button;
    Map fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set to mapscreen rn
        setContentView(R.layout.activity_main);
        //Commit Edward

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        button = (Button) findViewById(R.id.to_map);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapActivity();
            }
        });

    }
    public void openMapActivity(){
        setContentView(R.layout.activity_mapscreen);
        fragment = new Map();
        //open fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();

        Button kaplanButton = findViewById(R.id.kaplanButton);
        Button parkingDeckButton = findViewById(R.id.parkingDeckButton);
        Button setParkingLocation = findViewById(R.id.setParkingLocation);

        View.OnClickListener kaplanButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.setCurrentLocation(Map.hardcodedLocations.KaplanParkingLot);
                TextView dest = findViewById(R.id.destinationAddress);
                dest.setText("DESTINATION: " + fragment.getCurrentDesinationAddress());
            }
        };

        View.OnClickListener parkingDeckButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.setCurrentLocation(Map.hardcodedLocations.ParkingDeck);
                TextView dest = findViewById(R.id.destinationAddress);
                dest.setText("DESTINATION:  " + fragment.getCurrentDesinationAddress());
            }
        };

        View.OnClickListener setParkingLocationButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.addMarkerAtCurrentLocation();
                TextView park = findViewById(R.id.parkingAddress);
                park.setText("PARKED: " + fragment.getCurrentParkedAddress());
            }
        };

        kaplanButton.setOnClickListener(kaplanButtonListener);
        parkingDeckButton.setOnClickListener(parkingDeckButtonListener);
        setParkingLocation.setOnClickListener(setParkingLocationButtonListener);

        TextView dest = findViewById(R.id.destinationAddress);
        dest.setText("DESTINATION:  " + fragment.getCurrentDesinationAddress());

        TextView park = findViewById(R.id.parkingAddress);
        park.setText("PARKED: " + fragment.getCurrentParkedAddress());


    }
}
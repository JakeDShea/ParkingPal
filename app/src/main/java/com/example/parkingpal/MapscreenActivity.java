package com.example.parkingpal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapscreenActivity extends AppCompatActivity {
    com.example.parkingpal.Map fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapscreen);

        // Set Map Fragment on current Activity
        fragment = new com.example.parkingpal.Map();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, (Fragment) fragment)
                .commit();

        // Set parking and location buttons
        Button kaplanButton = findViewById(R.id.kaplanButton);
        Button parkingDeckButton = findViewById(R.id.parkingDeckButton);
        Button setParkingLocation = findViewById(R.id.setParkingLocation);

        // OnClickListeners for buttons
        View.OnClickListener kaplanButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.setCurrentLocation(Map.hardcodedLocations.KAPLAN_ARENA);
                TextView dest = findViewById(R.id.destinationAddress);
                dest.setText("DESTINATION: " + fragment.getCurrentDesinationAddress());
                getCarsInLot("Kaplan Arena");
                getTotalSpots("Kaplan Arena");
                updateDistance();
            }
        };
        View.OnClickListener parkingDeckButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.setCurrentLocation(Map.hardcodedLocations.PARKING_DECK);
                TextView dest = findViewById(R.id.destinationAddress);
                dest.setText("DESTINATION:  " + fragment.getCurrentDesinationAddress());
                getCarsInLot("Parking Deck");
                getTotalSpots("Parking Deck");
                updateDistance();
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

        // Defaults
        TextView dest = findViewById(R.id.destinationAddress);
        dest.setText("DESTINATION: " + fragment.getCurrentDesinationAddress());
        TextView park = findViewById(R.id.parkingAddress);
        park.setText("PARKED: " + fragment.getCurrentParkedAddress());
        getCarsInLot("Kaplan Arena");
        getTotalSpots("Kaplan Arena");

    }

    public void getCarsInLot(String lotName){
        DatabaseReference carsInArea = FirebaseDatabase.getInstance().getReference().
                child("Parking Locations").child(lotName).child("CarsInArea");
        carsInArea.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView inLot = findViewById(R.id.carsInArea);
                String cars = snapshot.getValue().toString();
                inLot.setText("SPACES TAKEN: "+ cars);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // // // // // // // // // // // // // // // // // //
                // TODO: ADD ERROR HANDLING (lotName not found in database)
                // // // // // // // // // // // // // // // // // //
            }
        });
    }
    public void getTotalSpots(String lotName){
        DatabaseReference lotCap = FirebaseDatabase.getInstance().getReference().
                child("Parking Locations").child(lotName).child("TotalSpots");
        lotCap.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView total = findViewById(R.id.totalSpots);
                String spots = snapshot.getValue().toString();
                total.setText("CAPACITY: "+ spots);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // // // // // // // // // // // // // // // // // //
                // TODO: ADD ERROR HANDLING (lotName not found in database)
                // // // // // // // // // // // // // // // // // //
            }
        });
    }

    public void updateDistance(){
        TextView distanceText = findViewById(R.id.currentLocationCordinates);
        float distance = fragment.getDistanceToDestinationFromCurrent(fragment.getDestinationLatLng());
        String dist = String.valueOf(distance);
        distanceText.setText("Distance: " + dist + " meters");
    }
}

package com.example.parkingpal;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapscreenActivity extends AppCompatActivity {
    // Get the database again
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    ArrayList<String> lots = new ArrayList<String>();
    com.example.parkingpal.MapscreenActivity context = this;

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
        Spinner pickLot = findViewById(R.id.pickLot);
        pickLot.setSelection(0);
        Button setParkingLocation = findViewById(R.id.setParkingLocation);

        // Defaults the permit text
        ((TextView) findViewById(R.id.permit)).setText("Permit: DEFAULT");
        // Finds saved permit type
        ((TextView) findViewById(R.id.permit)).setText("Permit: " + getIntent().getExtras().get("PERMIT").toString());

        (myRef.child("Parking Locations")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String lotName = snapshot.getKey();
                    lots.add(lotName);
                    Log.v("Data", lotName);
                }

                // Sets spinner to have data
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, lots);
                pickLot.setAdapter(adapter);

                pickLot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String lotSelected = pickLot.getSelectedItem().toString();

                        Log.v("Spinner", "Text: " + lotSelected);

                        // Find out how many cars are in a lot
                        final long[] carsInArea = new long[1];
                        final long[] totalSpots = new long[1];
                        final double[] latitude = new double[1];
                        final double[] longitude = new double[1];

                        (myRef.child("Parking Locations").child(lotSelected)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                // Finds out how many cars are and can be at the specific parking spot
                                for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                                    String childKey = snapshot1.getKey();

                                    if(childKey.equals("CarsInArea"))
                                        carsInArea[0] = (long) snapshot1.getValue();
                                    else if (childKey.equals("TotalSpots"))
                                        totalSpots[0] = (long) snapshot1.getValue();
                                    else if (childKey.equals("Latitude"))
                                        latitude[0] = (double) snapshot1.getValue();
                                    else if (childKey.equals("Longitude"))
                                        longitude[0] = (double) snapshot1.getValue();

                                    Log.v("DataLot", childKey);
                                }
                                Log.v("PARENT LOT", dataSnapshot1.getKey());
                                String lotName = dataSnapshot1.getKey();

                                String parent = lotName.replaceAll(" ", "");
                                String lotNameForm = parent.replaceAll("-", "");
                                Log.v("PARENT AFTER FORMATING", lotNameForm);

                                fragment.setCurrentLocation(Map.hardcodedLocations.valueOf(lotNameForm));
                                TextView dest = findViewById(R.id.destinationAddress);
                                dest.setText("DESTINATION: " + fragment.getCurrentDesinationAddress());
                                getCarsInLot(lotName);
                                getTotalSpots(lotName);
                                parkingLocationAllowsUserPass(lotName);
                                updateDistance();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        View.OnClickListener setParkingLocationButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.addMarkerAtCurrentLocation();
                TextView park = findViewById(R.id.parkingAddress);
                park.setText("PARKED: " + fragment.getCurrentParkedAddress());
            }
        };

        setParkingLocation.setOnClickListener(setParkingLocationButtonListener);

        // Defaults
        TextView dest = findViewById(R.id.destinationAddress);
        dest.setText("DESTINATION: " + fragment.getCurrentDesinationAddress());
        TextView park = findViewById(R.id.parkingAddress);
        park.setText("PARKED: " + "Not Set");
        getCarsInLot(pickLot.getSelectedItem().toString());
        getTotalSpots(pickLot.getSelectedItem().toString());

    }

    public boolean parkingLocationAllowsUserPass(String lotName){
        DatabaseReference lotPasses = FirebaseDatabase.getInstance().getReference().child("Parking Locations").child(lotName).child("PassRequired");
        lotPasses.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                //if the data retreival failed, exit
                boolean passFound = false;
                if (!task.isSuccessful()){
                    return;
                }
                for(DataSnapshot pass : task.getResult().getChildren()){
                    System.out.println("Pass comparison");
                    System.out.println(pass.getValue().toString());
                    System.out.println(getIntent().getExtras().get("PERMIT").toString());
                    if (pass.getValue().toString().equals(mapUserPassToDatabaseFormat())){
                        passFound = true;
                    }
                }
                //this means the pass accepts any
                TextView parkingPassAllowed = findViewById(R.id.allowed);
                if(!task.getResult().hasChildren()){
                    passFound = true;
                }
                if (passFound){
                    System.out.println("PASS FOUND");
                    parkingPassAllowed.setText("ALLOWED");
                }
                else{
                    System.out.println("PASS NOT FONUD");
                    parkingPassAllowed.setText("NOT ALLOWED");
                }

            }
        });

        return false;
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
        distanceText.setText("DISTANCE: " + dist + " meters");
    }

    private String mapUserPassToDatabaseFormat(){
        String userPass = getIntent().getExtras().get("PERMIT").toString();
        String toReturn;
        if (userPass.equals("COMMUTER - DAY") || userPass.equals("COMMUTER - EVENING")){
            toReturn = "Commuter";
        }
        else if(userPass.equals("CARPOOL")){
            toReturn = "Carpool";
        }
        else if(userPass.equals("FACULTY/STAFF")){
            toReturn = "Faculty and Staff";
        }
        else if(userPass.equals("RESIDENT")){
            toReturn = "Resident";
        }
        else{
            return "notImplemented, sorry :)";
        }
        return toReturn;
    }
}

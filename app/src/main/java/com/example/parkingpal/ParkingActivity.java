package com.example.parkingpal;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParkingActivity extends AppCompatActivity
{
    // Get the database again
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    ArrayList<String> lots = new ArrayList<String>();

    com.example.parkingpal.ParkingActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set to way to establish connection for parking lots
        setContentView(R.layout.activity_parking);

        //  Set options on the spinner for what is in database
        Spinner lotSpinner = (Spinner) findViewById(R.id.lotChoice);
        lotSpinner.setSelection(0);

        // Gets items for spinner
        (myRef.child("Parking Locations")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String user = snapshot.getKey();
                    lots.add(user);
                    Log.v("Data", user);
                }

                // Sets spinner to have data
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, lots);
                lotSpinner.setAdapter(adapter);

                lotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ((TextView) findViewById(R.id.lotAnswer)).setText(adapterView.getItemAtPosition(i).toString());
                        System.out.println("Hello");
                        Log.v("Spinner", "Text: " + lotSpinner.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                // Checks if data was found correctly
                Log.v("Array", lots.toString());

                // Checks to see if lot is full or empty
                lotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ((TextView) findViewById(R.id.lotAnswer)).setText(adapterView.getItemAtPosition(i).toString());

                        Log.v("Spinner", "Text: " + lotSpinner.getSelectedItem().toString());

                        // Find out how many cars are in a lot
                        final long[] carsIn = new long[1];
                        final long[] carsTotal = new long[1];

                        (myRef.child("Parking Locations").child(lotSpinner.getSelectedItem().toString())).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                // Finds out how many cars are and can be at the specific parking spot
                                for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                                    String carKey = snapshot1.getKey();

                                    if(carKey.equals("CarsInArea"))
                                        carsIn[0] = (long) snapshot1.getValue();
                                    else if (carKey.equals("TotalSpots"))
                                        carsTotal[0] = (long) snapshot1.getValue();

                                    Log.v("DataLot", carKey);
                                }

                                // Check if the number of cars is good or not

                                System.out.println(carsTotal[0]);

                                if (carsTotal[0] / carsIn[0] > 2)
                                    ((TextView) findViewById(R.id.carText)).setText("Green");
                                else if (carsTotal[0] / carsIn[0] == 2)
                                    ((TextView) findViewById(R.id.carText)).setText("Yellow");
                                else
                                    ((TextView) findViewById(R.id.carText)).setText("Red");
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
    }
}


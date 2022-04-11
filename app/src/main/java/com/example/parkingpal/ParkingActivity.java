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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set to way to establish connection for parking lots
        setContentView(R.layout.activity_parking);

        //  Set options on the spinner for what is in database
        Spinner lotSpinner = (Spinner) findViewById(R.id.lotChoice);
        lotSpinner.setSelection(0);


        lotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) findViewById(R.id.lotAnswer)).setText(adapterView.getItemAtPosition(i).toString());

                Log.v("Spinner", "Text: " + lotSpinner.getSelectedItem().toString());

                // Hardcode values
                if(lotSpinner.getSelectedItem().equals("Compton Lot"))
                    ((TextView) findViewById(R.id.carText)).setText("Yellow");
                if(lotSpinner.getSelectedItem().equals("Kaplan Arena"))
                    ((TextView) findViewById(R.id.carText)).setText("Yellow");
                if(lotSpinner.getSelectedItem().equals("McGlothlin-Street Hall Parking Lot"))
                    ((TextView) findViewById(R.id.carText)).setText("Red");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // Add this once you don't need hardcoding

        /*
        // Gets items for spinner
        ArrayList<String> lots = new ArrayList<String>();
        myRef.child("Parking Locations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String user = snapshot.getKey();
                    lots.add(user);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lots);
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

        lotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) findViewById(R.id.lotAnswer)).setText(adapterView.getItemAtPosition(i).toString());

                Log.v("Spinner", "Text: " + lotSpinner.getSelectedItem().toString());

                // Find out how many cars are in a lot
                final int[] carsIn = new int[1];
                final int[] carsTotal = new int[1];

                myRef.child("Parking Locations").child(adapterView.getItemAtPosition(i).toString()).child("Number of Cars in Area:").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            carsIn[0] = (int) task.getResult().getValue();
                        }
                    }
                });

                myRef.child("Parking Locations").child(adapterView.getItemAtPosition(i).toString()).child("Number of Total Spots:").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            carsTotal[0] = (int) task.getResult().getValue();
                        }
                    }
                });

                System.out.println(carsTotal[0]);

                if (carsTotal[0] / carsIn[0] > 2)
                    ((TextView) findViewById(R.id.carText)).setText("Green");
                else if (carsTotal[0] / carsIn[0] == 2)
                    ((TextView) findViewById(R.id.carText)).setText("Yellow");
                else
                    ((TextView) findViewById(R.id.carText)).setText("Red");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/
    }
}

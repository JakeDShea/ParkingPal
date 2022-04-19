package com.example.parkingpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button b1,b2;
    EditText ed1,ed2;

    TextView tx1;
    int counter = 3;

    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set to mapscreen rn
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        b1 = (Button) findViewById(R.id.button); //login button
        ed1 = (EditText) findViewById(R.id.editText); //email
        ed2 = (EditText) findViewById(R.id.editText2); //password

        //clicked the sign up button - take to sign up page
        b2 = (Button) findViewById(R.id.signUp); //sign up button
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Button Clicked");
                //when next button is clicked, user is brought to the screen to choose their permit type
                Intent activity2Intent = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(activity2Intent);
            }
        });

        //if log in button clicked, take them to main page....
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed1.getText().toString().equals("admin") &&
                        ed2.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...", Toast.LENGTH_SHORT).show();
                    //will need to link the login putton to the home page once implemented

                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();

                    //counting to limit amount of invalid login attempts
                    tx1.setVisibility(View.VISIBLE);
                    tx1.setBackgroundColor(Color.RED);
                    counter--;
                    tx1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        b1.setEnabled(false);
                    }
                }
            }
        });


        Button button = (Button) findViewById(R.id.to_map);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapActivity();
            }
        });

        Button button2 = (Button) findViewById(R.id.testParkingButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openParkActivity();
            }
        });

    }
    public void openMapActivity(){
        setContentView(R.layout.activity_mapscreen);
        com.example.parkingpal.Map fragment = new com.example.parkingpal.Map();
        //open fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, (Fragment) fragment)
                .commit();

        Button kaplanButton = findViewById(R.id.kaplanButton);
        Button parkingDeckButton = findViewById(R.id.parkingDeckButton);
        Button setParkingLocation = findViewById(R.id.setParkingLocation);

        View.OnClickListener kaplanButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.setCurrentLocation(com.example.parkingpal.Map.hardcodedLocations.KaplanParkingLot);
                TextView dest = findViewById(R.id.destinationAddress);
                dest.setText("DESTINATION: " + fragment.getCurrentDesinationAddress());

            }
        };

        View.OnClickListener parkingDeckButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.setCurrentLocation(com.example.parkingpal.Map.hardcodedLocations.ParkingDeck);
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

    public void openParkActivity(){
        // Creates a random seed
        Random generator = new Random();

        // Sets up the intent of this method with values to pass along
        Intent intent = new Intent(this, ParkingActivity.class);

        // Starts the activity
        startActivity(intent);
    }
}
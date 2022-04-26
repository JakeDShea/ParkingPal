package com.example.parkingpal;

import androidx.appcompat.app.AppCompatActivity;

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
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
//        myRef.setValue("Hello, World!");



        b1 = (Button) findViewById(R.id.button); //login button
        ed1 = (EditText) findViewById(R.id.editText); //email
        ed2 = (EditText) findViewById(R.id.email); //password

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
                //openMapActivity();
                Intent intent = new Intent(MainActivity.this, MapscreenActivity.class);
                startActivity(intent);
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



    public void openParkActivity(){
        // Creates a random seed
        Random generator = new Random();

        // Sets up the intent of this method with values to pass along
        Intent intent = new Intent(this, ParkingActivity.class);

        // Starts the activity
        startActivity(intent);
    }
}
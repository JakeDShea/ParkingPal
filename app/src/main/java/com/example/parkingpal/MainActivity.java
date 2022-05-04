package com.example.parkingpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button b1,b2;
    EditText ed1,ed2;
    Users user;
    Users.Info info;
    FirebaseDatabase database;
    DatabaseReference myRef;

    TextView tx1;
    int counter = 3;

    private Button button;
    private Button button2;

    private String permitType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        b1 = (Button) findViewById(R.id.login); //login button
        ed1 = (EditText) findViewById(R.id.id); //email
        ed2 = (EditText) findViewById(R.id.password); //password

        //clicked the sign up button - take to sign up page
        b2 = (Button) findViewById(R.id.signUp); //sign up button
        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Button Clicked");
                openCreateAccountActivity();
            }
        });

        //if log in button clicked, take them to main page....
        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                user = new Users();
                info = new Users.Info();
                info.setEmail(ed1.getText().toString());

                // Check if credential exist

                String username = ed1.getText().toString();
                String password = ed2.getText().toString();

                // Gets bare minimum values needed to move on!!! Can add later
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            // This user has the email in question
                            if(snapshot.child("email").getValue().toString().equals(username))
                            {
                                // Correct credentials
                                if(snapshot.child("password").getValue().toString().equals(password))
                                {
                                    permitType = snapshot.child("permit").getValue().toString();
                                    openMapscreenActivity();
                                }
                            }
                        }

                        // Only happens if user could not log in with given username/password
                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

/*
                //Read inputted username
                // currently in database:
                // username: 1527
                // password: 1234
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        readData(user, info.getEmail());
                        openMapscreenActivity();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
*/
//                if (ed1.getText().toString().equals("admin") &&
//                        ed2.getText().toString().equals("admin")) {
//                    Toast.makeText(getApplicationContext(),
//                            "Redirecting...", Toast.LENGTH_SHORT).show();
//                    //will need to link the login button to the home page once implemented
//
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
//
//                    //counting to limit amount of invalid login attempts
//                    tx1.setVisibility(View.VISIBLE);
//                    tx1.setBackgroundColor(Color.RED);
//                    counter--;
//                    tx1.setText(Integer.toString(counter));
//
//                    if (counter == 0) {
//                        b1.setEnabled(false);
//                    }
//                }
//
//
            }
        });


        Button button = (Button) findViewById(R.id.to_map);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapscreenActivity();
            }
        });

        Button button2 = (Button) findViewById(R.id.testParkingButton);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openParkActivity();
            }
        });

    }

    //Populates user/info class with database information.
    public void readData(Users user, String email){
        myRef.child(email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().exists()){
                        Toast.makeText(getApplicationContext(), "successful Read", Toast.LENGTH_SHORT).show();
                        DataSnapshot shot = task.getResult();
                        info.setName(String.valueOf(shot.child("name").getValue()));
                        info.setLocation(String.valueOf(shot.child("location").getValue()));
                        info.setPass(String.valueOf(shot.child("pass").getValue()));
                        info.setPassword(String.valueOf(shot.child("password").getValue()));
                        info.setUniversity(String.valueOf(shot.child("university").getValue()));
                        info.setPassExpiration(String.valueOf(shot.child("passExpiration").getValue()));

                        String pass =  ed2.getText().toString();

                        if(info.getPassword().equals(pass)){
                            //This needs to change to whatever activity comes after this.
                            //This is here to just check to see if it is working.
                            //openParkActivity();
                            openMapscreenActivity();
                        }

                        else{
                            Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else {
                        Toast.makeText(getApplicationContext(), "User does not exsist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "failed to read", Toast.LENGTH_SHORT).show();
                }
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

    public void openCreateAccountActivity(){
        // Sets up the intent of this method with values to pass along
        Intent intent = new Intent(this, CreateAccount.class);
        // Starts the activity
        startActivity(intent);
    }

    public void openMapscreenActivity(){
        // Sets up the intent of this method with values to pass along
        Intent intent = new Intent(this, MapscreenActivity.class);
        intent.putExtra("PERMIT", permitType);
        startActivity(intent);
    }

}
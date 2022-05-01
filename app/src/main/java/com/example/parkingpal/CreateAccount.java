package com.example.parkingpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccount extends AppCompatActivity {
    private static final String TAG = "";
    Button b1;
    private Users user;
    private Users.Info info;
    private Random rand;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Users");

    EditText fN;
    EditText lN;
    EditText email;
    EditText password;
    EditText university;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        b1 = (Button) findViewById(R.id.login); //back to login button

        Button buttonOne = findViewById(R.id.account_info_next_button);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");

                createUser();
                //when next button is clicked, user is brought to the screen to choose their permit type
                Intent intent = new Intent(getApplicationContext(), ChoosePermit.class);

                //Temp intents to pass I don't think we need to pass everything just create what we can create here then modify later.
                //We need the id to find the user we just created.
//                intent.putExtra("first", fN.getText().toString());
//                intent.putExtra("last", lN.getText().toString());
//                intent.putExtra("email", email.getText().toString());
//                intent.putExtra("password", password.getText().toString());
//                intent.putExtra("university", university.getText().toString());
                intent.putExtra("ID", String.valueOf(user.getID()));
//                intent.putExtra("", fN.getText().toString());
//                intent.putExtra("first", fN.getText().toString());


                startActivity(intent);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Button Clicked");
                //"or login" button clicked, bring back to landing page
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
            }
        });
    }

    //Adds user to database
    public void createUser(){
        user = new Users();
        rand = new Random();
        fN = (EditText) findViewById(R.id.FN);
        lN = (EditText) findViewById(R.id.LN);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.Passwd);
        university = (EditText) findViewById(R.id.uni);

        //Random number for account ID
//        user.setID(rand.nextInt(5000));

        // Read from the database
        ArrayList<String> lots = new ArrayList<String>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user.setID(rand.nextInt(5000));

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String snap = snapshot.getKey();
                    lots.add(snap);

                }

                if(lots.contains(user.getID())){
                    user.setID(rand.nextInt(5000));
                    Log.v("size", String.valueOf(lots.size()));
                }

                //Inputing information from text fields into info class. Hardcode some information.
                info = new Users.Info("Williamsburg", fN.getText().toString() + " " + lN.getText().toString(), "Default", email.getText().toString(), university.getText().toString(), "Default", password.getText().toString());
                user.setInfo(info);

                //Puts information into database.
                myRef.child(String.valueOf(user.getID())).setValue(user.getInfo());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}
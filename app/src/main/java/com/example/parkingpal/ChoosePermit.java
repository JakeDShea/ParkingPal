package com.example.parkingpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChoosePermit extends AppCompatActivity {
    private String id;
    private String permitType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_permit);

        //Intent to get passed ID for created user.
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");

        //TODO - Connect id to exsisting id and changed the parking pass status.

        // Get the database again
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Button done = findViewById(R.id.login);

        Spinner spinner = (Spinner) findViewById(R.id.permit_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.permits_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected (AdapterView < ? > adapterView, View view,int i, long l){
                System.out.println("Button Clicked");
                Toast.makeText(ChoosePermit.this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                // save the selected permit as a string to be entered into database
                permitType = adapterView.getSelectedItem().toString();

                Log.v("ID", id);

                // actually moves the permit into the database
                myRef.child("Users").child(id).child("permit").setValue(permitType);
            }

            @Override
            public void onNothingSelected (AdapterView < ? > adapterView){

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapscreenActivity();
            }
        });
    }

    public void openMapscreenActivity(){
        // Sets up the intent of this method with values to pass along
        Intent intent = new Intent(this, MapscreenActivity.class);
        intent.putExtra("PERMIT", permitType);
        startActivity(intent);
    }

}
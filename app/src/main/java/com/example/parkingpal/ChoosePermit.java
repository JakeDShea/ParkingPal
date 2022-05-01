package com.example.parkingpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ChoosePermit extends AppCompatActivity {
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_permit);

        //Intent to get passed ID for created user.
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");

        //TODO - Connect id to exsisting id and changed the parking pass status.


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
                String permitType = adapterView.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected (AdapterView < ? > adapterView){

            }
        });
    }



}
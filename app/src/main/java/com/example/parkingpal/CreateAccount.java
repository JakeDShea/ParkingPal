package com.example.parkingpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button buttonOne = findViewById(R.id.account_info_next_button);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                //when next button is clicked, user is brought to the screen to choose their permit type
                Intent activity2Intent = new Intent(getApplicationContext(), ChoosePermit.class);
                startActivity(activity2Intent);
            }
        });
    }
}
package com.project.travelhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        Button btnChatScreen = (Button) findViewById(R.id.btnChat);
        Button btnTripScreen = (Button) findViewById(R.id.btnTrips);

        btnChatScreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this , Chats.class);
                startActivity(i);
            }

        });

        btnTripScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this , OpenTrips.class);
                startActivity(i);
            }
        });
        

    }
}

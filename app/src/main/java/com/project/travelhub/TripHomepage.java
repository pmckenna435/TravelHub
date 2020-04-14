package com.project.travelhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TripHomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_homepage);


        Button btnItinerary = (Button) findViewById(R.id.btnItinerary);



        btnItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = getIntent();
                final String tripID = in.getStringExtra("trip_id");

                Intent i = new Intent(TripHomepage.this , Itinerary.class);
                i.putExtra("trip_id", tripID);
                startActivity(i);
            }
        });

    }
}

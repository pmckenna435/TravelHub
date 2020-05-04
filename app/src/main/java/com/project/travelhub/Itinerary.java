package com.project.travelhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Itinerary extends AppCompatActivity {

    ArrayList<ItineraryDay> itineraryDays = new ArrayList<ItineraryDay>();
    RecyclerView rvItinerary ;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        Intent i = getIntent();
        final String tripID = i.getStringExtra("trip_id");
        String username = i.getStringExtra("username");

        setNavBar(username);

        Toast.makeText(Itinerary.this,tripID, Toast.LENGTH_SHORT).show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Trips").child(tripID).child("itinerary");

        //Toast.makeText(Itinerary.this,"test before", Toast.LENGTH_SHORT).show();

        final Context c = this.getBaseContext();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String activity = (String) snapshot.child("activity").getValue();
                    int day = snapshot.child("day").getValue(Integer.class);
                    int month = snapshot.child("month").getValue(Integer.class);
                    int year = snapshot.child("year").getValue(Integer.class);

                    ItineraryDay nextDay = new ItineraryDay(day,month, year, 3, activity);
                    itineraryDays.add(nextDay);



                }


                rvItinerary =  findViewById(R.id.rvItinerary);

                LinearLayoutManager lm = new LinearLayoutManager(getBaseContext());

                rvItinerary.setLayoutManager(lm);

                adapter = new ItineraryDisplayAdapter(itineraryDays,c,tripID);
                rvItinerary.setAdapter(adapter);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public  void setNavBar(final String username) {
        BottomNavigationView navBar = findViewById(R.id.navBar);
        navBar.setSelectedItemId(R.id.nbTrips);


        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent i;

                switch (menuItem.getItemId()) {

                    case R.id.nbChats:

                        i = new Intent(Itinerary.this, Chats.class);
                        i.putExtra("username", username);

                        startActivity(i);

                        break;

                    case R.id.nbTrips:
                        i = new Intent(Itinerary.this, OpenTrips.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;

                    case R.id.nbhome:
                        i = new Intent(Itinerary.this, HomeScreen.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;

                    case R.id.nbCities:
                        i = new Intent(Itinerary.this, CitiesVisited.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;
                }


                return true;
            }
        });
    }



}

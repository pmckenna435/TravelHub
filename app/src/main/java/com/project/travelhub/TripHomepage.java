package com.project.travelhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.travelhub.data.User;

import java.util.ArrayList;

public class TripHomepage extends AppCompatActivity implements AddUserDialog.AddUserInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_homepage);
        final String username;

        Intent in = getIntent();
        final String tripID = in.getStringExtra("trip_id");
        username = in.getStringExtra("username");
        String tripname = in.getStringExtra("tripname");
        TextView tripnameText = findViewById(R.id.txtTitle);
        tripnameText.setText(tripname);
        setNavBar(username);
        Button btnItinerary = (Button) findViewById(R.id.btnItinerary);
        Button btnAddUser = (Button) findViewById(R.id.btnAddUser);
        Button btnChat = (Button) findViewById(R.id.btnGroupChat);


        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = getIntent();
                String tripname = in.getStringExtra("tripname");


                Intent  i  = new Intent(TripHomepage.this, OpenChats.class );

                i.putExtra("ID", tripID);
                i.putExtra("refToUse" , "Trips");
                i.putExtra("username", username);
                i.putExtra("tripname" , tripname);
                startActivity(i);

            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUserDialog dialog = new AddUserDialog();
                dialog.show(getSupportFragmentManager(), "Add user");
            }
        });



        btnItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i = new Intent(TripHomepage.this , Itinerary.class);
                i.putExtra("trip_id", tripID);
                i.putExtra("username" , username);
                startActivity(i);
            }
        });



    }

    @Override
    public void addUser(final String username) {

        Intent in = getIntent();
        final String tripID = in.getStringExtra("trip_id");
        Toast.makeText(TripHomepage.this,tripID + " is the trip id", Toast.LENGTH_SHORT).show();


        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String firebaseUsername = String.valueOf(snapshot.child("username").getValue());

                    // following code checks if the username in db matches the username that the user has input
                    // if it matches then the id gets added to the users information
                    if(username.equals(firebaseUsername)){
                        ArrayList user_trips;
                        user_trips = (ArrayList) snapshot.child("user_trips").getValue();
                        user_trips.add(tripID);

                        final String userID = snapshot.getKey();

                        ref.child(userID).child("user_trips").setValue(user_trips);

                        final DatabaseReference tripRef = FirebaseDatabase.getInstance().getReference("Trips").child(tripID);

                        // following code adds the user id to the trip section of db.
                        tripRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList users = new ArrayList();
                                users = (ArrayList) dataSnapshot.child("users").getValue();
                                users.add(userID);
                                tripRef.child("users").setValue(users);

                                Toast.makeText(TripHomepage.this,"User " + username + " is added", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




                    }


                }




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

                        i = new Intent(TripHomepage.this, Chats.class);
                        i.putExtra("username", username);

                        startActivity(i);

                        break;

                    case R.id.nbTrips:
                        i = new Intent(TripHomepage.this, OpenTrips.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;

                    case R.id.nbhome:
                        i = new Intent(TripHomepage.this, HomeScreen.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;

                    case R.id.nbCities:
                        i = new Intent(TripHomepage.this, CitiesVisited.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;
                }


                return true;
            }
        });
    }


}

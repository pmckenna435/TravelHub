package com.project.travelhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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


        Button btnItinerary = (Button) findViewById(R.id.btnItinerary);
        Button btnAddUser = (Button) findViewById(R.id.btnAddUser);

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

                Intent in = getIntent();
                final String tripID = in.getStringExtra("trip_id");

                Intent i = new Intent(TripHomepage.this , Itinerary.class);
                i.putExtra("trip_id", tripID);
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

                    boolean shouldAdd = false;



                    if(username.equals(firebaseUsername)){
                        ArrayList user_trips = new ArrayList();
                        user_trips = (ArrayList) snapshot.child("user_trips").getValue();
                        user_trips.add(tripID);

                        final String userID = snapshot.getKey();

                        ref.child(userID).child("user_trips").setValue(user_trips);

                        final DatabaseReference tripRef = FirebaseDatabase.getInstance().getReference("Trips").child(tripID);

                        tripRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList users = new ArrayList();
                                users = (ArrayList) dataSnapshot.child("users").getValue();
                                users.add(userID);
                                ref.child("users").setValue(users);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




                    }

                    // Toast.makeText(Chat.this,String.valueOf(cities_visited[0]), Toast.LENGTH_SHORT).show();

                    //GenericTypeIndicator<User> g = new GenericTypeIndicator<User>() { };


                    //User user = snapshot.getValue(g);

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }
}

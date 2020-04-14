package com.project.travelhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OpenTrips extends AppCompatActivity {


    private RecyclerView rvTrips;
    Button btnCreateTrip;
    private RecyclerView.Adapter adapter;
    private ArrayList openTripsID = new ArrayList();
    private ArrayList openTripsnames = new ArrayList();
    private int counter = 1;
    private boolean shouldContinue = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_trips);
        getUserTripsFromDatabase();





        btnCreateTrip = findViewById(R.id.btnNewTrip);

        btnCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OpenTrips.this , TripCreation.class);
                startActivity(i);
            }
        });
    }




    public void getUserTripsFromDatabase(){

        String fuser;

        fuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        final Context c = this.getBaseContext();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(fuser);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List user_trips = new ArrayList();
                user_trips = (ArrayList) dataSnapshot.child("user_trips").getValue();
                // String user = (String) dataSnapshot.child("email").getValue();
                String user = (String) FirebaseAuth.getInstance().getCurrentUser().getUid();
                getUsersFromDatabase(user_trips,user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }// get users from firebase



    public void getUsersFromDatabase(List usertrips, final String user){
        final int size = usertrips.size();
        // List usernames = new ArrayList();

        DatabaseReference ref;
        final Context c = this.getBaseContext();
        for (int i = 1; i < size; i++ ){
            final String tripID = (String) usertrips.get(i);
            // Toast.makeText(Chats.this,"before val", Toast.LENGTH_SHORT).show();
            // Toast.makeText(Chats.this,"event", Toast.LENGTH_SHORT).show();
            ref = FirebaseDatabase.getInstance().getReference("Trips").child(tripID);

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int index = 1;


                    String displayTripname = (String) dataSnapshot.child("tripName").getValue();



                    addToList(displayTripname, tripID);
                    shouldContinue = checkContinue(size);

                    if (shouldContinue == true){
                        // do this
                        populateRecyclerview();

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            }); // add val event listener

        }



    } // display users


    public void addToList(String tripname, String tripID){
        Toast.makeText(OpenTrips.this,tripID, Toast.LENGTH_SHORT).show();
        openTripsnames.add(tripname);
        openTripsID.add(tripID);
    }

    public void populateRecyclerview(){
        final Context c = this.getBaseContext();



        rvTrips =  findViewById(R.id.rvOpenTrips);
        // RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
        LinearLayoutManager lm = new LinearLayoutManager(getBaseContext());
        //lm.setStackFromEnd(true);
        rvTrips.setLayoutManager(lm);

        adapter = new OpenTripsAdapter(openTripsnames,openTripsID,c);
        rvTrips.setAdapter(adapter);


    }

    public boolean checkContinue(int size){
        if((counter + 1) == size){
            return true;
        }else{
            counter ++;
            return false;
        }

    }



}

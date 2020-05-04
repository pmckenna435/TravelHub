package com.project.travelhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CitiesVisited extends AppCompatActivity implements AddCityDialog.AddCityInterface {

    private ArrayList<String> cities = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_visited);
        final String username;
        Intent in = getIntent();
        username = in.getStringExtra("username");
        // call method to set nav bar
        setNavBar(username);


        // get current users id in order to get the users cities easier

        String fuser;
        fuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");


        // creates a link to the database
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(fuser).child("cities_visited");

        // adds an event listener to get the list every time there is a change in data
        // ie when a new city is added by the user
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cities = (ArrayList) dataSnapshot.getValue();

                addCityToView(cities);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Button add;
        add = findViewById(R.id.btnAddCities);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddCityDialog dialog = new AddCityDialog();
                dialog.show(getSupportFragmentManager(), "Add city");

            }
        });


    }

    private void addCityToView(ArrayList<String> cities) {

        Context context;
        context  = this.getBaseContext();
        RecyclerView rvCities;
        rvCities =  findViewById(R.id.rvCities);

        // populates the list in the UI
        // using the adapter
        LinearLayoutManager lm = new LinearLayoutManager(getBaseContext());
        rvCities.setLayoutManager(lm);
        RecyclerView.Adapter adapter;
        adapter = new CitiesAdapter(cities , context);
        rvCities.setAdapter(adapter);

    }

    @Override
    public void addCity(final String city) {
        final String cityToAdd = city;
        String fuser;
        fuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");


        // creates a link to the database
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(fuser).child("cities_visited");

        // adds an event listener to get the list every time there is a change in data
        // ie when a new city is added by the user
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cities = (ArrayList) dataSnapshot.getValue();

                boolean containsCity = cities.contains(cityToAdd);

                if (containsCity){
                    Toast toast = Toast.makeText(CitiesVisited.this,"You already have this city added", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }else{
                    cities.add(cityToAdd);
                    mDatabase.setValue(cities);


                    Toast toast = Toast.makeText(CitiesVisited.this,cityToAdd + " added to your profile", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }






                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public  void setNavBar(final String username){
        BottomNavigationView navBar = findViewById(R.id.navBar);
        navBar.setSelectedItemId(R.id.nbCities);


        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent i;

                switch (menuItem.getItemId())

                {

                    case R.id.nbChats:

                        i = new Intent(CitiesVisited.this , Chats.class);
                        i.putExtra("username", username);

                        startActivity(i);

                        break;

                    case R.id.nbTrips:
                        i = new Intent(CitiesVisited.this , OpenTrips.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;

                    case R.id.nbhome:
                        i = new Intent(CitiesVisited.this , HomeScreen.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;

                    case R.id.nbCities:
                        i = new Intent(CitiesVisited.this , CitiesVisited.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;
                }


                return true;
            }
        });


    } // nav

}

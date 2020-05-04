package com.project.travelhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

import static java.lang.Double.valueOf;

public class HomeScreen extends AppCompatActivity {
        private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Intent i = getIntent();
        username = i.getStringExtra("username");

        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(ID);

        // calls methods to update the display and navigation
        updateDisplay(username , ref);
        setNavBar(username);
    }


    public void updateDisplay(String username , DatabaseReference ref){

        // set the username display
        TextView txtUsername = findViewById(R.id.txtUsernameDisplay);
        txtUsername.setText(username);
        this.username = username;

        //set the rating


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // gets values from firebase
               String tempTotal = (String) dataSnapshot.child("rating_total").getValue();
                String tempAmount = (String) dataSnapshot.child("number_of_ratings").getValue();

                // converts the values
                double tempDoubleTotal = Double.parseDouble(tempTotal);
                double tempDoubleAmount = Double.parseDouble(tempAmount);

                // calculates the average
                double avg = tempDoubleTotal/tempDoubleAmount;
                DecimalFormat decFormat = new DecimalFormat("#.##");
                avg = valueOf(decFormat.format(avg));

                if(tempDoubleTotal == 0 && tempDoubleAmount == 0){
                    avg = 0;
                }

                //Displays
                TextView txtRating = findViewById(R.id.txtAvgRatingValue);
                txtRating.setText(""+ avg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });






    }

    public void setNavBar(final String username){

        BottomNavigationView navBar = findViewById(R.id.navBar);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent i;

                switch (menuItem.getItemId())

                {

                    case R.id.nbChats:

                        i = new Intent(HomeScreen.this , Chats.class);
                        i.putExtra("username", username);

                        startActivity(i);

                        break;

                    case R.id.nbTrips:
                        i = new Intent(HomeScreen.this , OpenTrips.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;

                    case R.id.nbhome:
                        i = new Intent(HomeScreen.this , HomeScreen.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;

                    case R.id.nbCities:
                        i = new Intent(HomeScreen.this , CitiesVisited.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;
                }


                return true;
            }
        });



    }// nav
}

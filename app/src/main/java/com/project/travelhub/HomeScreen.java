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

public class HomeScreen extends AppCompatActivity {
        private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        BottomNavigationView navBar = findViewById(R.id.navBar);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId())

                {

                    case R.id.nbChats:

                        Intent i = new Intent(HomeScreen.this , Chats.class);
                        i.putExtra("username", username);

                        startActivity(i);

                        break;

                    case R.id.nbTrips:
                        Intent ii = new Intent(HomeScreen.this , OpenTrips.class);
                        ii.putExtra("username", username);
                        startActivity(ii);
                        break;

                    case R.id.nbhome:

                        Intent in = new Intent(HomeScreen.this , HomeScreen.class);
                        in.putExtra("username", username);
                       startActivity(in);
                        break;

                }


                return true;
            }
        });


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(ID);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username;
                username = (String) dataSnapshot.child("username").getValue();
                updateDisplay(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Button btnChatScreen = (Button) findViewById(R.id.btnChat);
        Button btnTripScreen = (Button) findViewById(R.id.btnTrips);

        btnChatScreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this , Chats.class);
                i.putExtra("username", username);
                startActivity(i);
            }

        });

        btnTripScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this , OpenTrips.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });


    }


    public void updateDisplay(String username){
        TextView txtUsername = findViewById(R.id.txtUsernameDisplay);
        txtUsername.setText(username);
        this.username = username;

    }
}

package com.project.travelhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItineraryEdit extends AppCompatActivity {
    private TextView test;
    private TextView date;
    private EditText activity;
    private Button btnSave;
    private String tripID;
    private int posInList;
    ArrayList<ItineraryDay> itineraryDays = new ArrayList<ItineraryDay>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_edit);

        date = findViewById(R.id.txtDateOfSelectedDay);
        activity = findViewById(R.id.txtChangedText);
        btnSave = findViewById(R.id.btnSaveChanges);

        Intent i = getIntent();
        //final String textForTest = i.getStringExtra("position");
        posInList = i.getIntExtra("position",0);
        tripID = i.getStringExtra("trip");
        test =  findViewById(R.id.testpos);

        test.setText("" + posInList);

        //get list from firebase

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Trips").child(tripID).child("itinerary");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String activity = (String) snapshot.child("activity").getValue();
                    int day = snapshot.child("day").getValue(Integer.class);
                    int month = snapshot.child("month").getValue(Integer.class);
                    int year = snapshot.child("year").getValue(Integer.class);
                    int dayOfWeek = snapshot.child("dayOfWeek").getValue(Integer.class);

                    ItineraryDay nextDay = new ItineraryDay(day,month, year, dayOfWeek, activity);
                    itineraryDays.add(nextDay);



                }

                ItineraryDay dayToChange = itineraryDays.get(posInList);

                displayDetails(dayToChange);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get the text, make the change to the list
                String changedText = activity.getText().toString().trim();

               ItineraryDay dayToChange =  itineraryDays.get(posInList);

               dayToChange.setActivity(changedText);

               itineraryDays.set(posInList,dayToChange);

               // make the changes in firebase
                final DatabaseReference mDatabase;

                mDatabase = FirebaseDatabase.getInstance().getReference("Trips").child(tripID);

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mDatabase.child("itinerary").setValue(itineraryDays);

                        Toast.makeText(ItineraryEdit.this,"Changes Saved", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(ItineraryEdit.this , Itinerary.class);
                        i.putExtra("trip_id", tripID);
                        startActivity(i);




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });





    }


    public void displayDetails(ItineraryDay dayToChange){
        int day = dayToChange.getDay();
        int month = dayToChange.getMonth();
        month ++; // jan is 0 so month is 1 value behind
        int year = dayToChange.getYear();
        String mDate = day + "/" + month + "/" + year;

        date.setText(mDate);
        activity.setText(dayToChange.getActivity());


    }


}

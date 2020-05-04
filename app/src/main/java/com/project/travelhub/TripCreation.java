package com.project.travelhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.travelhub.data.Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.lang.Math;
import java.util.List;

public class TripCreation extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener startDate;
   private TextView startDateText;
    private DatePickerDialog.OnDateSetListener endDate;
    private TextView endDateText;
    private  TextView test;
    private String username;
    private List cities = new ArrayList();
    private EditText city;
    public ArrayList<Message> messages;
    Calendar startCal = Calendar.getInstance();
    Calendar endCal = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_creation);


        Intent in = getIntent();

        username = in.getStringExtra("username");

        setNavBar(username);


        final Calendar startCal = Calendar.getInstance();
        final Calendar endCal = Calendar.getInstance();
       city = findViewById(R.id.etCity);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        Button btnAddCity = (Button) findViewById(R.id.btnAdd);
        Button btnClear = (Button) findViewById(R.id.btnClear);


       startDateText = findViewById(R.id.tvStartDate);
       endDateText = findViewById(R.id.tvEndDate);
       test = findViewById(R.id.tvTest);
       final EditText name;
       name = findViewById(R.id.etTripName);

       btnClear.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               name.setText("");
               startDateText.setText("Start Date");
               endDateText.setText("End date");
               cities.clear();
               Toast.makeText(TripCreation.this,"cleared data", Toast.LENGTH_SHORT).show();

           }
       });



       btnAddCity.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String cityToAdd = city.getText().toString().trim();
               cities.add(cityToAdd);
               city.setText("");
               Toast.makeText(TripCreation.this,"City Added", Toast.LENGTH_SHORT).show();

           }
       });

       btnSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {









               String fuser;
               fuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
               String tripName = name.getText().toString();
               String startOfTrip = startDateText.getText().toString();
               String endOfTrip = endDateText.getText().toString();
               List<ItineraryDay> itinerary = new ArrayList<ItineraryDay>();
               List users = new ArrayList();
               users.add(fuser);



               //int numberOfDays = endCal.getTime() - startCal.getTime()
               Date startDateDate = startCal.getTime();
               Date endDateDate = endCal.getTime();


               if(startDateDate.compareTo(endDateDate) > 0){

                   name.setText("");
                   startDateText.setText("Start Date");
                   endDateText.setText("End date");
                   cities.clear();
                   Toast.makeText(TripCreation.this,"Error! Start date is later then End date", Toast.LENGTH_SHORT).show();

               } else {


                   Calendar compareCal = Calendar.getInstance();
                   compareCal = (Calendar) startCal.clone();
                   int i = 0;

                   // long diff = (( endDateDate.getTime() - startDateDate.getTime())/8640000);


                   // code below calculates the amount of days the user is going for
                   boolean dateMet = false;
                   while (dateMet == false) {

                       i++;
                       int year = compareCal.get(Calendar.YEAR);
                       int month = compareCal.get(Calendar.MONTH);
                       int date = compareCal.get(Calendar.DAY_OF_MONTH);
                       int dayOfWeek = compareCal.get(Calendar.DAY_OF_WEEK);

                       ItineraryDay dayToAdd = new ItineraryDay(date, month, year, dayOfWeek);
                       itinerary.add(dayToAdd);

                       compareCal.add(Calendar.DAY_OF_MONTH, 1);
                       Date compareDateDate = compareCal.getTime();

                       if (endDateDate.compareTo(compareDateDate) < 0) {
                           dateMet = true;
                       }

                   } // while

                   Trip newTrip = new Trip(tripName, cities, users, startOfTrip, endOfTrip, itinerary);

                   DatabaseReference mRef;
                   mRef = FirebaseDatabase.getInstance().getReference("Trips");
                   DatabaseReference childRef = mRef.push();
                   final String childkey = childRef.getKey();
                   mRef.child(childkey).setValue(newTrip);

                   final DatabaseReference mDatabase;

                   mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(fuser);


                   mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           List user_trips = new ArrayList();
                           user_trips = (ArrayList) dataSnapshot.child("user_trips").getValue();

                           user_trips.add(childkey);
                           mDatabase.child("user_trips").setValue(user_trips);
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });

                   Intent toTripPage = new Intent(TripCreation.this, TripHomepage.class);
                   toTripPage.putExtra("trip_id", childkey);
                   toTripPage.putExtra("tripname", tripName);
                   toTripPage.putExtra("username", username);
                   startActivity(toTripPage);

               }

           } // on click
       });



       startDateText.setOnClickListener(new View.OnClickListener() {
           @SuppressLint("ResourceAsColor")
           @Override
           public void onClick(View v) {
               Calendar cal = Calendar.getInstance();
               int currentYear = cal.get(Calendar.YEAR);
               int currentMonth = cal.get(Calendar.MONTH);
               int currentDate =  cal.get(Calendar.DAY_OF_MONTH);
               DatePickerDialog dialog = new DatePickerDialog(TripCreation.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,startDate, currentYear,currentMonth,currentDate);
               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
               dialog.show();
           };
       });


       startDate = new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int MonthToDisplay = month ++; // month starts at 0
               startDateText.setText(dayOfMonth + "/" + MonthToDisplay + "/" + year);

               startCal.set(year,month,dayOfMonth);



           }
       };

        endDateText.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int currentYear = cal.get(Calendar.YEAR);
                int currentMonth = cal.get(Calendar.MONTH);
                int currentDate =  cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(TripCreation.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,endDate, currentYear,currentMonth,currentDate);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                dialog.show();


            }
        });


        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int displayMonth = month ++;
                endDateText.setText(dayOfMonth + "/" + displayMonth + "/" + year);
                endCal.set(year,month,dayOfMonth);


            }
        };






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

                        i = new Intent(TripCreation.this, Chats.class);
                        i.putExtra("username", username);

                        startActivity(i);

                        break;

                    case R.id.nbTrips:
                        i = new Intent(TripCreation.this, OpenTrips.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;

                    case R.id.nbhome:
                        i = new Intent(TripCreation.this, HomeScreen.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;

                    case R.id.nbCities:
                        i = new Intent(TripCreation.this, CitiesVisited.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;
                }


                return true;
            }
        });
    }
}

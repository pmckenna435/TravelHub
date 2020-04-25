package com.project.travelhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.travelhub.data.ChatMessenger;
import com.project.travelhub.data.Message;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Double.valueOf;

public class OpenChats extends AppCompatActivity implements RateUserDialog.RateUserInterface {
    RecyclerView rvUsers;
    RecyclerView.Adapter adapter;
    ArrayList<Message> messages = new ArrayList<Message>();
    Button btnSendMessage , btnRating;
    TextView userText;
    String ID;
    String username;
    String recUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_chats);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);




        Intent i = getIntent();
        final String iD = i.getStringExtra("ID");
        this.ID = iD;
        final String refToUse = i.getStringExtra("refToUse");

        username = i.getStringExtra("username");
        if(refToUse.equals("Chats")){
            recUsername = i.getStringExtra("recUsername");
            TextView displayUsername = findViewById(R.id.txtDisplayUsername);
            displayUsername.setText(recUsername);
            final DatabaseReference chatRef;
            chatRef = FirebaseDatabase.getInstance().getReference(refToUse).child(iD);
            // check if the user has been rated for this chat
            // as the user can only provide 1 rating per chat
            chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String userRated = dataSnapshot.child("ratedUser").getValue().toString();
                    String userValidForRating = dataSnapshot.child("createdUser").getValue().toString();

                    if((userRated.equals("false")) && (username.equals(userValidForRating))){

                        Button btnRate = findViewById(R.id.btnRate);
                        btnRate.setVisibility(View.VISIBLE);

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


        Toast.makeText(OpenChats.this,iD, Toast.LENGTH_SHORT).show();
        getMessagesFromDatabase(iD, refToUse);
        btnSendMessage = findViewById(R.id.btnSend);
        userText = findViewById((R.id.txtMessage));
        btnRating = findViewById(R.id.btnRate);

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creates the dialog to allow the user to rate
                RateUserDialog dialog = new RateUserDialog();
                dialog.show(getSupportFragmentManager(), "Rating");
            }
        });

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final DatabaseReference ref;

                ref = FirebaseDatabase.getInstance().getReference(refToUse).child(iD).child("messages");

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Message> myMessages = new ArrayList<Message>();
                       // messages = (ArrayList<Message>)dataSnapshot.getValue();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            String s = (String) snapshot.child("sender").getValue();
                            String text = (String) snapshot.child("text").getValue();
                            Message nextMessage = new Message(s,text);
                            myMessages.add(nextMessage);

                        }

                        if(!userText.getText().toString().equals("")){
                            String userMessage = userText.getText().toString().trim();
                            String fuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            userText.setText("");

                            final Message message = new Message(username, userMessage);
                            myMessages.add(message);


                            //DatabaseReference mDatabase;
                           // mDatabase = FirebaseDatabase.getInstance().getReference();
                           // mDatabase.child("Chats").child(chatID).setValue(cm);
                            ref.setValue(myMessages);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    } // on create

    public void getMessagesFromDatabase(String iD, String refToUse){


        DatabaseReference ref;

        ref = FirebaseDatabase.getInstance().getReference(refToUse).child(iD).child("messages");

        final Context c = this.getBaseContext();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();

               for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                   String s = (String) snapshot.child("sender").getValue();
                   String text = (String) snapshot.child("text").getValue();
                   Message nextMessage = new Message(s,text);
                   messages.add(nextMessage);

               }

               rvUsers =  findViewById(R.id.rvMessages);
               LinearLayoutManager lm = new LinearLayoutManager(getBaseContext());
               lm.setStackFromEnd(true);
               rvUsers.setLayoutManager(lm);
               adapter = new DisplayChatsAdapter(messages,username,c);
               rvUsers.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void rateUser(final double rateUser) {
        // following code adds the rating to the users total
        // as well as the amount of ratings the user has received


        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String id = snapshot.getKey();
                    String snapshotUsername = snapshot.child("username").getValue().toString();

                    if(recUsername.equals(snapshotUsername)){
                        // get current user amount
                        String amountOfRatings = (String) snapshot.child("number_of_ratings").getValue();
                       String totalRating = (String) snapshot.child("rating_total").getValue();


                       double sAmountOfRatings = Double.parseDouble(amountOfRatings);
                       double sRating = Double.parseDouble(totalRating);

                        // make the changes to the values
                        sAmountOfRatings ++;
                        //long test = (long) rateUser;
                        sRating = sRating + rateUser;


                       String sTest = String.valueOf(sAmountOfRatings);
                       String sTestTwo = String.valueOf(sRating);



                       // amountOfRatings ++;
                        //long test = (long) rateUser;
                       // totalRating = totalRating + test;
                       //double avg = testtwo/test;
                       // DecimalFormat decformat = new DecimalFormat("#.##");
                       // avg = valueOf(decformat.format(avg));


                       // Toast.makeText(OpenChats.this,"avg " + avg, Toast.LENGTH_SHORT).show();

                        // uploads the new values

                        ref.child(id).child("number_of_ratings").setValue(sTest);
                       ref.child(id).child("rating_total").setValue(sTestTwo);

                        Intent i = getIntent();
                        final String iD = i.getStringExtra("ID");

                        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chats").child(iD);

                        chatRef.child("ratedUser").setValue("true");

                        Button btnRate = findViewById(R.id.btnRate);
                        btnRate.setVisibility(View.INVISIBLE);
                        Toast.makeText(OpenChats.this,"Thank you for rating " + recUsername, Toast.LENGTH_SHORT).show();


                       // double testone = 2;
                       // double testtwo = 10;

                       // ref.child(id).child("number_of_ratings").setValue(testone);
                       // ref.child(id).child("rating_total").setValue(testtwo);




                    }



                } // for loop


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Toast.makeText(OpenChats.this,"rating " + rateUser, Toast.LENGTH_SHORT).show();


    }
}

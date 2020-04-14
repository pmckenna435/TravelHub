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

import java.util.ArrayList;

public class OpenChats extends AppCompatActivity {
    RecyclerView rvUsers;
    RecyclerView.Adapter adapter;
    ArrayList<Message> messages = new ArrayList<Message>();
    Button btnSendMessage;
    TextView userText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_chats);
        Intent i = getIntent();
        final String chatID = i.getStringExtra("ChatID");

        Toast.makeText(OpenChats.this,chatID, Toast.LENGTH_SHORT).show();
        getMessagesFromDatabase(chatID);
        btnSendMessage = findViewById(R.id.btnSend);
        userText = findViewById((R.id.txtMessage));
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Chats").child(chatID).child("messages");


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

                            final Message message = new Message(fuser, userMessage);
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

    public void getMessagesFromDatabase(String chatID){


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Chats").child(chatID).child("messages");

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





               // messages.add(new Message("userone", "this is the 1st message"));
               // messages.add(new Message("usertwo", "this is the 2nd message"));
               //messages = (ArrayList<Message>) dataSnapshot.child("messages").getValue();
               //String textTest = messages.get(0).getText();
                //Toast.makeText(OpenChats.this,textTest, Toast.LENGTH_SHORT).show();

              // RecyclerView rvUsers;
             //  RecyclerView.Adapter adapter;

              rvUsers =  findViewById(R.id.rvMessages);
              // RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());

                LinearLayoutManager lm = new LinearLayoutManager(getBaseContext());
               lm.setStackFromEnd(true);
               rvUsers.setLayoutManager(lm);

               adapter = new DisplayChatsAdapter(messages,c);
              rvUsers.setAdapter(adapter);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}

package com.project.travelhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.project.travelhub.data.User;

import java.util.ArrayList;
import java.util.List;

public class UserChat extends AppCompatActivity {
    Button btnSendMessage;
    TextView userText;
    Intent i;
   String chatID , childkey;
    ArrayList<Message> messages = new ArrayList<Message>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);
        btnSendMessage = findViewById(R.id.btnSend);
        userText = findViewById(R.id.txtMessage);


        i = getIntent();
        String e = i.getStringExtra("email");


        String fuser;
        fuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final Message message = new Message(fuser, "this is the 1st message");
        messages.add(message);
        ChatMessenger cm = new ChatMessenger(fuser,e,messages);
        final DatabaseReference mDatabase , refTest;
        DatabaseReference mRef;
        mRef = FirebaseDatabase.getInstance().getReference("Chats");
        DatabaseReference childRef = mRef.push();

        childkey = childRef.getKey();
        mRef.child(childkey).setValue(cm); // above creates chat in firebase

        // code that follows adds chat id to users chats array in firebase

        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(fuser);
        refTest = FirebaseDatabase.getInstance().getReference("Users").child(fuser);






        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() { //change to single event, have a counter and if statement
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List user_chats = new ArrayList();
                user_chats = (ArrayList) dataSnapshot.child("user_chats").getValue();
                //snapshot.child("cities_visited").getValue()
                user_chats.add(childkey);
                mDatabase.child("user_chats").setValue(user_chats);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Toast.makeText(UserChat.this,childkey, Toast.LENGTH_SHORT).show();


        btnSendMessage.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ArrayList<Message> messages = new ArrayList<Message>();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Chats").child(childkey);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            chatID = snapshot.getKey();
                           // Toast.makeText(UserChat.this,chatID, Toast.LENGTH_SHORT).show();
                            messages = (ArrayList<Message>) snapshot.child("messages").getValue();
                            //sendMessage(chatID);

                            if(!userText.getText().toString().equals("")){
                                String userMessage = userText.getText().toString().trim();
                                String fuser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                                i = getIntent();
                                String e = i.getStringExtra("email");
                                userText.setText("");

                                final Message message = new Message(fuser, userMessage);
                                messages.add(message);
                                ChatMessenger cm = new ChatMessenger(fuser,e,messages);

                                DatabaseReference mDatabase;
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("Chats").child(chatID).setValue(cm);
                            }




                        } // for
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



               // String userMessage = userText.getText().toString().trim();
               // String fuser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
               // i = getIntent();
               // String e = i.getStringExtra("email");
               // userText.setText("");

               // final Message message = new Message(fuser, userMessage);
               // messages.add(message);
               //ChatMessenger cm = new ChatMessenger(fuser,e,messages);

                //DatabaseReference mDatabase;
                //mDatabase = FirebaseDatabase.getInstance().getReference();
                //mDatabase.child("Chats").child(chatID).setValue(cm);

               // Toast.makeText(UserChat.this,chatID, Toast.LENGTH_SHORT).show();



            }
        })); // on click




    } // on create


}

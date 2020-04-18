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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.travelhub.data.User;

import java.util.ArrayList;
import java.util.List;

public class Chats extends AppCompatActivity {

    private RecyclerView rvUsers;
    Button btnSearchChat;
    private RecyclerView.Adapter adapter;
    private ArrayList openChats = new ArrayList();
    private ArrayList openChatsUsernames = new ArrayList();
    private int counter = 1;
    private boolean shouldContinue = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        getUserChatsFromDatabase();

        btnSearchChat = findViewById(R.id.btnNewChat);

        btnSearchChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Chats.this , Chat.class);
                startActivity(i);
            }
        });

    }


    public void getUserChatsFromDatabase(){

        String fuser;

        fuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        final Context c = this.getBaseContext();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(fuser);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List user_chats = new ArrayList();
                openChats.clear();
                openChatsUsernames.clear();
                user_chats = (ArrayList) dataSnapshot.child("user_chats").getValue();
               // String user = (String) dataSnapshot.child("email").getValue();
                String user = (String) FirebaseAuth.getInstance().getCurrentUser().getUid();
                getUsersFromDatabase(user_chats,user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }// get users from firebase


    public void getUsersFromDatabase(List userchats, final String user){
        final int size = userchats.size();
       // List usernames = new ArrayList();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Chats");
       final Context c = this.getBaseContext();
        for (int i = 1; i < size; i++ ){
            final String chatID = (String) userchats.get(i);

            ref = FirebaseDatabase.getInstance().getReference("Chats").child(chatID);

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int index = 1;
                    String createdUser = (String) dataSnapshot.child("createdUser").getValue();
                    //Toast.makeText(Chats.this,createdUser, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Chats.this,"test", Toast.LENGTH_SHORT).show();
                    String displayUser;
                    if (user.equals(createdUser)){
                        displayUser = (String) dataSnapshot.child("nonCreatedUser").getValue();

                    }else{
                        displayUser = (String) dataSnapshot.child("createdUser").getValue();
                    }

                    addToList(displayUser, chatID);
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


    public void addToList(String username, String ChatID){
        Toast.makeText(Chats.this,ChatID, Toast.LENGTH_SHORT).show();
        openChatsUsernames.add(username);
        openChats.add(ChatID);
    }

    public void populateRecyclerview(){
        final Context c = this.getBaseContext();


        rvUsers =  findViewById(R.id.rvOpenChats);
       // RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
       LinearLayoutManager lm = new LinearLayoutManager(getBaseContext());
       //lm.setStackFromEnd(true);
       rvUsers.setLayoutManager(lm);

       adapter = new OpenChatsAdapter(openChatsUsernames,openChats,c);

       rvUsers.setAdapter(adapter);


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

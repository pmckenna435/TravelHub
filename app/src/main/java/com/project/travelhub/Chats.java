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
           // Toast.makeText(Chats.this,"before val", Toast.LENGTH_SHORT).show();
           // Toast.makeText(Chats.this,"event", Toast.LENGTH_SHORT).show();
            ref = FirebaseDatabase.getInstance().getReference("Chats").child(chatID);

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int index = 1;
                    String createdUser = (String) dataSnapshot.child("createdUser").getValue();
                    //Toast.makeText(Chats.this,createdUser, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Chats.this,"test", Toast.LENGTH_SHORT).show();
                    String displayUser = "";
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

        }// for loop for populating the usernames list to display

        //while loop waiting on user data to be collected

       // while( shouldContinue == false){
        //    Toast.makeText(Chats.this,"while loop", Toast.LENGTH_SHORT).show();
        //}

        //Toast.makeText(Chats.this,"outside loop", Toast.LENGTH_SHORT).show();

        // next block of code diplays users in the recyclerview

        //Toast.makeText(Chats.this,String.valueOf(usernames.get(0)), Toast.LENGTH_SHORT).show();

        //List cities_visited = new ArrayList();
       // cities_visited.add("london");
        //cities_visited.add("dublin");
       // ArrayList<User> chatUsers = new ArrayList<User>();

        //chatUsers.add(new User("testemail@gmail.com", cities_visited ));
       // chatUsers.add(new User("testTwoemail@gmail.com", cities_visited));

        //rvUsers =  findViewById(R.id.rvUserList);
        //RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
        //rvUsers.setLayoutManager(lm);

        //adapter = new UserAdapter(chatUsers,c);
        //rvUsers.setAdapter(adapter);




    } // display users


    public void addToList(String username, String ChatID){
        Toast.makeText(Chats.this,ChatID, Toast.LENGTH_SHORT).show();
        openChatsUsernames.add(username);
        openChats.add(ChatID);
    }

    public void populateRecyclerview(){
        final Context c = this.getBaseContext();


       // Toast.makeText(Chats.this,"into the populate", Toast.LENGTH_SHORT).show();



       /*ArrayList chatUsers = new ArrayList();
       ArrayList chatcodes = new ArrayList();

       chatUsers.add("testemail@gmail.com");
       chatUsers.add("two@gmail.com");
       chatcodes.add("qwertyuiop");
       chatcodes.add("asdfghjkla"); */

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

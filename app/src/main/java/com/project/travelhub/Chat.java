package com.project.travelhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.project.travelhub.data.User;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    private RecyclerView rvUsers;
    private RecyclerView.Adapter adapter;
    private ArrayList<User> chatUsers = new ArrayList<User>();

    //private ArrayList<User> chatUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        List cities_visited = new ArrayList();
        //cities_visited.add("london");
        //cities_visited.add("dublin");
        //rvUsers = findViewById(R.id.rvUserList);
        //rvUsers.setHasFixedSize(true);

        //getUsersFromDatabase();

        final Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsersFromDatabase();
            }
        });

        //chatUsers.add(new User("testemail@gmail.com", cities_visited ));
        //chatUsers.add(new User("testTwoemail@gmail.com", cities_visited));

        //rvUsers =  findViewById(R.id.rvUserList);
        //RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        //this.rvUsers.setLayoutManager(lm);

       // adapter = new UserAdapter(chatUsers);
        //this.rvUsers.setAdapter(adapter);
    } // on create

    public boolean searchArray(ArrayList<String> cities ){
        EditText DesiredCityTxt = findViewById(R.id.txtCity);
        String DesiredCity = DesiredCityTxt.getText().toString().trim();

        for (String city : cities){
            if (city.equals(DesiredCity)){
                return true;
            }
        }

        return false;
    }//search array

    private void getUsersFromDatabase() {

        // FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        final Context c = this.getBaseContext();



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatUsers.clear();
                ArrayList<String> userID = new ArrayList<String>();


                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String username = snapshot.child("username").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    ArrayList cities_visited = new ArrayList();
                    cities_visited = (ArrayList) snapshot.child("cities_visited").getValue();
                    boolean shouldAdd = false;

                    String currentUser;

                    currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String snapshotUser = snapshot.getKey();

                    if(!currentUser.equals(snapshotUser)) {
                        shouldAdd = searchArray(cities_visited);
                    }


                    if(shouldAdd){
                        User user = new User(email,cities_visited,username);
                        chatUsers.add(user);
                        userID.add(snapshotUser);

                    }

                   // Toast.makeText(Chat.this,String.valueOf(cities_visited[0]), Toast.LENGTH_SHORT).show();

                    //GenericTypeIndicator<User> g = new GenericTypeIndicator<User>() { };


                   //User user = snapshot.getValue(g);

                }

                rvUsers =  findViewById(R.id.rvUserList);
                RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
                rvUsers.setLayoutManager(lm);

                adapter = new UserAdapter(chatUsers,userID ,c);
                rvUsers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }//get user from database



}

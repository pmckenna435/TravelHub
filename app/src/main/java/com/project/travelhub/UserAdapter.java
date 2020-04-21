package com.project.travelhub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private String currentUser;
    private ArrayList<User> users;
    private Context context;
    private ArrayList<String> usersID;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       final User nextUser = users.get(position);
       final String nextUserID = usersID.get(position);
       holder.username.setText(nextUser.getUsername());


       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               final String chatID ;
               //childkey;
               String fuser;
               fuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
               final Message message = new Message(currentUser, "this is the 1st message");
               ArrayList<Message> messages = new ArrayList<Message>();
               messages.add(message);
               ChatMessenger cm = new ChatMessenger(currentUser,nextUser.getUsername(),messages);
               final DatabaseReference currentUserRef ;
               final DatabaseReference otherUserRef;
               DatabaseReference mRef;
               mRef = FirebaseDatabase.getInstance().getReference("Chats");
               DatabaseReference childRef = mRef.push();

               chatID = childRef.getKey();
               mRef.child(chatID).setValue(cm);

               // The purpose of the above code is to create the chat in firebase "Chats" table


               //The code that will follow will add the chat id to the created user and non created user in the "Users" table

               currentUserRef = FirebaseDatabase.getInstance().getReference("Users").child(fuser);

               currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() { //change to single event, have a counter and if statement
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       List user_chats = new ArrayList();
                       user_chats = (ArrayList) dataSnapshot.child("user_chats").getValue();
                       //snapshot.child("cities_visited").getValue()
                       user_chats.add(chatID);
                       currentUserRef.child("user_chats").setValue(user_chats);

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });

               // Above code makes the change in the current user (created user)


               otherUserRef = FirebaseDatabase.getInstance().getReference("Users").child(nextUserID);

               otherUserRef.addListenerForSingleValueEvent(new ValueEventListener() { //change to single event, have a counter and if statement
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       List user_chats = new ArrayList();
                       user_chats = (ArrayList) dataSnapshot.child("user_chats").getValue();
                       //snapshot.child("cities_visited").getValue()
                       user_chats.add(chatID);
                       otherUserRef.child("user_chats").setValue(user_chats);

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });


               // code above adds the chat id to the other user so they can now see the newly created chat

              Intent  i  = new Intent(context, OpenChats.class );

              i.putExtra("ID", chatID);
              i.putExtra("refToUse", "Chats");
              i.putExtra("username", currentUser);
              i.putExtra("otherUser" , "");
              context.startActivity(i);


           }
       });

    }

    @Override
    public int getItemCount() {
        int size = 0;
        size = users.size();

        return size;
    }

    public UserAdapter(ArrayList<User> users, ArrayList<String> usersID,String currentUsername, Context c){
        this .context = c;
        this.users= users;
        this.usersID = usersID;
        this.currentUser = currentUsername;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final View view;
        public final TextView username;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            username = view.findViewById(R.id.txtUserEmail);
        }

    }


}



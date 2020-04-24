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
import java.text.DecimalFormat;
import static java.lang.Double.valueOf;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private String currentUser ;
    private String city;
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

       String tempTotal = nextUser.getRating_total();
       String tempAmount = nextUser.getNumber_of_ratings();

       double tempDoubleTotal = Double.parseDouble(tempTotal);
       double tempDoubleAmount = Double.parseDouble(tempAmount);

        double avg = tempDoubleTotal/tempDoubleAmount;
         DecimalFormat decFormat = new DecimalFormat("#.##");
         avg = valueOf(decFormat.format(avg));



       holder.rating.setText("Rating: " + avg);
       holder.username.setText(nextUser.getUsername());


       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               final String chatID ;
               //childkey;
               String fuser;
               fuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
               User start = new User();
               start.setUsername("start");
               final Message message = new Message();
               ArrayList<Message> messages = new ArrayList<Message>();
               messages.add(message);
               ChatMessenger cm = new ChatMessenger(city ,currentUser,nextUser.getUsername(),messages);
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
               i.putExtra("recUsername", nextUser.getUsername());
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

    public UserAdapter(ArrayList<User> users, ArrayList<String> usersID,String currentUsername,String city, Context c){
        this .context = c;
        this.users= users;
        this.usersID = usersID;
        this.currentUser = currentUsername;
        this.city = city;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final View view;
        public final TextView username;
        public final TextView rating;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            username = view.findViewById(R.id.txtUserEmail);
            rating = view.findViewById(R.id.txtRateOrCity);
        }

    }


}



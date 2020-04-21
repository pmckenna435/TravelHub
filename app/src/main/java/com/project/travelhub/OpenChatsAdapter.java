package com.project.travelhub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.travelhub.data.User;

import java.util.ArrayList;
import java.util.List;

public class OpenChatsAdapter extends RecyclerView.Adapter<OpenChatsAdapter.ViewHolder> {
    private ArrayList chats;
    private ArrayList users;
    private Context context;
    private String username;
    //private List<User> users;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String nextUser = (String) users.get(position);
        final String nextChat = (String) chats.get(position);





        holder.userEmail.setText(nextUser);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  i  = new Intent(context, OpenChats.class );
                i.putExtra("username", username);
                i.putExtra("ID", nextChat);
                i.putExtra("refToUse" , "Chats");
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

    public OpenChatsAdapter(ArrayList users, ArrayList chats,String username, Context c){
        this .context = c;
        this.chats = chats;
        this.users = users;
        this.username = username;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final View view;
        public final TextView userEmail;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            userEmail = view.findViewById(R.id.txtUserEmail);
        }

    }


}//



package com.project.travelhub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.travelhub.data.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<User> users;
    private Context context;
    //private List<User> users;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       final User nextUser = users.get(position);
       holder.username.setText(nextUser.getUsername());


       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

              Intent  i  = new Intent(context, UserChat.class );
              i.putExtra("email", nextUser.getUsername());
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

    public UserAdapter(ArrayList<User> users, Context c){
        this .context = c;
        this.users= users;
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



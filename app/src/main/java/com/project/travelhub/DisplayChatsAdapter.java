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
import com.project.travelhub.data.Message;
import com.project.travelhub.data.User;

import java.util.ArrayList;
import java.util.List;

public class DisplayChatsAdapter extends RecyclerView.Adapter<DisplayChatsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Message> messages = new ArrayList<Message>();
    private  String usename;
    //private List<User> users;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sent_text, parent, false);
            return new ViewHolder(v);
        }else if (viewType == 2){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_start, parent, false);
            return new ViewHolder(v);
        }else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_received_text, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Message nextMessage = messages.get(position);
        String nextText = nextMessage.getText();

        holder.messageToDisplay.setText(nextText);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }// on bind view holder

    @Override
    public int getItemCount() {
        int size = 0;
        size = messages.size();

        return size;
    }

    public DisplayChatsAdapter(ArrayList<Message> messages, String username, Context c){
        this.context = c;
        this.messages = messages;
        this.usename = username;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final View view;
        public final TextView messageToDisplay;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            messageToDisplay = view.findViewById(R.id.txtSentMessage);
        }

    }

    @Override
    public int getItemViewType(int position) {
        //String fuser;

       // fuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(messages.get(position).getSender().equals(usename)){
            return 1;
        } else if(messages.get(position).getSender().equals("start")){
            return 2;
        }else{
            return 0;
        }



    }
}//



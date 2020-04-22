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

public class OpenTripsAdapter extends RecyclerView.Adapter<OpenTripsAdapter.ViewHolder> {
    private ArrayList tripIDs;
    private ArrayList tripnames;
    private Context context;
    private String username;
    //private List<User> users;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_display_trip,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String nextTripname = (String) tripnames.get(position);
        final String nextTripID = (String) tripIDs.get(position);
        holder.txttripname.setText(nextTripname);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  i  = new Intent(context, TripHomepage.class );
                i.putExtra("tripname", nextTripname);
                i.putExtra("trip_id", nextTripID);
                i.putExtra("username" , username);
                context.startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        int size = 0;
        size = tripIDs.size();

        return size;
    }

    public OpenTripsAdapter(ArrayList IDs, ArrayList names, String username, Context c){
        this .context = c;
        this.tripIDs = names;
        this.tripnames = IDs;
        this.username = username;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final View view;
        public final TextView txttripname;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            txttripname = view.findViewById(R.id.txtTripName);
        }

    }


}//



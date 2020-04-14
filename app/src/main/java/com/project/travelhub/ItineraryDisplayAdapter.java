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

public class ItineraryDisplayAdapter extends RecyclerView.Adapter<ItineraryDisplayAdapter.ViewHolder> {

    private ArrayList<ItineraryDay> itinerary;
    private Context context;
    private String tripID;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_display_days,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItineraryDisplayAdapter.ViewHolder holder, final int position) {
        ItineraryDay nextDay = itinerary.get(position);
        String activities = nextDay.getActivity();
        int day = nextDay.getDay();
        int month = nextDay.getMonth();
        month ++; // jan is 0 so month is 1 value behind
        int year = nextDay.getYear();
        String mDate = day + "/" + month + "/" + year;

        holder.txtActivity.setText(activities);
        holder.aDate.setText(mDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i  = new Intent(context, ItineraryEdit.class );
                i.putExtra("position", position);
                i.putExtra("trip", tripID);
                context.startActivity(i);


            }
        });



    }

    public ItineraryDisplayAdapter(ArrayList it, Context c, String trip){
        this.context = c;
        this.itinerary = it;
        this.tripID = trip;
    }

    @Override
    public int getItemCount() {
        int size = 0;
        size = itinerary.size();
        return size;

    }




    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final View view;
        public final TextView aDate;
        public final TextView txtActivity;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            aDate = view.findViewById(R.id.txtdate);
            txtActivity = view.findViewById(R.id.txtActivity);
        }

    }
}

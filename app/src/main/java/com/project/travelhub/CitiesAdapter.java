package com.project.travelhub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private ArrayList<String> cities;
    private Context context;

    public CitiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cities,parent,false);
        return new CitiesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesAdapter.ViewHolder holder, final int position) {
        String nextCity = cities.get(position);

        holder.city.setText(nextCity);


    }

    public CitiesAdapter(ArrayList cities, Context c){
        this.cities = cities;
        this.context = c;

    }

    @Override
    public int getItemCount() {
        int size = 0;
        size = cities.size();
        return size;

    }




    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final View view;
        public final TextView city;


        public ViewHolder(View view){
            super(view);
            this.view = view;
            city = view.findViewById(R.id.txtCity);

        }

    }




}

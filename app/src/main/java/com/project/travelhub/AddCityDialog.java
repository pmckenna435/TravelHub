package com.project.travelhub;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddCityDialog extends AppCompatDialogFragment {


    private EditText txtCity;
    private AddCityDialog.AddCityInterface addCityInterface;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater i  = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.layout_add_city_dialog, null);

        builder.setView(view);
        builder.setTitle("Add City");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String city = txtCity.getText().toString();
                addCityInterface.addCity(city);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        txtCity = view.findViewById(R.id.city);
        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        addCityInterface = (AddCityDialog.AddCityInterface) context;
    }

    public interface AddCityInterface{
        public void addCity(String city);

    }
}

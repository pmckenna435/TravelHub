package com.project.travelhub;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class RateUserDialog extends AppCompatDialogFragment {


    private RatingBar userRatingBar;
    private RateUserDialog.RateUserInterface rateUserInterface;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater i  = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.layout_rate_users, null);

        builder.setView(view);
        builder.setTitle("Rate User");
        builder.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                // gets user rating and calls method to add the rating to the user in firebase which is in openchats

                double rating = (double) userRatingBar.getRating();

                rateUserInterface.rateUser(rating);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });


        userRatingBar = view.findViewById(R.id.rbRateUser);
        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        rateUserInterface = (RateUserDialog.RateUserInterface) context;
    }

    public interface RateUserInterface{
        public void rateUser(double rateUser);

    }
}

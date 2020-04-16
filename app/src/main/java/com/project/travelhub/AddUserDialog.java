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

public class AddUserDialog extends AppCompatDialogFragment {

    private EditText txtusername;
    private AddUserInterface addUserInterface;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater i  = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.layout_add_user_dialog, null);

        builder.setView(view);
        builder.setTitle("Add User");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = txtusername.getText().toString();
                addUserInterface.addUser(username);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        txtusername = view.findViewById(R.id.username);
        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        addUserInterface = (AddUserInterface) context;
    }

    public interface AddUserInterface{
        public void addUser(String username);

    }
}

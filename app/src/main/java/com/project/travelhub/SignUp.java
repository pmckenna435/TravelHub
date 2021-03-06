package com.project.travelhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.travelhub.data.User;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity {

    EditText password, email, city , username;
    Button btnCreateUser , btnClear;
    FirebaseAuth fAuth;
    List cities = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.txtUserEmail);
        password = findViewById(R.id.txtPassword);
        city = findViewById(R.id.txtCity);
        username = findViewById(R.id.txtUsername);
        btnCreateUser = findViewById(R.id.btnCreate);
       btnClear = findViewById(R.id.btnClear);


        fAuth = FirebaseAuth.getInstance();



        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password.setText("");
                username.setText("");
                email.setText("");
                city.setText("");
                Toast.makeText(SignUp.this,"Data Cleared", Toast.LENGTH_SHORT).show();

            }
        });



        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                final String userUsername = username.getText().toString().trim();
                String cityToAdd = city.getText().toString().trim();
                cities.add(cityToAdd);


                fAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignUp.this,"Account Created", Toast.LENGTH_SHORT).show();
                            String userid = fAuth.getCurrentUser().getUid();
                            User user = new User(userEmail,cities,userUsername);
                            // populate user info table
                            DatabaseReference mDatabase;
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("Users").child(userid).setValue(user);

                            Intent i = new Intent(SignUp.this , HomeScreen.class);

                            i.putExtra("username" , userUsername);
                            i.putExtra("total" , "0");
                            i.putExtra("amount" , "0");

                            startActivity(i);
                        } else{
                            Toast.makeText(SignUp.this,"Failed to Create!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });





    }
}

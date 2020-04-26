package com.project.travelhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.travelhub.data.User;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button Login;
    TextView tvCreateAccount;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        fAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.txtUserEmail);
        password = findViewById(R.id.txtPassword);
        Login = findViewById(R.id.btnLogin);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                if(userEmail.isEmpty()){
                    email.setError("Please enter an email address");
                }else if(userPassword.isEmpty()){
                    password.setError("Please enter password");
                } else {

                    fAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isSuccessful()) {

                            //  DatabaseReference mDatabase;
                             // mDatabase = FirebaseDatabase.getInstance().getReference();


                              String fuser;
                              fuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                              DatabaseReference mDatabase;
                              mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(fuser);

                              mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                      String username = dataSnapshot.child("username").getValue().toString();
                                      String total = dataSnapshot.child("rating_total").getValue().toString();
                                      String amount = dataSnapshot.child("number_of_ratings").getValue().toString();


                                      Intent i = new Intent(Login.this, HomeScreen.class);
                                      i.putExtra("username" , username);
                                      i.putExtra("total" , total);
                                      i.putExtra("amount" , amount);
                                      startActivity(i);



                                  }

                                  @Override
                                  public void onCancelled(@NonNull DatabaseError databaseError) {

                                  }
                              });





                              //Intent i = new Intent(Login.this, HomeScreen.class);
                              //startActivity(i);
                          }else{

                          }
                        }
                    });
                }// else


            }
        });





    }




}

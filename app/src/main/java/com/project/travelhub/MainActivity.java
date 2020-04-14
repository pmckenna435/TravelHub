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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       Button btnLogin =  findViewById(R.id.btnLogin);
       Button btnCreateAccount =  findViewById(R.id.btnCreateAccount);

       btnLogin.setOnClickListener(new View.OnClickListener(){
           @Override
          public void onClick(View view) {
              Intent i = new Intent(MainActivity.this , Login.class);
              startActivity(i);
           }

        });

       btnCreateAccount.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(MainActivity.this , SignUp.class);
               startActivity(i);


           }
       });



   }





}

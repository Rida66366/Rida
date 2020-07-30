package com.example.cts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
        EditText edittext;
        EditText password_;
        Button button1;
    Button signup;


    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edittext=(EditText) findViewById(R.id.edittext);
        password_=(EditText) findViewById(R.id.password_);
        button1=(Button) findViewById(R.id.button1);
        signup=(Button) findViewById(R.id.signup);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String email = edittext.getText().toString();
//                String pass = password_.getText().toString();
                String email = "ridabaig39@gmail.com";
                String pass = "12345678";

                Log.d("TAG112", edittext + "/" + password_);
                if (!edittext.equals("") && !password_.equals("")) {

                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                String id = auth.getUid();

                                mRef.child("Users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Log.d("TAG101", "" + dataSnapshot);
                                        String role = dataSnapshot.child("role").getValue().toString();
                                        Log.d("TAG101", "" + role);
                                        if (role.equals("admin")) {
                                            Intent intent = new Intent(Login.this, Feedback.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                         else {
                                            Intent intent = new Intent(Login.this, UserFeedback.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                            else
                            {
                                Toast.makeText(Login.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(Login.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }

        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this,Registration.class);
                startActivity(intent);
            }
        });
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Login.this, UserFeedback.class);
//                startActivity(intent);
//
//            }
//
//        });
    }}





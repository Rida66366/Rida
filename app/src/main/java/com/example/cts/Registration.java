package com.example.cts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    EditText EditTextemail;
    EditText edittextpassword;
    EditText EditTextUserName;
    EditText address;
    Button Signup;
    Button Login;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference("User");
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edittextpassword = (EditText) findViewById(R.id.edittextpassword);
        EditTextUserName = (EditText) findViewById(R.id.EditTextUserName);
        EditTextemail = (EditText) findViewById(R.id.EditTextemail);
        address = (EditText) findViewById(R.id.address);
        Signup = (Button) findViewById(R.id.Signup);
        Login = (Button) findViewById(R.id.Login);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = EditTextemail.getText().toString();
                String pass  = edittextpassword.getText().toString();

                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            //TODO For checking auth
                            if(auth != null)
                            {
                                String user_id = auth.getUid();
                                String drivername = EditTextUserName.getText().toString();
                                String mob = address.getText().toString();


                                //  AdminModel admin = new AdminModel(driverName);
                                UserModel user = new UserModel(drivername,email,mob,user_id,"driver",false,false);
                                //  mRef.child(user_id).setValue(admin);
                                mRef.child(user_id).setValue(user);

                            }
                            else
                            {
                                Toast.makeText(Registration.this, "Something Went Wrong..", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(Registration.this, "User Created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registration.this,DriverDashboard.class);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(Registration.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//        Intent login = new Intent(Registration.this,MainActivity.class);
//        startActivity(login);

                Login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent signup= new Intent(Registration.this,Login.class);
                    }
                });
            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, UserFeedback.class);
                startActivity(intent);

            }

        });
    }}











package com.example.cts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.internal.FederatedSignInActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Feedback extends AppCompatActivity {

    TextView urFeedback;
    Button button3;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = firebaseDatabase.getReference("feedback");
    RecyclerView recyclerView;
    ArrayList<FeedbackModel> feedbackModels = new ArrayList<>();
    Adapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        recyclerView = findViewById(R.id.feedback_list);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getApplicationContext(), ""+snapshot, Toast.LENGTH_LONG).show();
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    for(DataSnapshot s : snap.getChildren())
                    {
                        Toast.makeText(Feedback.this, ""+s, Toast.LENGTH_LONG).show();
                       // Toast.makeText(Feedback.this, ""+s.child("email").getValue(), Toast.LENGTH_LONG).show();
                        FeedbackModel feedbackModel = s.getValue(FeedbackModel.class);
                        feedbackModels.add(feedbackModel);
                    }
                }
                adapter = new Adapter(Feedback.this,feedbackModels);
                recyclerView.setLayoutManager(new LinearLayoutManager(Feedback.this));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

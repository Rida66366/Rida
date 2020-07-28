package com.example.cts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Feedback extends AppCompatActivity {

    TextView urFeedback;
    Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        urFeedback=(TextView)findViewById(R.id.urFeedback);
        button3=(Button) findViewById(R.id.button3);


    }
}

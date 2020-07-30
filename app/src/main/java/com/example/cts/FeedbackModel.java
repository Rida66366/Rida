package com.example.cts;

import android.net.Uri;

public class FeedbackModel {

    public String email;
    public String subject;
    public String message;
    public String imageFileName;

    public FeedbackModel(String email, String subject, String message,String imageFileName) {
        this.email = email;
        this.subject = subject;
        this.message = message;
    this.imageFileName  = imageFileName;
    }

    public FeedbackModel() {
    }
}

package com.example.cts;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<holder> {
    private Context context;
    ArrayList<FeedbackModel> feedbackList = new ArrayList<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();


    public Adapter(Context context, ArrayList<FeedbackModel> feedbackList) {
        this.context = context;
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.feedback_item,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final holder holder, int position) {
        holder.subject.setText(feedbackList.get(position).email);
        holder.feedback.setText(feedbackList.get(position).message);
        String imageName = feedbackList.get(position).imageFileName;
        StorageReference image = storageReference.child("pictures/" + imageName);
        image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.imageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }
}

class holder extends RecyclerView.ViewHolder{
    TextView subject,feedback;
    ImageView imageView;
    public holder(@NonNull View itemView) {
        super(itemView);
        subject =itemView.findViewById(R.id.txtName);
        feedback = itemView.findViewById(R.id.txtFeedback);
        imageView = itemView.findViewById(R.id.image_cus);
    }
}

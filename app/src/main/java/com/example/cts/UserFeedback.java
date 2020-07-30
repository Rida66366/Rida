package com.example.cts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UserFeedback extends AppCompatActivity {

    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQ_CODE = 105;

    EditText writeEmail,subject_,message;

    Button send,camera,galleryButton;
    ImageView selectedImage;
    String currentPhotoPath;

    Firebase firebase;

    private Uri fileData;
    String imageFileName;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference mRef = firebaseDatabase.getReference();
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback);

        writeEmail = findViewById(R.id.writeEmail);
        subject_ = findViewById(R.id.subject_);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);
        camera = findViewById(R.id.camera);
        selectedImage = findViewById(R.id.displayImage);
       // storageReference = FirebaseStorage.getInstance().getReference();
        galleryButton = findViewById(R.id.galleryButton);
        Firebase.setAndroidContext(this);

        userId = auth.getCurrentUser().getUid();


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                uploadImageToFirebase(imageFileName,fileData);





//                Firebase child_email = firebase.child("Email");
//                child_email.setValue(email);
//                if (email.isEmpty()) {
//                    writeEmail.setError("this is a required field");
//                    send.setEnabled(false);
//
//                } else {
//                    writeEmail.setError(null);
//                    send.setEnabled(true);
//                }
//
//                Firebase child_subject= firebase.child("Subject");
//                child_subject.setValue(subject);
//                if (subject.isEmpty()){
//                    subject_.setError("this is a required field");
//                    send.setEnabled(false);
//
//                }else
//                {
//                    subject_.setError(null);
//                    send.setEnabled(false);
//                }
//
//                Firebase child_message1=firebase.child("Message");
//                child_message1.setValue("this is a required field");
//                if (message1.isEmpty()){
//                    message.setError("this is a required field");
//                    send.setEnabled(false);
//                }else
//                {
//                    message.setError(null);
//                    send.setEnabled(false);
//                }
//


            }


            // yahann say del kia code asas
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();


                Toast.makeText(UserFeedback.this, "Camera is Clicked", Toast.LENGTH_LONG).show();
            }
        });
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserFeedback.this, "Gallery is Clicked", Toast.LENGTH_LONG).show();

                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQ_CODE);
            }
        });
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            dispatchTakePictureIntent();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission is required to use", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                File f = new File(currentPhotoPath);
                //selectedImage.setImageURI(Uri.fromFile(f));
                Log.d("tag", "ABsolute Url of Image is " + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                fileData = contentUri;
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                imageFileName = f.getName();

              //  uploadImageToFirebase(f.getName(),contentUri);

            }
        }
        if(requestCode == GALLERY_REQ_CODE){
            if(resultCode == Activity.RESULT_OK){
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                imageFileName = "JPEG_" + timeStamp +"."+getFileExt(contentUri);
                Log.d("tag", "onActivityResult: Gallery Image Uri:  " +  imageFileName);
                fileData = contentUri;
                selectedImage.setImageURI(contentUri);
             //   uploadImageToFirebase(imageFileName,contentUri);
    }
        }
    }

    private void uploadImageToFirebase(String name, Uri contentUri) {
        final StorageReference image = storageReference.child("pictures/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    Picasso.get().load(uri).into(selectedImage);


                       // Log.d("tag", "onSuccess: Uploaded Image URl is " + uri.toString());
                    }

                });
                String email = writeEmail.getText().toString();
                String subject = subject_.getText().toString();
                String message1 = message.getText().toString();
                FeedbackModel feedback = new FeedbackModel(email,subject,message1,imageFileName);

                DatabaseReference model = mRef.child("feedback").child(userId).push();
                model.setValue(feedback);
                Toast.makeText(UserFeedback.this, "Image Is Uploaded.", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserFeedback.this, "Upload Failled.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }





    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();


        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.cts.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
    }




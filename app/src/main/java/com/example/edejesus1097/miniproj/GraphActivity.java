package com.example.edejesus1097.miniproj;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class GraphActivity extends Activity {

    private final String TAG= "GraphActivity";
    static FirebaseAuth mAuth;
    static FirebaseUser user;
    static File localFile;
    static Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth= FirebaseAuth.getInstance();
        String filename = getIntent().getStringExtra("filename").trim();
        user = mAuth.getCurrentUser();
        setContentView(R.layout.activity_graph);
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://miniproj-7246c.appspot.com/");
        StorageReference pathReference = storageRef.child("users/" + user.getUid() + "/" + filename.trim());

        try {
            localFile = File.createTempFile("graph", "csv");
        }
        catch (IOException ie)
        {
            Toast.makeText(GraphActivity.this,"Can't Download file", Toast.LENGTH_LONG).show();
        }
        //local location of file
        uri = Uri.parse(localFile.toURI().toString());

        pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                Toast.makeText(GraphActivity.this, "Works:"+uri.toString(), Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


    }

}
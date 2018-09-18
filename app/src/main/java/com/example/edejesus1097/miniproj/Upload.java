package com.example.edejesus1097.miniproj;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.*;
import android.content.Intent;
import android.support.annotation.*;
import android.widget.Toast;
import android.net.*;


import com.google.android.gms.tasks.*;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.*;
import com.google.firebase.*;
import com.google.firebase.auth.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.*;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.auth.account.*;
public class Upload extends Activity{
    private static final int READ_REQUEST_CODE = 42;
    private static final String TAG = "Upload";
    static GoogleSignInAccount account;
    static FirebaseAuth mAuth;
    static FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        // Create a storage reference from our app
        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account ==null){
            Toast.makeText(this, "wrong", Toast.LENGTH_SHORT).show();
        }
        mAuth =  FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user == null) {
            finish();
        }

        performFileSearch();
    }
/**
 * Fires an intent to spin up the "file chooser" UI and select an image.
 */
        public void performFileSearch() {

            // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
            // browser.
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

            // Filter to only show results that can be "opened", such as a
            // file (as opposed to a list of contacts or timezones)
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            // Filter to show only images, using the image MIME data type.
            // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
            // To search for all documents available via installed storage providers,
            // it would be "*/*".
            intent.setType("text/plain");

            startActivityForResult(intent, READ_REQUEST_CODE);
        }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account == null)
        {
            finish();
        }
        //updateUI(account);

    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            final Uri uri = resultData.getData();
            if (resultData != null) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference csvRef = storageRef.child("users/"+user.getUid()+"/"+uri.getLastPathSegment());
                UploadTask uploadTask = csvRef.putFile(uri);

// Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();
                        //setting path in database for building list to show in List.java
                        myRef.child("users").child(user.getUid()).child(uri.getLastPathSegment()).setValue("users/"+user.getUid()+"/"+uri.getLastPathSegment());
                        Toast.makeText(Upload.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.i(TAG, "Uri: " + uri.toString());
            }
        }
    }
}


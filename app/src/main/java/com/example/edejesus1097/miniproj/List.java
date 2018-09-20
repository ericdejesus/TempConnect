package com.example.edejesus1097.miniproj;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.common.images.internal.PostProcessedResourceCache;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class List extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference myRef;
    static String string;
    static Button button;
    ArrayList<String> names;
    private static final String TAG = "List";
    static FirebaseDatabase database;
    static LinearLayout linearLayout;
    static NestedScrollView scrollView;
    static ScrollView scrollView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //have to recast view since the buttons don't have ids
                Intent intent = new Intent(List.this, GraphActivity.class);
                startActivity(intent);
        }});


        //these are for adding buttons dynamically based on count of files
        scrollView = findViewById(R.id.nestedscrollview);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        //these are used to access the data as well as find which user it is.
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        //pointing reference to the user's folder
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users/" + user.getUid() + "/");

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG, "onDataChange: " + dataSnapshot.getValue());
                string = dataSnapshot.getValue().toString();

                int count = 0;
                for (int i = 0; i < string.length(); i++) {
                    if (string.charAt(i) == '=') {
                        count++;
                    }
                }

                //creates a list of strings for the buttons off of file names
                String[] stringList = new String[count];
                int listitr = 0;
                for (int i = 0; i < string.length(); i++) {
                    if (listitr == count) break;
                    else if (string.charAt(i) == ',') {
                        stringList[listitr] = "";
                    } else if (string.charAt(i) == '=') {
                        listitr++;
                    } else if (i == 0) {
                        stringList[listitr] = "";
                    } else {
                        stringList[listitr] += string.charAt(i);
                    }
                }


                for (int i = 0; i < count; i++) {
                    button = new Button(List.this);
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //have to recast view since the buttons don't have ids
                            Button button = (Button) v;

                            //puts which file to download and access
                            Intent intent = new Intent(List.this, GraphActivity.class);
                            intent.putExtra("filename", button.getText());
                            Log.d(TAG, "onClick: " + button.getText());
                            startActivity(intent);
                        }
                    });

                    //adds title to the buttons
                    button.setText(stringList[i]);
                    linearLayout.addView(button);
                }
                setContentView(R.layout.activity_list);
                //adds the buttons to the view
             scrollView1 =findViewById(R.id.scrollview);
                scrollView1.addView(linearLayout);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //for errors
            }
        });

    }


}

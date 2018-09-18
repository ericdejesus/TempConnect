package com.example.edejesus1097.miniproj;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
    //static String[] stringList;
    ArrayList<String> names;
    private static final String TAG = "List";
    static FirebaseDatabase database;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth =  FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users/"+user.getUid()+"/");

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG, "onDataChange: "+ dataSnapshot.getValue());
                string = dataSnapshot.getValue().toString();

                int count = 0;
                for(int i =0; i<string.length();i++)
                {
                    if(string.charAt(i) == '=')
                    {
                        count++;
                    }
                }

                String[] stringList= new String[count];

                int listitr =0;
                for( int i=0; i<string.length() ;i++)
                {
                    if(string.charAt(i) == '=')
                    {
                        stringList[listitr] = "";
                    }
                    else if(string.charAt(i) == ',')
                    {
                        listitr++;
                    }
                    else
                    {
                        stringList[listitr] += string.charAt(i);
                    }
                }
                string ="";
                for(int i =0; i<count;i++)
                {
                    string+=stringList[i]+" ";
                }
                tv.setText(string);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
            //myRef.addValueEventListener(postListener);

        tv = findViewById(R.id.Listtext);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, string, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}

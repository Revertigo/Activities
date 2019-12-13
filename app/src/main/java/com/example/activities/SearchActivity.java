package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.activities.data.rtdb.activity.Activity;
import com.example.activities.data.rtdb.activity.ShowActivities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.example.activities.PostActivity.activities;
import static com.google.firebase.auth.FirebaseAuth.getInstance;
import static java.lang.Thread.sleep;

public class SearchActivity extends AppCompatActivity {

    private Button buttonLogout;
    private Button closeAppFromSearch;
    private Button searchByString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchByString=findViewById(R.id.btnSearchByString);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
       final String path="activities";
       final DatabaseReference ref = database.getReference(path);


       searchByString.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               ref.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       Intent intent=new Intent(SearchActivity.this, ShowActivities.class);

                       //get current activity from database

                        Activity  currentActivity =dataSnapshot.child("Activity_"+Activity.getId_counter()).getValue(Activity.class);

                        //create array of strings and send it to next intent
                        String[] str={Long.toString(currentActivity.getId()), currentActivity.getName(),currentActivity.getType(),
                                currentActivity.getAddr().getCity_set(),
                                currentActivity.getAddr().getStreet(),
                                Integer.toString(currentActivity.getAddr().getApartment_number()),currentActivity.getDifficulty(),
                                currentActivity.getGender(), currentActivity.getDescription(),
                                new SimpleDateFormat("MM/dd/yyyy").format(currentActivity.getDate()), currentActivity.getTime(),
                                String.valueOf(currentActivity.isGroup())};

                        //send the string array to the next activity
                       intent.putExtra("str", str);
                       startActivity(intent);
                   }
                   @Override
                   public void onCancelled(DatabaseError databaseError) {
                   }
               });

           }

       });


        //close app button
        closeAppFromSearch = findViewById(R.id.closeAppSearchActivity);
        closeAppFromSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);

            }
        });

        //log out button
        buttonLogout = findViewById(R.id.logoutSearchActivity);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInstance().signOut();
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}

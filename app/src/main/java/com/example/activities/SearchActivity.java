package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.SearchView;

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

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import static com.example.activities.PostActivity.activities;
import static com.google.firebase.auth.FirebaseAuth.getInstance;
import static java.lang.Thread.sleep;

public class SearchActivity extends AppCompatActivity {


    private static DatabaseReference mDatabase = null;

    private Button buttonLogout;
    private Button closeAppFromSearch;
    private Button searchByString;
    private SearchView search_by_string;


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
        closeAppFromSearch = findViewById(R.id.closeBtn);
        closeAppFromSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);

            }
        });

        //log out button
        buttonLogout = findViewById(R.id.logoutBtn);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInstance().signOut();
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        search_by_string = findViewById(R.id.searchByString);
        //Search via soft keyboard key
        search_by_string.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mDatabase = FirebaseDatabase.getInstance().getReference(PostActivity.activities);
                Query search_result = mDatabase.orderByChild("name").equalTo(query);
                search_result.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        Log.wtf("The object is:", dataSnapshot.toString());

                            Activity act = dataSnapshot.getValue(Activity.class) ;
                            Log.wtf("Activity.name ",act.getName());
                            Log.wtf("Activity.type ",act.getType());
                            Log.wtf("The value is ", dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //Search via search button on the activity
        search_by_string.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("This is the text:", search_by_string.getQuery().toString());
            }
        });
    }
}

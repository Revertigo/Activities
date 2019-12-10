package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.activities.Util.CsvReader;
import com.google.firebase.auth.FirebaseAuth;

public class SearchOrPost extends AppCompatActivity {
    private Button buttonLogout;
    private FirebaseAuth auth;
    private Button closeAppFromSearchPost;
    private Button changeActivityToPostActivity;
    private Button changeActivityToSearchActivity;

    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);


        //Button to change to post activity
        changeActivityToPostActivity = findViewById(R.id.button8);
        changeActivityToPostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchOrPost.this, firstStepPostActivity.class);
                startActivity(intent);
            }
        });

        //Button to change to search activity
        changeActivityToSearchActivity = findViewById(R.id.button9);
        changeActivityToSearchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchOrPost.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        //close app button
        closeAppFromSearchPost = findViewById(R.id.button13);
        closeAppFromSearchPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);

            }
        });

        buttonLogout = findViewById(R.id.button7);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();
                Intent intent = new Intent(SearchOrPost.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //Read all relevant data from Realtime DB(cities and settlements, streets)
        CsvReader.readRecordsfromDB(CsvReader.CITIES_AND_SETTLEMENTS, "server/cities_settlements");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(SearchOrPost.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
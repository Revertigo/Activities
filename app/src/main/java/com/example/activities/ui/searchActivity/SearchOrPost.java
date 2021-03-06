package com.example.activities.ui.searchActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.activities.ui.main.MainActivity;
import com.example.activities.ui.postActivitiyJava.NamePostActivity;
import com.example.activities.R;
import com.example.activities.ui.main.SettingsActivity;
import com.example.activities.data.entities.user.User;
import com.google.firebase.auth.FirebaseAuth;

public class SearchOrPost extends AppCompatActivity {
    private Button buttonLogout;
    private Button closeAppFromSearchPost;
    private Button changeActivityToPostActivity;
    private Button changeActivityToSearchActivity;
    private Button myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);


        //Button to change to post activity
        changeActivityToPostActivity = findViewById(R.id.button8);
        changeActivityToPostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchOrPost.this, NamePostActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Button to change to search activity
        changeActivityToSearchActivity = findViewById(R.id.button9);
        changeActivityToSearchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchOrPost.this, SearchActivity.class);
                startActivity(intent);
                finish();
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
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SearchOrPost.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        myProfile = findViewById(R.id.myProfileSearch);
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = User.getCurrentUser().loadProfile(SearchOrPost.this);
                startActivity(intent);
                finish();
            }
        });
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
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
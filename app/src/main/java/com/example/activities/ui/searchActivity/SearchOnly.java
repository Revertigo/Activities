package com.example.activities.ui.searchActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.activities.ui.main.MainActivity;
import com.example.activities.R;
import com.example.activities.ui.main.SettingsActivity;
import com.example.activities.ui.profile.UserProfile;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class SearchOnly extends AppCompatActivity {
    private Button buttonLogout;
    private Button closeAppFromSearch;
    private Button changeActivityToSearchActivity;
    private Button myProfile;

    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_only);

        //Button to change to search activity
        changeActivityToSearchActivity = findViewById(R.id.button9);
        changeActivityToSearchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchOnly.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //close app button
        closeAppFromSearch = findViewById(R.id.button13);
        closeAppFromSearch.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(SearchOnly.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        myProfile = findViewById(R.id.myProfileSearch);
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchOnly.this, UserProfile.class);
                intent.putExtra("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
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
            Intent intent = new Intent(SearchOnly.this, SettingsActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
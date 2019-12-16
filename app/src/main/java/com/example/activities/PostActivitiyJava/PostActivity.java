package com.example.activities.PostActivitiyJava;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.activities.MainActivity;
import com.example.activities.R;
import com.example.activities.SearchActivity.SearchActivity;
import com.example.activities.SearchActivity.SearchOrPost;
import com.example.activities.Util.CsvReader;
import com.example.activities.data.rtdb.activity.Activity;
import com.example.activities.data.rtdb.activity.ShowActivities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PostActivity extends AppCompatActivity {
    private static final String activities = "activities/";
    private static DatabaseReference database_activity = null;

    private Button closeAppFromPost;
    private Button buttonLogout;
    private Button clickToPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        final Activity newPost=getIntent().getParcelableExtra("newPost");

        //close app button
        closeAppFromPost = findViewById(R.id.closeAppPostActivity);
        closeAppFromPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);

            }
        });

        //Setup back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Sort the data
        CsvReader.reorganize_data();



        //log out button
        buttonLogout = findViewById(R.id.logoutPostActivity);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(PostActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //send data to the database
        clickToPost = findViewById(R.id.btnClickToPost);
        clickToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set Date
                EditText theDate=findViewById(R.id.enterDatePlainText);
                DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
                try {
                    newPost.setDate(format.parse( theDate.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //set Format
                String format2 = "dd/mm/yyy";
                newPost.setTime(format2);
                //Write new id counter to the database
                while(!NamePostActivity.is_id_read);
                newPost.setId(Activity.getId_counter());//Update the real id of the user
                String [] tokens = NamePostActivity.id_counter_path.split("/");//[0] = resources, [1] = activity_id_counter
                NamePostActivity.database_ref_id_counter.child(tokens[1]).setValue(newPost.getId() + 1);
                NamePostActivity.is_id_read = false;

                //Write new activity to the database
                database_activity = FirebaseDatabase.getInstance().getReference(activities + "Activity_" + newPost.getId());
                database_activity.setValue(newPost);

                startActivity(new Intent(PostActivity.this, SearchActivity.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
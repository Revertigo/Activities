package com.example.activities;


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

import com.example.activities.Util.CsvReader;
import com.example.activities.data.rtdb.activity.Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity extends AppCompatActivity {
    private Button closeAppFromPost;
    private Button buttonLogout;
    FirebaseAuth auth;
    private Button clickToPost;
    private static DatabaseReference database_activity = null;
    private static DatabaseReference database_ref_id_counter = null;
    private static final String id_counter_path = "resources/activity_id_counter";
    static final String activities = "activities/";

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

        final String[] cities_settlments = CsvReader.cities.toArray(new String[CsvReader.cities.size()]);
        Spinner cities_settlmentsSpinner = findViewById(R.id.citySettlementSpinner);
        ArrayAdapter<String> adapterForcities_settlmentsSpinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cities_settlments);
        adapterForcities_settlmentsSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cities_settlmentsSpinner.setAdapter(adapterForcities_settlmentsSpinner);

        //string array for streetSpinner
        String[] streets = new String[]{
                "Street", "Dizingoff", "Hertzel", "Another"
        };
        //using streets string array
        Spinner streetsSpinner = findViewById(R.id.streetsSpinner);
        ArrayAdapter<String> adapterForstreetsSpinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, streets);
        adapterForstreetsSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        streetsSpinner.setAdapter(adapterForstreetsSpinner);


        //log out button
        buttonLogout = findViewById(R.id.logoutPostActivity);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();
                Intent intent = new Intent(PostActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //send data to the database
        clickToPost = findViewById(R.id.btnClickToPost);
        clickToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //First read from the database
                String [] tokens = id_counter_path.split("/");//[0] = resources, [1] = activity_id_counter

                database_ref_id_counter = FirebaseDatabase.getInstance().getReference(tokens[0]);//Get Database instance
                final TextView apartNum = findViewById(R.id.apartNumberPlainText);

                //City
                final Spinner activityCity = findViewById(R.id.citySettlementSpinner);

                //Street
                final Spinner activityStreet = findViewById(R.id.streetsSpinner);

                //Difficulty

                EditText theDate=findViewById(R.id.enterDatePlainText);
                String format = "Todo Format";

                newPost.completeDataInit(new Activity.Address(activityCity.getSelectedItem().toString(),
                                activityStreet.getSelectedItem().toString(),Integer.parseInt(apartNum.getText().toString())),
                        theDate.getText().toString(), format);


                //Write new id counter to the database
                while(!firstStepPostActivity.is_id_read);
                newPost.setId(Activity.getId_counter());//Update the real id of the user
                database_ref_id_counter.child(tokens[1]).setValue(newPost.getId() + 1);
                firstStepPostActivity.is_id_read = false;

                //Write new activity to the database
                database_activity = FirebaseDatabase.getInstance().getReference(activities + "Activity_" + newPost.getId());
                database_activity.setValue(newPost);
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
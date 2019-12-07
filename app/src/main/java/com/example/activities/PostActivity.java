package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activities.Util.CsvReader;
import com.example.activities.data.rtdb.activity.Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;


public class PostActivity extends AppCompatActivity {

    private Button closeAppFromPost;
    private Button buttonLogout;
    FirebaseAuth auth;
    private Button clickToPost;
    private static DatabaseReference myRef = null;
    static final String activities = "activities/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //close app button
        closeAppFromPost = findViewById(R.id.button14);
        closeAppFromPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);

            }
        });

        //Setup back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //spinner info slots
        String[] types = new String[]{
                "Type", "Sport", "Food", "Online gaming", "Art", "Learning", "Other"
        };

        //Sort the data
        CsvReader.reorganize_data();

        final String[] cities_settlments = CsvReader.cities.toArray(new String[CsvReader.cities.size()]);

        String[] streets = new String[]{
                "Street", "Dizingoff", "Hertzel", "Another"
        };

        String[] difficulty = new String[]{
                "Difficulty", "Beginner", "Advanced", "Professional"
        };

        Spinner spin1 = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> adapterForSpinner2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cities_settlments);
        adapterForSpinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapterForSpinner2);

        Spinner spin2 = (Spinner) findViewById(R.id.spinner4);
        ArrayAdapter<String> adapterForSpinner1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, types);
        adapterForSpinner1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adapterForSpinner1);


        Spinner spin3 = (Spinner) findViewById(R.id.spinner5);
        ArrayAdapter<String> adapterForSpinner3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, streets);
        adapterForSpinner3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(adapterForSpinner3);

        Spinner spin4 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapterForSpinner4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, difficulty);
        adapterForSpinner4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin4.setAdapter(adapterForSpinner4);

        //log out button
        buttonLogout = findViewById(R.id.button16);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();
                Intent intent = new Intent(PostActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //send data to database
        clickToPost = findViewById(R.id.btnClickToPost);
        clickToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TextView activityName, apartNum, desc;
                activityName = findViewById(R.id.editText2);
                desc = findViewById(R.id.EditTextDescribeApp);
                apartNum = findViewById(R.id.editText);

                //Type
                final Spinner activityType = findViewById(R.id.spinner4);
                //addapt Type

                //end adapt Type

                //City
                final Spinner activityCity = findViewById(R.id.spinner3);

                //Street
                final Spinner activityStreet = findViewById(R.id.spinner5);

                //Difficulty
                final Spinner activityDifficulty = findViewById(R.id.spinner2);

                myRef = FirebaseDatabase.getInstance().getReference(activities);

                Activity.Address addr = new Activity.Address(activityCity.getSelectedItem().toString(),
                        activityStreet.getSelectedItem().toString(), Integer.parseInt(apartNum.getText().toString()));
                boolean single_group = true;//TODO:Finish

                Date date = new Date();
                String format = "format";

                RadioGroup rg=findViewById(R.id.rgGender);
                final String gender = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                rg=findViewById(R.id.rgActivityFor);
                final String activityFor = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();

                Activity currentActivity = new Activity(activityName.getText().toString(), activityType.getSelectedItem().toString(),
                        addr, activityDifficulty.getSelectedItem().toString(), single_group, gender, desc.getText().toString(), date, format,activityFor);

                String currentPostData[] = currentActivity.getData();

                for (int i = 0; i < currentPostData.length; i++) {
                    myRef.child(Activity.getNames(i)).setValue(currentPostData[i]);
                }

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

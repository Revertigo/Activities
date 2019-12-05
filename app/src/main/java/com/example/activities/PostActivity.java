package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
        closeAppFromPost=findViewById(R.id.button14);
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
        String[] types = new String[] {
                "Type","Sport", "Food", "Fun","Another"
        };

        //Sort the data
        CsvReader.reorganize_data();

        String[] cities_settlments = CsvReader.cities.toArray(new String[CsvReader.cities.size()]);

        String[] streets = new String[] {
                "Street","Dizingoff", "Hertzel", "Another"
        };

        String[] difficulty = new String[] {
                "Difficulty","Begginer", "Advanced", "Proffessional"
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
        buttonLogout=findViewById(R.id.button16);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();
                Intent intent =new Intent(PostActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //send data to database
        clickToPost=findViewById(R.id.btnClickToPost);
        clickToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = FirebaseDatabase.getInstance().getReference(activities+ FirebaseAuth.getInstance().getCurrentUser().getUid());

                /*
                Activity currentActivity=new Activity(FirebaseAuth.getInstance().getCurrentUser().getUid(),string name,string type,Activity.Adress addr,String difficulty,Boolean single_group, Activity.Gender gender,String describption,Date date, String time);


                String currentPostData[]=currentActivity.getData();

                for(int i=0;i<2;i++) {
                    myRef.setValue(currentPostData[i]);
                }
                 */
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

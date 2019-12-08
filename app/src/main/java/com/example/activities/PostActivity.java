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
import com.example.activities.data.rtdb.activity.NewActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class PostActivity extends AppCompatActivity {

    private Button closeAppFromPost;
    private Button buttonLogout;
    FirebaseAuth auth;
    private Button clickToPost;
    private static DatabaseReference myRef = null;
    static final String activities = "activities/";
    private NewActivity newPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        newPost=getIntent().getParcelableExtra("newPost");

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


        //send data to database
        clickToPost = findViewById(R.id.btnClickToPost);
        clickToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TextView  apartNum;

                apartNum = findViewById(R.id.apartNumberPlainText);

                //City
                final Spinner activityCity = findViewById(R.id.citySettlementSpinner);

                //Street
                final Spinner activityStreet = findViewById(R.id.streetsSpinner);

                //Difficulty

                myRef = FirebaseDatabase.getInstance().getReference(activities);


                EditText theDate=findViewById(R.id.enterDatePlainText);
                String date =   theDate.getText().toString();
                String format = "Todo Format";
                

                 newPost.completeDataInit(activityCity.getSelectedItem().toString(), activityStreet.getSelectedItem().toString(),apartNum.getText().toString(),date, format);

                String currentPostData[] = newPost.getData();

                for (int i = 0; i < currentPostData.length; i++) {
                    myRef.child(NewActivity.getNames(i)).setValue(currentPostData[i]);
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

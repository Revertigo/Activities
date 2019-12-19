package com.example.activities.PostActivitiyJava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.activities.R;
import com.example.activities.Util.CsvReader;
import com.example.activities.data.rtdb.activity.Activity;

public class AdressPostActivity extends AppCompatActivity {
private Button nextToPostActivity;
private Activity newPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_post);
        newPost=getIntent().getParcelableExtra("newPost");
        nextToPostActivity=findViewById(R.id.nextToPostActivity);

        //Setup back button
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


        nextToPostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView apartNum = findViewById(R.id.apartNumberPlainText);
                String apartmentNumber=apartNum.getText().toString();
                //City
                final Spinner activityCity = findViewById(R.id.citySettlementSpinner);

                //Street

                final Spinner activityStreet = findViewById(R.id.streetsSpinner);
                if(apartmentNumber.isEmpty()){
                    apartNum.setError("Please enter apartment number");
                }else {
                    newPost.setAddr(new Activity.Address(activityCity.getSelectedItem().toString(),
                            activityStreet.getSelectedItem().toString(), Integer.parseInt(apartmentNumber)));

                    Intent intent = new Intent(AdressPostActivity.this, PostActivity.class);
                    intent.putExtra("newPost", newPost);
                    startActivity(intent);
                }
            }
        });
    }
}

package com.example.activities.ui.postActivitiyJava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.activities.R;
import com.example.activities.util.CsvReader;
import com.example.activities.data.entities.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdressPostActivity extends AppCompatActivity {
    private Button nextToPostActivity;
    private Activity newPost;
    //Class members for implementing streets view according to city
    private List<String> streets;
    private ArrayAdapter<String> adapterForstreetsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_post);
        newPost = getIntent().getParcelableExtra("newPost");
        nextToPostActivity = findViewById(R.id.nextToPostActivity);


        //Setup back button
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Sort the data
        CsvReader.reorganize_data(CsvReader.cities, "City/Settlement");
        final String[] cities_settlments = CsvReader.cities.toArray(new String[CsvReader.cities.size()]);

        Spinner cities_settlmentsSpinner = findViewById(R.id.citySettlementSpinner);

        final ArrayAdapter<String> adapterForcities_settlmentsSpinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cities_settlments);
        adapterForcities_settlmentsSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cities_settlmentsSpinner.setAdapter(adapterForcities_settlmentsSpinner);

        cities_settlmentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {
                String selecteditem = adapter.getItemAtPosition(i).toString();
                if (!selecteditem.equals("City/Settlement")) {
                    HashMap<Integer, String> streets_in_city = (HashMap<Integer, String>) CsvReader.all_streets.get(selecteditem.trim());

                    //Remove all existing strings before adding new
                    streets.removeIf(s -> !s.equals("Street"));
                    if (streets_in_city != null) {
                        for (String street : streets_in_city.values()) {
                            //We don't insert null or the name of the city/settlment
                            if (street != null && !street.trim().equals(selecteditem.trim())) {
                                streets.add(street);
                            }
                        }
                    }

                    CsvReader.reorganize_data(streets, "Street");
                    //notify the adapter to update the view
                    adapterForstreetsSpinner.notifyDataSetChanged();

                    TextView tv = findViewById(R.id.streetsTextView);
                    tv.setVisibility(View.VISIBLE);
                    findViewById(R.id.streetsSpinner).setVisibility(View.VISIBLE);//Set visability to spinner
                }
                else{  TextView tv = findViewById(R.id.streetsTextView);
                    tv.setVisibility(View.INVISIBLE);
                    findViewById(R.id.streetsSpinner).setVisibility(View.INVISIBLE);//Set visability to spinner}
                    //or this can be also right: selecteditem = level[i];
                }
                //or this can be also right: selecteditem = level[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        //using streets string array
        streets = new ArrayList<String>();
        Spinner streetsSpinner = findViewById(R.id.streetsSpinner);
        adapterForstreetsSpinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, streets);
        adapterForstreetsSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        streetsSpinner.setAdapter(adapterForstreetsSpinner);

        streetsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {

                String selecteditem = adapter.getItemAtPosition(i).toString();
                if (!selecteditem.equals("Street")) {
                    TextView tv = findViewById(R.id.apartNumberTextView);
                    tv.setVisibility(View.VISIBLE);
                    EditText et = findViewById(R.id.apartNumberPlainText);
                    et.setVisibility(View.VISIBLE);
                }
                else{    TextView tv = findViewById(R.id.apartNumberTextView);
                    tv.setVisibility(View.INVISIBLE);
                    EditText et = findViewById(R.id.apartNumberPlainText);
                    et.setVisibility(View.INVISIBLE);}
                //or this can be also right: selecteditem = level[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        nextToPostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView apartNum = findViewById(R.id.apartNumberPlainText);
                String apartmentNumber = apartNum.getText().toString();
                int apartNumber = 0;
                if (!apartmentNumber.isEmpty()) {
                    apartNumber = Integer.parseInt(apartmentNumber);
                }

                //City
                String city = "No relevant city";
                final Spinner activityCity = findViewById(R.id.citySettlementSpinner);
                if (!(activityCity.getSelectedItem().toString().equals("City/Settlement"))) {
                    city = activityCity.getSelectedItem().toString();
                }

                //Street

                final Spinner activityStreet = findViewById(R.id.streetsSpinner);

                String street = "Street are not relevant";
                try {
                    if (!activityStreet.getSelectedItem().toString().isEmpty()) {
                        street = activityStreet.getSelectedItem().toString();
                    }
                } catch (Exception e) {

                }

                newPost.setAddr(new Activity.Address(city, street, apartNumber));

                Intent intent = new Intent(AdressPostActivity.this, PostActivity.class);
                intent.putExtra("newPost", newPost);
                startActivity(intent);
                finish();

            }
        });
    }
}

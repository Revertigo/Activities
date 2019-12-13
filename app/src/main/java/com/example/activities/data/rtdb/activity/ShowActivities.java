package com.example.activities.data.rtdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.activities.R;

import java.util.ArrayList;

public class ShowActivities extends AppCompatActivity {
    private Activity currentActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_activities);

        ArrayList<Activity> theActivities;
        theActivities=getIntent().getParcelableArrayListExtra("activitiesArray");
    Activity a=theActivities.get(0);
    Activity b=theActivities.get(5);
        EditText activityName=findViewById(R.id.activityName);
        activityName.setText(a.getName());
        EditText activity2=findViewById(R.id.activityType);
        activity2.setText(b.getDescription());
        Log.wtf("we have ",theActivities.size()+" activities");
        /*
        Activity currentActivity= getIntent().getParcelableExtra("theActivity");
        EditText activityID=findViewById(R.id.activityID);
        activityID.setText(String.valueOf(currentActivity.getId()));
        EditText activityName=findViewById(R.id.activityName);
        activityName.setText(currentActivity.getName());
        EditText activityType=findViewById(R.id.activityType);
        EditText activityCity = findViewById(R.id.activityCity);
        EditText activityStreet = findViewById(R.id.activityStreet);
        EditText activityApartNum = findViewById(R.id.activityApartNum);
        EditText activityDifficulty = findViewById(R.id.activityDifficulty);
        EditText activityGroup = findViewById(R.id.activityGroup);
        EditText activityGender = findViewById(R.id.activityGender);
        EditText activityDescription = findViewById(R.id.activityDescription);
        EditText activityDate = findViewById(R.id.activityDate);
        EditText activityTime = findViewById(R.id.activityTime);
         */

    }
}

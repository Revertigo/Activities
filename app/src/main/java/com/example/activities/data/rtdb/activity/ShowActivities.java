package com.example.activities.data.rtdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.activities.R;

public class ShowActivities extends AppCompatActivity {
    private Activity currentActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_activities);


       String[] str=getIntent().getStringArrayExtra("str");

        EditText activityID=findViewById(R.id.activityID);
        EditText activityName=findViewById(R.id.activityName);
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

        activityID.setText(str[0]);
        activityName.setText(str[1]);
        activityType.setText(str[2]);
        activityCity.setText(str[3]);
        activityStreet.setText(str[4]);
        activityApartNum.setText(str[5]);
        activityDifficulty.setText(str[6]);
        activityGroup.setText(str[7]);
        activityGender.setText(str[8]);
        activityDescription.setText(str[9]);
        activityDate.setText(str[10]);
        activityTime.setText(str[11]);


    }
}

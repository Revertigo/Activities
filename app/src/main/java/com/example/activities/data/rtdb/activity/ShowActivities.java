package com.example.activities.data.rtdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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
        ListView activityListView=findViewById(R.id.listViewShowActivities);
        ArrayAdapter<Activity> adapter =new ArrayAdapter<Activity>(ShowActivities.this,android.R.layout.simple_list_item_1,theActivities);
        activityListView.setAdapter(adapter);

    }
}

package com.example.activities.data.rtdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.activities.R;

import java.util.ArrayList;

public class JoinActivity extends AppCompatActivity {
    private Button backToShowActivities;
    private Button joinThisActitivty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

      final  ArrayList<Activity> showActivitiesAgain=getIntent().getParcelableArrayListExtra("showActivitiesAgain");

        ArrayList<Activity> activityArray= getIntent().getParcelableArrayListExtra("joinActivity");


        backToShowActivities=findViewById(R.id.backToShowActivities);
        joinThisActitivty=findViewById(R.id.joinThisActivity);

        ListView lv=findViewById(R.id.joinActivityListView);
        ArrayAdapter<Activity> adapter =new ArrayAdapter<Activity>(JoinActivity.this,android.R.layout.simple_list_item_1,activityArray);
        lv.setAdapter(adapter);

        backToShowActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(JoinActivity.this,ShowActivities.class);
            intent.putExtra("activitiesArray",showActivitiesAgain);
            startActivity(intent);
            }
        });

        joinThisActitivty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





    }
}

package com.example.activities.data.rtdb.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.activities.R;
import com.example.activities.SearchActivity.SearchActivity;
import com.example.activities.SearchActivity.SearchOrPost;

import java.util.ArrayList;

public class ShowActivities extends AppCompatActivity {
    private Button searchOrPost;
    private Button searchMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_activities);

        searchOrPost=findViewById(R.id.backToMainActivity);
        searchMenu=findViewById(R.id.backToSearchActivity);
        searchOrPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowActivities.this, SearchOrPost.class);
                startActivity(intent);
            }
        });

        searchMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowActivities.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<Activity> theActivities;
        theActivities=getIntent().getParcelableArrayListExtra("activitiesArray");
        ListView activityListView=findViewById(R.id.listViewShowActivities);
        ArrayAdapter<Activity> adapter =new ArrayAdapter<Activity>(ShowActivities.this,android.R.layout.simple_list_item_1,theActivities);
        activityListView.setAdapter(adapter);
        //Todo need to fix the 0 1 10 id sorting by the adapter or listvew
        activityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ShowActivities.this,JoinActivity.class);
                ArrayList<Activity> joinActivity=new ArrayList<Activity>();
                joinActivity.add(theActivities.get(position));
                intent.putExtra("joinActivity",joinActivity);
                startActivity(intent);
            }
        });

    }
}

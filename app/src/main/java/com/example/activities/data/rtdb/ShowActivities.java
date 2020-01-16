package com.example.activities.data.rtdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.activities.data.entities.Activity;
import com.example.activities.ui.postActivitiyJava.PostActivity;
import com.example.activities.R;
import com.example.activities.ui.searchActivity.SearchActivity;
import com.example.activities.data.entities.user.User;
import com.example.activities.util.ActivityAdapter;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShowActivities extends AppCompatActivity {
    private Button backToMainMenu;
    private Button backToProfile;
    private Button clickToSearchAgain;
    public static boolean activityFilter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_activities);
        clickToSearchAgain = findViewById(R.id.clickToSearchAgain);
        clickToSearchAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowActivities.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backToProfile = findViewById(R.id.backToProfile);
        backToMainMenu = findViewById(R.id.backToSearchOrPost);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = User.getCurrentUser().loadMainMenu(ShowActivities.this);
                startActivity(intent);
                finish();
            }
        });

        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = User.getCurrentUser().loadProfile(ShowActivities.this);
                startActivity(intent);
                finish();
            }
        });

        final ArrayList<Activity> theActivities;
        theActivities = getIntent().getParcelableArrayListExtra("activitiesArray");
        if (activityFilter) {
            for (int i = 0; i < theActivities.size(); i++) {
                String[] date = {theActivities.get(i).getDate().getDay(), theActivities.get(i).getDate().getMonth(), theActivities.get(i).getDate().getYear()};
                String[] time = theActivities.get(i).getTime().split(":");
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date dateobj = new Date();
                String[] dateAndTime = df.format(dateobj).split(" ");
                String[] dateStr = dateAndTime[0].split("/");
                String[] timeStr = dateAndTime[1].split(":");
                //check if year passed
                if (Integer.parseInt(date[2]) < Integer.parseInt(dateStr[2])) {
                    theActivities.remove(i);
                } else {//year not passed so check month
                    if (Integer.parseInt(date[2]) == Integer.parseInt(dateStr[2])) {//if year are equals check month
                        if ((Integer.parseInt(date[1]) < Integer.parseInt(dateStr[1]))) {//if month passed remove activity
                            theActivities.remove(i);
                        } else {//month not passed so check days
                            if (Integer.parseInt(date[1]) == Integer.parseInt(dateStr[1])) {//if same month check days
                                if (Integer.parseInt(date[0]) < Integer.parseInt(dateStr[0])) {//if day passed so remove
                                    theActivities.remove(i);

                                } else {//day is fine check the time.
                                    if (Integer.parseInt(date[0]) == Integer.parseInt(dateStr[0])) {//if we are in same day check time else all is fine
                                        if (Integer.parseInt(time[0]) < Integer.parseInt(timeStr[0])) {//if hour is passed so remove else check min
                                            theActivities.remove(i);
                                        } else {
                                            if (Integer.parseInt(time[0]) == Integer.parseInt(timeStr[0])) {//if we are in same hour check min
                                                if (Integer.parseInt(time[1]) < Integer.parseInt(timeStr[1])) {//if mid passed remove
                                                    theActivities.remove(i);
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        ListView activityListView = findViewById(R.id.listViewShowActivities);
        ActivityAdapter adapter = new ActivityAdapter(theActivities, ShowActivities.this);
        activityListView.setAdapter(adapter);

        //Todo need to fix the 0 1 10 id sorting by the adapter or listvew
        activityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShowActivities.this, ActivityInfo.class);
                ArrayList<Activity> joinActivity = new ArrayList<Activity>();
                joinActivity.add(theActivities.get(position));
                intent.putExtra("joinActivity", joinActivity);
                intent.putExtra("showActivitiesAgain", theActivities);
                startActivity(intent);
                finish();
            }
        });

    }
}

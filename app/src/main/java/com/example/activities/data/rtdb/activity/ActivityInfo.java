package com.example.activities.data.rtdb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.activities.MainActivity;
import com.example.activities.PostActivitiyJava.PostActivity;
import com.example.activities.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityInfo extends AppCompatActivity {
    private Button backToShowActivities;
    private Button joinThisActitivty;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Button leaveThisActivity;
    private Button showRegisteredUsers;
    private static final String users_in_activities = "users_in_activities";


    public static String getUsers_in_activities() {
        return users_in_activities;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(users_in_activities);
        final ArrayList<Activity> showActivitiesAgain = getIntent().getParcelableArrayListExtra("showActivitiesAgain");

        final ArrayList<Activity> currentActivity = getIntent().getParcelableArrayListExtra("joinActivity");


        backToShowActivities = findViewById(R.id.backToShowActivities);
        joinThisActitivty = findViewById(R.id.joinThisActivity);

        ListView lv = findViewById(R.id.joinActivityListView);
        ArrayAdapter<Activity> adapter = new ArrayAdapter<Activity>(ActivityInfo.this, android.R.layout.simple_list_item_1, currentActivity);
        lv.setAdapter(adapter);

        backToShowActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityInfo.this, ShowActivities.class);
                intent.putExtra("activitiesArray", showActivitiesAgain);
                startActivity(intent);
            }
        });

        joinThisActitivty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        myRef.child("Activity_" + currentActivity.get(0).getId())
                                .child(FirebaseAuth.getInstance().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                        DatabaseReference history_ref = FirebaseDatabase.getInstance().getReference("users_history_joined");
                        history_ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Activity_"+currentActivity.get(0).getId()).setValue(currentActivity.get(0));
                        Intent intent = new Intent(ActivityInfo.this, MainActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        showRegisteredUsers = findViewById(R.id.showRegisteredUsers);
        showRegisteredUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference showRegisteredUsersRef = FirebaseDatabase.getInstance().getReference("users_in_activities/Activity_" + currentActivity.get(0).getId());
                showRegisteredUsersRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<String> registeredUsers = new ArrayList<String>();
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityInfo.this, android.R.layout.simple_list_item_1, registeredUsers);
                        lv.setAdapter(adapter);
                        lv.clearAnimation();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            registeredUsers.add(ds.getValue(String.class));
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        leaveThisActivity = findViewById(R.id.leaveThisActivity);
        leaveThisActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}

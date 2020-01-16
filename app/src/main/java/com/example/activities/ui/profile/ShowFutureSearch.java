package com.example.activities.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.activities.data.entities.Activity;
import com.example.activities.data.rtdb.ActivityInfo;
import com.example.activities.data.rtdb.ShowActivities;
import com.example.activities.ui.postActivitiyJava.PostActivity;
import com.example.activities.R;
import com.example.activities.util.UpdateFirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ShowFutureSearch extends AppCompatActivity {
    private Button backToProfile;
    private Button showFutureJoin;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_search);

        backToProfile = findViewById(R.id.backToProfile);
        showFutureJoin = findViewById(R.id.showJoinedFuture);
        database = FirebaseDatabase.getInstance();
        UpdateFirebaseDatabase.fixDatabaseHistoryJoined();//update future and history Trees on database
        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowFutureSearch.this, UserProfile.class);
                startActivity(i);
                finish();
            }
        });

        showFutureJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowActivities.activityFilter = true;
                DatabaseReference joinedRef = database.getReference("users_future_joined");
                joinedRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final ArrayList<String> joinedActivitiesArray = new ArrayList<String>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //if my uid is exist here
                            if (ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {

                                joinedActivitiesArray.add(ds.getKey());
                            }
                        }
                        if (joinedActivitiesArray.size() > 0) {
                            final ArrayList<Activity> activitiesArray = new ArrayList<Activity>();
                            final Intent i = new Intent(ShowFutureSearch.this, ShowActivities.class);
                            DatabaseReference activitiesRef = database.getReference(PostActivity.getActivities());
                            activitiesRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (int i = 0; i < joinedActivitiesArray.size(); i++) {
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            if (ds.getKey().equals(joinedActivitiesArray.get(i))) {
                                                activitiesArray.add(ds.getValue(Activity.class));
                                                break;
                                            }
                                        }
                                    }
                                    i.putExtra("activitiesArray", activitiesArray);
                                    startActivity(i);
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        } else {
                            Toast.makeText(ShowFutureSearch.this, "Your not joined any Activity.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}
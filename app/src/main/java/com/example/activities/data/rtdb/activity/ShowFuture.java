package com.example.activities.data.rtdb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.activities.PostActivitiyJava.PostActivity;
import com.example.activities.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ShowFuture extends AppCompatActivity {
    private Button backToProfile;
    private Button showJoin;
    private Button showPosts;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_post);

        backToProfile = findViewById(R.id.backToProfile);
        showJoin = findViewById(R.id.showJoinedFuture);
        showPosts = findViewById(R.id.showPostedFuture);
        database = FirebaseDatabase.getInstance();

        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowFuture.this, UserProfile.class);
                i.putExtra("email",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                startActivity(i);
                finish();
            }
        });

        showPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowActivities.activityFilter = true;
                DatabaseReference myActivitiesRef = database.getReference(PostActivity.getActivities());
                final ArrayList<Activity> myPostedActivities = new ArrayList<Activity>();
                myActivitiesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Activity ac = ds.getValue(Activity.class);
                            if (ac.getpostedUser().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                myPostedActivities.add(ds.getValue(Activity.class));
                            }
                        }
                        if (myPostedActivities.size() > 0) {
                            Intent i = new Intent(ShowFuture.this, ShowActivities.class);
                            i.putExtra("activitiesArray", myPostedActivities);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(ShowFuture.this, "You not posted any activity.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        showJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowActivities.activityFilter = true;
                DatabaseReference joinedRef = database.getReference(ActivityInfo.getUsers_in_activities());
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
                            final Intent i = new Intent(ShowFuture.this, ShowActivities.class);
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
                            Toast.makeText(ShowFuture.this, "Your not joined any Activity.", Toast.LENGTH_LONG).show();
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